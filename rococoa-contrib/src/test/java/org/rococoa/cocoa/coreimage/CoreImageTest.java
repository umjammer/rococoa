/*
 * Copyright (c) 2022 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.coreimage;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.sun.jna.Pointer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.rococoa.cocoa.CGFloat;
import org.rococoa.cocoa.appkit.NSScreen;
import org.rococoa.cocoa.coregraphics.CGImage;
import org.rococoa.cocoa.coregraphics.CGRect;
import org.rococoa.cocoa.coregraphics.CoreGraphicsLibrary;
import org.rococoa.cocoa.foundation.NSArray;
import org.rococoa.cocoa.foundation.NSNumber;
import org.rococoa.cocoa.foundation.NSObject;
import org.rococoa.cocoa.foundation.NSRect;
import vavi.util.Debug;

import static org.rococoa.cocoa.foundation.FoundationKitFunctions.NSRectFromCGRect;
import static org.rococoa.cocoa.foundation.FoundationKitFunctions.NSRectToCGRect;


/**
 * CoreImageTest.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2022-09-11 nsano initial version <br>
 */
class CoreImageTest {

    /** @see "https://stackoverflow.com/a/23877167" */
    @Test
    @DisplayName("use primitives")
    @EnabledIfSystemProperty(named = "vavi.test", matches = "ide")
    void test1() throws Exception {

        Pointer/*CGColorRef*/ colorRef = CoreGraphicsLibrary.library.CGColorCreateGenericRGB(
                new CGFloat(255.0),
                new CGFloat(0),
                new CGFloat(255.0),
                new CGFloat(255.0));
        String colorString = CIColor.colorWithCGColor(colorRef).stringRepresentation();
        CIColor coreColor = CIColor.colorWithString(colorString);

        CIContext context = CIContext.contextWithOptions(null);
//Debug.println("context: " + context);

        CGImage cgImage = new CGImage(CoreImageTest.class.getResourceAsStream("/sample1.heic"));
Debug.println("cgImage: " + cgImage.getWidth() + "x" + cgImage.getHeight());

        //  Convert UIImage to CIImage
        CIImage ciImage = cgImage.toCIImage();
Debug.println("ciImage: " + ciImage);

        //  Set values for CIColorMonochrome Filter
        CIFilter filter = CIFilter.filterWithName("CIColorMonochrome");
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
Debug.println("extent: " + extent.getPointer() + ", " + extent);

        NSScreen screen = NSScreen.mainScreen();
Debug.println("screen: " + screen);
//Debug.println("userSpaceScaleFactor: " + screen.userSpaceScaleFactor());

Debug.println("------------------------------------------------------------");

        //  it doesn't make sense

//        NSRect nsRect1 = NSRectFromCGRect(extent);
//Debug.println("nsRect1: " + nsRect1);
////        NSRect nsRect2 = screen.convertRectToBacking(nsRect1);
////        nsRect2.read();
//        NSRect nsRect2 = convertRectToBacking(nsRect1);
//Debug.println("nsRect2: " + nsRect2);
//        CGRect extent2 = NSRectToCGRect(nsRect2);

        CGRect extent2 = NSRectToCGRect(screen.convertRectToBacking(NSRectFromCGRect(extent))); // TODO wtf return value
//        CGRect extent2 = NSRectToCGRect(convertRectToBacking(NSRectFromCGRect(extent)));
Debug.println("extent2: " + extent2.getPointer() + ", " + extent2);
        Pointer /* CGImageRef */ cgImage2 = context.createCGImage_fromRect(result, extent2); // TODO why returns nil
Debug.println("createCGImage:fromRect: " + cgImage2);

//        NSImage nsImage = NSImage.initWithCGImageSize(cgImage2, extent2.size.toNSSize());
//Debug.println("nsImage: " + nsImage + ", " + nsImage.size().width.intValue() + "x" + nsImage.size().height.intValue());
//        show(nsImage.toBufferedImage());

        //
        CGImage cgImageX = new CGImage(cgImage2);
Debug.println("cgImageX: " + cgImageX.getWidth() + ", " + cgImageX.getHeight());

//        BufferedImage image = nsImage.toBufferedImage();
        BufferedImage image = cgImageX.toBufferedImage();

        // *** NSImage ***
//        CGImage cgImage3 = new CGImage(context.createCGImage_fromRect(result, extent));
//Debug.println("cgImage3: " + cgImage3.getWidth() + ", " + cgImage3.getHeight());
//        BufferedImage image3 = NSImage.initWithCGImageSize(cgImage3.pointer(), NSSize.NSZeroSize).toBufferedImage();

        show(image);
    }

    static NSRect convertRectToBacking(NSRect rect) { // TODO this doesn't help
        NSRect rect2 = new NSRect();
        rect2.origin.x = new CGFloat(10);
        rect2.origin.y = new CGFloat(10 );
        rect2.size.width = new CGFloat(200);
        rect2.size.height = new CGFloat(200);
        rect2.write();
        return rect2;
    }

    /** using cdl cause junit stops awt thread suddenly */
    void show(BufferedImage image) throws Exception {
        CountDownLatch cdl = new CountDownLatch(1);
        JFrame frame = new JFrame();
        frame.addWindowListener(new WindowAdapter() {
            @Override public void windowClosing(WindowEvent e) { cdl.countDown(); }
        });
        JPanel panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                g.drawImage(image, 0, 0, this);
            }
        };
        panel.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
        frame.setContentPane(panel);
        frame.setTitle("CoreImage");
        frame.pack();
        frame.setVisible(true);
        cdl.await();
    }

    @Test
    @DisplayName("CIImage -> BufferedImage")
    @EnabledIfSystemProperty(named = "vavi.test", matches = "ide")
    void test2() throws Exception {
        CIImage ciImage = CIImage.newInstance(CoreImageTest.class.getResourceAsStream("/sample1.heic"));
Debug.println("ciImage: " + ciImage);
        show(ciImage.toBufferedImage());
    }

    @Test
    @DisplayName("BufferedImage -> CIImage")
    @EnabledIfSystemProperty(named = "vavi.test", matches = "ide")
    void test3() throws Exception {
        BufferedImage bi = ImageIO.read(CoreImageTest.class.getResourceAsStream("/test.jpg"));
Debug.println("bi: " + bi);
        CIImage ciImage = CIImage.newInstance(bi);
Debug.println("ciImage: " + ciImage);
        show(ciImage.toBufferedImage());
    }

    @Test
    @DisplayName("use utilities")
    @EnabledIfSystemProperty(named = "vavi.test", matches = "ide")
    void test4() throws Exception {
        BufferedImage image = ImageIO.read(CoreImageTest.class.getResourceAsStream("/test.jpg"));

long t = System.currentTimeMillis();
        Map<String, NSObject> options = new HashMap<>();
        options.put("inputColor", CIColor.newInstance(0, 255, 0, 255));
        options.put("inputIntensity", NSNumber.numberWithInt(1));

        BufferedImage filteredImage = new CIFilterOp("CIColorMonochrome", options).filter(image, null);
Debug.println((System.currentTimeMillis() - t) + " ms");

        show(filteredImage);
    }

    @Test
    @DisplayName("CIUnsharpMask")
    @EnabledIfSystemProperty(named = "vavi.test", matches = "ide")
    void test5() throws Exception {
        BufferedImage image = ImageIO.read(CoreImageTest.class.getResourceAsStream("/test.jpg"));

long t = System.currentTimeMillis();
        Map<String, NSObject> options = new HashMap<>();
        options.put("inputIntensity", NSNumber.numberWithDouble(2.0));
        options.put("inputRadius", NSNumber.numberWithDouble(1.0));

        BufferedImage filteredImage = new CIFilterOp("CIUnsharpMask", options).filter(image, null);
Debug.println((System.currentTimeMillis() - t) + " ms");

        show(filteredImage);
    }
}
