/*
 * Copyright (c) 2022 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.coregraphics;

import java.util.Collections;

import com.sun.jna.Callback;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import org.rococoa.cocoa.CGFloat;
import org.rococoa.internal.RococoaTypeMapper;


/**
 * CGLibrary.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2022-09-10 nsano initial version <br>
 */
public interface CGLibrary extends Library {

    CGLibrary library = Native.load(
            "CoreGraphics", CGLibrary.class, Collections.singletonMap(Library.OPTION_TYPE_MAPPER, new RococoaTypeMapper()));

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
}
