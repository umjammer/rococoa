/*
 * Copyright (c) 2024 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.gamecontroller;

import java.util.logging.Logger;

import org.rococoa.ObjCClass;
import org.rococoa.Rococoa;
import org.rococoa.cocoa.foundation.NSDictionary;
import org.rococoa.cocoa.foundation.NSObject;
import org.rococoa.cocoa.foundation.NSSet;


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

    private static final Logger logger = Logger.getLogger(GCDeviceLight.class.getName());

    public static final _Class CLASS = Rococoa.createClass("GCDeviceLight", _Class.class);

    public interface _Class extends ObjCClass {
        GCDeviceLight alloc();
    }

    public abstract GCDeviceLight init();

    /** The color of a device’s light. */
    public abstract GCColor color();
}
