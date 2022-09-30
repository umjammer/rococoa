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
import org.rococoa.cocoa.foundation.NSArray;
import org.rococoa.cocoa.foundation.NSObject;
import org.rococoa.cocoa.foundation.NSURL;


/**
 * CIImage.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2022-09-04 nsano initial version <br>
 */
public abstract class CIImage extends NSObject {

    public static final _Class CLASS = Rococoa.createClass("CIImage", _Class.class);

    public interface _Class extends ObjCClass {
        CIImage emptyImage();
        CIImage imageWithCGImage(Pointer/*CGImageRef*/ image);
        CIImage imageWithContentsOfURL(NSURL url);
    }

    public abstract CGRect extent();
}
