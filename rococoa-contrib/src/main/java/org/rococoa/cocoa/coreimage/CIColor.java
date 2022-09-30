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
import org.rococoa.cocoa.foundation.NSString;


/**
 * CIColor.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2022-09-04 nsano initial version <br>
 */
public abstract class CIColor extends NSObject {
    
    public static final _Class CLASS = Rococoa.createClass("CIColor",  _Class.class);

    public interface _Class extends ObjCClass {
        CIColor colorWithCGColor(Pointer/*CGColorRef*/ c);

        CIColor colorWithString(String representation);
    }

    public abstract Pointer/*CGImageRef*/ createCGImage_fromRect(CIImage image, CGRect fromRect);

    public abstract String stringRepresentation();
}
