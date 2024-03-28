/*
 * Copyright (c) 2024 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.gamecontroller;

import java.util.logging.Logger;

import org.rococoa.ID;
import org.rococoa.ObjCBlock;
import org.rococoa.ObjCBlocks.BlockLiteral;
import org.rococoa.ObjCClass;
import org.rococoa.Rococoa;


/**
 * GCMotion.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2024-03-27 nsano initial version <br>
 */
public abstract class GCMotion extends GCControllerElement {

    static {
        GameControllerLibrary.library.toString();
    }

    private static final Logger logger = Logger.getLogger(GCMotion.class.getName());

    public static final _Class CLASS = Rococoa.createClass("GCMotion", _Class.class);

    public interface _Class extends ObjCClass {
        GCMotion alloc();
    }

    public abstract GCMotion init();

    /** A Boolean value that indicates whether the controller provides attitude data. */
    public abstract boolean hasAttitude();
    /** A Boolean value that indicates whether the controller provides rotation data. */
    public abstract boolean hasRotationRate();
    /** A Boolean value that indicates whether the controller provides gravity and user acceleration data. */
    public abstract boolean hasGravityAndUserAcceleration();

    /** The attitude of the controller. */
    public abstract GCQuaternion attitude();

    /** The rotation rate of the controller. */
    public abstract GCRotationRate rotationRate();

    /**
     * The total acceleration of the controller that includes gravity and the acceleration the user applies to the
     * controller.
     */
    public abstract GCAcceleration acceleration();

    /** The gravity acceleration vector from the controller’s reference frame. */
    public abstract GCAcceleration gravity();

    /** The acceleration that the user applies to the controller. */
    public abstract GCAcceleration userAcceleration();
}
