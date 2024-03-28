/*
 * Copyright (c) 2024 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.gamecontroller;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Structure;


/**
 * A quaternion that represents a controller’s measurement of attitude.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2024-03-28 nsano initial version <br>
 */
public class GCQuaternion extends Structure {

    /** The value for the x-axis of the quaternion. */
    public double x;
    /** The value for the y-axis of the quaternion. */
    public double y;
    /** The value for the z-axis of the quaternion. */
    public double z;
    /** The value for the w-axis of the quaternion. */
    public double w;

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList("x", "y", "z", "w");
    }
}
