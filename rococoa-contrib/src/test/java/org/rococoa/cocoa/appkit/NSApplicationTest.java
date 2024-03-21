/*
 * Copyright (c) 2022 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.appkit;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.concurrent.CountDownLatch;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.rococoa.ID;
import org.rococoa.ObjCObject;
import org.rococoa.Rococoa;
import org.rococoa.cocoa.foundation.NSData;
import org.rococoa.cocoa.foundation.NSPasteboard;
import org.rococoa.cocoa.foundation.NSRect;
import vavi.util.Debug;
import vavi.util.StringUtil;

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
    @DisplayName("test dialog TODO wip")
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
        // never stop, u need to close the window by yourself
        CountDownLatch cdl = new CountDownLatch(1);
        cdl.await();
    }

    @Test
    @DisplayName("test clipboard")
    void test2() throws Exception {
        NSPasteboard pasteboard = NSPasteboard.generalPasteboard();
        String string = pasteboard.stringForType(NSPasteboard.StringPboardType);
System.err.println("string: " + string);
        assertEquals("vavi", string);
        NSData data = pasteboard.dataForType(NSPasteboard.StringPboardType);
System.err.println("dataForType:\n" + StringUtil.getDump(data.getBytes()));
    }

    @Test
    @DisplayName("test display density")
    void test3() throws Exception {
        NSScreen screen = NSScreen.mainScreen();
        NSRect rect = new NSRect(1000, 1000, 1000, 1000);
Debug.println("rect: " + rect);
        NSRect converted = screen.convertRectFromBacking(rect);
Debug.println("converted: " + converted);
Debug.printf("converted: %d, %d - %d, %d", converted.origin.x.intValue(), converted.origin.y.intValue(), converted.size.width.intValue(), converted.size.height.intValue());
    }

    @Test
    @DisplayName("test application window")
    void test4() throws Exception {
        System.out.println("frontmost ----");
        NSRunningApplication fa = NSWorkspace.sharedWorkspace().frontmostApplication();
        System.out.println(" " + fa.localizedName() + " (" + fa.bundleIdentifier() + ", " + fa.executableURL() + ")");
        System.out.println("running ----");
        NSWorkspace.sharedWorkspace().runningApplications().stream()
                .map(o -> Rococoa.cast(o, NSRunningApplication.class))
                .forEach(a -> System.out.println(" " + a.localizedName() + " (" + a.bundleIdentifier() + ", " + a.bundleURL() + ")"));
    }
}
