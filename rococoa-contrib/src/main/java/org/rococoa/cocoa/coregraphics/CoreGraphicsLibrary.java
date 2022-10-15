/*
 * Copyright (c) 2022 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.coregraphics;

import com.sun.jna.Callback;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import org.rococoa.cocoa.CGFloat;


/**
 * CoreGraphicsLibrary.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2022-09-10 nsano initial version <br>
 */
public interface CoreGraphicsLibrary extends Library {

    CoreGraphicsLibrary library = Native.load("CoreGraphics", CoreGraphicsLibrary.class);

    int kCGEventTapDisabledByTimeout = 0xFFFF_FFFE;

    int kCGHIDEventTap = 0;
    int kCGSessionEventTap = 1;
    int kCGHeadInsertEventTap = 0;
    int kCGEventTapOptionListenOnly = 1;
    long kCGEventMaskForAllEvents = 0xFFFF_FFFF_FFFF_FFFFL;

    interface CGEventTapCallBack extends Callback {
        Pointer/*CGEventRef*/ apply(Pointer/*CGEventTapProxy*/ proxy,
                                    int/*CGEventType*/ type,
                                    Pointer /*CGEventRef*/ event,
                                    Pointer userInfo);
    }

    void CGEventTapEnable(Pointer/*CFMachPortRef*/ tap, boolean enable);

    Pointer/*CFMachPortRef*/ CGEventTapCreate(int /*CGEventTapLocation*/ tap,
                                              int /*CGEventTapPlacement*/ place,
                                              int /*CGEventTapOptions*/ options,
                                              long /*CGEventMask*/ eventsOfInterest,
                                              CGEventTapCallBack callback, Pointer userInfo);

    Pointer/*CGColorRef*/ CGColorCreateGenericRGB(CGFloat red, CGFloat green, CGFloat blue, CGFloat alpha);

    Pointer/*CGImageRef*/ CGImageCreateWithImageInRect(Pointer /*CGImageRef*/ image, CGRect rect);

    int CGImageGetWidth(Pointer/*CGImageRef*/ image);

    int CGImageGetHeight(Pointer/*CGImageRef*/ image);

    void CGImageRelease(Pointer/*CGImageRef*/ image);

    boolean CGImageIsMask(Pointer/*CGImageRef*/ image);

    /**
     * Returns the number of bits allocated for a single color component of a bitmap image.
     */
    int CGImageGetBitsPerComponent(Pointer/*CGImageRef*/ image);
    /**
     * Returns the number of bits allocated for a single pixel in a bitmap image.
     */
    int CGImageGetBitsPerPixel(Pointer/*CGImageRef*/ image);
    /**
     * Returns the number of bytes allocated for a single row of a bitmap image.
     */
    int CGImageGetBytesPerRow(Pointer/*CGImageRef*/ image);
    /**
     * Returns the bitmap information for a bitmap image.
     */
    int CGImageGetBitmapInfo(Pointer/*CGImageRef*/ image);
    /**
     * Returns the decode array for a bitmap image.
     */
    CGFloat CGImageGetDecode(Pointer/*CGImageRef*/ image);
    /**
     * Return the color space for a bitmap image.
     */
    Pointer/*CGColorSpaceRef*/ CGImageGetColorSpace(Pointer/*CGImageRef*/ image);

    Pointer/*CGDataProviderRef*/ CGImageGetDataProvider(Pointer/*CGImageRef*/ image);

    Pointer/*CFDataRef*/ CGDataProviderCopyData(Pointer/*CGDataProviderRef*/ provider);

    int CFDataGetLength(Pointer/*CFDataRef*/ dataRef);

    Pointer CFDataGetBytePtr(Pointer/*CFDataRef*/ ref);

    int kCGColorSpaceModelUnknown = -1;
    int kCGColorSpaceModelMonochrome = 0;
    int kCGColorSpaceModelRGB = 1;
    int kCGColorSpaceModelCMYK = 2;
    int kCGColorSpaceModelIndexed = 5;

    int CGColorSpaceGetModel(Pointer/*CGColorSpaceRef*/ space);
}
