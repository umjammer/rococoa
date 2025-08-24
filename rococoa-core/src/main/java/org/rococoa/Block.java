/*
 * Copyright (c) 2024 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa;

import org.rococoa.ObjCBlocks.BlockLiteral;


/**
 * Represents an Objective-C block.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2024/03/12 nsano initial version <br>
 */
public class Block {

    private final BlockLiteral literal;
    private final ObjCBlock callback;

    /**
     * @param callback must not be a lambda directly
     */
    public Block(ObjCBlock callback) {
        this.callback = callback;
        this.literal = ObjCBlocks.block(callback);
    }

    public BlockLiteral getLiteral() {
        return literal;
    }
}
