/*
 * Copyright (c) 2026 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.appkit;

import org.rococoa.ObjCClass;
import org.rococoa.cocoa.foundation.NSObject;


/**
 * @interface NSViewController : NSObject
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (umjammer)
 * @version 0.00 2020/??/?? umjammer initial version <br>
 */
public abstract class NSViewController extends NSObject {

    @SuppressWarnings("unused")
    private static final _Class CLASS = org.rococoa.Rococoa.createClass("NSViewController", _Class.class);

    private interface _Class extends ObjCClass {
        NSViewController alloc();
    }

    public abstract void loadView();

    public abstract NSView view();
}