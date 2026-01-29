/*
 * Copyright (c) 2024 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.gamecontroller;

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

    private static final _Class CLASS = Rococoa.createClass("GCControllerTouchpad", _Class.class);

    private interface _Class extends ObjCClass {

        GCControllerTouchpad alloc();
    }

    @Override
    public abstract GCControllerTouchpad init();

    /** The element that represents the state of the user’s touch on the surface of the touchpad. */
    public abstract GCControllerDirectionPad touchSurface();

    /** The element that represents the button component on the touchpad. */
    public abstract GCControllerButtonInput button();

    /** The state of the user’s touch on the surface of the touchpad. */
    public abstract NSInteger /* GCTouchState */ touchState();
}
