/*
 * Copyright (c) 2023 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.gamecontroller;

import java.util.concurrent.CountDownLatch;

import com.sun.jna.Callback;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.rococoa.Foundation;
import org.rococoa.ObjCObject;
import org.rococoa.Rococoa;
import org.rococoa.Selector;
import org.rococoa.cocoa.foundation.NSBundle;
import org.rococoa.cocoa.foundation.NSNotification;
import org.rococoa.cocoa.foundation.NSNotificationCenter;
import vavi.util.Debug;

import static org.junit.jupiter.api.Assertions.fail;


/**
 * GCControllerTest.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2023-05-18 nsano initial version <br>
 */
class GCControllerTest {

    final CountDownLatch cdl = new CountDownLatch(1);

    class MyObserver implements Callback {

        public void controllerDidConnect(NSNotification notification) {
            GCController controller = Rococoa.cast(notification.object(), GCController.class);
Debug.println("controllerDidConnect: " + controller);
            cdl.countDown();
        }

        public void controllerDidDisconnect(NSNotification notification) {
            Debug.println("controllerDidDisconnect");
        }
    }

    @Test
    @EnabledIfSystemProperty(named = "vavi.test", matches = "ide")
    void test1() throws Exception {
Debug.println(NSBundle.mainBundle().bundleIdentifier());
        GCController.controllers().forEach(System.err::println);
Debug.println("controllers: " + GCController.controllers().size());
        if (!GCController.controllers().isEmpty()) {
            GCController controller = GCController.controllers().get(0);
Debug.println("controller: " + controller);

            ObjCObject proxy = Rococoa.proxy(new MyObserver());
            Selector sel1 = Foundation.selector("controllerDidConnect:");
            Selector sel2 = Foundation.selector("controllerDidDisconnect:");

            NSNotificationCenter notificationCenter = NSNotificationCenter.defaultCenter();
            notificationCenter.addObserver_selector_name_object(proxy.id(), sel1, GCController.GCControllerDidConnectNotification, null);
            notificationCenter.addObserver_selector_name_object(proxy.id(), sel2, GCController.GCControllerDidDisconnectNotification, null);

            // fake notification
            NSNotification notification = NSNotification.init(GCController.GCControllerDidConnectNotification, "hello world");
            notificationCenter.postNotification(notification);

            cdl.await();
        } else {
            fail("connect at least one game controller");
        }
    }

    @Test
    @EnabledIfSystemProperty(named = "vavi.test", matches = "ide")
    void test2() throws Exception {

        GCController.controllers().forEach(System.err::println);
Debug.println("controllers: " + GCController.controllers().size());
        GCController controller = GCController.controllers().get(0);
Debug.println("controller: " + controller);
        GCExtendedGamepad gamepad = controller.extendedGamepad();
Debug.println("gamepad: " + gamepad);

Debug.println("allElements: ------------------------------------------------------------");
gamepad.allElements().allObjects().toList().forEach(System.err::println);
Debug.println("allButtons: ------------------------------------------------------------");
gamepad.allButtons().allObjects().toList().forEach(System.err::println);
Debug.println("allAxes: ------------------------------------------------------------");
gamepad.allAxes().allObjects().toList().forEach(System.err::println);
Debug.println("allDpads: ------------------------------------------------------------");
gamepad.allDpads().allObjects().toList().forEach(System.err::println);
Debug.println("allTouchpads: ------------------------------------------------------------");
gamepad.allTouchpads().allObjects().toList().forEach(System.err::println);

        CountDownLatch cdl = new CountDownLatch(1);

        GCControllerButtonInput triangleButton = gamepad.buttonY();
        GCControllerButtonInput circleButton = gamepad.buttonB();
        GCControllerButtonInput crossButton = gamepad.buttonA();
        GCControllerButtonInput rectButton = gamepad.buttonX();

        // setting
        GCControllerButtonInput menuButton = gamepad.buttonMenu();
        GCControllerButtonInput shareButton = gamepad.buttonOptions();

        // thumbstich
        GCControllerDirectionPad leftThumbstick = gamepad.leftThumbstick();
        GCControllerDirectionPad rightThumbstick = gamepad.rightThumbstick();
        GCControllerButtonInput leftThumbstickButton = gamepad.leftThumbstickButton();
        GCControllerButtonInput rightThumbstickButton = gamepad.rightThumbstickButton();

        // ↑←↓→ key
        GCControllerDirectionPad directionPad = gamepad.dpad();

        // L1, L2, R1, R2　button
        GCControllerButtonInput l1Button = gamepad.leftShoulder();
        GCControllerButtonInput l2Button = gamepad.leftTrigger();
        GCControllerButtonInput r1Button = gamepad.rightShoulder();
        GCControllerButtonInput r2Button = gamepad.rightTrigger();

//        circleButton.setPressedChangedHandler(block((GCControllerButtonValueChangedHandler) (literal, button, value, pressed) -> {
//Debug.println("◯ pressed");
//            cdl.countDown();
//        }));
//
//        cdl.await();
    }
}