/*
 * Copyright (c) 2024 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.gamecontroller;

import java.util.logging.Logger;

import org.rococoa.ObjCClass;
import org.rococoa.Rococoa;
import org.rococoa.cocoa.foundation.NSObject;


/**
 * The locations of haptic actuators on a game controller.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2024-03-27 nsano initial version <br>
 */
public abstract class GCDeviceHaptics extends NSObject {

    static {
        GameControllerLibrary.library.toString();
    }

    private static final Logger logger = Logger.getLogger(GCDeviceHaptics.class.getName());

    public static final _Class CLASS = Rococoa.createClass("GCDeviceHaptics", _Class.class);

    public interface _Class extends ObjCClass {
        GCDeviceHaptics alloc();
    }

    //public abstract CHHapticEngine createEngineWithLocality(GCHapticsLocality locality);
}
