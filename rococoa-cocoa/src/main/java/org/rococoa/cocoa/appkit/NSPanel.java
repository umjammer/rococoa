/*
 * Copyright (c) 2022 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.appkit;

import org.rococoa.Rococoa;
import org.rococoa.RunOnMainThread;
import org.rococoa.cocoa.foundation.NSObject;


/**
 * A special kind of window that typically performs a function that is auxiliary to the main window.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2022/10/22 nsano initial version <br>
 */
public @RunOnMainThread abstract class NSPanel extends NSObject {
    
    private static final _Class CLASS = Rococoa.createClass("NSPanel", _Class.class);

    private static @RunOnMainThread abstract class _Class extends _class_ {
    }

    /** A Boolean value that indicates whether the receiver is a floating panel. */
    public abstract boolean floatingPanel();
}
