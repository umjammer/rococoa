/*
 * Copyright (c) 2023 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.foundation;

import org.junit.jupiter.api.Test;

import org.rococoa.Foundation;
import org.rococoa.ObjCClass;
import org.rococoa.Rococoa;
import vavi.util.Debug;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * FoundationTest.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2023-10-20 nsano initial version <br>
 */
public class FoundationTest {

    @Test public void testFactory() {
        NSNumber._Class nsNumberClass = Rococoa.createClass("NSNumber",  NSNumber._Class.class);
        assertEquals(nsNumberClass.id(), Foundation.getClass("NSNumber"));
    }

    @Test
    void tes11() throws Exception {
        NSRect r = new NSRect(10, 20, 200, 100);
Debug.println("rect: " + r);
        assertEquals(10, r.getPointer().getDouble(0));
        assertEquals(20, r.getPointer().getDouble(8));
        assertEquals(200, r.getPointer().getDouble(16));
        assertEquals(100, r.getPointer().getDouble(24));
    }

    @Test
    void tes12() throws Exception {
        NSPoint p = new NSPoint(10, 20);
Debug.println("point: " + p);
        assertEquals(10, p.getPointer().getDouble(0));
        assertEquals(20, p.getPointer().getDouble(8));
    }

    @Test
    void tes13() throws Exception {
        NSSize s = new NSSize(200, 100);
Debug.println("size: " + s);
        assertEquals(200, s.getPointer().getDouble(0));
        assertEquals(100, s.getPointer().getDouble(8));
    }
}
