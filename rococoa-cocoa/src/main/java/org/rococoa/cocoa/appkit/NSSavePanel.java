/*
 * Copyright (c) 2022 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.appkit;

import org.rococoa.ID;
import org.rococoa.Rococoa;
import org.rococoa.RunOnMainThread;
import org.rococoa.cocoa.foundation.NSArray;
import org.rococoa.cocoa.foundation.NSObject;
import org.rococoa.cocoa.foundation.NSString;
import org.rococoa.cocoa.foundation.NSURL;


/**
 * A panel that prompts the user for information about where to save a file.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2022/10/21 nsano initial version <br>
 */
public @RunOnMainThread abstract class NSSavePanel extends NSPanel {
    
    public static final int NSOKButton = 1;
    public static final int NSCancelButton = 0;
    
    public static final _Class CLASS = Rococoa.createClass("NSSavePanel", _Class.class);

    public static @RunOnMainThread abstract class _Class extends _class_ {
        public abstract NSSavePanel savePanel();
    }

    /** A URL that contains the fully specified location of the targeted file. */
    public abstract NSURL URL();

    /** The title of the panel. */
    public abstract String title();
    public abstract void setTitle(String title);
    /** The text to display in the default button. */
    public abstract String prompt();
    /** The message text displayed in the panel. */
    public abstract String message();

    /** The current directory shown in the panel. */
    public abstract NSURL directoryURL();
    public abstract void directoryURL(NSURL url);

    /** @see "https://developer.apple.com/documentation/appkit/nsopensavepaneldelegate?language=objc" */
    public abstract void setDelegate(ID ocProxy);
}
