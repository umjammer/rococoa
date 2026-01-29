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
public abstract class GCDeviceLight extends NSObject {

    static {
        GameControllerLibrary.library.toString();
    }

    private static final _Class CLASS = Rococoa.createClass("GCDeviceLight", _Class.class);

    private interface _Class extends ObjCClass {
        GCDeviceLight alloc();
    }

    public abstract GCDeviceLight init();

    /** The color of a device’s light. */
    public abstract GCColor color();
}
