/*
 * Copyright (c) 2021 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.foundation;


/**
 * NSNotification.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (umjammer)
 * @version 0.00 2021/10/30 umjammer initial version <br>
 */
public class NSNotificationTest {

    public static void main(String[] args) throws Exception {
        NSNotificationCenter notificationCenter = NSNotificationCenter.CLASS.defaultCenter();

        NSNotification notification = NSNotification.CLASS.notificationWithName_object("vavi", NSString.stringWithString("hello world"));

        notificationCenter.postNotification(notification);
System.err.println("done");
    }
}
