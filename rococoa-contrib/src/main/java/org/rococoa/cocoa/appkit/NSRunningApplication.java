/*
 * Copyright (c) 2023 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.appkit;

import java.util.logging.Logger;

import com.sun.jna.NativeLong;
import org.rococoa.ObjCClass;
import org.rococoa.Rococoa;
import org.rococoa.cocoa.foundation.NSArray;
import org.rococoa.cocoa.foundation.NSObject;
import org.rococoa.cocoa.foundation.NSURL;


/**
 * An object that can manipulate and provide information for a single instance of an app.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2023-12-20 nsano initial version <br>
 */
public abstract class NSRunningApplication extends NSObject {

    private static final Logger logger = Logger.getLogger(NSRunningApplication.class.getName());

    public static final _Class CLASS = Rococoa.createClass("NSRunningApplication", _Class.class);

    public interface _Class extends ObjCClass {

        /** Returns the running application with the given process identifier, or nil if no application has that pid. */
        NSRunningApplication runningApplicationWithProcessIdentifier(NativeLong /* pid_t */ pid);

        /** Returns an array of currently running applications with the specified bundle identifier. */
         NSArray /* NSRunningApplication */ runningApplicationsWithBundleIdentifier(String bundleIdentifier);

         /** Returns an NSRunningApplication representing this application. */
        NSRunningApplication currentApplication();
    }

    /** Indicates the localized name of the application. */
    public abstract String localizedName();

    /** Indicates the CFBundleIdentifier of the application. */
    public abstract String bundleIdentifier();

    /** Indicates the URL to the application's executable. */
    public abstract NSURL executableURL();

    /** Indicates the URL to the application's bundle. */
    public abstract NSURL bundleURL();

    /** Indicates the process identifier (pid) of the application. */
    public abstract NativeLong /* pid_t */ processIdentifier();

    /** Indicates whether the application is currently frontmost. */
    public abstract boolean active();

    /**
     * By default, activation brings only the main and key windows forward. If you specify
     * NSApplicationActivateAllWindows, all of the application's windows are brought forward.
     */
    public static final int NSApplicationActivateAllWindows = 1 << 0;

    /** Attempts to activate the application using the specified options. */
    public abstract boolean activateWithOptions(int /* NSApplicationActivationOptions */ options);
}
