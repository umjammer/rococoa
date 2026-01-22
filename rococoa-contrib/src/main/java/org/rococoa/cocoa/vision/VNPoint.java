/*
 * Copyright (c) 2022 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.vision;

import org.rococoa.ObjCClass;
import org.rococoa.Rococoa;
import org.rococoa.cocoa.coregraphics.CGPoint;
import org.rococoa.cocoa.foundation.NSObject;


/**
 * An immutable object that represents a single, two-dimensional point in an image.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2022-10-16 nsano initial version <br>
 */
public abstract class VNPoint extends NSObject {

    private static final _Class CLASS = Rococoa.createClass("VNPoint", _Class.class);

    private interface _Class extends ObjCClass {
        VNPoint alloc();
    }

    public abstract double x();

    public abstract double y();

    public abstract CGPoint location();
}
