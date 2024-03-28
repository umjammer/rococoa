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
public class GCRotationRate extends Structure {

    /** The rotation rate around the x-axis in radians per second. */
    public double x;
    /** The rotation rate around the y-axis in radians per second. */
    public double y;
    /** The rotation rate around the z-axis in radians per second. */
    public double z;

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList("x", "y", "z");
    }
}
