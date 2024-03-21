/*
 * Copyright (c) 2023 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.vision;

import java.util.logging.Logger;

import org.rococoa.ObjCClass;
import org.rococoa.Rococoa;


/**
 * A request that detects a human hand pose.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2023-12-18 nsano initial version <br>
 */
public abstract class VNDetectHumanHandPoseRequest extends VNImageBasedRequest {

    static {
        VisionLibrary.library.toString();
    }

    private static final Logger logger = Logger.getLogger(VNDetectHumanHandPoseRequest.class.getName());

    public static final _Class CLASS = Rococoa.createClass("VNDetectHumanHandPoseRequest", _Class.class);

    public interface _Class extends ObjCClass {
        VNDetectHumanHandPoseRequest alloc();
    }

    @Override
    public abstract VNDetectHumanHandPoseRequest init();

    /** utility */
    public static VNDetectHumanHandPoseRequest newRequest() {
        return CLASS.alloc().init();
    }

    public abstract int maximumHandCount();

    /** The maximum number of hands to detect in an image. */
    public abstract void setMaximumHandCount(int count);
}
