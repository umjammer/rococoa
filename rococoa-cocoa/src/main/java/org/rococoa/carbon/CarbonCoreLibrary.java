/*
 * Copyright (c) 2023 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.carbon;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

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

    Pointer /* TISInputSourceRef */ TISCopyCurrentKeyboardInputSource();

    Pointer TISGetInputSourceProperty(Pointer /* TISInputSourceRef */ source, CFStringRef key);

//#endregion

//#region HIToolbox

    // TODO on mac studio m2, some key name is not matched
    //  karabiner-elements event viewer also

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

    /** java to native conversion */
    Map<Integer, Integer> javaNativeMap = new HashMap<>() {{
        put(KeyEvent.VK_A, kVK_ANSI_A);
        put(KeyEvent.VK_S, kVK_ANSI_S);
        put(KeyEvent.VK_D, kVK_ANSI_D);
        put(KeyEvent.VK_F, kVK_ANSI_F);
        put(KeyEvent.VK_H, kVK_ANSI_H);
        put(KeyEvent.VK_G, kVK_ANSI_G);
        put(KeyEvent.VK_Z, kVK_ANSI_Z);
        put(KeyEvent.VK_X, kVK_ANSI_X);
        put(KeyEvent.VK_C, kVK_ANSI_C);
        put(KeyEvent.VK_V, kVK_ANSI_V);
        put(KeyEvent.VK_B, kVK_ANSI_B);
        put(KeyEvent.VK_Q, kVK_ANSI_Q);
        put(KeyEvent.VK_W, kVK_ANSI_W);
        put(KeyEvent.VK_E, kVK_ANSI_E);
        put(KeyEvent.VK_R, kVK_ANSI_R);
        put(KeyEvent.VK_Y, kVK_ANSI_Y);
        put(KeyEvent.VK_T, kVK_ANSI_T);
        put(KeyEvent.VK_1, kVK_ANSI_1);
        put(KeyEvent.VK_2, kVK_ANSI_2);
        put(KeyEvent.VK_3, kVK_ANSI_3);
        put(KeyEvent.VK_4, kVK_ANSI_4);
        put(KeyEvent.VK_6, kVK_ANSI_6);
        put(KeyEvent.VK_5, kVK_ANSI_5);
        put(KeyEvent.VK_EQUALS, kVK_ANSI_Equal);
        put(KeyEvent.VK_9, kVK_ANSI_9);
        put(KeyEvent.VK_7, kVK_ANSI_7);
        put(KeyEvent.VK_MINUS, kVK_ANSI_Minus);
        put(KeyEvent.VK_8, kVK_ANSI_8);
        put(KeyEvent.VK_0, kVK_ANSI_0);
        put(KeyEvent.VK_RIGHT_PARENTHESIS, kVK_ANSI_RightBracket);
        put(KeyEvent.VK_O, kVK_ANSI_O);
        put(KeyEvent.VK_U, kVK_ANSI_U);
        put(KeyEvent.VK_LEFT_PARENTHESIS, kVK_ANSI_LeftBracket);
        put(KeyEvent.VK_I, kVK_ANSI_I);
        put(KeyEvent.VK_P, kVK_ANSI_P);
        put(KeyEvent.VK_L, kVK_ANSI_L);
        put(KeyEvent.VK_J, kVK_ANSI_J);
        put(KeyEvent.VK_QUOTE, kVK_ANSI_Quote);
        put(KeyEvent.VK_K, kVK_ANSI_K);
        put(KeyEvent.VK_SEMICOLON, kVK_ANSI_Semicolon);
        put(KeyEvent.VK_BACK_SLASH, kVK_ANSI_Backslash);
        put(KeyEvent.VK_COMMA, kVK_ANSI_Comma);
        put(KeyEvent.VK_SLASH, kVK_ANSI_Slash);
        put(KeyEvent.VK_N, kVK_ANSI_N);
        put(KeyEvent.VK_M, kVK_ANSI_M);
        put(KeyEvent.VK_PERIOD, kVK_ANSI_Period);
        put(KeyEvent.VK_DEAD_GRAVE, kVK_ANSI_Grave);
        put(KeyEvent.VK_DECIMAL, kVK_ANSI_KeypadDecimal);
        put(KeyEvent.VK_MULTIPLY, kVK_ANSI_KeypadMultiply);
        put(KeyEvent.VK_PLUS, kVK_ANSI_KeypadPlus);
        put(KeyEvent.VK_CLEAR, kVK_ANSI_KeypadClear);
        put(KeyEvent.VK_DIVIDE, kVK_ANSI_KeypadDivide);
//        put(KeyEvent.VK_ENTER, kVK_ANSI_KeypadEnter);
//        put(KeyEvent.VK_MINUS, kVK_ANSI_KeypadMinus);
//        put(KeyEvent.VK_EQUALS, kVK_ANSI_KeypadEquals);
        put(KeyEvent.VK_NUMPAD0, kVK_ANSI_Keypad0);
        put(KeyEvent.VK_NUMPAD1, kVK_ANSI_Keypad1);
        put(KeyEvent.VK_NUMPAD2, kVK_ANSI_Keypad2);
        put(KeyEvent.VK_NUMPAD3, kVK_ANSI_Keypad3);
        put(KeyEvent.VK_NUMPAD4, kVK_ANSI_Keypad4);
        put(KeyEvent.VK_NUMPAD5, kVK_ANSI_Keypad5);
        put(KeyEvent.VK_NUMPAD6, kVK_ANSI_Keypad6);
        put(KeyEvent.VK_NUMPAD7, kVK_ANSI_Keypad7);
        put(KeyEvent.VK_NUMPAD8, kVK_ANSI_Keypad8);
        put(KeyEvent.VK_NUMPAD9, kVK_ANSI_Keypad9);
        put(KeyEvent.VK_ENTER, kVK_Return);
        put(KeyEvent.VK_TAB, kVK_Tab);
        put(KeyEvent.VK_SPACE, kVK_Space);
        put(KeyEvent.VK_DELETE, kVK_Delete);
        put(KeyEvent.VK_ESCAPE, kVK_Escape);
        put(KeyEvent.VK_META, kVK_Command);
        put(KeyEvent.VK_SHIFT, kVK_Shift);
        put(KeyEvent.VK_CAPS_LOCK, kVK_CapsLock);
        put(KeyEvent.VK_ALT, kVK_Option);
        put(KeyEvent.VK_CONTROL, kVK_Control);
//        put(KeyEvent.VK_META, kVK_RightCommand);
//        put(KeyEvent.VK_SHIFT, kVK_RightShift);
//        put(KeyEvent.VK_ALT, kVK_RightOption);
//        put(KeyEvent.VK_CONTROL, kVK_RightControl);
//        put(KeyEvent.VK_???, kVK_Function);
        put(KeyEvent.VK_F17, kVK_F17);
//        put(KeyEvent.VK_???, kVK_VolumeUp);
//        put(KeyEvent.VK_???, kVK_VolumeDown);
//        put(KeyEvent.VK_???, kVK_Mute);
        put(KeyEvent.VK_F18, kVK_F18);
        put(KeyEvent.VK_F19, kVK_F19);
        put(KeyEvent.VK_F20, kVK_F20);
        put(KeyEvent.VK_F5, kVK_F5);
        put(KeyEvent.VK_F6, kVK_F6);
        put(KeyEvent.VK_F7, kVK_F7);
        put(KeyEvent.VK_F3, kVK_F3);
        put(KeyEvent.VK_F8, kVK_F8);
        put(KeyEvent.VK_F9, kVK_F9);
        put(KeyEvent.VK_F11, kVK_F11);
        put(KeyEvent.VK_F13, kVK_F13);
        put(KeyEvent.VK_F16, kVK_F16);
        put(KeyEvent.VK_F14, kVK_F14);
        put(KeyEvent.VK_F10, kVK_F10);
        put(KeyEvent.VK_F12, kVK_F12);
        put(KeyEvent.VK_F15, kVK_F15);
        put(KeyEvent.VK_HELP, kVK_Help);
        put(KeyEvent.VK_HOME, kVK_Home);
        put(KeyEvent.VK_PAGE_UP, kVK_PageUp);
//        put(KeyEvent.VK_DELETE, kVK_ForwardDelete);
        put(KeyEvent.VK_F4, kVK_F4);
        put(KeyEvent.VK_END, kVK_End);
        put(KeyEvent.VK_F2, kVK_F2);
        put(KeyEvent.VK_PAGE_DOWN, kVK_PageDown);
        put(KeyEvent.VK_F1, kVK_F1);
        put(KeyEvent.VK_LEFT, kVK_LeftArrow);
        put(KeyEvent.VK_RIGHT, kVK_RightArrow);
        put(KeyEvent.VK_DOWN, kVK_DownArrow);
        put(KeyEvent.VK_UP, kVK_UpArrow);
//        put(KeyEvent.VK_???, kVK_ISO_Section);
//        put(KeyEvent.VK_BACK_SLASH, kVK_JIS_Yen);
        put(KeyEvent.VK_UNDERSCORE, kVK_JIS_Underscore);
//        put(KeyEvent.VK_COMMA, kVK_JIS_KeypadComma);
        put(KeyEvent.VK_ALPHANUMERIC, kVK_JIS_Eisu);
        put(KeyEvent.VK_HIRAGANA, kVK_JIS_Kana);
    }};

//#endregion

    int LMGetKbdType();
}
