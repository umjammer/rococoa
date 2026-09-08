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

package org.rococoa;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.rococoa.cocoa.foundation.NSObject;
import org.rococoa.test.RococoaTestCase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class RococoaAbstractClassTest extends RococoaTestCase {

	public static abstract class NSNumberAsClass extends NSObject {

	    private static final _Class CLASS = Rococoa.createClass("NSNumber", _Class.class);

	    private interface _Class extends ObjCClass {
	        NSNumberAsClass numberWithInt(int value);
	    }

	    public static NSNumberAsClass numberWithInt(int value) {
	        return CLASS.numberWithInt(value);
	    }

	    public abstract int intValue();

	    public int twice() {
	        return 2 * intValue();
	    }

	    public int over(int limit) {
	        int value = intValue();
	        if (value > limit) {
	            throw new IllegalArgumentException("over " + limit + ": " + value);
	        }
	        return value;
	    }
    }

    @Test public void test() {
        NSNumberAsClass number = NSNumberAsClass.numberWithInt(42);
        assertEquals(42, number.intValue());
        assertEquals(84, number.twice());
    }

    /**
     * A concrete method is reached through reflection, which wraps whatever it throws. The
     * caller is calling the override though, not reflection, so it must see the exception the
     * override declared.
     */
    @Test public void testConcreteMethodThrowsUnwrapped() {
        NSNumberAsClass number = NSNumberAsClass.numberWithInt(42);
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> number.over(41));
        assertEquals("over 41: 42", e.getMessage());
    }

	@Disabled("currently ByteBuddy cannot cache classes")
    @Test public void testCGLibResusesClasses() {
        NSNumberAsClass number = NSNumberAsClass.numberWithInt(42);
        NSNumberAsClass number2 = NSNumberAsClass.numberWithInt(42);
        assertSame(number.getClass(), number2.getClass());
    }
}
