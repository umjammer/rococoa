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

import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import org.rococoa.ID;
import org.rococoa.ObjCClass;
import org.rococoa.ObjCObjectByReference;
import org.rococoa.Rococoa;
import org.rococoa.Selector;

public abstract class NSInvocation extends NSObject {

    private static final _Class CLASS = Rococoa.createClass("NSInvocation", _Class.class);

    private interface _Class extends ObjCClass {
        NSInvocation invocationWithMethodSignature(NSMethodSignature sig);
    }

    public static NSInvocation invocationWithMethodSignature(NSMethodSignature sig) {
        return CLASS.invocationWithMethodSignature(sig);
    }

    public abstract NSMethodSignature methodSignature();
    public abstract void getArgument_atIndex(Pointer receiver, int index);
    public abstract void setArgument_atIndex(Pointer receiver, int index);
    public abstract void setReturnValue(Memory buffer);
    public abstract void invoke();
    public abstract void invokeWithTarget(ID target);
    public abstract void getReturnValue(ObjCObjectByReference retLoc);
    public abstract void getReturnValue(Pointer retLoc);
    public abstract void setTarget(ID target);
    public abstract void setSelector(Selector selector);
}
