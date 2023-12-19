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
import com.sun.jna.platform.mac.CoreFoundation.CFTypeRef;
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

    // enum CGEventTapLocation
    int kCGHIDEventTap = 0;
    int kCGSessionEventTap = 1;
    int kCGHeadInsertEventTap = 0;

    // enum CGEventSourceStateID
    int kCGEventSourceStateHIDSystemState = 1;

    int kCGEventTapOptionListenOnly = 1;

    int NX_ALPHASHIFTMASK = 0x00010000;
    int NX_SHIFTMASK = 0x00020000;
    int NX_CONTROLMASK = 0x00040000;
    int NX_ALTERNATEMASK = 0x00080000;
    int NX_COMMANDMASK = 0x00100000;
    int NX_NUMERICPADMASK = 0x00200000;
    int NX_HELPMASK = 0x00400000;
    int NX_SECONDARYFNMASK = 0x00800000;
    int NX_ALPHASHIFT_STATELESS_MASK = 0x01000000;

    int NX_DEVICELCTLKEYMASK = 0x00000001;
    int NX_DEVICELSHIFTKEYMASK = 0x00000002;
    int NX_DEVICERSHIFTKEYMASK = 0x00000004;
    int NX_DEVICELCMDKEYMASK = 0x00000008;
    int NX_DEVICERCMDKEYMASK = 0x00000010;
    int NX_DEVICELALTKEYMASK = 0x00000020;
    int NX_DEVICERALTKEYMASK = 0x00000040;
    int NX_DEVICE_ALPHASHIFT_STATELESS_MASK = 0x00000080;
    int NX_DEVICERCTLKEYMASK = 0x00002000;

    int KEYBOARD_FLAGSMASK = NX_ALPHASHIFTMASK | NX_SHIFTMASK | NX_CONTROLMASK | NX_ALTERNATEMASK
            | NX_COMMANDMASK | NX_NUMERICPADMASK | NX_HELPMASK | NX_SECONDARYFNMASK
            | NX_DEVICELSHIFTKEYMASK | NX_DEVICERSHIFTKEYMASK | NX_DEVICELCMDKEYMASK
            | NX_ALPHASHIFT_STATELESS_MASK | NX_DEVICE_ALPHASHIFT_STATELESS_MASK
            | NX_DEVICERCMDKEYMASK | NX_DEVICELALTKEYMASK | NX_DEVICERALTKEYMASK
            | NX_DEVICELCTLKEYMASK | NX_DEVICERCTLKEYMASK;

    // enum CGEventFlags
    long kCGEventFlagMaskCommand = NX_COMMANDMASK;

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

    /** Returns a Quartz event source created with a specified source state. */
    Pointer /* CGEventSourceRef */ CGEventSourceCreate(int /* CGEventSourceStateID */ stateID);

    /** Returns a new Quartz keyboard event. */
    Pointer /* CGEventRef */ CGEventCreateKeyboardEvent(Pointer /*CGEventSourceRef*/ source, char /*CGKeyCode*/ virtualKey, boolean keyDown);

    /** Sets the event flags of a Quartz event. */
    void CGEventSetFlags(Pointer /* CGEventRef */ event, long /* CGEventFlags */ flags);

    /** Posts a Quartz event into the event stream at a specified location. */
    void CGEventPost(int /* CGEventTapLocation */ tap, Pointer /* CGEventRef */ event);

    /** Returns a rectangle with the specified coordinate and size values. */
    CGRect CGRectMake(CGFloat x, CGFloat y, CGFloat width, CGFloat height);
}
