/*
 * Copyright (c) 2022 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.coregraphics;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;


/**
 * CGPoint.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2022-09-04 nsano initial version <br>
 */
public class CGPoint extends Structure {

    public double x;
    public double y;

    public CGPoint() {
    }

    public CGPoint(Pointer pointer) {
        super(pointer);
    }

    public CGPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "CGPoint{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList("x", "y");
    }
}
