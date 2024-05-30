/*
 * Copyright (c) 2024 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.gamecontroller;

import org.rococoa.ObjCClass;
import org.rococoa.Rococoa;
import org.rococoa.cocoa.foundation.NSInteger;
import org.rococoa.cocoa.foundation.NSObject;


/**
 * The charge level and state of a device’s battery.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2024-03-27 nsano initial version <br>
 */
public abstract class GCDeviceBattery extends NSObject {

    static {
        GameControllerLibrary.library.toString();
    }

    public static final _Class CLASS = Rococoa.createClass("GCDeviceBattery", _Class.class);

    public interface _Class extends ObjCClass {
        GCDeviceBattery alloc();
    }

    /** The charge level of a device’s battery. */
    public abstract float batteryLevel();
    /** The state of a device’s battery. */
    public abstract NSInteger /* GCDeviceBatteryState */ batteryState();
}
