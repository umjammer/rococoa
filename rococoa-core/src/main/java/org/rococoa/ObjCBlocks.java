/*
 * Copyright (c) 2024 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa;

import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;

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
        public Pointer descriptor;
        public Pointer javaCallback;

        public BlockLiteral() {}
        public BlockLiteral(Pointer p) { super(p); }

        @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList("isa", "flags", "reserved", "invoke", "descriptor", "javaCallback");
        }
    }
}
