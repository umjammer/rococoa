/*
 * Copyright (c) 2023 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.foundation;

import org.junit.jupiter.api.Test;
import org.rococoa.cocoa.CGFloat;
import org.rococoa.cocoa.coregraphics.CGRect;
import vavi.util.Debug;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.rococoa.cocoa.foundation.FoundationKitFunctions.NSMakeRect;
import static org.rococoa.cocoa.foundation.FoundationKitFunctions.NSRectFromCGRect;
import static org.rococoa.cocoa.foundation.FoundationKitFunctions.NSRectToCGRect;


class FoundationTest {

    @Test
    void test1() throws Exception {
        NSRect nsRect = NSMakeRect(new CGFloat(100), new CGFloat(200), new CGFloat(300),new CGFloat(400));
Debug.println(nsRect);
        CGRect cgRect = NSRectToCGRect(nsRect);
Debug.println(cgRect);
        assertEquals(100, cgRect.getPointer().getDouble(0));
        assertEquals(200, cgRect.getPointer().getDouble(8));
        assertEquals(300, cgRect.getPointer().getDouble(16));
        assertEquals(400, cgRect.getPointer().getDouble(24));
        NSRect nsRect2 = NSRectFromCGRect(cgRect);
Debug.println(nsRect2);
        assertEquals(100, nsRect2.getPointer().getDouble(0));
        assertEquals(200, nsRect2.getPointer().getDouble(8));
        assertEquals(300, nsRect2.getPointer().getDouble(16));
        assertEquals(400, nsRect2.getPointer().getDouble(24));
    }
}