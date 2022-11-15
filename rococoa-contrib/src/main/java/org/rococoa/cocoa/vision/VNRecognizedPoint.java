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
 * An object that represents a normalized point in an image, along with an identifier label and a confidence value.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2022-10-16 nsano initial version <br>
 */
public abstract class VNRecognizedPoint extends VNDetectedPoint {

    private static final Logger logger = Logger.getLogger(VNRecognizedPoint.class.getName());

    public static final _Class CLASS = Rococoa.createClass("VNRecognizedPoint", _Class.class);

    public interface _Class extends ObjCClass {
        VNRecognizedPoint alloc();
    }

    /** The point’s identifier label. */
    public abstract String/*VNRecognizedPointKey*/ identifier();
}
