/*
 * Copyright (c) 2023 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.coreanimation;

import org.rococoa.ID;
import org.rococoa.ObjCClass;
import org.rococoa.Rococoa;
import org.rococoa.cocoa.foundation.NSObject;


/**
 * An object that manages image-based content and allows you to perform animations on that content.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2023-12-18 nsano initial version <br>
 */
public abstract class CALayer extends NSObject {

    public static final _Class CLASS = Rococoa.createClass("CALayer", _Class.class);

    public interface _Class extends ObjCClass {
        /** Creates and returns an instance of the layer object. */
        CALayer layer();
    }

    /** Returns an initialized CALayer object. */
    public abstract CALayer init();

    /** Override to copy or initialize custom fields of the specified layer. */
    public abstract CALayer initWithLayer(ID layer);
}
