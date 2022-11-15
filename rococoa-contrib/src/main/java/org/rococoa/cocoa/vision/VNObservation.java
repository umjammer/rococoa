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
 * The abstract superclass for analysis results.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2022-10-15 nsano initial version <br>
 */
public abstract class VNObservation extends VNImageBasedRequest {

    private static final Logger logger = Logger.getLogger(VNObservation.class.getName());

    public static final _Class CLASS = Rococoa.createClass("VNObservation", _Class.class);

    public interface _Class extends ObjCClass {
        VNObservation alloc();
    }
}
