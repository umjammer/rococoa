/*
 * Copyright (c) 2022 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.coreimage;

import org.rococoa.ID;
import org.rococoa.ObjCClass;
import org.rococoa.Rococoa;
import org.rococoa.cocoa.foundation.NSArray;
import org.rococoa.cocoa.foundation.NSDictionary;
import org.rococoa.cocoa.foundation.NSObject;
import org.rococoa.cocoa.foundation.NSString;


/**
 * CIFilter.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2022-09-04 nsano initial version <br>
 */
public abstract class CIFilter extends NSObject {
    
    public static final _Class CLASS = Rococoa.createClass("CIFilter",  _Class.class);

    public interface _Class extends ObjCClass {
        CIFilter filterWithName(String name);
        CIFilter filterWithName_withInputParameters(String name, NSDictionary params);
        CIFilter filterWithName_keysAndValues(String name, ID... key0);
    }

    public static CIFilter of(String filterName) {
        return CIFilter.CLASS.filterWithName(filterName);
    }

    public abstract void setValue_forKey(NSObject value, String key);

    public abstract NSDictionary attributes();

    public abstract void setEnabled(boolean enabled);

    public abstract boolean enabled();

    public abstract NSArray inputKeys();

    public abstract NSArray outputKeys();

    public abstract CIImage outputImage();

    public abstract NSObject value_ForKey(String key);
}
