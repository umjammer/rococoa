/*
 * Copyright (c) 2022 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.coreimage;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import com.sun.jna.Pointer;

import org.rococoa.ObjCClass;
import org.rococoa.Rococoa;
import org.rococoa.cocoa.coregraphics.CGImage;
import org.rococoa.cocoa.coregraphics.CGRect;
import org.rococoa.cocoa.coregraphics.CGSize;
import org.rococoa.cocoa.foundation.NSData;
import org.rococoa.cocoa.foundation.NSDictionary;
import org.rococoa.cocoa.foundation.NSObject;
import org.rococoa.cocoa.foundation.NSURL;

import static java.lang.System.getLogger;


/**
 * CIImage.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2022-09-04 nsano initial version <br>
 */
public abstract class CIImage extends NSObject {

    private static final Logger logger = getLogger(CIImage.class.getName());

    private static final _Class CLASS = Rococoa.createClass("CIImage", _Class.class);

    private interface _Class extends ObjCClass {

        CIImage emptyImage();

        CIImage imageWithCGImage(Pointer /* CGImageRef */ image);

        CIImage imageWithContentsOfURL(NSURL url);

        CIImage imageWithData(NSData data);

        CIImage imageWithBitmapData_bytesPerRow_size_format_colorSpace(
                NSData data, long bytesPerRowm, CGSize size, int /* CIFormat */ format, Pointer /* CGColorSpaceRef */ colorSpace);

        CIImage alloc();
    }

    public static CIImage imageWithCGImage(Pointer /* CGImageRef */ image) {
        return CLASS.imageWithCGImage(image);
    }

    /** A rectangle that specifies the extent of the image. */
    public abstract CGRect extent();

    /** The color space of the image. */
    public abstract Pointer /* CGColorSpaceRef */ colorSpace();

    /** A dictionary containing metadata about the image. */
    public abstract NSDictionary properties();

    /** The CoreGraphics image object this image was created from, if applicable. */
    public abstract Pointer /* CGImageRef */ CGImage();

    public static final String kCIInputImageKey = "inputImage";

    /** */
    public static CIImage newInstance(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] b = new byte[8192];
        int l = 0;
        while (true) {
            int r = is.read(b, 0, b.length);
            if (r < 0) break;
            baos.write(b, 0, r);
            l += r;
        }

        return newInstance(baos.toByteArray());
    }

    /** */
    public static CIImage newInstance(byte[] data) {
        return CLASS.imageWithData(NSData.dataWithBytes(data));
    }

    /** */
    public static CIImage newInstance(BufferedImage image) throws IOException {
        CGImage cgImage = new CGImage(image);
        return cgImage.toCIImage();
    }

    /** */
    public BufferedImage toBufferedImage() {
        Pointer cgImage = CGImage();
logger.log(Level.TRACE, "cgImage1: " + cgImage);
        if (cgImage == Pointer.NULL) {
            CIContext context = CIContext.contextWithOptions(null);
            cgImage = context.createCGImage_fromRect(this, extent());
logger.log(Level.TRACE, "cgImage2: " + cgImage);
        }
        return new CGImage(cgImage).toBufferedImage();
    }
}
