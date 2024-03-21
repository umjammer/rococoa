/*
 * Copyright (c) 2023 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.corefoundation;

import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.nio.ByteBuffer;

import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.PointerType;
import org.rococoa.cocoa.CFIndex;

import static java.lang.System.getLogger;
import static org.rococoa.cocoa.corefoundation.CoreFoundation.kCFStringEncodingUTF8;
import static org.rococoa.cocoa.corefoundation.CoreFoundation.library;


public class CFStringRef extends PointerType {

    private static final Logger logger = getLogger(CFStringRef.class.getName());

    public CFStringRef() {
    }

    public CFStringRef(Pointer p) {
        super(p);
    }

    public static CFStringRef toCFString(String s) {
        char[] chars = s.toCharArray();
        int length = chars.length;
        return library.CFStringCreateWithCharacters(null, chars, CFIndex.of(length));
    }

    @Override
    public String toString() {
logger.log(Level.DEBUG, "CFStringRef: " + getPointer());
        int lengthInChars = library.CFStringGetLength(getPointer()).intValue();
        NativeLong potentialLengthInBytes = new NativeLong(3L * lengthInChars + 1); // UTF8 fully escaped 16 bit chars, plus nul

        ByteBuffer buffer = ByteBuffer.allocate(potentialLengthInBytes.intValue());
        boolean ok = library.CFStringGetCString(this, buffer, potentialLengthInBytes, kCFStringEncodingUTF8);
        if (!ok)
            throw new RuntimeException("Could not convert string");
        return Native.toString(buffer.array());
    }
}