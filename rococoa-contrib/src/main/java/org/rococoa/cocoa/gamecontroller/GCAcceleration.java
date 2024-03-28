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
 * A three-dimensional acceleration vector.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2024-03-28 nsano initial version <br>
 */
public class GCAcceleration extends Structure {

    /** The acceleration measurement along the x-axis, in multiples of earth’s gravity. */
    public double x;
    /** The acceleration measurement along the y-axis, in multiples of earth’s gravity. */
    public double y;
    /** The acceleration measurement along the z-axis, in multiples of earth’s gravity. */
    public double z;

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList("x", "y", "z");
    }
}
