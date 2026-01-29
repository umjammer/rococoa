/*
 * Copyright (c) 2022 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.coreimage;

import com.sun.jna.Pointer;
import org.rococoa.ObjCClass;
import org.rococoa.Rococoa;
import org.rococoa.cocoa.CGFloat;
import org.rococoa.cocoa.coregraphics.CGRect;
import org.rococoa.cocoa.coregraphics.CoreGraphicsLibrary;
import org.rococoa.cocoa.foundation.NSObject;


/**
 * CIColor.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2022-09-04 nsano initial version <br>
 */
public abstract class CIColor extends NSObject {
    
    private static final _Class CLASS = Rococoa.createClass("CIColor",  _Class.class);

    private interface _Class extends ObjCClass {
        CIColor colorWithCGColor(Pointer /* CGColorRef */ c);

        CIColor colorWithString(String representation);
    }

    public static CIColor colorWithCGColor(Pointer /* CGColorRef */ c) {
        return CLASS.colorWithCGColor(c);
    }

    public static CIColor colorWithString(String representation) {
        return CLASS.colorWithString(representation);
    }

    public abstract Pointer /* CGImageRef */ createCGImage_fromRect(CIImage image, CGRect fromRect);

    public abstract String stringRepresentation();

    public static CIColor newInstance(double r, double g, double b, double a) {
        Pointer colorRef = CoreGraphicsLibrary.library.CGColorCreateGenericRGB(
                new CGFloat(r), new CGFloat(g), new CGFloat(b), new CGFloat(a));
        String colorString = colorWithCGColor(colorRef).stringRepresentation();
        return colorWithString(colorString);
    }
}
