/*
 * Copyright (c) 2023 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.carbon;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;
import org.junit.jupiter.api.Test;
import org.rococoa.cocoa.corefoundation.CFStringRef;
import vavi.util.Debug;
import vavi.util.StringUtil;

import static org.junit.jupiter.api.Assertions.assertNotNull;
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
        Pointer s = library.TISCopyCurrentKeyboardLayoutInputSource();
Debug.println("TISCopyCurrentKeyboardInputSource: " + s);
        Pointer p = library.TISGetInputSourceProperty(s, kTISPropertyUnicodeKeyLayoutData);
Debug.println("TISGetInputSourceProperty: " + p);
        assertNotNull(p);
    }

    @Test
    void test3() throws Exception {
        PointerByReference pp = new PointerByReference(CarbonCoreLibrary.NATIVE_LIBRARY.getGlobalVariableAddress("kTISPropertyUnicodeKeyLayoutData"));
        Pointer p = pp.getValue().getPointer(0);
        assertNotNull(p);
Debug.printf("pointer: %s", p);
Debug.printf("pointer: %s", new CFStringRef(p));
        byte[] bytes = new byte[32];
        p.read(0, bytes, 0, bytes.length);
Debug.println("\n" + StringUtil.getDump(bytes));
    }

    @Test
    void test4() throws Exception {
        PointerByReference pp = new PointerByReference(CarbonCoreLibrary.NATIVE_LIBRARY.getGlobalVariableAddress("kTISPropertyUnicodeKeyLayoutData"));
        Pointer kTISPropertyUnicodeKeyLayoutData = pp.getValue().getPointer(0);
Debug.println("kTISPropertyUnicodeKeyLayoutData: " + kTISPropertyUnicodeKeyLayoutData);
        Pointer s = library.TISCopyCurrentKeyboardLayoutInputSource();
Debug.println("TISCopyCurrentKeyboardInputSource: " + s);
        Pointer p = library.TISGetInputSourceProperty(s, new CFStringRef(kTISPropertyUnicodeKeyLayoutData));
Debug.println("TISGetInputSourceProperty: " + p);
        assertNotNull(p);
    }
}