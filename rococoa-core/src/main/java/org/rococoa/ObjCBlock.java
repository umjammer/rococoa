/*
 * Copyright (c) 2022 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa;

import com.sun.jna.Callback;
import org.rococoa.ObjCBlocks.BlockLiteral;


/**
 * ObjCBlock.
 * <p>
 * extended class's method must have {@link BlockLiteral} as a 1st argument.
 * TODO hide this from user
 * </p>
 * e.g.
 * <pre>
 *     // for int (^MyBlock)(int)
 *     public interface MyBlock extends ObjCBlock {
 *         int apply(BlockLiteral literal, int n);
 *     }
 * </pre>
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2022/10/22 nsano initial version <br>
 */
public interface ObjCBlock extends Callback {
}
