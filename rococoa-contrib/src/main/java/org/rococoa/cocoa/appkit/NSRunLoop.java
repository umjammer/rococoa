/*
 * Copyright (c) 2026 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.appkit;

import org.rococoa.ObjCClass;
import org.rococoa.Rococoa;
import org.rococoa.cocoa.foundation.NSDate;
import org.rococoa.cocoa.foundation.NSObject;


/**
 * The programmatic interface to objects that manage input sources.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2026-01-22 nsano initial version <br>
 */
public abstract class NSRunLoop extends NSObject {

    private static final _Class CLASS = Rococoa.createClass("NSRunLoop", _Class.class);

    private interface _Class extends ObjCClass {
        NSRunLoop currentRunLoop();
    }

    /** Returns the run loop for the current thread. */
    public static NSRunLoop currentRunLoop() {
        return CLASS.currentRunLoop();
    }

    /**
     * Runs the loop until the specified date, during which time it processes data from all attached input sources.
     */
    public abstract void runUntilDate(NSDate limitDate);
}
