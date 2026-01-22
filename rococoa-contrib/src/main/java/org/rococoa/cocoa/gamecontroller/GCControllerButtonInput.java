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
 * GCControllerButtonInput.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2024-03-27 nsano initial version <br>
 */
public abstract class GCControllerButtonInput extends GCControllerElement {

    static {
        GameControllerLibrary.library.toString();
    }

    private static final _Class CLASS = Rococoa.createClass("GCControllerButtonInput", _Class.class);

    private interface _Class extends ObjCClass {
        GCControllerButtonInput alloc();
    }

    @Override
    public abstract GCControllerButtonInput init();

    /** A Boolean value that indicates whether the user is touching the button. */
    public abstract boolean touched();
    /** A Boolean value that indicates whether the user is pressing the button. */
    public abstract boolean pressed();
    /** The level of pressure the user is applying to the button. */
    public abstract float value();

    /** The block that the element calls when the user touches the button. */
    public abstract BlockLiteral /* GCControllerButtonTouchedChangedHandler */ touchedChangedHandler();
    public abstract void setTouchedChangedHandler(BlockLiteral /* GCControllerButtonTouchedChangedHandler */ handler);
    /** The signature for the block that executes when the user touches the button if the controller supports that feature. */
    public interface GCControllerButtonTouchedChangedHandler extends ObjCBlock {
        void callbak(BlockLiteral literal, ID /* GCControllerButtonInput */ button, float value, boolean pressed, boolean touched);
    }
    /** The block that the element calls when the user presses or releases the button. */
    public abstract BlockLiteral /* GCControllerButtonValueChangedHandler */ pressedChangedHandler();
    public abstract void setPressedChangedHandler(BlockLiteral /* GCControllerButtonValueChangedHandler */ handler);
    /** The block that the element calls when the user changes the level of pressure on the button. */
    public abstract BlockLiteral /* GCControllerButtonValueChangedHandler */ valueChangedHandler();
    public abstract void setValueChangedHandler(BlockLiteral /* GCControllerButtonValueChangedHandler */  handler);
    /** The signature for the block that executes when a button’s state changes. */
    public interface GCControllerButtonValueChangedHandler extends ObjCBlock {
        void callbak(BlockLiteral literal, ID /* GCControllerButtonInput */ button, float value, boolean pressed);
    }
}
