/*
 * Copyright (c) 2022 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.coregraphics;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;

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
public class CGImage extends Image {

    /** */
    private Pointer/*CGImageRef*/ image;

    /** utility NSImage -> CGImageRef */
    private static Pointer/*CGImageRef*/ initFrom(InputStream stream) throws IOException {
        ByteBuffer bb = ByteBuffer.allocate(stream.available() != -1 ? stream.available() : Integer.MAX_VALUE / 2);
        int l = 0;
        while (l < bb.capacity()) {
            int r = Channels.newChannel(stream).read(bb);
            if (r < 0) break;
            l += r;
        }

        NSImage image = NSImage.imageWithData(NSData.dataWithBytes(bb.array()));
        return image.CGImageForProposedRect_context_hints(null, null, null);
    }

    /** */
    public CGImage(InputStream stream) throws IOException {
        this.image = initFrom(stream);
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

    @Override
    public int getWidth(ImageObserver observer) {
        return getWidth();
    }

    @Override
    public int getHeight(ImageObserver observer) {
        return getHeight();
    }

    @Override
    public ImageProducer getSource() {
        return null;
    }

    @Override
    public Graphics getGraphics() {
        return null;
    }

    @Override
    public Object getProperty(String name, ImageObserver observer) {
        return null;
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
