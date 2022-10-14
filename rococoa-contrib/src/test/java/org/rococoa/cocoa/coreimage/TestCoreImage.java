/*
 * Copyright (c) 2022 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.coreimage;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.sun.jna.Pointer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.rococoa.cocoa.CGFloat;
import org.rococoa.cocoa.appkit.NSImage;
import org.rococoa.cocoa.coregraphics.CGImage;
import org.rococoa.cocoa.coregraphics.CGLibrary;
import org.rococoa.cocoa.coregraphics.CGRect;
import org.rococoa.cocoa.foundation.NSArray;
import org.rococoa.cocoa.foundation.NSNumber;
import org.rococoa.cocoa.foundation.NSObject;
import vavi.util.Debug;


/**
 * TestCoreImage.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2022-09-11 nsano initial version <br>
 */
class TestCoreImage {

    /** @see "https://stackoverflow.com/a/23877167" */
    @Test
    @EnabledIfSystemProperty(named = "vavi.test", matches = "ide")
    void test1() throws Exception {

        Pointer/*CGColorRef*/ colorRef = CGLibrary.library.CGColorCreateGenericRGB(
                new CGFloat(255.0),
                new CGFloat(0),
                new CGFloat(255.0),
                new CGFloat(255.0));
        String colorString = CIColor.CLASS.colorWithCGColor(colorRef).stringRepresentation();
        CIColor coreColor = CIColor.CLASS.colorWithString(colorString);

        CIContext context = CIContext.CLASS.contextWithOptions(null);

        CGImage cgImage = new CGImage(TestCoreImage.class.getResourceAsStream("/sample1.heic"));
Debug.println("cgImage: " + cgImage.getWidth() + "x" + cgImage.getHeight());

        //  Convert UIImage to CIImage
        CIImage ciImage = cgImage.toCIImage();
Debug.println("ciImage: " + ciImage);

        //  Set values for CIColorMonochrome Filter
        CIFilter filter = CIFilter.CLASS.filterWithName("CIColorMonochrome");
        NSArray a = filter.inputKeys();
        for (int i = 0; i < a.count(); i++) {
Debug.printf("[%d] %s%n", i, a.objectAtIndex(i));
        }
        filter.setValue_forKey(ciImage, "inputImage");
        filter.setValue_forKey(NSNumber.numberWithInt(1), "inputIntensity");
        filter.setValue_forKey(coreColor, "inputColor");

        CIImage result = filter.outputImage();
Debug.println("result: " + result);

        CGRect extent = result.extent();
Debug.println("extent: " + extent);

        Pointer/*CGImageRef*/ cgImage2 = context.createCGImage_fromRect(result, extent);
Debug.println("hereE: " + cgImage2);
        NSImage nsImage = NSImage.initWithCGImageSize(cgImage2, extent.size.toNSSize());
Debug.println("nsImage: " + nsImage);

        //
        CGImage cgImageX = new CGImage(cgImage2);
Debug.println("cgImageX: " + cgImageX.getWidth() + ", " + cgImageX.getHeight());

//        BufferedImage image = nsImage.toBufferedImage();
        BufferedImage image = cgImageX.toBufferedImage();

        show(image);
    }

    void show(BufferedImage image) {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel() {
            public void paintComponent(Graphics g) {
                g.drawImage(image, 0, 0, this);
            }
        };
        panel.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
        frame.setContentPane(panel);
        frame.setTitle("CoreImage");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        while (true) Thread.yield();
    }

    @Test
    @DisplayName("CIImage -> BufferedImage")
    @EnabledIfSystemProperty(named = "vavi.test", matches = "ide")
    void test2() throws Exception {
        CIImage ciImage = CIImage.newInstance(TestCoreImage.class.getResourceAsStream("/sample1.heic"));
Debug.println("ciImage: " + ciImage);
        show(ciImage.toBufferedImage());
    }

    @Test
    @DisplayName("BufferedImage -> CIImage")
    @EnabledIfSystemProperty(named = "vavi.test", matches = "ide")
    void test3() throws Exception {
        BufferedImage bi = ImageIO.read(TestCoreImage.class.getResourceAsStream("/test.jpg"));
Debug.println("bi: " + bi);
        CIImage ciImage = CIImage.newInstance(bi);
Debug.println("ciImage: " + ciImage);
        show(ciImage.toBufferedImage());
    }

    @Test
    @EnabledIfSystemProperty(named = "vavi.test", matches = "ide")
    void test4() throws Exception {
        BufferedImage image = ImageIO.read(TestCoreImage.class.getResourceAsStream("/test.jpg"));

long t = System.currentTimeMillis();
        Map<String, NSObject> options = new HashMap<>();
        options.put("inputColor", CIColor.newInstance(0, 255, 0, 255));
        options.put("inputIntensity", NSNumber.numberWithInt(1));

        BufferedImage filteredImage = new CIFilterOp("CIColorMonochrome", options).filter(image, null);
Debug.println((System.currentTimeMillis() - t) + " ms");

        show(filteredImage);
    }

    @Test
    @EnabledIfSystemProperty(named = "vavi.test", matches = "ide")
    void test5() throws Exception {
        BufferedImage image = ImageIO.read(TestCoreImage.class.getResourceAsStream("/test.jpg"));

long t = System.currentTimeMillis();
        Map<String, NSObject> options = new HashMap<>();
        options.put("inputIntensity", NSNumber.of(2.0));
        options.put("inputRadius", NSNumber.of(1.0));

        BufferedImage filteredImage = new CIFilterOp("CIUnsharpMask", options).filter(image, null);
Debug.println((System.currentTimeMillis() - t) + " ms");

        show(filteredImage);
    }
}
