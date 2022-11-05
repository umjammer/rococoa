/*
 * Copyright (c) 2022 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.appkit;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.rococoa.ID;
import org.rococoa.ObjCObject;
import org.rococoa.ObjCObjectByReference;
import org.rococoa.Rococoa;
import vavi.util.Debug;
import vavi.util.StringUtil;
import org.rococoa.cocoa.foundation.NSData;
import org.rococoa.cocoa.foundation.NSPasteboard;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * NSApplicationTest.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2022-09-14 nsano initial version <br>
 */
class NSApplicationTest {

    @BeforeEach
    void setup() {
        Toolkit kit = Toolkit.getDefaultToolkit();
        Clipboard clip = kit.getSystemClipboard();
        StringSelection ss = new StringSelection("vavi");
        clip.setContents(ss, ss);
    }

    @Test
    @DisplayName("TODO wip")
    @EnabledIfSystemProperty(named = "vavi.test", matches = "ide")
    void test1() throws Exception {
        NSApplication.ServicesProviderCallback servicesProvider = (pboard, userData, error) -> {
            NSPasteboard pasteboard = Rococoa.wrap(pboard, NSPasteboard.class);
System.err.println(pasteboard.stringForType("NSPasteboardTypeString"));
        };
        ObjCObject proxy = Rococoa.proxy(servicesProvider);
        NSApplication application = NSApplication.sharedApplication();
        ID proxyId = proxy.id();
        application.setServicesProvider(proxyId);
Debug.println(proxyId);

        Toolkit kit = Toolkit.getDefaultToolkit();
        Clipboard clip = kit.getSystemClipboard();
        StringSelection ss = new StringSelection("umjammer");
        clip.setContents(ss, ss);

        ID id = application.validRequestorForSendType_returnType("NSPasteboardTypeString", "NSPasteboardTypeString");
Debug.println(id);
        while (true) Thread.yield();
    }

    @Test
    void test2() throws Exception {
        NSPasteboard pasteboard = NSPasteboard.generalPasteboard();
        String string = pasteboard.stringForType(NSPasteboard.StringPboardType);
System.err.println("string: " + string);
        assertEquals("vavi", string);
        NSData data = pasteboard.dataForType(NSPasteboard.StringPboardType);
System.err.println("dataForType:\n" + StringUtil.getDump(data.getBytes()));
    }
}
