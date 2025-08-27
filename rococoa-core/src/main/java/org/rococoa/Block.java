/*
 * Copyright (c) 2024 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa;

import com.sun.jna.NativeLong;
import org.rococoa.ObjCBlocks.BlockDescriptor;
import org.rococoa.ObjCBlocks.BlockLiteral;
import org.rococoa.internal.RococoaNative;


/**
 * Represents an Objective-C block.
 * <p>
 * IMPORTANT: you must maintain a strong reference to the {@link Block} instance
 * as long as the native code might call the block.
 * </p>
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2024/03/12 nsano initial version <br>
 */
public class Block {

    private final BlockLiteral literal;
    private final BlockDescriptor descriptor;
    private final ObjCBlock callback;

    /**
     * @param callback must not be a lambda directly
     */
    public Block(ObjCBlock callback) {
        this.callback = callback;
        this.descriptor = new BlockDescriptor();
        this.literal = new BlockLiteral();

        this.descriptor.reserved = new NativeLong(0);
        this.descriptor.block_size = new NativeLong(this.literal.size());
        this.descriptor.copy_helper = Foundation.getRococoaLibrary().get_block_copy_helper();
        this.descriptor.dispose_helper = Foundation.getRococoaLibrary().get_block_dispose_helper();
        this.descriptor.write();

        this.literal.isa = Foundation.getRococoaLibrary().get_NSConcreteStackBlock();
        this.literal.flags = ObjCBlocks.BLOCK_HAS_COPY_DISPOSE;
        this.literal.reserved = 0;
        this.literal.invoke = this.callback;
        this.literal.descriptor = this.descriptor.getPointer();
        this.literal.javaCallback = RococoaNative.getJObject(this.callback);
        this.literal.write();
    }

    public BlockLiteral getLiteral() {
        return literal;
    }
}
