/*
 * Copyright (c) 2023 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.gamecontroller;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

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
        NSArray controllers();
        GCController alloc();
    }

    public abstract GCController init();

    public static final String GCControllerDidConnectNotification = "GCControllerDidConnectNotification";

    public static final String GCControllerDidDisconnectNotification = "GCControllerDidDisconnectNotification";

    public static List<GCController> controllers() {
        List<GCController> result = new ArrayList<>();
        NSArray a = GCController.CLASS.controllers();
logger.fine("controllers: " + a.count());
        for (int i = 0; i < a.count(); i++) {
            result.add(Rococoa.cast(a.objectAtIndex(i), GCController.class));
        }
        return result;
    }
}
