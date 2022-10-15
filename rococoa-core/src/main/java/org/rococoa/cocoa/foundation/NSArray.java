/*
 * Copyright 2007, 2008 Duncan McGregor
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


/**
 * A static ordered collection of objects.
 */
public abstract class NSArray extends NSEnumerator {

    public static final _Class CLASS = Rococoa.createClass("NSArray", _Class.class);

    public interface _Class extends ObjCClass {
        /** Creates and returns an empty array. */
        NSArray array();
        /**
         * @param objects Contents and then a trailing null
         */
        NSArray arrayWithObjects(NSObject...objects);
    }

    /** */
    public static NSArray arrayWithObjects(NSObject...objects) {
        return CLASS.arrayWithObjects(objects);
    }

    /**The number of objects in the array.  */
    public abstract int count();

    /** The first object in the array. */
    public abstract NSObject firstObject();
    /** The last object in the array. */
    public abstract NSObject lastObject();
    /** Returns the object located at the specified index. */
    public abstract NSObject objectAtIndex(int zeroOffsetIndex);

    /** Returns an enumerator object that lets you access each object in the array. */
    public abstract NSEnumerator objectEnumerator();
}
