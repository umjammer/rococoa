/*
 * Copyright (c) 2023 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.vision;

import java.util.logging.Logger;

import com.sun.jna.Pointer;
import org.rococoa.ObjCClass;
import org.rococoa.ObjCObjectByReference;
import org.rococoa.Rococoa;
import org.rococoa.cocoa.foundation.NSArray;
import org.rococoa.cocoa.foundation.NSObject;


/**
 * An object that processes image analysis requests for each frame in a sequence.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2023-12-18 nsano initial version <br>
 */
public abstract class VNSequenceRequestHandler extends NSObject {

    private static final Logger logger = Logger.getLogger(VNSequenceRequestHandler.class.getName());

    public static final _Class CLASS = Rococoa.createClass("VNSequenceRequestHandler", _Class.class);

    public interface _Class extends ObjCClass {
        VNSequenceRequestHandler alloc();
    }

    public abstract VNSequenceRequestHandler init();

    /** utility */
    public static VNSequenceRequestHandler newInstance() {
        return CLASS.alloc().init();
    }

    /** Schedules Vision requests to be performed on a Core Graphics image. */
    public abstract boolean performRequests_onCGImage_error(NSArray /* VNRequest */ requests, Pointer /* CGImageRef */ image, ObjCObjectByReference /* NSError */ error);
}
