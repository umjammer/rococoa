/*
 * Copyright (c) 2023 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.appkit;

import org.rococoa.ObjCBlock;
import org.rococoa.ObjCClass;
import org.rococoa.ObjCObjectByReference;
import org.rococoa.Rococoa;
import org.rococoa.cocoa.foundation.NSArray;
import org.rococoa.cocoa.foundation.NSObject;
import org.rococoa.cocoa.foundation.NSURL;


/**
 * An object that represents a subprocess of the current process.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2023-12-20 nsano initial version <br>
 */
public abstract class NSTask extends NSObject {

    private static final _Class CLASS = Rococoa.createClass("NSTask", _Class.class);

    private interface _Class extends ObjCClass {
        /** Creates and runs a task with a specified executable and arguments. */
        NSTask launchedTaskWithExecutableURL_arguments_error_terminationHandler(
                NSURL url,
                NSArray /* NSString */ arguments,
                /* NSError */ ObjCObjectByReference error,
                ObjCBlock /* (LNSTask;)V */ terminationHandler);
    }

    /** Returns an initialized process object with the environment of the current process. */
    public abstract NSTask init();

    /** The receiver’s process identifier. */
    public abstract int processIdentifier();
}
