/*
 * Copyright (c) 2024 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.gamecontroller;

import org.rococoa.ObjCClass;
import org.rococoa.Rococoa;
import org.rococoa.cocoa.foundation.NSObject;


/**
 * The colored light on a device.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2024-03-27 nsano initial version <br>
 */
public abstract class GCColor extends NSObject {

    static {
        GameControllerLibrary.library.toString();
    }

    public static final _Class CLASS = Rococoa.createClass("GCDeviceLight", _Class.class);

    public interface _Class extends ObjCClass {
        GCColor alloc();
    }

    /** Creates a color with the specified red, green, and blue values. */
    public abstract GCColor initWithRed_green_blue(float red, float green, float blue);

    /** The normalized value of the red component ranging from 0 to 1. */
    public abstract float red();

    /** The normalized value of the green component ranging from 0 to 1. */
    public abstract float green();

    /** The normalized value of the blue component ranging from 0 to 1. */
    public abstract float blue();
}
