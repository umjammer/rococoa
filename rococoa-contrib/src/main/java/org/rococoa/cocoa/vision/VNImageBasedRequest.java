/*
 * Copyright (c) 2022 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.vision;

import org.rococoa.ObjCClass;
import org.rococoa.Rococoa;


/**
 * VNImageBasedRequest.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2022-10-15 nsano initial version <br>
 */
public abstract class VNImageBasedRequest extends VNRequest {

    public static final _Class CLASS = Rococoa.createClass("VNImageBasedRequest", _Class.class);

    public interface _Class extends ObjCClass {
    }
}
