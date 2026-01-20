/*
 * Copyright (c) 2024 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.gamecontroller;

import org.rococoa.ID;
import org.rococoa.ObjCBlock;
import org.rococoa.ObjCBlocks.BlockLiteral;
import org.rococoa.ObjCClass;
import org.rococoa.Rococoa;


/**
 * GCControllerAxisInput.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2024-03-27 nsano initial version <br>
 */
public abstract class GCControllerAxisInput extends GCControllerElement {

    static {
        GameControllerLibrary.library.toString();
    }

    public static final _Class CLASS = Rococoa.createClass("GCControllerAxisInput", _Class.class);

    public interface _Class extends ObjCClass {
        GCControllerAxisInput alloc();
    }

    @Override
    public abstract GCControllerAxisInput init();

    // Accessing the input values

    /** The current value of the axis. */
    public abstract float value();

    /** Sets the normalized value of the axis. */
    public abstract void setValue(float value);

    // Getting change information

    /** The block that the element calls when the user changes the axis value. */
    public abstract BlockLiteral /* GCControllerAxisValueChangedHandler */ valueChangedHandler();
    public abstract void setValueChangedHandler(BlockLiteral /* GCControllerAxisValueChangedHandler */ handler);
    /** The signature for the block that executes when the user changes the axis value. */
    public interface GCControllerAxisValueChangedHandler extends ObjCBlock {
        void callback(BlockLiteral literal, ID /* GCControllerAxisInput */ axis, float value);
    }
}
