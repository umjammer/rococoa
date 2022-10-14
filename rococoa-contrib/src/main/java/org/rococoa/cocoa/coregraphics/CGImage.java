/*
 * Copyright (c) 2022 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.coregraphics;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import com.sun.jna.Pointer;
import org.rococoa.cocoa.appkit.NSImage;
import org.rococoa.cocoa.coreimage.CIImage;
import org.rococoa.cocoa.foundation.NSData;

import static org.rococoa.cocoa.coregraphics.CGLibrary.library;


/**
 * CGImage.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2022-09-11 nsano initial version <br>
 */
public class CGImage {

    private static final Logger logger = Logger.getLogger(CGImage.class.getName());

    /** CGImageRef */
    private Pointer/*CGImageRef*/ image;

    /** utility NSImage -> CGImageRef */
    private static Pointer/*CGImageRef*/ initFrom(InputStream stream) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] b = new byte[8192];
        int l = 0;
        while (true) {
            int r = stream.read(b, 0, b.length);
            if (r < 0) break;
            baos.write(b, 0, r);
            l += r;
        }
        return initFrom(baos.toByteArray());
    }

    private static Pointer/*CGImageRef*/ initFrom(byte[] data) throws IOException {
        NSImage image = NSImage.imageWithData(NSData.dataWithBytes(data));
        return image.CGImageForProposedRect_context_hints(null, null, null);
    }

    /** */
    public CGImage(InputStream stream) throws IOException {
        this.image = initFrom(stream);
    }

    /** */
    public CGImage(BufferedImage image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "PNG", baos);
        this.image = initFrom(baos.toByteArray());
    }

    /** */
    public CGImage(Pointer/*CGImageRef*/ cgImageRef) {
        this.image = cgImageRef;
        int cBits = CGLibrary.library.CGImageGetBitsPerComponent(image);
        int bits = CGLibrary.library.CGImageGetBitsPerPixel(image);
logger.finer(String.format("cgImage: %dx%d, cb:%d, b:%d%n", getWidth(), getHeight(), cBits, bits));
    }

    /** */
    public void dispose() {
        library.CGImageRelease(image);
    }

    /** */
    public int getWidth() {
        return library.CGImageGetWidth(image);
    }

    /** */
    public int getHeight() {
        return library.CGImageGetHeight(image);
    }

    /** TODO some color conversion needed */
    public BufferedImage toBufferedImage() {

        int width = getWidth();
        int height = getHeight();
        int cBits = library.CGImageGetBitsPerComponent(image);
        int bits = library.CGImageGetBitsPerPixel(image);
        int stride = library.CGImageGetBytesPerRow(image);
        Pointer colorSpace = library.CGImageGetColorSpace(image);
        int colorModel = library.CGColorSpaceGetModel(colorSpace);
logger.fine(String.format("cgImage: %dx%d, cBits:%d, bits:%d, stride:%d, cm:%d%n", width, height, cBits, bits, stride, colorModel));

        Pointer dataProvider = library.CGImageGetDataProvider(image);
        Pointer data = library.CGDataProviderCopyData(dataProvider);
        Pointer buffer = library.CFDataGetBytePtr(data);
        byte[] src = buffer.getByteArray(0, stride * height);

        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        byte[] dst = ((DataBufferByte) bi.getRaster().getDataBuffer()).getData();

        int dP = 0;
        for (int y = 0; y < height; y++) {
            int sP = y * stride;
            for (int x = 0; x < width; x++) {
                dst[dP + 0] = src[sP + 3]; // a
                dst[dP + 1] = src[sP + 2]; // b
                dst[dP + 2] = src[sP + 1]; // g
                dst[dP + 3] = src[sP + 0]; // r
                sP += 4;
                dP += 4;
            }
        }

        return bi;
    }

    /** */
    public CIImage toCIImage() {
        return CIImage.CLASS.imageWithCGImage(image);
    }

    /** */
    public Pointer pointer() {
        return image;
    }
}
