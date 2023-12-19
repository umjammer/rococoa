/*
 * Copyright (c) 2022 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.coregraphics;

import com.sun.jna.Pointer;
import org.junit.jupiter.api.Test;
import org.rococoa.cocoa.coreimage.CIImage;
import org.rococoa.cocoa.foundation.FoundationKitFunctions;
import vavi.util.Debug;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.rococoa.cocoa.coregraphics.CoreGraphicsLibrary.library;


/**
 * CoreGraphicsLibraryTest.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2022-09-11 nsano initial version <br>
 */
public class CoreGraphicsLibraryTest {

    @Test
    void tes1() throws Exception {
        CGRect r = new CGRect(10, 20, 200, 100);
Debug.println("rect: " + r);
        assertEquals(10, r.getPointer().getDouble(0));
        assertEquals(20, r.getPointer().getDouble(8));
        assertEquals(200, r.getPointer().getDouble(16));
        assertEquals(100, r.getPointer().getDouble(24));
    }

    @Test
    void tes12() throws Exception {
        CGPoint p = new CGPoint(10, 20);
Debug.println("point: " + p);
        assertEquals(10, p.getPointer().getDouble(0));
        assertEquals(20, p.getPointer().getDouble(8));
    }

    @Test
    void tes13() throws Exception {
        CGSize s = new CGSize(200, 100);
Debug.println("size: " + s);
        assertEquals(200, s.getPointer().getDouble(0));
        assertEquals(100, s.getPointer().getDouble(8));
    }

    @Test
    void tes2() throws Exception {
        CGImage cgImage = new CGImage(CoreGraphicsLibraryTest.class.getResourceAsStream("/sample1.heic"));
Debug.println("cgImage: " + cgImage.getWidth() + "x" + cgImage.getHeight());
        CGRect r = new CGRect(10, 20, 200, 100);
Debug.println("rect: " + r);

        CIImage ciImage = CIImage.CLASS.imageWithCGImage(cgImage.pointer());
Debug.println("ciImage: " + ciImage);

        Pointer cgImage2 = library.CGImageCreateWithImageInRect(cgImage.pointer(), r);
Debug.println("CGImageCreateWithImageInRect: " + cgImage2);
        int w = library.CGImageGetWidth(cgImage2);
        int h = library.CGImageGetHeight(cgImage2);
Debug.println("w: " + w + ", h: " + h);
        assertEquals(200, w);
        assertEquals(100, h);
    }

    /** @see "https://stackoverflow.com/a/10745616" */
    @Test
    void test3() throws Exception {
        Pointer /* CGEventSourceRef */ src = library.CGEventSourceCreate(library.kCGEventSourceStateHIDSystemState);

        Pointer /* CGEventRef */ cmdd = library.CGEventCreateKeyboardEvent(src, (char) 0x38, true);
        Pointer /* CGEventRef */ cmdu = library.CGEventCreateKeyboardEvent(src, (char) 0x38, false);
        Pointer /* CGEventRef */ spcd = library.CGEventCreateKeyboardEvent(src, (char) 0x31, true);
        Pointer /* CGEventRef */ spcu = library.CGEventCreateKeyboardEvent(src, (char) 0x31, false);

        library.CGEventSetFlags(spcd, library.kCGEventFlagMaskCommand);
        library.CGEventSetFlags(spcu, library.kCGEventFlagMaskCommand);

        int /* CGEventTapLocation */ loc = library.kCGHIDEventTap; // kCGSessionEventTap also works
        library.CGEventPost(loc, cmdd);
        library.CGEventPost(loc, spcd);
        library.CGEventPost(loc, spcu);
        library.CGEventPost(loc, cmdu);

        FoundationKitFunctions.library.CFRelease(cmdd);
        FoundationKitFunctions.library.CFRelease(cmdu);
        FoundationKitFunctions.library.CFRelease(spcd);
        FoundationKitFunctions.library.CFRelease(spcu);
        FoundationKitFunctions.library.CFRelease(src);
    }
}
