/*
 * Copyright (c) 2022 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.coregraphics;

import com.sun.jna.Pointer;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;


/**
 * CGLibraryTest.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2022-09-11 nsano initial version <br>
 */
public class CGLibraryTest {

    @Test
    void tes1() throws Exception {
        CGRect r = new CGRect(10, 20, 200, 100);
System.err.println("rect: " + r);
    }

    @Test
    @Disabled("TODO crash at CGRect")
    void tes2() throws Exception {
        CGImage cgImage = new CGImage(CGLibraryTest.class.getResourceAsStream("/sample1.heic"));
        CGRect r = new CGRect(10, 20, 200, 100);
        Pointer cgImage2 = CGLibrary.library.CGImageCreateWithImageInRect(cgImage.pointer(), r);
System.err.println("CGImageCreateWithImageInRect: " + cgImage2);
    }
}
