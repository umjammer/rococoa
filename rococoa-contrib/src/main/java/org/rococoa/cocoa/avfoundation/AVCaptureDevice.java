/*
 * Copyright (c) 2023 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.avfoundation;

import java.util.logging.Logger;

import org.rococoa.ObjCClass;
import org.rococoa.Rococoa;
import org.rococoa.cocoa.foundation.NSObject;


/**
 * AVCaptureDevice.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2023-12-18 nsano initial version <br>
 */
public abstract class AVCaptureDevice extends NSObject {

    private static final Logger logger = Logger.getLogger(AVCaptureDevice.class.getName());

    public static final _Class CLASS = Rococoa.createClass("AVCaptureDevice", _Class.class);

    public interface _Class extends ObjCClass {
    }
}
