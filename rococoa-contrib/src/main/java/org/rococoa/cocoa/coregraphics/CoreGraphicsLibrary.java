/*
 * Copyright (c) 2022 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.coregraphics;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.sun.jna.Callback;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.NativeLongByReference;
import com.sun.jna.ptr.ShortByReference;
import org.rococoa.carbon.CarbonCoreLibrary;
import org.rococoa.cocoa.CFIndex;
import org.rococoa.cocoa.CGFloat;
import org.rococoa.cocoa.corefoundation.CFStringRef;
import org.rococoa.cocoa.corefoundation.CoreFoundation;

import static org.rococoa.carbon.CarbonCoreLibrary.kUCKeyActionDisplay;
import static org.rococoa.carbon.CarbonCoreLibrary.kUCKeyTranslateNoDeadKeysBit;
import static org.rococoa.cocoa.corefoundation.CFAllocatorRef.kCFAllocatorDefault;


/**
 * CoreGraphicsLibrary.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2022-09-10 nsano initial version <br>
 */
public interface CoreGraphicsLibrary extends Library {

    Logger logger = Logger.getLogger(CoreGraphicsLibrary.class.getName());

    CoreGraphicsLibrary library = Native.load("CoreGraphics", CoreGraphicsLibrary.class);

    /**
     * <i>native declaration : /System/Library/Frameworks/ApplicationServices.framework/Headers/../Frameworks/CoreGraphics.framework/Headers/CGGeometry.h:36</i><br>
     * enum values
     */
    interface CGRectEdge {
        int CGRectMinXEdge = 0;
        int CGRectMinYEdge = 1;
        int CGRectMaxXEdge = 2;
        int CGRectMaxYEdge = 3;
    }

    int kCGEventTapDisabledByTimeout = 0xFFFF_FFFE;

    // enum CGEventTapLocation
    int kCGHIDEventTap = 0;
    int kCGSessionEventTap = 1;
    int kCGHeadInsertEventTap = 0;

    // enum CGEventSourceStateID
    int kCGEventSourceStateHIDSystemState = 1;

    int kCGEventTapOptionListenOnly = 1;

    /** internal use */
    int NX_NULLEVENT = 0;

    // mouse events

    /** left mouse-down event */
    int NX_LMOUSEDOWN = 1;
    /** left mouse-up event */
    int NX_LMOUSEUP = 2;
    /** right mouse-down event */
    int NX_RMOUSEDOWN = 3;
    /** right mouse-up event */
    int NX_RMOUSEUP = 4;
    /** mouse-moved event */
    int NX_MOUSEMOVED = 5;
    /** left mouse-dragged event */
    int NX_LMOUSEDRAGGED = 6;
    /** right mouse-dragged event */
    int NX_RMOUSEDRAGGED = 7;
    /** mouse-entered event */
    int NX_MOUSEENTERED = 8;
    /** mouse-exited event */
    int NX_MOUSEEXITED = 9;

    // keyboard events

    /** key-down event */
    int NX_KEYDOWN = 10;
    /** key-up event */
    int NX_KEYUP = 11;
    /** flags-changed event */
    int NX_FLAGSCHANGED = 12;

    // composite events

    /** application-kit-defined event */
    int NX_KITDEFINED = 13;
    /** system-defined event */
    int NX_SYSDEFINED = 14;
    /** application-defined event */
    int NX_APPDEFINED = 15;

    // There are additional DPS client defined events past this point.

    // Scroll wheel events

    int NX_SCROLLWHEELMOVED = 22;

    // tablet events

    int NX_TABLETPOINTER = 23;
    int NX_TABLETPROXIMITY = 24;

    int NX_FIRSTEVENT = 0;
    int NX_LASTEVENT = 24;

    int NX_NUMPROCS = (NX_LASTEVENT - NX_FIRSTEVENT + 1);

    // Event masks

    /** left mouse-down */
    int NX_LMOUSEDOWNMASK = (1 << NX_LMOUSEDOWN);
    /** left mouse-up */
    int NX_LMOUSEUPMASK = (1 << NX_LMOUSEUP);
    /** right mouse-down */
    int NX_RMOUSEDOWNMASK = (1 << NX_RMOUSEDOWN);
    /** right mouse-up */
    int NX_RMOUSEUPMASK = (1 << NX_RMOUSEUP);
    /** mouse-moved */
    int NX_MOUSEMOVEDMASK = (1 << NX_MOUSEMOVED);
    /** left-dragged */
    int NX_LMOUSEDRAGGEDMASK = (1 << NX_LMOUSEDRAGGED);
    /** right-dragged */
    int NX_RMOUSEDRAGGEDMASK = (1 << NX_RMOUSEDRAGGED);
    /** mouse-entered */
    int NX_MOUSEENTEREDMASK = (1 << NX_MOUSEENTERED);
    /** mouse-exited */
    int NX_MOUSEEXITEDMASK = (1 << NX_MOUSEEXITED);
    /** key-down */
    int NX_KEYDOWNMASK = (1 << NX_KEYDOWN);
    /** key-up */
    int NX_KEYUPMASK = (1 << NX_KEYUP);
    /** flags-changed */
    int NX_FLAGSCHANGEDMASK = (1 << NX_FLAGSCHANGED);
//    /** kit-defined */
//    int NX_KITDEFINEDMASK = (1 << NX_WINCHANGED);
    /** system-defined */
    int NX_SYSDEFINEDMASK = (1 << NX_SYSDEFINED);
    /** app-defined */
    int NX_APPDEFINEDMASK = (1 << NX_APPDEFINED);
    /** scroll wheel moved */
    int NX_SCROLLWHEELMOVEDMASK = (1 << NX_SCROLLWHEELMOVED);
    /** tablet pointer moved */
    int NX_TABLETPOINTERMASK = (1 << NX_TABLETPOINTER);
    /** tablet pointer proximity */
    int NX_TABLETPROXIMITYMASK = (1 << NX_TABLETPROXIMITY);

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
        Pointer /* CGEventRef */ apply(Pointer /* CGEventTapProxy */ proxy,
                                    int /* CGEventType*/ type,
                                    Pointer /* CGEventRef */ event,
                                    Pointer userInfo);
    }

    void CGEventTapEnable(Pointer/*CFMachPortRef*/ tap, boolean enable);

    Pointer/*CFMachPortRef*/ CGEventTapCreate(int /* CGEventTapLocation */ tap,
                                              int /* CGEventTapPlacement */ place,
                                              int /* CGEventTapOptions */ options,
                                              long /* CGEventMask */ eventsOfInterest,
                                              CGEventTapCallBack callback, Pointer userInfo);

    Pointer /* CGColorRef */ CGColorCreateGenericRGB(CGFloat red, CGFloat green, CGFloat blue, CGFloat alpha);

    Pointer /* CGImageRef */ CGImageCreateWithImageInRect(Pointer /* CGImageRef */ image, CGRect rect);

    int CGImageGetWidth(Pointer /* CGImageRef */ image);

    int CGImageGetHeight(Pointer /* CGImageRef */ image);

    void CGImageRelease(Pointer /* CGImageRef */ image);

    boolean CGImageIsMask(Pointer /* CGImageRef */ image);

    /**
     * Returns the number of bits allocated for a single color component of a bitmap image.
     */
    int CGImageGetBitsPerComponent(Pointer /* CGImageRef */ image);
    /**
     * Returns the number of bits allocated for a single pixel in a bitmap image.
     */
    int CGImageGetBitsPerPixel(Pointer /* CGImageRef */ image);
    /**
     * Returns the number of bytes allocated for a single row of a bitmap image.
     */
    int CGImageGetBytesPerRow(Pointer /* CGImageRef */ image);
    /**
     * Returns the bitmap information for a bitmap image.
     */
    int CGImageGetBitmapInfo(Pointer /* CGImageRef */ image);
    /**
     * Returns the decode array for a bitmap image.
     */
    CGFloat CGImageGetDecode(Pointer /* CGImageRef */ image);
    /**
     * Return the color space for a bitmap image.
     */
    Pointer /* CGColorSpaceRef */ CGImageGetColorSpace(Pointer /* CGImageRef */ image);

    Pointer /* CGDataProviderRef */ CGImageGetDataProvider(Pointer /* CGImageRef */ image);

    Pointer /* CFDataRef */ CGDataProviderCopyData(Pointer /* CGDataProviderRef */ provider);

    int CFDataGetLength(Pointer /* CFDataRef */ dataRef);

    Pointer CFDataGetBytePtr(Pointer /* CFDataRef */ ref);

    int kCGColorSpaceModelUnknown = -1;
    int kCGColorSpaceModelMonochrome = 0;
    int kCGColorSpaceModelRGB = 1;
    int kCGColorSpaceModelCMYK = 2;
    int kCGColorSpaceModelIndexed = 5;

    int CGColorSpaceGetModel(Pointer/*CGColorSpaceRef*/ space);

    /** Returns a Quartz event source created with a specified source state. */
    Pointer /* CGEventSourceRef */ CGEventSourceCreate(int /* CGEventSourceStateID */ stateID);

    // CGKeyCode

    /**
     * Returns string representation of key, if it is printable.
     * Ownership follows the Create Rule; that is, it is the caller's
     * responsibility to release the returned object.
     * @see "https://stackoverflow.com/a/1971027"
     */
    private static CFStringRef createStringForKey(char /* CGKeyCode */ keyCode) {
        Pointer /* TISInputSourceRef */ currentKeyboard = CarbonCoreLibrary.library.TISCopyCurrentKeyboardInputSource();
logger.fine("currentKeyboard: " + currentKeyboard);// + ", " + kTISPropertyUnicodeKeyLayoutData);
        Pointer /* CFDataRef */ layoutData = CarbonCoreLibrary.library.TISGetInputSourceProperty(currentKeyboard, CFStringRef.toCFString("TISPropertyUnicodeKeyLayoutData"));
logger.finer("layoutData: " + layoutData);
        Pointer /* UCKeyboardLayout */ keyboardLayout = CoreFoundation.library.CFDataGetBytePtr(layoutData);

        IntByReference keysDown = new IntByReference(0);
        char[] chars = new char[4];
        NativeLongByReference /* UniCharCount */ realLength = new NativeLongByReference();

        CarbonCoreLibrary.library.UCKeyTranslate(keyboardLayout,
                keyCode,
                kUCKeyActionDisplay,
                0,
                CarbonCoreLibrary.library.LMGetKbdType(),
                kUCKeyTranslateNoDeadKeysBit,
                keysDown,
                new NativeLong(chars.length / Character.BYTES),
                realLength,
                chars);
        CoreFoundation.library.CFRelease(currentKeyboard);

        return CoreFoundation.library.CFStringCreateWithCharacters(kCFAllocatorDefault, chars, CFIndex.of(1));
    }

    /** key code, char map */
    Map <String, Character> charToCodeDict = new HashMap<>(128);

    /**
     * Returns key code for given character via the above function.
     */
    static char /* CGKeyCode */ keyCodeForChar(char c) {
        // Generate table of keycodes and characters.
        if (charToCodeDict.isEmpty()) {
            /* Loop through every keycode (0 - 127) to find its current mapping. */
            for (char i = 0; i < 128; i++) {
                CFStringRef string = createStringForKey(/* CGKeyCode */ i);
logger.finest("key: " + (int) i + ", 0x" + Integer.toHexString(i) + ", string: " + string + (string != null && !string.toString().isEmpty() ? ", 0x" + Integer.toHexString(string.toString().charAt(0)) : "null"));
                if (string != null) {
                    charToCodeDict.put(string.toString(), i);
                    CoreFoundation.library.CFRelease(string);
                }
            }
        }

        char[] /* UniChar */ character = new char[] { c };
        CFStringRef charStr = CoreFoundation.library.CFStringCreateWithCharacters(kCFAllocatorDefault, character, CFIndex.of(1));
        /* Our values may be NULL (0), so we need to use this function. */
        char code = charToCodeDict.getOrDefault(charStr.toString(), Character.MAX_VALUE);
        CoreFoundation.library.CFRelease(charStr);

        return code;
    }

//#endregion

    /** Returns a new Quartz keyboard event. */
    Pointer /* CGEventRef */ CGEventCreateKeyboardEvent(Pointer /* CGEventSourceRef */ source, char /* CGKeyCode */ virtualKey, boolean keyDown);

    /** Sets the event flags of a Quartz event. */
    void CGEventSetFlags(Pointer /* CGEventRef */ event, long /* CGEventFlags */ flags);

    /** Posts a Quartz event into the event stream at a specified location. */
    void CGEventPost(int /* CGEventTapLocation */ tap, Pointer /* CGEventRef */ event);

    /** Returns a rectangle with the specified coordinate and size values. */
    CGRect CGRectMake(CGFloat x, CGFloat y, CGFloat width, CGFloat height);

//    /** Returns a point with the specified coordinates. */
//    /* inline */ CGPoint CGPointMake(CGFloat x, CGFloat y);

    // CGMouseButton
    int kCGMouseButtonLeft = 0;
    int kCGMouseButtonRight = 1;
    int kCGMouseButtonCenter = 2;

    int kCGEventNull = NX_NULLEVENT;
    int kCGEventLeftMouseDown = NX_LMOUSEDOWN;
    int kCGEventLeftMouseUp = NX_LMOUSEUP;
    int kCGEventRightMouseDown = NX_RMOUSEDOWN;
    int kCGEventRightMouseUp = NX_RMOUSEUP;
    int kCGEventMouseMoved = NX_MOUSEMOVED;
    int kCGEventLeftMouseDragged = NX_LMOUSEDRAGGED;
    int kCGEventRightMouseDragged = NX_RMOUSEDRAGGED;
    int kCGEventKeyDown = NX_KEYDOWN;
    int kCGEventKeyUp = NX_KEYUP;
    int kCGEventFlagsChanged = NX_FLAGSCHANGED;
    int kCGEventScrollWheel = NX_SCROLLWHEELMOVED;

    /** Returns a new Quartz mouse event. */
    Pointer /* CGEventRef */ CGEventCreateMouseEvent(Pointer /* CGEventSourceRef */ source, int /* CGEventType */ mouseType, CGPoint mouseCursorPosition, int /* CGMouseButton */ mouseButton);
}
