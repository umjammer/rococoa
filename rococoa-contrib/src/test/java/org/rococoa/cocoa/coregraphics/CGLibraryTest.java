/*
 * Copyright (c) 2022 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.coregraphics;

import com.sun.jna.Pointer;
import org.junit.jupiter.api.Test;
import org.rococoa.cocoa.coreimage.CIImage;
import vavi.util.Debug;


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
Debug.println("rect: " + r);
    }

    @Test
    void tes2() throws Exception {
        CGImage cgImage = new CGImage(CGLibraryTest.class.getResourceAsStream("/sample1.heic"));
Debug.println("cgImage: " + cgImage.getWidth() + "x" + cgImage.getHeight());
        CGRect r = new CGRect(10, 20, 200, 100);
Debug.println("rect: " + r);

        CIImage ciImage = CIImage.CLASS.imageWithCGImage(cgImage.pointer());
Debug.println("ciImage: " + ciImage);

        Pointer cgImage2 = CGLibrary.library.CGImageCreateWithImageInRect(cgImage.pointer(), r);
Debug.println("CGImageCreateWithImageInRect: " + cgImage2);
    }
}
