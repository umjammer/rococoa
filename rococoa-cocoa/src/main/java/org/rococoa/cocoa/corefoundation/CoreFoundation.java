/*
 * Copyright (c) 2023 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.corefoundation;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;

import com.sun.jna.Callback;
import com.sun.jna.CallbackReference;
import com.sun.jna.Library;
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.PointerType;
import com.sun.jna.Structure;
import com.sun.jna.ptr.ByReference;
import org.rococoa.cocoa.CFIndex;


/**
 * CoreFoundation.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2023-12-22 nsano initial version <br>
 */
public interface CoreFoundation extends Library {

    CoreFoundation library = Native.load("CoreFoundation", CoreFoundation.class);

    NativeLibrary NATIVE_LIBRARY = NativeLibrary.getInstance("CoreFoundation");

//#region CFString

    int kCFStringEncodingUTF8 = 0x8000100;

    CFIndex CFStringGetLength(CFStringRef theString);
    CFIndex CFStringGetLength(Pointer theString);

    boolean CFStringGetCString(CFStringRef theString, ByteBuffer buffer, NativeLong bufferSize, int encoding);

    CFStringRef CFStringCreateWithCharacters(CFAllocatorRef allocator, char[] chars, CFIndex index);

//#endregion

//#region CFAllocator


//#endregion

//#region Polymorphic

    /**
     * Releases a Core Foundation object. If the retain count of cf becomes zero the memory allocated to the object is
     * deallocated and the object is destroyed. If you create, copy, or explicitly retain (see the CFRetain function) a
     * Core Foundation object, you are responsible for releasing it when you no longer need it (see Memory Management
     * Programming Guide for Core Foundation).
     *
     * @param ref A CFType object to release. This value must not be NULL.
     */
    void CFRelease(Pointer ref);
    void CFRelease(PointerType ref);

//#endregion

//#region FRunLoop

    void CFRunLoopAddSource(Pointer /* CFRunLoopRef */ rl, Pointer/*CFRunLoopSourceRef*/ source, CFStringRef /* CFRunLoopMode */ mode);

    Pointer /* CFRunLoopSourceRef */ CFMachPortCreateRunLoopSource(CFAllocatorRef allocator, Pointer /* CFMachPortRef */ port, CFIndex order);

    CFStringRef kCFRunLoopCommonModes = CFStringRef.toCFString("kCFRunLoopCommonModes");

    Pointer CFRunLoopGetCurrent();

    void CFRunLoopRun();

    void CFRunLoopStop(Pointer runningLoop);

//#endregion

//#region CFDictionary

    interface CFDictionaryRetainCallBack extends Callback {

        Pointer invoke(CFAllocatorRef allocator, Pointer value);
    }

    interface CFDictionaryReleaseCallBack extends Callback {

        void invoke(CFAllocatorRef allocator, Pointer value);
    }

    interface CFDictionaryCopyDescriptionCallBack extends Callback {

        CFStringRef invoke(Pointer value);
    }

    interface CFDictionaryEqualCallBack extends Callback {

        boolean invoke(Pointer value1, Pointer value2);
    }

    interface CFDictionaryHashCallBack extends Callback {

        NativeLong invoke(Pointer value);
    }

    class CFDictionaryKeyCallBacks extends Structure {

        public NativeLong version;
        public CFDictionaryRetainCallBack retain;
        public CFDictionaryReleaseCallBack release;
        public CFDictionaryCopyDescriptionCallBack copyDescription;
        public CFDictionaryEqualCallBack equal;
        public CFDictionaryHashCallBack hash;

        Memory memory;

        public CFDictionaryKeyCallBacks() {
            setAutoWrite(false);
            memory = new Memory(8 * 6);
            useMemory(memory);
        }

        public CFDictionaryKeyCallBacks(Pointer p) {
            this();
            version = getPointer().getNativeLong(0);
            retain = (CFDictionaryRetainCallBack) CallbackReference.getCallback(CFDictionaryRetainCallBack.class, p.getPointer(0x08));
            release = (CFDictionaryReleaseCallBack) CallbackReference.getCallback(CFDictionaryReleaseCallBack.class, p.getPointer(0x10));
            copyDescription = (CFDictionaryCopyDescriptionCallBack) CallbackReference.getCallback(CFDictionaryCopyDescriptionCallBack.class, p.getPointer(0x18));
            equal = (CFDictionaryEqualCallBack) CallbackReference.getCallback(CFDictionaryEqualCallBack.class, p.getPointer(0x20));
            hash = (CFDictionaryHashCallBack) CallbackReference.getCallback(CFDictionaryHashCallBack.class, p.getPointer(0x28));
            write();
        }

        @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList("version", "retain", "release", "copyDescription", "equal", "hash");
        }
    }

    class CFDictionaryValueCallBacks extends Structure {

        public NativeLong version;
        public CFDictionaryRetainCallBack retain;
        public CFDictionaryReleaseCallBack release;
        public CFDictionaryCopyDescriptionCallBack copyDescription;
        public CFDictionaryEqualCallBack equal;

        Memory memory;

        public CFDictionaryValueCallBacks() {
            setAutoWrite(false);
            memory = new Memory(8 * 5);
            useMemory(memory);
        }

        public CFDictionaryValueCallBacks(Pointer p) {
            this();
            version = getPointer().getNativeLong(0);
            retain = (CFDictionaryRetainCallBack) CallbackReference.getCallback(CFDictionaryRetainCallBack.class, p.getPointer(0x08));
            release = (CFDictionaryReleaseCallBack) CallbackReference.getCallback(CFDictionaryReleaseCallBack.class, p.getPointer(0x10));
            copyDescription = (CFDictionaryCopyDescriptionCallBack) CallbackReference.getCallback(CFDictionaryCopyDescriptionCallBack.class, p.getPointer(0x18));
            equal = (CFDictionaryEqualCallBack) CallbackReference.getCallback(CFDictionaryEqualCallBack.class, p.getPointer(0x20));
            write();
        }

        @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList("version", "retain", "release", "copyDescription", "equal");
        }
    }

    CFDictionaryKeyCallBacks kCFCopyStringDictionaryKeyCallBacks = new CFDictionaryKeyCallBacks(NATIVE_LIBRARY.getGlobalVariableAddress("kCFCopyStringDictionaryKeyCallBacks"));

    CFDictionaryKeyCallBacks kCFTypeDictionaryKeyCallBacks = new CFDictionaryKeyCallBacks(NATIVE_LIBRARY.getGlobalVariableAddress("kCFTypeDictionaryKeyCallBacks"));

    CFDictionaryValueCallBacks kCFTypeDictionaryValueCallBacks = new CFDictionaryValueCallBacks(NATIVE_LIBRARY.getGlobalVariableAddress("kCFTypeDictionaryValueCallBacks"));

    void CFDictionaryAddValue(Pointer /* CFDictionaryRef */ theDict, CFStringRef key, Pointer value);

    Pointer /* CFDictionaryRef */ CFDictionaryCreateMutable(CFAllocatorRef allocator, NativeLong capacity, CFDictionaryKeyCallBacks keyCallBacks, CFDictionaryValueCallBacks valueCallBacks);

    boolean CFDictionaryGetValueIfPresent(Pointer /* CFDictionaryRef */ theDict, Pointer key, ByReference value);

//#endregion

//#region CFData

    Pointer CFDataGetBytePtr(Pointer /* CFDataRef */ theData);

//#endregion
}
