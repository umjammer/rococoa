/*
 * Copyright (c) 2023 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.carbon;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.NativeLongByReference;
import org.rococoa.cocoa.corefoundation.CFStringRef;


/**
 * CarbonCoreLibrary.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2023-12-22 nsano initial version <br>
 */
public interface CarbonCoreLibrary extends Library {

    CarbonCoreLibrary library = Native.load("Carbon", CarbonCoreLibrary.class);

    NativeLibrary NATIVE_LIBRARY = NativeLibrary.getInstance("Carbon");

//#region UnicodeUtilities

    @Deprecated
    int kUCKeyTranslateNoDeadKeysBit = 0;
    char kUCKeyActionDisplay = 3;

    /**
     * Converts a combination of a virtual key code, a modifier key state, and a dead-key state into a string of
     * one or more Unicode characters.
     * @see "https://opensource.apple.com/source/CarbonHeaders/CarbonHeaders-18.1/MacTypes.h"
     */
    int /* OSStatus */ UCKeyTranslate(Pointer /* UCKeyboardLayout */ keyLayoutPtr, char /* UInt16 */ virtualKeyCode, char /* UInt16 */ keyAction, int /* UInt32 */ modifierKeyState, int /* UInt32 */ keyboardType, int /* OptionBits */ keyTranslateOptions, IntByReference /* UInt32* */ deadKeyState, NativeLong /* UniCharCount */ maxStringLength, NativeLongByReference /* UniCharCount* */ actualStringLength, char[] /* UniChar[] */ unicodeString);

//#endregion

//#region TextInputSource

    CFStringRef kTISPropertyUnicodeKeyLayoutData = new CFStringRef(NATIVE_LIBRARY.getGlobalVariableAddress("kTISPropertyUnicodeKeyLayoutData"));

    Pointer /* TISInputSourceRef */ TISCopyCurrentKeyboardInputSource();

    Pointer TISGetInputSourceProperty(Pointer /* TISInputSourceRef */ source, CFStringRef key);

//#endregion

    int LMGetKbdType();
}
