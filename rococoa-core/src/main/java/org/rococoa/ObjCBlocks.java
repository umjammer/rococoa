/*
 * Copyright (c) 2024 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa;

import java.util.Arrays;
import java.util.List;
import com.sun.jna.NativeLibrary;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;



/**
 * ObjCBlocks.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2024/02/26 nsano initial version <br>
 */
public class ObjCBlocks {


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

    private static Pointer NSConcreteGlobalBlock;

    static {
        try {
            NSConcreteGlobalBlock = NativeLibrary.getInstance("objc").getGlobalVariableAddress("_NSConcreteGlobalBlock");
        } catch (UnsatisfiedLinkError e) { 
            // Fallback for tests or non-mac processing
            NSConcreteGlobalBlock = Pointer.NULL;
        }
    }

    /** utility conversion java closure to obj-c block */
    public static BlockLiteral block(ObjCBlock block) {
        BlockDescriptor descriptor = new BlockDescriptor();
        descriptor.reserved = new NativeLong(0);
        descriptor.block_size = new NativeLong(new BlockLiteral().size());
        descriptor.write();

        BlockLiteral literal = new BlockLiteral();
        literal.isa = NSConcreteGlobalBlock;
        literal.flags = BLOCK_IS_GLOBAL | BLOCK_HAS_SIGNATURE;
        literal.reserved = 0;
        literal.invoke = block;
        literal.descriptor = descriptor.getPointer();
        literal.write();
        
        return literal;
    }

    /** */
    public static class BlockDescriptor extends Structure {
        public NativeLong reserved;
        public NativeLong block_size;
        public String signature;
        

        public BlockDescriptor() {}
        public BlockDescriptor(Pointer p) { super(p); }
        
        @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList("reserved", "block_size", "signature");
        }
    }

    /** */
    public static class BlockLiteral extends Structure {
        public Pointer isa;
        public int flags;
        public int reserved;
        public ObjCBlock invoke;
        public Pointer descriptor;
        
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
}
