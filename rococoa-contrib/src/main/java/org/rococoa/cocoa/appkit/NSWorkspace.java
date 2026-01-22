/*
 * Copyright (c) 2023 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.appkit;

import org.rococoa.ObjCClass;
import org.rococoa.Rococoa;
import org.rococoa.cocoa.foundation.NSArray;
import org.rococoa.cocoa.foundation.NSNotificationCenter;
import org.rococoa.cocoa.foundation.NSObject;


/**
 * A workspace that can launch other apps and perform a variety of file-handling services.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2023-12-20 nsano initial version <br>
 */
public abstract class NSWorkspace extends NSObject {

    private static final _Class CLASS = Rococoa.createClass("NSWorkspace", _Class.class);

    private interface _Class extends ObjCClass {
        NSWorkspace sharedWorkspace();
    }

    /** The shared workspace object. */
    public static NSWorkspace sharedWorkspace() {
        return CLASS.sharedWorkspace();
    }

    /** The notification center for workspace notifications. */
    public abstract NSNotificationCenter notificationCenter();

    /** Returns an array of running apps. */
    public abstract NSArray /* NSRunningApplication */ runningApplications();

    /** Returns the frontmost app, which is the app that receives key events. */
    public abstract NSRunningApplication frontmostApplication();

    /** Returns the app that owns the currently displayed menu bar. */
    public abstract NSRunningApplication menuBarOwningApplication();

    public static final String NSWorkspaceDidActivateApplicationNotification = "NSWorkspaceDidActivateApplicationNotification";
    public static final String NSWorkspaceDidDeactivateApplicationNotification = "NSWorkspaceDidDeactivateApplicationNotification";
}
