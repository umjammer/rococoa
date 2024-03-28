/*
 * Copyright (c) 2024 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.gamecontroller;

import java.util.logging.Logger;

import javax.crypto.Mac;

import org.rococoa.ID;
import org.rococoa.ObjCBlock;
import org.rococoa.ObjCBlocks.BlockLiteral;
import org.rococoa.ObjCClass;
import org.rococoa.Rococoa;
import org.rococoa.cocoa.foundation.NSInteger;


/**
 * GCControllerTouchpad.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2024-03-27 nsano initial version <br>
 */
public abstract class GCControllerTouchpad extends GCControllerElement {

    static {
        GameControllerLibrary.library.toString();
    }

    private static final Logger logger = Logger.getLogger(GCControllerTouchpad.class.getName());

    public static final _Class CLASS = Rococoa.createClass("GCControllerTouchpad", _Class.class);

    public interface _Class extends ObjCClass {

        GCControllerTouchpad alloc();
    }

    public abstract GCControllerTouchpad init();

    /** The element that represents the state of the user’s touch on the surface of the touchpad. */
    public abstract GCControllerDirectionPad touchSurface();

    /** The element that represents the button component on the touchpad. */
    public abstract GCControllerButtonInput button();

    /** The state of the user’s touch on the surface of the touchpad. */
    public abstract NSInteger /* GCTouchState */ touchState();
}
