/*
 * Copyright (c) 2023 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.gamecontroller;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef.BOOL;
import org.rococoa.ObjCClass;
import org.rococoa.Rococoa;
import org.rococoa.cocoa.foundation.NSArray;
import org.rococoa.cocoa.foundation.NSObject;


/**
 * GCController.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2023-05-18 nsano initial version <br>
 * @see "https://developer.apple.com/documentation/gamecontroller/gccontroller?language=objc"
 */
public abstract class GCController extends NSObject {

    static {
        GameControllerLibrary.library.toString();
    }

    private static final Logger logger = Logger.getLogger(GCController.class.getName());

    public static final _Class CLASS = Rococoa.createClass("GCController", _Class.class);

    public interface _Class extends ObjCClass {
        GCController alloc();
        NSArray controllers();
        boolean supportsHIDDevice(Pointer /* IOHIDDeviceRef */ device);
    }

    public abstract GCController init();

    /** A notification that posts when a controller becomes the current controller. */
    public static final String GCControllerDidConnectNotification = "GCControllerDidConnectNotification";

    /** A notification that posts when a controller stops being the current controller. */
     public static final String GCControllerDidDisconnectNotification = "GCControllerDidDisconnectNotification";

    /** Returns cast ready controllers list. */
    public static List<GCController> controllers() {
        List<GCController> result = new ArrayList<>();
        NSArray a = GCController.CLASS.controllers();
logger.fine("controllers: " + a.count());
        for (int i = 0; i < a.count(); i++) {
            result.add(Rococoa.cast(a.objectAtIndex(i), GCController.class));
        }
        return result;
    }

    /** Returns the connected controllers for the device. */
    public abstract GCExtendedGamepad extendedGamepad();

    /** The most recently used game controller. */
    public abstract GCController current();

    /** The motion input profile. */
    public abstract GCMotion motion();

    /** The controller’s battery information. */
    public abstract GCDeviceBattery battery();
    /** The controller’s haptics information. */
    public abstract GCDeviceHaptics haptics();
    /** The controller’s light settings. */
    public abstract GCDeviceLight light();
}
