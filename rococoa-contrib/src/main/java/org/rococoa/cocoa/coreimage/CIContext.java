/*
 * Copyright (c) 2022 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.coreimage;

import com.sun.jna.Pointer;
import org.rococoa.ObjCClass;
import org.rococoa.Rococoa;
import org.rococoa.cocoa.coregraphics.CGRect;
import org.rococoa.cocoa.foundation.NSDictionary;
import org.rococoa.cocoa.foundation.NSObject;


/**
 * CIContext.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2022-09-04 nsano initial version <br>
 */
public abstract class CIContext extends NSObject {
    
    public static final _Class CLASS = Rococoa.createClass("CIContext",  _Class.class);

    public interface _Class extends ObjCClass {
        CIContext contextWithOptions(NSDictionary options);
    }

    /** Creates a Quartz 2D image from a region of a Core Image image object. */
    public abstract Pointer /* CGImageRef */ createCGImage_fromRect(CIImage image, CGRect fromRect);
}
