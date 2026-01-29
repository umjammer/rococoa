/*
 * Copyright (c) 2002-2009 David Kocher. All rights reserved.
 *
 * http://cyberduck.ch/
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * Bug fixes, suggestions and comments should be sent to:
 * dkocher@cyberduck.ch
 */

package org.rococoa.cocoa.foundation;

import org.rococoa.ID;
import org.rococoa.ObjCClass;

/// <i>native declaration : :62</i>
public abstract class NSMutableDictionary extends NSDictionary {
    private static final _Class CLASS = org.rococoa.Rococoa.createClass("NSMutableDictionary", _Class.class);

    private interface _Class extends ObjCClass {
        NSMutableDictionary dictionary();
        /**
         * Original signature : <code>id dictionaryWithCapacity(NSUInteger)</code><br>
         * <i>from NSMutableDictionaryCreation native declaration : :80</i>
         */
        NSMutableDictionary dictionaryWithCapacity(int numItems);

        NSMutableDictionary dictionaryWithDictionary(NSDictionary dict);
        NSMutableDictionary dictionaryWithObjects_forKeys(NSArray objects, NSArray keys);
        @Deprecated(since = "aarch64")
        NSMutableDictionary dictionaryWithObjectsAndKeys(NSObject...objects);
    }

    public static NSMutableDictionary dictionary() {
        return CLASS.dictionary();
    }

    public static NSMutableDictionary dictionaryWithCapacity(int numItems) {
        return CLASS.dictionaryWithCapacity(numItems);
    }

    public static NSMutableDictionary dictionaryWithDictionary(NSDictionary dictionary) {
        return CLASS.dictionaryWithDictionary(dictionary);
    }

    public static NSMutableDictionary dictionaryWithObjects_forKeys(NSArray objects, NSArray keys) {
        return CLASS.dictionaryWithObjects_forKeys(objects, keys);
    }

    public static NSMutableDictionary dictionaryWithObjectsAndKeys(NSObject...objects) {
        NSMutableArray[] pair = flatToSeparate(objects);
        return dictionaryWithObjects_forKeys(pair[0], pair[1]);
    }

    /**
     * Original signature : <code>void removeObjectForKey(id)</code><br>
     * <i>native declaration : :64</i>
     */
    public abstract void removeObjectForKey(String aKey);

    /**
     * If aKey already exists in the receiver, the receiver’s previous value
     * object for that key is sent a release message and anObject takes its place.
     *
     * @param anObject The object receives a retain message before being added to
     *                 the receiver. This value must not be nil.
     * @param aKey     The key is copied (using copyWithZone:; keys must conform to the NSCopying protocol). The key must not be nil.
     */
    public void setObjectForKey(String anObject, String aKey) {
        this.setObjectForKey(NSString.stringWithString(anObject), aKey);
    }

    public void setObjectForKey(NSObject anObject, String aKey) {
        this.setObject_forKey(anObject, NSString.stringWithString(aKey));
    }

    /**
     * Original signature : <code>public abstract void setObject(id, id)</code><br>
     * <i>native declaration : :65</i>
     */
    public abstract void setObject_forKey(NSObject anObject, NSObject aKey);

    public abstract void setValue_forKey(NSObject object, NSObject key);

    public abstract void setValue_forKey(NSObject object, String key);

    public abstract void setObject_forKey(ID anObject, ID aKey);

    /**
     * Original signature : <code>public abstract void addEntriesFromDictionary(NSDictionary*)</code><br>
     * <i>from NSExtendedMutableDictionary native declaration : :71</i>
     */
    public abstract void addEntriesFromDictionary(NSDictionary otherDictionary);

    /**
     * Original signature : <code>public abstract void removeAllObjects()</code><br>
     * <i>from NSExtendedMutableDictionary native declaration : :72</i>
     */
    public abstract void removeAllObjects();

    /**
     * Original signature : <code>public abstract void removeObjectsForKeys(NSArray*)</code><br>
     * <i>from NSExtendedMutableDictionary native declaration : :73</i>
     */
    public abstract void removeObjectsForKeys(NSArray keyArray);

    /**
     * Original signature : <code>public abstract void setDictionary(NSDictionary*)</code><br>
     * <i>from NSExtendedMutableDictionary native declaration : :74</i>
     */
    public abstract void setDictionary(NSDictionary otherDictionary);

    /**
     * Original signature : <code>id initWithCapacity(NSUInteger)</code><br>
     * <i>from NSMutableDictionaryCreation native declaration : :81</i>
     */
    public abstract NSMutableDictionary initWithCapacity(NSUInteger numItems);
}