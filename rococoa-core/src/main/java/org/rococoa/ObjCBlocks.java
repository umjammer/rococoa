/*
 * BridJ - Dynamic and blazing-fast native interop for Java.
 * http://bridj.googlecode.com/
 *
 * Copyright (c) 2010-2015, Olivier Chafik (http://ochafik.com/)
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of Olivier Chafik nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY OLIVIER CHAFIK AND CONTRIBUTORS ``AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE REGENTS AND CONTRIBUTORS BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.rococoa;

import com.sun.jna.Callback;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import org.rococoa.internal.RococoaLibrary;


public class ObjCBlocks {

    private static final RococoaLibrary rococoaLibrary;

    static {
        rococoaLibrary = Native.load("rococoa", RococoaLibrary.class);
    }

    /** */
    static synchronized Pointer createObjCBlockWithFunctionPointer(Pointer pcb) {
        Pointer pointer = new Pointer(rococoaLibrary.createObjCBlockWithFunctionPointer(Pointer.nativeValue(pcb)));
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            releaseObjCBlock(pointer);
        }));
        return pointer;
    }

    /** */
    static synchronized Pointer getObjCBlockFunctionPointer(Pointer blockPtr) {
        return new Pointer(rococoaLibrary.getObjCBlockFunctionPointer(Pointer.nativeValue(blockPtr)));
    }

    /** */
    static synchronized void releaseObjCBlock(Pointer pBlock) {
        rococoaLibrary.releaseObjCBlock(Pointer.nativeValue(pBlock));
    }

    void x(ObjCObject instance) {
        if (instance instanceof ObjCBlock) {

//            Pointer pcb = registerCallbackInstance((Callback) instance);
//            ((ObjCBlock) instance).pCallback = pcb;

//            Pointer pBlock = createObjCBlockWithFunctionPointer(pcb);
        }
    }
}
