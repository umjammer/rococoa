/*
 * Copyright (c) 2022 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.vision;

import java.util.logging.Logger;

import com.sun.jna.Pointer;
import org.rococoa.ObjCClass;
import org.rococoa.Rococoa;


/**
 * An observation that provides the body points the analysis recognized.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2022-10-16 nsano initial version <br>
 */
public abstract class VNRecognizedPointsObservation extends VNObservation {

    private static final Logger logger = Logger.getLogger(VNRecognizedPointsObservation.class.getName());

    public static final _Class CLASS = Rococoa.createClass("VNRecognizedPointsObservation", _Class.class);

    public interface _Class extends ObjCClass {
        VNRecognizedPointsObservation alloc();
    }
}
