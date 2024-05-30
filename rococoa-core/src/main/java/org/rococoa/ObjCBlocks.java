/*
 * Copyright (c) 2024 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa;

import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.util.Arrays;
import java.util.List;

import com.sun.jna.Memory;
import com.sun.jna.NativeLibrary;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;

import static java.lang.System.getLogger;


/**
 * ObjCBlocks.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2024/02/26 nsano initial version <br>
 */
public class ObjCBlocks {

    private static final Logger logger = getLogger("org.rococoa.foundation");

    //    ↓    ↓    ↓    ↓    ↓    ↓    ↓   ↓↓
    //    2    2    2    1    1
    //    8    4    0    6    2    8    4   10
    // .... .... .... .... .... .... .... ....
    //

    public static final int BLOCK_IS_NOESCAPE = 1 << 23;
    public static final int BLOCK_HAS_COPY_DISPOSE =  1 << 25;
    /** helpers have C++ code */
    public static final int BLOCK_HAS_CTOR = 1 << 26;
    public static final int BLOCK_IS_GLOBAL = 1 << 28;
    /** IFF BLOCK_HAS_SIGNATURE */
    public static final int BLOCK_HAS_STRET = 1 << 29;
    public static final int BLOCK_HAS_SIGNATURE = 1 << 30;

    /** utility conversion java closure to obj-c block */
    public static BlockLiteral block(ObjCBlock block) {
        Memory m = new Memory(48);
        BlockLiteral literal = new BlockLiteral(m);
        literal.flags = 0;
logger.log(Level.TRACE, String.format("block: %s, %08x", block, literal.flags));
        literal.invoke = block;
        literal.write();
        return literal;
    }

    /** */
    public static class BlockDescriptor extends Structure {
        public NativeLong reserved;
        public NativeLong block_size;
        public Pointer rest;
        public BlockDescriptor() {}
        @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList("reserved", "block_size", "rest");
        }
    }

    /** */
    public static class BlockLiteral extends Structure {
        public Pointer isa = Pointer.NULL;
        public int flags;
        public int reserved;
        public ObjCBlock invoke;
        public BlockDescriptor descriptor;
        public BlockLiteral() {}
        public BlockLiteral(Pointer p) { super(p); }
        @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList("isa", "flags", "reserved", "invoke", "descriptor");
        }

        /** by GPTo: doesn't work */
        public void retain() {
            NativeLibrary.getInstance("objc").getFunction("objc_retain").invoke(Void.class, new Object[] {this});
        }

        /** by GPTo: doesn't work */
        public void release() {
            NativeLibrary.getInstance("objc").getFunction("objc_release").invoke(Void.class, new Object[] {this});
        }
    }

    /** TODO doesn't work */
    public static BlockLiteral block2(ObjCBlock block) {
        BlockLiteral literal = new BlockLiteral(Foundation.getRococoaLibrary().createObjCBlock());
        literal.flags = 0;
        literal.invoke = block; // got error. Block_copy returns heap doesn't it?
        literal.write();
        return literal;
    }
}
