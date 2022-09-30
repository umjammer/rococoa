/*
 * Copyright (c) 2022 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.appkit;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import vavi.util.StringUtil;
import org.rococoa.cocoa.foundation.NSData;
import org.rococoa.cocoa.foundation.NSPasteboard;


/**
 * NSApplicationTest.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2022-09-14 nsano initial version <br>
 */
class NSApplicationTest {
    @Test
    @Disabled("TODO wip")
    void test1() throws Exception {
        NSApplication.sharedApplication().setServicesProvider((pboard, userData, error) -> {
            System.err.println(pboard.stringForType("NSPasteboardTypeString"));
        });
    }

    @Test
    void test2() throws Exception {
        NSPasteboard pasteboard = NSPasteboard.generalPasteboard();
        String typeString = pasteboard.stringForType(NSPasteboard.StringPboardType);
        System.err.println("typeString: " + typeString);
        NSData data = pasteboard.dataForType(NSPasteboard.StringPboardType);
        System.err.println("dataForType:\n" + StringUtil.getDump(data.getBytes()));
    }
}
