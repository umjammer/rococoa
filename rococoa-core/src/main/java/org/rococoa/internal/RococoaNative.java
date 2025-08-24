/*
 * Copyright (c) 2024 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.internal;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import org.rococoa.ObjCBlock;


/**
 * RococoaNative.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2024/03/13 nsano initial version <br>
 */
public class RococoaNative {

    /** JNA library for Rococoa native functions */
    private interface RococoaNativeLibrary extends Library {
        void Java_org_rococoa_internal_RococoaNative_cache_1jvm();
        Pointer Java_org_rococoa_internal_RococoaNative_getJObject(Object o);
    }

    private static final RococoaNativeLibrary nativeLib;

    static {
        nativeLib = Native.load("rococoa", RococoaNativeLibrary.class);
        nativeLib.Java_org_rococoa_internal_RococoaNative_cache_1jvm();
    }

    public static Pointer getJObject(Object o) {
        return nativeLib.Java_org_rococoa_internal_RococoaNative_getJObject(o);
    }
}
