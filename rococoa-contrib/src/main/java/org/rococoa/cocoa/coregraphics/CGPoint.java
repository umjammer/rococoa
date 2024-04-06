/*
 * Copyright (c) 2022 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.coregraphics;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Structure;
import org.rococoa.cocoa.CGFloat;


/**
 * CGPoint.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2022-09-04 nsano initial version <br>
 */
public class CGPoint extends Structure implements Structure.ByValue {

    public CGFloat x;
    public CGFloat y;

    public CGPoint() {
        this(0, 0);
    }

    public CGPoint(int x, int y) {
        this.x = new CGFloat(x);
        this.y = new CGFloat(y);
        write();
    }

    /** for {@link #update} */
    private final double[] buf = new double[2];

    /**
     * DON'T use for ordinary use.
     * for {@link #update}
     */
    public static class CGMutableFloat extends CGFloat {

        private double value;

        public CGMutableFloat() {
            value = 0;
        }

        public CGMutableFloat(double d) {
            value = d;
        }

        @Override
        public int intValue() {
            return (int) value;
        }

        @Override
        public long longValue() {
            return (long) value;
        }

        @Override
        public float floatValue() {
            return (float) value;
        }

        @Override
        public double doubleValue() {
            return value;
        }

        @Override
        public int hashCode() {
            return Double.hashCode(value);
        }

        @Override
        public boolean equals(Object other) {
            // Modified Double.equals
            return (other instanceof CGMutableFloat) && (Double.doubleToLongBits(((CGMutableFloat) other).value) == Double.doubleToLongBits(value));
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }

    /**
     * DON'T use for ordinary use.
     * for {@link #update}
     */
    public CGPoint(CGMutableFloat mx, CGMutableFloat my) {
        x = mx;
        y = my;
    }

    /**
     * DON'T use for ordinary use.
     * for performance, assume CGFloat SIZE is double
     */
    public void update(int x, int y) {
        buf[0] = x;
        buf[1] = y;
        getPointer().write(0, buf, 0, 2);
        ((CGMutableFloat) this.x).value = x;
        ((CGMutableFloat) this.y).value = y;
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
