/*
 * Copyright (c) 2022 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.vision;

import org.rococoa.ObjCClass;
import org.rococoa.Rococoa;


/**
 * An object that represents a normalized point in an image, along with a confidence value.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2022-10-16 nsano initial version <br>
 */
public abstract class VNDetectedPoint extends VNPoint {

    public static final _Class CLASS = Rococoa.createClass("VNDetectedPoint", _Class.class);

    public interface _Class extends ObjCClass {
        VNDetectedPoint alloc();
    }

    /** A confidence score that indicates the detected point’s accuracy. */
    public abstract float/*VNConfidence*/ confidence();
}
