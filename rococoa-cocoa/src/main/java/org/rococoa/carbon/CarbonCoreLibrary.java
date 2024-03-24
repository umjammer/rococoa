/*
 * Copyright (c) 2023 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.carbon;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.NativeLongByReference;
import com.sun.jna.ptr.PointerByReference;
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

    CFStringRef kTISPropertyUnicodeKeyLayoutData = new CFStringRef(new PointerByReference(NATIVE_LIBRARY.getGlobalVariableAddress("kTISPropertyUnicodeKeyLayoutData")).getValue().getPointer(0));

    Pointer /* TISInputSourceRef */ TISCopyCurrentKeyboardLayoutInputSource();

    Pointer /* TISInputSourceRef */ TISCopyCurrentKeyboardInputSource();

    Pointer TISGetInputSourceProperty(Pointer /* TISInputSourceRef */ source, CFStringRef key);

//#endregion

//#region HIToolbox

    /*
key: 0, 0x0, string: a, 0x61
key: 1, 0x1, string: s, 0x73
key: 2, 0x2, string: d, 0x64
key: 3, 0x3, string: f, 0x66
key: 4, 0x4, string: h, 0x68
key: 5, 0x5, string: g, 0x67
key: 6, 0x6, string: z, 0x7a
key: 7, 0x7, string: x, 0x78
key: 8, 0x8, string: c, 0x63
key: 9, 0x9, string: v, 0x76
key: 10, 0xa, string: §, 0xa7
key: 11, 0xb, string: b, 0x62
key: 12, 0xc, string: q, 0x71
key: 13, 0xd, string: w, 0x77
key: 14, 0xe, string: e, 0x65
key: 15, 0xf, string: r, 0x72
key: 16, 0x10, string: y, 0x79
key: 17, 0x11, string: t, 0x74
key: 18, 0x12, string: 1, 0x31
key: 19, 0x13, string: 2, 0x32
key: 20, 0x14, string: 3, 0x33
key: 21, 0x15, string: 4, 0x34
key: 22, 0x16, string: 6, 0x36
key: 23, 0x17, string: 5, 0x35
key: 24, 0x18, string: ^, 0x5e
key: 25, 0x19, string: 9, 0x39
key: 26, 0x1a, string: 7, 0x37
key: 27, 0x1b, string: -, 0x2d
key: 28, 0x1c, string: 8, 0x38
key: 29, 0x1d, string: 0, 0x30
key: 30, 0x1e, string: [, 0x5b
key: 31, 0x1f, string: o, 0x6f
key: 32, 0x20, string: u, 0x75
key: 33, 0x21, string: @, 0x40
key: 34, 0x22, string: i, 0x69
key: 35, 0x23, string: p, 0x70
key: 36, 0x23, string: ???, 0xd
key: 37, 0x25, string: l, 0x6c
key: 38, 0x26, string: j, 0x6a
key: 39, 0x27, string: :, 0x3a
key: 40, 0x28, string: k, 0x6b
key: 41, 0x29, string: ;, 0x3b
key: 42, 0x2a, string: ], 0x5d
key: 43, 0x2b, string: ,, 0x2c
key: 44, 0x2c, string: /, 0x2f
key: 45, 0x2d, string: n, 0x6e
key: 46, 0x2e, string: m, 0x6d
key: 47, 0x2f, string: ., 0x2e
key: 48, 0x30, string: 	, 0x9
key: 49, 0x31, string:  , 0x20
key: 50, 0x32, string: `, 0x60
key: 51, 0x33, string:, 0x8
key: 52, 0x34, string: , 0x3
key: 53, 0x35, string: , 0x1b
key: 54, 0x36, string: null
key: 55, 0x37, string: null
key: 56, 0x38, string: null
key: 57, 0x39, string: null
key: 58, 0x3a, string: null
key: 59, 0x3b, string: null
key: 60, 0x3c, string: null
key: 61, 0x3d, string: null
key: 62, 0x3e, string: null
key: 63, 0x3f, string: null
key: 64, 0x40, string: null
key: 65, 0x41, string: ., 0x2e
key: 66, 0x42, string: , 0x1d
key: 67, 0x43, string: *, 0x2a
key: 68, 0x44, string: null
key: 69, 0x45, string: +, 0x2b
key: 70, 0x46, string: , 0x1c
key: 71, 0x47, string: , 0x1b
key: 72, 0x48, string: , 0x1f
key: 73, 0x49, string: null
key: 74, 0x4a, string: null
key: 75, 0x4b, string: /, 0x2f
key: 76, 0x4c, string: , 0x3
key: 77, 0x4d, string: , 0x1e
key: 78, 0x4e, string: -, 0x2d
key: 79, 0x4f, string: null
key: 80, 0x50, string: null
key: 81, 0x51, string: =, 0x3d
key: 82, 0x52, string: 0, 0x30
key: 83, 0x53, string: 1, 0x31
key: 84, 0x54, string: 2, 0x32
key: 85, 0x55, string: 3, 0x33
key: 86, 0x56, string: 4, 0x34
key: 87, 0x57, string: 5, 0x35
key: 88, 0x58, string: 6, 0x36
key: 89, 0x59, string: 7, 0x37
key: 90, 0x5a, string: null
key: 91, 0x5b, string: 8, 0x38
key: 92, 0x5c, string: 9, 0x39
key: 93, 0x5d, string: ¥, 0xa5
key: 94, 0x5e, string: _, 0x5f
key: 95, 0x5f, string: ,, 0x2c
key: 96, 0x60, string: , 0x10
key: 97, 0x61, string: , 0x10
key: 98, 0x62, string: , 0x10
key: 99, 0x63, string: , 0x10
key: 100, 0x64, string: , 0x10
key: 101, 0x65, string: , 0x10
key: 102, 0x66, string:  , 0x20
key: 103, 0x67, string: , 0x10
key: 104, 0x68, string:  , 0x20
key: 105, 0x69, string: , 0x10
key: 106, 0x6a, string: , 0x10
key: 107, 0x6b, string: , 0x10
key: 108, 0x6c, string: , 0x10
key: 109, 0x6d, string: , 0x10
key: 110, 0x6e, string: , 0x10
key: 111, 0x6f, string: , 0x10
key: 112, 0x70, string: , 0x10
key: 113, 0x71, string: , 0x10
key: 114, 0x72, string: , 0x5
key: 115, 0x73, string: , 0x1
key: 116, 0x74, string: , 0xb
key: 117, 0x75, string: , 0x7f
key: 118, 0x76, string: , 0x10
key: 119, 0x77, string: , 0x4
key: 120, 0x78, string: , 0x10
key: 121, 0x79, string: , 0xc
key: 122, 0x7a, string: , 0x10
key: 123, 0x7b, string: , 0x1c
key: 124, 0x7c, string: , 0x1d
key: 125, 0x7d, string: , 0x1f
key: 126, 0x7e, string: , 0x1e
key: 127, 0x7f, string: null
     */

    // TODO on mac studio m2, some key name is not matched
    //  karabiner-elements event viewer also
    // https://qiita.com/nak435/items/37cf3352b4d77585c38e

    int kVK_ANSI_A                    = 0x00;
    int kVK_ANSI_S                    = 0x01;
    int kVK_ANSI_D                    = 0x02;
    int kVK_ANSI_F                    = 0x03;
    int kVK_ANSI_H                    = 0x04;
    int kVK_ANSI_G                    = 0x05;
    int kVK_ANSI_Z                    = 0x06;
    int kVK_ANSI_X                    = 0x07;
    int kVK_ANSI_C                    = 0x08;
    int kVK_ANSI_V                    = 0x09;
    int kVK_ANSI_B                    = 0x0B;
    int kVK_ANSI_Q                    = 0x0C;
    int kVK_ANSI_W                    = 0x0D;
    int kVK_ANSI_E                    = 0x0E;
    int kVK_ANSI_R                    = 0x0F;
    int kVK_ANSI_Y                    = 0x10;
    int kVK_ANSI_T                    = 0x11;
    int kVK_ANSI_1                    = 0x12;
    int kVK_ANSI_2                    = 0x13;
    int kVK_ANSI_3                    = 0x14;
    int kVK_ANSI_4                    = 0x15;
    int kVK_ANSI_6                    = 0x16;
    int kVK_ANSI_5                    = 0x17;
    int kVK_ANSI_Equal                = 0x18;
    int kVK_ANSI_9                    = 0x19;
    int kVK_ANSI_7                    = 0x1A;
    int kVK_ANSI_Minus                = 0x1B;
    int kVK_ANSI_8                    = 0x1C;
    int kVK_ANSI_0                    = 0x1D;
    int kVK_ANSI_RightBracket         = 0x1E;
    int kVK_ANSI_O                    = 0x1F;
    int kVK_ANSI_U                    = 0x20;
    int kVK_ANSI_LeftBracket          = 0x21;
    int kVK_ANSI_I                    = 0x22;
    int kVK_ANSI_P                    = 0x23;
    int kVK_ANSI_L                    = 0x25;
    int kVK_ANSI_J                    = 0x26;
    int kVK_ANSI_Quote                = 0x27;
    int kVK_ANSI_K                    = 0x28;
    int kVK_ANSI_Semicolon            = 0x29;
    int kVK_ANSI_Backslash            = 0x2A;
    int kVK_ANSI_Comma                = 0x2B;
    int kVK_ANSI_Slash                = 0x2C;
    int kVK_ANSI_N                    = 0x2D;
    int kVK_ANSI_M                    = 0x2E;
    int kVK_ANSI_Period               = 0x2F;
    int kVK_ANSI_Grave                = 0x32;
    int kVK_ANSI_KeypadDecimal        = 0x41;
    int kVK_ANSI_KeypadMultiply       = 0x43;
    int kVK_ANSI_KeypadPlus           = 0x45;
    int kVK_ANSI_KeypadClear          = 0x47;
    int kVK_ANSI_KeypadDivide         = 0x4B;
    int kVK_ANSI_KeypadEnter          = 0x4C;
    int kVK_ANSI_KeypadMinus          = 0x4E;
    int kVK_ANSI_KeypadEquals         = 0x51;
    int kVK_ANSI_Keypad0              = 0x52;
    int kVK_ANSI_Keypad1              = 0x53;
    int kVK_ANSI_Keypad2              = 0x54;
    int kVK_ANSI_Keypad3              = 0x55;
    int kVK_ANSI_Keypad4              = 0x56;
    int kVK_ANSI_Keypad5              = 0x57;
    int kVK_ANSI_Keypad6              = 0x58;
    int kVK_ANSI_Keypad7              = 0x59;
    int kVK_ANSI_Keypad8              = 0x5B;
    int kVK_ANSI_Keypad9              = 0x5C;

    // keycodes for keys that are independent of keyboard layout

    int kVK_Return                    = 0x24;
    int kVK_Tab                       = 0x30;
    int kVK_Space                     = 0x31;
    int kVK_Delete                    = 0x33;
    int kVK_Escape                    = 0x35;
    int kVK_Command                   = 0x37;
    int kVK_Shift                     = 0x38;
    int kVK_CapsLock                  = 0x39;
    int kVK_Option                    = 0x3A;
    int kVK_Control                   = 0x3B;
    int kVK_RightCommand              = 0x36;
    int kVK_RightShift                = 0x3C;
    int kVK_RightOption               = 0x3D;
    int kVK_RightControl              = 0x3E;
    int kVK_Function                  = 0x3F;
    int kVK_F17                       = 0x40;
    int kVK_VolumeUp                  = 0x48;
    int kVK_VolumeDown                = 0x49;
    int kVK_Mute                      = 0x4A;
    int kVK_F18                       = 0x4F;
    int kVK_F19                       = 0x50;
    int kVK_F20                       = 0x5A;
    int kVK_F5                        = 0x60;
    int kVK_F6                        = 0x61;
    int kVK_F7                        = 0x62;
    int kVK_F3                        = 0x63;
    int kVK_F8                        = 0x64;
    int kVK_F9                        = 0x65;
    int kVK_F11                       = 0x67;
    int kVK_F13                       = 0x69;
    int kVK_F16                       = 0x6A;
    int kVK_F14                       = 0x6B;
    int kVK_F10                       = 0x6D;
    int kVK_F12                       = 0x6F;
    int kVK_F15                       = 0x71;
    int kVK_Help                      = 0x72;
    int kVK_Home                      = 0x73;
    int kVK_PageUp                    = 0x74;
    int kVK_ForwardDelete             = 0x75;
    int kVK_F4                        = 0x76;
    int kVK_End                       = 0x77;
    int kVK_F2                        = 0x78;
    int kVK_PageDown                  = 0x79;
    int kVK_F1                        = 0x7A;
    int kVK_LeftArrow                 = 0x7B;
    int kVK_RightArrow                = 0x7C;
    int kVK_DownArrow                 = 0x7D;
    int kVK_UpArrow                   = 0x7E;

    // ISO keyboards only

    int kVK_ISO_Section               = 0x0A;

    // JIS keyboards only

    int kVK_JIS_Yen                   = 0x5D;
    int kVK_JIS_Underscore            = 0x5E;
    int kVK_JIS_KeypadComma           = 0x5F;
    int kVK_JIS_Eisu                  = 0x66;
    int kVK_JIS_Kana                  = 0x68;

    record JavaNativePair(int java, int carbon) {}

    /** TODO java to native conversion */
    List<JavaNativePair> javaNativeMap = new ArrayList<>() {{
        add(new JavaNativePair(KeyEvent.VK_A, kVK_ANSI_A));
        add(new JavaNativePair(KeyEvent.VK_S, kVK_ANSI_S));
        add(new JavaNativePair(KeyEvent.VK_D, kVK_ANSI_D));
        add(new JavaNativePair(KeyEvent.VK_F, kVK_ANSI_F));
        add(new JavaNativePair(KeyEvent.VK_H, kVK_ANSI_H));
        add(new JavaNativePair(KeyEvent.VK_G, kVK_ANSI_G));
        add(new JavaNativePair(KeyEvent.VK_Z, kVK_ANSI_Z));
        add(new JavaNativePair(KeyEvent.VK_X, kVK_ANSI_X));
        add(new JavaNativePair(KeyEvent.VK_C, kVK_ANSI_C));
        add(new JavaNativePair(KeyEvent.VK_V, kVK_ANSI_V));
        add(new JavaNativePair(KeyEvent.VK_B, kVK_ANSI_B));
        add(new JavaNativePair(KeyEvent.VK_Q, kVK_ANSI_Q));
        add(new JavaNativePair(KeyEvent.VK_W, kVK_ANSI_W));
        add(new JavaNativePair(KeyEvent.VK_E, kVK_ANSI_E));
        add(new JavaNativePair(KeyEvent.VK_R, kVK_ANSI_R));
        add(new JavaNativePair(KeyEvent.VK_Y, kVK_ANSI_Y));
        add(new JavaNativePair(KeyEvent.VK_T, kVK_ANSI_T));
        add(new JavaNativePair(KeyEvent.VK_1, kVK_ANSI_1));
        add(new JavaNativePair(KeyEvent.VK_2, kVK_ANSI_2));
        add(new JavaNativePair(KeyEvent.VK_3, kVK_ANSI_3));
        add(new JavaNativePair(KeyEvent.VK_4, kVK_ANSI_4));
        add(new JavaNativePair(KeyEvent.VK_6, kVK_ANSI_6));
        add(new JavaNativePair(KeyEvent.VK_5, kVK_ANSI_5));
        add(new JavaNativePair(KeyEvent.VK_EQUALS, kVK_ANSI_Equal));
        add(new JavaNativePair(KeyEvent.VK_9, kVK_ANSI_9));
        add(new JavaNativePair(KeyEvent.VK_7, kVK_ANSI_7));
        add(new JavaNativePair(KeyEvent.VK_MINUS, kVK_ANSI_Minus));
        add(new JavaNativePair(KeyEvent.VK_8, kVK_ANSI_8));
        add(new JavaNativePair(KeyEvent.VK_0, kVK_ANSI_0));
        add(new JavaNativePair(KeyEvent.VK_RIGHT_PARENTHESIS, kVK_ANSI_RightBracket));
        add(new JavaNativePair(KeyEvent.VK_O, kVK_ANSI_O));
        add(new JavaNativePair(KeyEvent.VK_U, kVK_ANSI_U));
        add(new JavaNativePair(KeyEvent.VK_LEFT_PARENTHESIS, kVK_ANSI_LeftBracket));
        add(new JavaNativePair(KeyEvent.VK_I, kVK_ANSI_I));
        add(new JavaNativePair(KeyEvent.VK_P, kVK_ANSI_P));
        add(new JavaNativePair(KeyEvent.VK_L, kVK_ANSI_L));
        add(new JavaNativePair(KeyEvent.VK_J, kVK_ANSI_J));
        add(new JavaNativePair(KeyEvent.VK_QUOTE, kVK_ANSI_Quote));
        add(new JavaNativePair(KeyEvent.VK_K, kVK_ANSI_K));
        add(new JavaNativePair(KeyEvent.VK_SEMICOLON, kVK_ANSI_Semicolon));
        add(new JavaNativePair(KeyEvent.VK_BACK_SLASH, kVK_ANSI_Backslash));
        add(new JavaNativePair(KeyEvent.VK_COMMA, kVK_ANSI_Comma));
        add(new JavaNativePair(KeyEvent.VK_SLASH, kVK_ANSI_Slash));
        add(new JavaNativePair(KeyEvent.VK_N, kVK_ANSI_N));
        add(new JavaNativePair(KeyEvent.VK_M, kVK_ANSI_M));
        add(new JavaNativePair(KeyEvent.VK_PERIOD, kVK_ANSI_Period));
        add(new JavaNativePair(KeyEvent.VK_DEAD_GRAVE, kVK_ANSI_Grave));
        add(new JavaNativePair(KeyEvent.VK_DECIMAL, kVK_ANSI_KeypadDecimal));
        add(new JavaNativePair(KeyEvent.VK_MULTIPLY, kVK_ANSI_KeypadMultiply));
        add(new JavaNativePair(KeyEvent.VK_PLUS, kVK_ANSI_KeypadPlus));
        add(new JavaNativePair(KeyEvent.VK_CLEAR, kVK_ANSI_KeypadClear));
        add(new JavaNativePair(KeyEvent.VK_DIVIDE, kVK_ANSI_KeypadDivide));
//        add(new JavaNativePair(KeyEvent.VK_ENTER, kVK_ANSI_KeypadEnter));
//        add(new JavaNativePair(KeyEvent.VK_MINUS, kVK_ANSI_KeypadMinus));
//        add(new JavaNativePair(KeyEvent.VK_EQUALS, kVK_ANSI_KeypadEquals));
        add(new JavaNativePair(KeyEvent.VK_NUMPAD0, kVK_ANSI_Keypad0));
        add(new JavaNativePair(KeyEvent.VK_NUMPAD1, kVK_ANSI_Keypad1));
        add(new JavaNativePair(KeyEvent.VK_NUMPAD2, kVK_ANSI_Keypad2));
        add(new JavaNativePair(KeyEvent.VK_NUMPAD3, kVK_ANSI_Keypad3));
        add(new JavaNativePair(KeyEvent.VK_NUMPAD4, kVK_ANSI_Keypad4));
        add(new JavaNativePair(KeyEvent.VK_NUMPAD5, kVK_ANSI_Keypad5));
        add(new JavaNativePair(KeyEvent.VK_NUMPAD6, kVK_ANSI_Keypad6));
        add(new JavaNativePair(KeyEvent.VK_NUMPAD7, kVK_ANSI_Keypad7));
        add(new JavaNativePair(KeyEvent.VK_NUMPAD8, kVK_ANSI_Keypad8));
        add(new JavaNativePair(KeyEvent.VK_NUMPAD9, kVK_ANSI_Keypad9));
        add(new JavaNativePair(KeyEvent.VK_ENTER, kVK_Return));
        add(new JavaNativePair(KeyEvent.VK_TAB, kVK_Tab));
        add(new JavaNativePair(KeyEvent.VK_SPACE, kVK_Space));
        add(new JavaNativePair(KeyEvent.VK_DELETE, kVK_Delete));
        add(new JavaNativePair(KeyEvent.VK_ESCAPE, kVK_Escape));
        add(new JavaNativePair(KeyEvent.VK_META, kVK_Command));
        add(new JavaNativePair(KeyEvent.VK_SHIFT, kVK_Shift));
        add(new JavaNativePair(KeyEvent.VK_CAPS_LOCK, kVK_CapsLock));
        add(new JavaNativePair(KeyEvent.VK_ALT, kVK_Option));
        add(new JavaNativePair(KeyEvent.VK_CONTROL, kVK_Control));
//        add(new JavaNativePair(KeyEvent.VK_META, kVK_RightCommand));
//        add(new JavaNativePair(KeyEvent.VK_SHIFT, kVK_RightShift));
//        add(new JavaNativePair(KeyEvent.VK_ALT, kVK_RightOption));
//        add(new JavaNativePair(KeyEvent.VK_CONTROL, kVK_RightControl));
//        add(new JavaNativePair(KeyEvent.VK_???, kVK_Function));
        add(new JavaNativePair(KeyEvent.VK_F17, kVK_F17));
//        add(new JavaNativePair(KeyEvent.VK_???, kVK_VolumeUp));
//        add(new JavaNativePair(KeyEvent.VK_???, kVK_VolumeDown));
//        add(new JavaNativePair(KeyEvent.VK_???, kVK_Mute));
        add(new JavaNativePair(KeyEvent.VK_F18, kVK_F18));
        add(new JavaNativePair(KeyEvent.VK_F19, kVK_F19));
        add(new JavaNativePair(KeyEvent.VK_F20, kVK_F20));
        add(new JavaNativePair(KeyEvent.VK_F5, kVK_F5));
        add(new JavaNativePair(KeyEvent.VK_F6, kVK_F6));
        add(new JavaNativePair(KeyEvent.VK_F7, kVK_F7));
        add(new JavaNativePair(KeyEvent.VK_F3, kVK_F3));
        add(new JavaNativePair(KeyEvent.VK_F8, kVK_F8));
        add(new JavaNativePair(KeyEvent.VK_F9, kVK_F9));
        add(new JavaNativePair(KeyEvent.VK_F11, kVK_F11));
        add(new JavaNativePair(KeyEvent.VK_F13, kVK_F13));
        add(new JavaNativePair(KeyEvent.VK_F16, kVK_F16));
        add(new JavaNativePair(KeyEvent.VK_F14, kVK_F14));
        add(new JavaNativePair(KeyEvent.VK_F10, kVK_F10));
        add(new JavaNativePair(KeyEvent.VK_F12, kVK_F12));
        add(new JavaNativePair(KeyEvent.VK_F15, kVK_F15));
        add(new JavaNativePair(KeyEvent.VK_HELP, kVK_Help));
        add(new JavaNativePair(KeyEvent.VK_HOME, kVK_Home));
        add(new JavaNativePair(KeyEvent.VK_PAGE_UP, kVK_PageUp));
//        add(new JavaNativePair(KeyEvent.VK_DELETE, kVK_ForwardDelete));
        add(new JavaNativePair(KeyEvent.VK_F4, kVK_F4));
        add(new JavaNativePair(KeyEvent.VK_END, kVK_End));
        add(new JavaNativePair(KeyEvent.VK_F2, kVK_F2));
        add(new JavaNativePair(KeyEvent.VK_PAGE_DOWN, kVK_PageDown));
        add(new JavaNativePair(KeyEvent.VK_F1, kVK_F1));
        add(new JavaNativePair(KeyEvent.VK_LEFT, kVK_LeftArrow));
        add(new JavaNativePair(KeyEvent.VK_RIGHT, kVK_RightArrow));
        add(new JavaNativePair(KeyEvent.VK_DOWN, kVK_DownArrow));
        add(new JavaNativePair(KeyEvent.VK_UP, kVK_UpArrow));
//        add(new JavaNativePair(KeyEvent.VK_???, kVK_ISO_Section));
//        add(new JavaNativePair(KeyEvent.VK_BACK_SLASH, kVK_JIS_Yen));
        add(new JavaNativePair(KeyEvent.VK_UNDERSCORE, kVK_JIS_Underscore));
//        add(new JavaNativePair(KeyEvent.VK_COMMA, kVK_JIS_KeypadComma));
        add(new JavaNativePair(KeyEvent.VK_ALPHANUMERIC, kVK_JIS_Eisu));
        add(new JavaNativePair(KeyEvent.VK_HIRAGANA, kVK_JIS_Kana));
    }};

//#endregion

    int LMGetKbdType();
}
