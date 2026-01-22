/*
 * Copyright (c) 2023 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.avfoundation;

import org.rococoa.ObjCClass;
import org.rococoa.Rococoa;
import org.rococoa.cocoa.foundation.NSObject;


/**
 * AVCaptureSession.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2023-12-18 nsano initial version <br>
 */
public abstract class AVCaptureSession extends NSObject {

    private static final _Class CLASS = Rococoa.createClass("AVCaptureSession", _Class.class);

    private interface _Class extends ObjCClass {
    }
}
