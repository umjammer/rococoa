/*
 * Copyright (c) 2024 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa;

import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import org.rococoa.internal.RococoaNative;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;


/**
 * ObjCBlocks.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2024/02/26 nsano initial version <br>
 */
public class ObjCBlocks {

    private static final Logger logging = Logger.getLogger("org.rococoa.foundation");

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
        BlockLiteral literal = new BlockLiteral();
        literal.isa = Foundation.getRococoaLibrary().get_NSConcreteStackBlock();
        literal.flags = BLOCK_HAS_COPY_DISPOSE;
        literal.reserved = 0;
        literal.invoke = block;
        literal.descriptor.reserved = new NativeLong(0);
        literal.descriptor.block_size = new NativeLong(literal.size());
        literal.descriptor.copy_helper = Foundation.getRococoaLibrary().get_block_copy_helper();
        literal.descriptor.dispose_helper = Foundation.getRococoaLibrary().get_block_dispose_helper();
        literal.javaCallback = RococoaNative.getJObject(block);
        literal.write();
        return literal;
    }

    /** */
    public static class BlockDescriptor extends Structure {
        public NativeLong reserved;
        public NativeLong block_size;
        public Pointer copy_helper;
        public Pointer dispose_helper;

        public BlockDescriptor() {}

        @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList("reserved", "block_size", "copy_helper", "dispose_helper");
        }
    }

    /** */
    public static class BlockLiteral extends Structure {
        public Pointer isa = Pointer.NULL;
        public int flags;
        public int reserved;
        public ObjCBlock invoke;
        public BlockDescriptor descriptor = new BlockDescriptor();
        public Pointer javaCallback;

        public BlockLiteral() {}
        public BlockLiteral(Pointer p) { super(p); }

        @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList("isa", "flags", "reserved", "invoke", "descriptor", "javaCallback");
        }
    }
}
