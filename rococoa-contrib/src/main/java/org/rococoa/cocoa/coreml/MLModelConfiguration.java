/*
 * Copyright (c) 2022 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.coreml;

import java.util.logging.Logger;

import org.rococoa.ObjCClass;
import org.rococoa.Rococoa;
import org.rococoa.cocoa.foundation.NSDictionary;
import org.rococoa.cocoa.foundation.NSObject;


/**
 * The settings for creating or updating a machine learning model.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2022-10-15 nsano initial version <br>
 */
public abstract class MLModelConfiguration extends NSObject {

    static {
        CoreMLLibrary.library.toString();
    }

    private static final Logger logger = Logger.getLogger(MLModelConfiguration.class.getName());

    public static final _Class CLASS = Rococoa.createClass("MLModelConfiguration", _Class.class);

    public interface _Class extends ObjCClass {
        MLModelConfiguration alloc();
    }

    public abstract MLModelConfiguration init();

    /** A human readable name of a model for display purposes. */
    public abstract String modelDisplayName();

    /** A dictionary of configuration settings your app can override when loading a model. */
    public abstract NSDictionary parameters();
}
