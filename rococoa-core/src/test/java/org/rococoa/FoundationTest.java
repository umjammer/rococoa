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

import java.util.logging.Logger;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.rococoa.cocoa.CGFloat;
import org.rococoa.cocoa.foundation.NSNumber;
import org.rococoa.test.RococoaTestCase;
import vavi.util.Debug;
import vavi.util.StringUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FoundationTest extends RococoaTestCase {

    private static final Logger logger = Logger.getLogger(RococoaTestCase.class.getName());

    @Test void testCFString() {
        ID string = Foundation.cfString("Hello World");
        assertNotNull(string);
        assertEquals("Hello World", Foundation.toString(string));
    }

    @Test void testCFStringWithDifferentEncoding() throws Exception {
        String stringWithOddChar = "Hello \u2648"; // Aries
        ID string = Foundation.cfString(stringWithOddChar); 
        assertEquals(stringWithOddChar, Foundation.toString(string));
    }

    @Test void testStringPerformance() {
        String stringWithOddChar = "Hello \u2648";
        String longString = stringWithOddChar.repeat(1000);
        ID string = Foundation.cfString(longString); 

        for (int i = 0; i < 10000; i++) {
            String s = Foundation.toStringViaUTF16(string);
        }
    }

    @Test void testInt() {
        ID clas = Foundation.getClass("NSNumber");
        ID anInt = Foundation.sendReturnsID(clas, "numberWithInt:", 42);
        int anIntValue = Foundation.send(anInt, "intValue", int.class);
        assertEquals(42, anIntValue);
    }

    @Test void testDouble() {
        ID clas = Foundation.getClass("NSNumber");
        ID aDouble = Foundation.sendReturnsID(clas, "numberWithDouble:", Math.E);
        Object[] args = {};
        double aDoubleValue = Foundation.send(aDouble, Foundation.selector("doubleValue"), double.class, args);
        assertEquals(Math.E, aDoubleValue, 0.001);
    }

    @Test void testFloat() {
        ID clas = Foundation.getClass("NSNumber");
        ID aFloat = Foundation.sendReturnsID(clas, "numberWithFloat:", 3.142f);
        String aStringValue = Foundation.send(aFloat, Foundation.selector("stringValue"), String.class);
logger.info("NSNumber: " + aStringValue + ", " + CGFloat.SIZE);
        Object[] args = {};
        float aFloatValue = Foundation.send(aFloat, Foundation.selector("floatValue"), float.class, args);
Debug.println(StringUtil.toBits(Float.floatToIntBits(3.142f), 32));
Debug.println(StringUtil.toBits(Float.floatToIntBits(aFloatValue), 32));
        assertEquals(3.142f, aFloatValue, 0.001);
    }

    @Test void testSendNoArgs() {
        ID clas = Foundation.getClass("NSDate");
        ID instance = Foundation.sendReturnsID(clas, "date");
        ID result = Foundation.sendReturnsID(instance, "description");
        assertTrue(Foundation.toString(result).startsWith("2")); // 2007-11-15 16:01:50 +0000
    }

    @Test void testSelector() {
        Selector selector = Foundation.selector("selectorName:");
        assertTrue(selector.longValue() != 0); // selectors always exist
        assertSame("selectorName:", selector.getName());

        Selector noSuchSelector = Foundation.selector("noSelector:NamedThis:OrribleThing:");
        assertTrue(noSuchSelector.longValue() != 0);
        assertSame("noSelector:NamedThis:OrribleThing:", noSuchSelector.getName());
    }

    @Test void sendMessageToNilIsOK() {
        assertEquals(new ID(0), Foundation.sendReturnsID(new ID(0), "description"));
    }

    // TODO - make work by wrapping call with native try- catch
    @Disabled("to make work")
    @Test void testInvokeUnknownSelector() {
        Selector noSuchSelector = Foundation.selector("noSelector:NamedThis:OrribleThing:");
        assertTrue(noSuchSelector.longValue() != 0); 
        ID clas = Foundation.getClass("NSNumber");
        assertThrows(NoSuchMethodError.class, () -> Foundation.send(clas, noSuchSelector, int.class));
    }

    @Test
    void test1() throws Exception {
        NSNumber number = NSNumber.of(1234);
        assertEquals(1234f, number.floatValue());
        assertEquals(1234, number.doubleValue());
        assertNotEquals(1235, number.floatValue());

        NSNumber number3 = NSNumber.of(1.234d);
        assertEquals(1.234f, number3.floatValue());
        assertEquals(1.234d, number3.doubleValue());

        NSNumber number2 = NSNumber.of(1.234f);
Debug.println(StringUtil.toBits(Float.floatToIntBits(1.234f), 32));
Debug.println(StringUtil.toBits(Float.floatToIntBits(number2.floatValue()), 32));
        assertEquals(1.234f, number2.floatValue()); // TODO error
    }
}
