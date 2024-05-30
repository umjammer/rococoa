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
 * GCControllerDirectionPad.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2024-03-27 nsano initial version <br>
 */
public abstract class GCControllerDirectionPad extends GCControllerElement {

    static {
        GameControllerLibrary.library.toString();
    }

    public static final _Class CLASS = Rococoa.createClass("GCControllerDirectionPad", _Class.class);

    public interface _Class extends ObjCClass {
        GCControllerDirectionPad alloc();
    }

    @Override
    public abstract GCControllerDirectionPad init();

    // Accessing values using the axes

    /** The x-axis element of the directional pad. */
    public abstract GCControllerAxisInput xAxis();

    /** The y-axis element of the directional pad. */
    public abstract GCControllerAxisInput yAxis();

    // Accessing values using directional buttons

    /** The button element that changes the positive x-axis. */
    public abstract GCControllerButtonInput right();
    /** The button element that changes the negative x-axis. */
    public abstract GCControllerButtonInput left();
    /** The button element that changes the positive y-axis. */
    public abstract GCControllerButtonInput up();
    /** The button element used for the negative y-axis direction. */
    public abstract GCControllerButtonInput down();

    // Getting change information

    /** The block that the directional pad calls when the user changes its values. */
    public abstract /* GCControllerDirectionPadValueChangedHandler */ BlockLiteral valueChangedHandler();
    public abstract void setValueChangedHandler(BlockLiteral /* GCControllerDirectionPadValueChangedHandler */ handler);
    /** The signature for the block that executes when either axis changes values. */
    public interface GCControllerDirectionPadValueChangedHandler extends ObjCBlock {
        void callback(BlockLiteral literal, ID /* GCControllerDirectionPad */ dpad, float xValue, float yValue);
    }
}
