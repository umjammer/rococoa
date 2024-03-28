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
 * A structure that represents rotation rates around the x, y, and z axes.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2024-03-28 nsano initial version <br>
 */
public class GCEulerAngles extends Structure {

    /** The pitch of the controller in radians. */
    public double pitch;
    /** The yaw of the device in radians. */
    public double yaw;
    /** The roll of the controller in radians. */
    public double roll;

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList("pitch", "yaw", "roll");
    }
}
