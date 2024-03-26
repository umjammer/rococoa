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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.rococoa.ObjCClass;
import org.rococoa.Rococoa;


/**
 * A static ordered collection of objects.
 */
public abstract class NSArray extends NSEnumerator implements List<NSObject> {

    public static final _Class CLASS = Rococoa.createClass("NSArray", _Class.class);

    public interface _Class extends ObjCClass {
        /** Creates and returns an empty array. */
        NSArray array();
        /**
         * @param objects Contents and then a trailing null
         */
        @Deprecated(since = "aarch64")
        NSArray arrayWithObjects(NSObject...objects);

        /**
         * Creates and returns an array containing the objects in another given array.
         */
        NSArray arrayWithArray(NSArray array);
    }

    /** */
    public static NSArray arrayWithObjects(NSObject...objects) {
        return CLASS.arrayWithArray(toArray(objects));
    }

    static NSMutableArray toArray(NSObject...objects) {
        NSMutableArray array = NSMutableArray.CLASS.array();
        for (NSObject o : objects) {
            if (o == null) {
                break;
            }
            array.addObject(o);
        }
        return array;
    }

    @SuppressWarnings("unchecked")
    public <T extends NSObject> List<T> toList() {
        List<NSObject> result = new ArrayList<>();
        for (int i = 0; i < this.count(); i++) {
            result.add(this.objectAtIndex(i));
        }
        return (List<T>) result;
    }

    /** The number of objects in the array.  */
    public abstract int count();

    @Override
    public int size() {
        return count();
    }

    /** The first object in the array. */
    public abstract NSObject firstObject();
    /** The last object in the array. */
    public abstract NSObject lastObject();
    /** Returns the object located at the specified index. */
    public abstract NSObject objectAtIndex(int zeroOffsetIndex);

    @Override
    public NSObject get(int i) {
        return objectAtIndex(i);
    }

    /** Returns an enumerator object that lets you access each object in the array. */
    public abstract NSEnumerator objectEnumerator();

    @Override
    public Iterator<NSObject> iterator() {
        return new Iterator<>() {
            int i = 0;

            @Override
            public boolean hasNext() {
                return i < count() - 1;
            }

            @Override
            public NSObject next() {
                if (hasNext()) {
                    return objectAtIndex(i++);
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
    }
}
