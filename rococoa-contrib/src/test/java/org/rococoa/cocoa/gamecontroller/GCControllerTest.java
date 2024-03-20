/*
 * Copyright (c) 2023 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.gamecontroller;

import java.util.concurrent.CountDownLatch;

import com.sun.jna.Callback;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.rococoa.Foundation;
import org.rococoa.ObjCObject;
import org.rococoa.Rococoa;
import org.rococoa.Selector;
import org.rococoa.cocoa.foundation.NSBundle;
import org.rococoa.cocoa.foundation.NSNotification;
import org.rococoa.cocoa.foundation.NSNotificationCenter;
import vavi.util.Debug;


/**
 * GCControllerTest.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2023-05-18 nsano initial version <br>
 */
class GCControllerTest {

    CountDownLatch cdl = new CountDownLatch(1);

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
    @Disabled("wip")
    void test1() throws Exception {
Debug.println(NSBundle.mainBundle().bundleIdentifier());
        GCController.controllers().forEach(System.err::println);
Debug.println("here");
        GCController controller = GCController.controllers().get(0);

        ObjCObject proxy = Rococoa.proxy(new MyObserver());
        Selector sel1 = Foundation.selector("controllerDidConnect");
        Selector sel2 = Foundation.selector("controllerDidDisconnect");

        NSNotificationCenter notificationCenter = NSNotificationCenter.CLASS.defaultCenter();
        notificationCenter.addObserver_selector_name_object(proxy.id(), sel1, GCController.GCControllerDidConnectNotification, null);

        cdl.await();
    }
}