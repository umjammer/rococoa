/*
 * Copyright (c) 2022 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.vision;

import java.util.logging.Logger;

import org.rococoa.ObjCClass;
import org.rococoa.Rococoa;


/**
 * A request that detects a human body pose.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2022-10-16 nsano initial version <br>
 */
public abstract class VNDetectHumanBodyPoseRequest extends VNImageBasedRequest {

    static {
        VisionLibrary.library.toString();
    }

    private static final Logger logger = Logger.getLogger(VNDetectHumanBodyPoseRequest.class.getName());

    public static final _Class CLASS = Rococoa.createClass("VNDetectHumanBodyPoseRequest", _Class.class);

    public interface _Class extends ObjCClass {
        VNDetectHumanBodyPoseRequest alloc();
    }

    @Override
    public abstract VNDetectHumanBodyPoseRequest init();

    /** utility */
    public static VNDetectHumanBodyPoseRequest newRequest() {
        return CLASS.alloc().init();
    }
}
