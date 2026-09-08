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

package org.rococoa.cocoa.foundation;

import org.rococoa.ObjCClass;
import org.rococoa.Rococoa;


public abstract class NSNumber extends NSObject {

    private static final _Class CLASS = Rococoa.createClass("NSNumber", _Class.class);

    private interface _Class extends ObjCClass {
        NSNumber numberWithBool(boolean value);
        NSNumber numberWithInt(int value);
        NSNumber numberWithDouble(double e);
        NSNumber numberWithLong(long value);
        NSNumber numberWithFloat(float value);
    }

    public static NSNumber numberWithInt(int value) {
        return CLASS.numberWithInt(value);
    }
    public static NSNumber of(int value) {
        return numberWithInt(value);
    }
    public static NSNumber numberWithDouble(double value) {
        return CLASS.numberWithDouble(value);
    }
    public static NSNumber numberWithFloat(float value) {
        return CLASS.numberWithFloat(value);
    }
    public static NSNumber numberWithLong(long value) {
        return CLASS.numberWithLong(value);
    }
    public static NSNumber numberWithBool(boolean value) {
        return CLASS.numberWithBool(value);
    }

    public abstract short shortValue();
    public abstract int intValue();
    public abstract long longValue();
    public abstract float floatValue();
    public abstract double doubleValue();
    public abstract int compare(NSNumber another);

    public abstract String stringValue();

    public abstract String objCType();

    @Override
    public String toString() {
        return stringValue();
    }

    public static Class<? extends ObjCClass> objCClass() {
        return _Class.class;
    }
}
