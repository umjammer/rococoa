/*
 * Copyright (c) 2022 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.coreimage;

import com.sun.jna.Pointer;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.rococoa.cocoa.CGFloat;
import org.rococoa.cocoa.coregraphics.CGImage;
import org.rococoa.cocoa.coregraphics.CGLibrary;
import org.rococoa.cocoa.coregraphics.CGRect;
import org.rococoa.cocoa.foundation.NSArray;
import org.rococoa.cocoa.foundation.NSNumber;


/**
 * TestCoreImage.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2022-09-11 nsano initial version <br>
 */
class TestCoreImage {

    /** @see "https://stackoverflow.com/a/23877167" */
    @Test
    @Disabled("TODO crash at CIImage#extent()")
    void test1() throws Exception {

        Pointer/*CGColorRef*/ colorRef = CGLibrary.library.CGColorCreateGenericRGB(
                new CGFloat(255.0),
                new CGFloat(0),
                new CGFloat(255.0),
                new CGFloat(255.0));
        String colorString = CIColor.CLASS.colorWithCGColor(colorRef).stringRepresentation();
        CIColor coreColor = CIColor.CLASS.colorWithString(colorString);

        CIContext context = CIContext.CLASS.contextWithOptions(null);;

        CGImage cgImage = new CGImage(TestCoreImage.class.getResourceAsStream("/sample1.heic"));
System.err.println("cgImage: " + cgImage.getWidth() + "x" + cgImage.getHeight());

        //  Convert UIImage to CIImage
        CIImage ciImage = cgImage.toCIImage();

        //  Set values for CIColorMonochrome Filter
        CIFilter filter = CIFilter.CLASS.filterWithName("CIColorMonochrome");
        NSArray a = filter.inputKeys();
for (int i = 0; i < a.count(); i++) {
 System.err.printf("[%d] %s%n", i, a.objectAtIndex(i));
}
        filter.setValue_forKey(ciImage, "inputImage");
        filter.setValue_forKey(NSNumber.numberWithInt(1), "inputIntensity");
        filter.setValue_forKey(coreColor, "inputColor");

        CIImage result = filter.outputImage();
System.err.println("result: " + result);

        CGRect extent = result.extent();
System.err.println("extent: " + extent);

        Pointer/*CGImageRef*/ cgImage2 = context.createCGImage_fromRect(result, extent);
System.err.println("hereE: " + cgImage2);
    }
}
