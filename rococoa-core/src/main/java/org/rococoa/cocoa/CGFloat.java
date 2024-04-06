/*
 * Copyright 2007, 2008, 2009 Duncan McGregor
 *
 * This file is part of Rococoa, a library to allow Java to talk to Cocoa.
 *
 * Rococoa is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Rococoa is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Rococoa.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.rococoa.cocoa;

import com.sun.jna.FromNativeContext;
import com.sun.jna.Native;
import com.sun.jna.NativeMapped;

/**
 * CGFloat
 *
 * @author <a href="mailto:harald.kuhr@gmail.com">Harald Kuhr</a>
 * @author duncan
 */
public class CGFloat extends Number implements NativeMapped {

    // Inspired by JNA NativeLong and IntegerType
    public static final int SIZE = Native.LONG_SIZE;

    private final double value;

    public CGFloat() {
        value = 0;
    }

    public CGFloat(double d) {
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
        return (other instanceof CGFloat) && (Double.doubleToLongBits(((CGFloat) other).value) == Double.doubleToLongBits(value));
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    // Native mapping
    @Override
    public Object fromNative(Object o, FromNativeContext fromNativeContext) {
        return switch (SIZE) {
            case 4 -> new CGFloat((Float) o);
            case 8 -> new CGFloat((Double) o);
            default -> throw new Error("Unknown Native.LONG_SIZE: " + SIZE);
        };
    }

    @Override
    public Object toNative() {
        return switch (SIZE) {
            case 4 -> floatValue();
            case 8 -> doubleValue();
            default -> throw new Error("Unknown Native.LONG_SIZE: " + SIZE);
        };
    }

    @Override
    public Class<?> nativeType() {
        return switch (SIZE) {
            case 4 -> Float.class;
            case 8 -> Double.class;
            default -> throw new Error("Unknown Native.LONG_SIZE: " + SIZE);
        };
    }
}
