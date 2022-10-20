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

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.rococoa.test.RococoaTestCase;

import com.sun.jna.Library;
import com.sun.jna.Native;
import vavi.util.Debug;
import vavi.util.StringUtil;


@SuppressWarnings("nls")
public class FoundationStructureReturnTest extends RococoaTestCase {     
    private interface StructLibrary extends Library {
        TestStruct.ByValue createIntDoubleStruct(int a, double b);
        double addFieldsOfStructByValue(TestStruct.ByValue s);
        double addFieldsOfStructByValueVARARGS(int count, TestStruct.ByValue ...objects);
    }
    
    private StructLibrary library = Native.load("rococoa", StructLibrary.class);    
    
    @Test public void testStaticReceiveStructure() {
        TestStruct.ByValue result = library.createIntDoubleStruct(42, Math.E);
        assertEquals(42, result.anInt);
        assertEquals(Math.E, result.aDouble, 0);        
    }
    
    @Test public void testStaticPassStructure() {
        TestStruct.ByValue arg = new TestStruct.ByValue(42, Math.PI);
        double result = library.addFieldsOfStructByValue(arg);
        assertEquals(42 + Math.PI, result, 0);
    }

    @Test public void testStaticPassStructureVARARGS() {
        // demonstrate bug in JNA 3.0.3
        TestStruct.ByValue arg = new TestStruct.ByValue(42, Math.PI);
        double result = library.addFieldsOfStructByValueVARARGS(1, arg);
        assertEquals(42 + Math.PI, result, 0);
    }
    
    @Test public void testCallMethod() {
        ID testID = Foundation.sendReturnsID(Foundation.getClass("TestShunt"), "new");
        Foundation.sendReturnsID(testID, "autorelease");
        Object[] args = { 42, Math.E };
        TestStruct result = Foundation.send(testID, 
                Foundation.selector("testReturnStructByValue:and:"), 
                TestStruct.ByValue.class, args);
        assertEquals(42, result.anInt);
        assertEquals(Math.E, result.aDouble, 0);        
    }
    
    @Test public void testAsPassStructAsArgument() {
        ID testID = Foundation.sendReturnsID(Foundation.getClass("TestShunt"), "new");
        Foundation.sendReturnsID(testID, "autorelease");
        TestStruct.ByValue arg = new TestStruct.ByValue(42, Math.PI);
        Object[] args = { arg };
        double result = Foundation.send(testID, 
                Foundation.selector("testAddFieldsOfStructByValue:"), 
                double.class, args);
        assertEquals(42 + Math.PI, result, 0);        
    }
    
    @Test public void testStructOfStruct() {
        ID testID = Foundation.sendReturnsID(Foundation.getClass("TestShunt"), "new");
        Foundation.sendReturnsID(testID, "autorelease");
        Object[] args1 = { 42, Math.E };
        TestStructOfStruct result = Foundation.send(testID, 
                Foundation.selector("testReturnStructOfStructByValue:and:"), 
                TestStructOfStruct.ByValue.class, args1);
        assertEquals(Math.E, result.aDouble, 0);
        assertEquals(42, result.aStruct.anInt);        
        assertEquals(Math.E, result.aStruct.aDouble, 0);
        Object[] args = { result };
        
        double result2 = Foundation.send(testID, 
                Foundation.selector("testPassStructOfStructByValue:"), 
                double.class, args);
        assertEquals(Math.E, result2, 0);        
    }        

    @Test
    @Disabled("float: fromNative is ok")
    void test1() throws Exception {
        ID testID = Foundation.sendReturnsID(Foundation.getClass("TestShunt"), "new");
        Foundation.sendReturnsID(testID, "autorelease");
        float result = Foundation.send(testID,
                Foundation.selector("testPassFloatByValue"),
                float.class);
        assertEquals(3.14f, result, 0.01);
    }

    @Test
    @Disabled("float: toNative is NG")
    void test3() throws Exception {
        ID testID = Foundation.sendReturnsID(Foundation.getClass("TestShunt"), "new");
        Foundation.sendReturnsID(testID, "autorelease");
        int result = Foundation.send(testID,
                Foundation.selector("testConvertFloatToInt:"),
                int.class, 3.14d);
Debug.printf("%08x, %s", Float.floatToIntBits(3.14f), StringUtil.toBits(Float.floatToIntBits(3.14f), 32));
Debug.printf("%08x, %s", result, StringUtil.toBits(result, 32));
        assertEquals(Float.floatToIntBits(3.14f), result);
    }

    @Test
    @Disabled("float: toNative is NG")
    void test2() throws Exception {
        ID testID = Foundation.sendReturnsID(Foundation.getClass("TestShunt"), "new");
        Foundation.sendReturnsID(testID, "autorelease");
        boolean result = Foundation.send(testID,
                Foundation.selector("testGetFloatByValue:"),
                boolean.class, 3.14f);
        assertTrue(result);
    }
}
