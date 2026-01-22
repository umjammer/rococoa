/*
 * Copyright (c) 2022 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.vision;

import org.rococoa.ObjCClass;
import org.rococoa.Rococoa;
import org.rococoa.cocoa.foundation.NSObject;


/**
 * The abstract superclass for analysis results.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2022-10-15 nsano initial version <br>
 */
public abstract class VNObservation extends NSObject {

    private static final _Class CLASS = Rococoa.createClass("VNObservation", _Class.class);

    private interface _Class extends ObjCClass {
        VNObservation alloc();
    }
}
