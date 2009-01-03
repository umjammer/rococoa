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

import junit.framework.Test;

import org.rococoa.MyStruct.MyStructByValue;
import org.rococoa.cocoa.NSAutoreleasePool;
import org.rococoa.cocoa.NSNotification;
import org.rococoa.cocoa.NSNotificationCenter;
import org.rococoa.cocoa.NSString;

import com.sun.jna.NativeLong;

@SuppressWarnings("nls")
public class JavaProxyTest extends RococoaTestCase {
    
    public static class JavaImplementor {
        public Object arg;
    
        public void nothing() {
            arg = null;
        }
        
        public ID sayHello() {
            return Foundation.cfString("Hello");
        }
        
        public ID testTakesIDReturnsID(ID id) {
            arg = id;
            return Foundation.cfString("Hello");
        }
        
        public NSObject takesNSObjectReturnsNSObject(NSObject s) {
            arg = s;
            return s;
        }
        
        public void takesInt_AndInt(int a, int b) {
            arg = new Object[] {a, b};
        }
        
        public byte takesStringReturnsByte(NSString s) {
            arg = s;
            return 42;
        }
        
        public boolean takesBooleanReturnsBoolean(boolean b) {
            arg = b;
            return !b;
        }
        
        public String takesJavaStringReturnsJavaString(String s) {
            arg = s;
            return s.toLowerCase();
        }
        
        public MyStruct takesStructureReturnsStructure(MyStruct s) {
            arg = s;
            return new MyStruct(s.anInt, s.aDouble);
        }

        public MyStruct.MyStructByValue takesStructureByValueReturnsStructureByValue(MyStruct.MyStructByValue s) {
            arg = s;
            return new MyStruct.MyStructByValue(s.anInt, s.aDouble);
        }
        
        public NativeLong takesNativeLongReturnsNativeLong(NativeLong l) {
            arg = l;
            return l;
        }

        public long takesLongReturnsLong(long l) {
            arg = l;
            return l;
        }
        
        public double takesDoubleReturnsDouble(double d) {
            arg = d;
            return d;
        }

        public void notify(NSNotification notification) {
            this.arg = notification;
        }
     }

    private JavaImplementor implementor;
    private NSObject proxy;
    
    public static Test suite() {
        return skipOnJava6Suite(FoundationStructureReturnTest.class);
    }
    
    @Override
    protected void setUp() throws Exception {
        implementor = new JavaImplementor();
        proxy = Rococoa.proxy(implementor, NSObject.class); // hang onto this to prevent GC issues
    }   
    
    public void testRepondsToSelector() {
        // respond to selector is required for delegates
        assertEquals(0, (byte) Foundation.send(proxy.id(), "respondsToSelector:", 
                byte.class, Foundation.selector("Bo")));
        assertEquals(1, (byte) Foundation.send(proxy.id(), "respondsToSelector:", 
                byte.class, Foundation.selector("sayHello")));
    }
    
    public void testNoArgsReturnsVoid() {
        implementor.arg = "something";
        ID result = Foundation.sendReturnsID(proxy.id(), "nothing");
        assertTrue(result.isNull());
        assertNull(implementor.arg);
    }

    public void testNoArgsReturnsID() {
        ID result = Foundation.sendReturnsID(proxy.id(), "sayHello");
        assertEquals("Hello", Foundation.toString(result));        
        assertNull(implementor.arg);
    }

    public void testTakesIDReturnsID() {
        ID result = Foundation.sendReturnsID(proxy.id(), "testTakesIDReturnsID:", 
               ID.fromLong(42));
        assertEquals("Hello", Foundation.toString(result));        
        assertEquals(ID.fromLong(42), implementor.arg);
    }
    
    public void testTakesNSObjectReturnsNSObject() {
        ID result = Foundation.sendReturnsID(proxy.id(), "takesNSObjectReturnsNSObject:", 
                Foundation.cfString("hello"));
        assertEquals("hello", Foundation.toString(result));
        assertEquals("hello", 
                Rococoa.cast((NSObject) implementor.arg, NSString.class).toString());
                // as parameter was NSObject, it lost its string-ness
    }

    public void testTakesStringReturnsByte() {
        byte result = Foundation.send(proxy.id(), "takesStringReturnsByte:", 
                byte.class, Foundation.cfString("hello"));
        assertEquals(42, result);
        assertEquals("hello", ((NSString) implementor.arg).toString());
    }
    
    public void testTakesBooleanReturnsBoolean() {
        assertTrue(Foundation.send(proxy.id(), "takesBooleanReturnsBoolean:", 
                boolean.class, false));
        assertFalse(Foundation.send(proxy.id(), "takesBooleanReturnsBoolean:", 
                boolean.class, true));
    }
    
    public void testTakesIntAndInt() {
        ID result =  Foundation.sendReturnsID(proxy.id(), "takesInt:AndInt:", 
                42, -1);
        assertTrue(result.isNull());
        Object[] arg = (Object[]) implementor.arg;
        assertEquals(42, arg[0]);
        assertEquals(-1, arg[1]);
    }
    
    public void testTakesJavaStringReturnsJavaString() {
        assertEquals("lower", Foundation.send(proxy.id(), "takesJavaStringReturnsJavaString:", 
                String.class, "LoWeR"));
    }
    
    public void testSendAndReceiveStructByReference() {
        MyStruct struct = new MyStruct(42, Math.PI);
        MyStruct result = Foundation.send(proxy.id(), "takesStructureReturnsStructure:", 
                MyStruct.class, struct);
        assertEquals("passing to java", 42, ((MyStruct) implementor.arg).anInt);
        assertEquals("passing to java", Math.PI, ((MyStruct) implementor.arg).aDouble, 0.00001);
        assertEquals("returning to OC", 42, result.anInt);
        assertEquals("returning to OC", Math.PI, result.aDouble, 0.00001);
    }

    public void testSendAndReceiveStructByValue() {
        // Hmmm, difficult to prove this is passed by value
        MyStructByValue struct = new MyStructByValue(42, Math.PI);
        MyStruct result = Foundation.send(proxy.id(), "takesStructureByValueReturnsStructureByValue:", 
                MyStructByValue.class, struct);
        assertEquals("passing to java", 42, ((MyStruct) implementor.arg).anInt);
        assertEquals("passing to java", Math.PI, ((MyStruct) implementor.arg).aDouble, 0.00001);
        assertEquals("returning to OC", 42, result.anInt);
        assertEquals("returning to OC", Math.PI, result.aDouble, 0.00001);
    }
    
    public void testSendAndReceiveNativeLong() {
        NativeLong result = Foundation.send(proxy.id(), "takesNativeLongReturnsNativeLong:", 
                NativeLong.class, new NativeLong(42));
        assertEquals(42, result.longValue());
    }
    
    public void testSendAndReceiveLong() {
        long result = Foundation.send(proxy.id(), "takesLongReturnsLong:", 
                long.class, 42);
        assertEquals(42, result);

        result = Foundation.send(proxy.id(), "takesLongReturnsLong:", 
                long.class, Long.MAX_VALUE);
        assertEquals(Long.MAX_VALUE, result);
    }
    
    public void testSendAndReceiveDouble() {
        double result = Foundation.send(proxy.id(), "takesDoubleReturnsDouble:", 
                double.class, Math.PI);
        assertEquals(0.0, result - Math.PI);
        
        result = Foundation.send(proxy.id(), "takesDoubleReturnsDouble:", 
                double.class, Double.MAX_VALUE);
        assertEquals(0.0, result - Double.MAX_VALUE);
    }
    
    public void testMultipleCallbacks() {
        // We managed to have static callback data, so that the last callback
        // registered was always the one called!
        // @see https://rococoa.dev.java.net/issues/show_bug.cgi?id=9
        JavaImplementor implementor2 = new JavaImplementor();
        NSObject proxy2 = Rococoa.proxy(implementor2);
        
        Foundation.sendReturnsVoid(proxy.id(), "testTakesIDReturnsID:", ID.fromLong(42));
        assertEquals(ID.fromLong(42), implementor.arg);        

        Foundation.sendReturnsVoid(proxy2.id(), "testTakesIDReturnsID:", ID.fromLong(43));
        assertEquals(ID.fromLong(43), implementor2.arg);        
    }    
    
    public void testNotifications() {
        NSNotificationCenter notificationCentre = NSNotificationCenter.CLASS.defaultCenter();
        notificationCentre.addObserver_selector_name_object(
                proxy, 
                Foundation.selector("notify:"),
                "MyNotification",
                null);

        NSNotification notification = NSNotification.CLASS.notificationWithName_object("MyNotification", null);
        
        assertNull(implementor.arg);
        notificationCentre.postNotification(notification);             
        assertEquals(notification, implementor.arg);
    }
    
    public void testMemoryManagement() {
        // we were autorelease'ing the proxy - so that this failed
        NSNotificationCenter notificationCentre = NSNotificationCenter.CLASS.defaultCenter();
        notificationCentre.addObserver_selector_name_object(
                proxy, 
                Foundation.selector("notify:"),
                "MyNotification",
                null);
        pool.release();
        pool = NSAutoreleasePool.new_();

        NSNotification notification = NSNotification.CLASS.notificationWithName_object("MyNotification", null);        
        notificationCentre.postNotification(notification);             
    }
    
}
