/*
 * Copyright (c) 2023 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.carbon;

import com.sun.jna.Pointer;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.rococoa.cocoa.corefoundation.CFStringRef;
import vavi.util.Debug;
import vavi.util.StringUtil;

import static org.junit.jupiter.api.Assertions.*;
import static org.rococoa.carbon.CarbonCoreLibrary.kTISPropertyUnicodeKeyLayoutData;
import static org.rococoa.carbon.CarbonCoreLibrary.library;


class CarbonCoreLibraryTest {

    @Test
    void test1() throws Exception {
         int r = library.LMGetKbdType();
Debug.println("LMGetKbdType: " + r);
    }

    @Test
    void test2() throws Exception {
        Pointer s = library.TISCopyCurrentKeyboardInputSource();
Debug.println("TISCopyCurrentKeyboardInputSource: " + s);
        Pointer p = library.TISGetInputSourceProperty(s, CFStringRef.toCFString("TISPropertyUnicodeKeyLayoutData"));
Debug.println("TISGetInputSourceProperty: " + p);
    }

    @Test
    @Disabled("TODO kTISPropertyUnicodeKeyLayoutData cause crash")
    void test3() throws Exception {
        Pointer p = CarbonCoreLibrary.NATIVE_LIBRARY.getGlobalVariableAddress("kTISPropertyUnicodeKeyLayoutData");
Debug.printf("pointer: %08x", Pointer.nativeValue(p));
        byte[] bytes = new byte[32];
        p.read(0, bytes, 0, bytes.length);
Debug.println("\n" + StringUtil.getDump(bytes));
Debug.println("kTISPropertyUnicodeKeyLayoutData: " + kTISPropertyUnicodeKeyLayoutData);
    }
}