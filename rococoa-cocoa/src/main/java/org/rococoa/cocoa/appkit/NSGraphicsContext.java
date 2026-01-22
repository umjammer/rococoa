/*
 * Copyright (c) 2022 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.appkit;

import org.rococoa.ObjCClass;
import org.rococoa.Rococoa;
import org.rococoa.cocoa.foundation.NSObject;


/**
 * NSGraphicsContext.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2022-09-04 nsano initial version <br>
 */
public abstract class NSGraphicsContext extends NSObject {
    
    private static final _Class CLASS = Rococoa.createClass("NSGraphicsContext",  _Class.class);

    private interface _Class extends ObjCClass {
    }
}
