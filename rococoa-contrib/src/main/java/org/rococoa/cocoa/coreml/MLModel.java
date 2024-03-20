/*
 * Copyright (c) 2022 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.coreml;

import java.util.logging.Logger;

import org.rococoa.ObjCClass;
import org.rococoa.ObjCObjectByReference;
import org.rococoa.Rococoa;
import org.rococoa.cocoa.foundation.NSError;
import org.rococoa.cocoa.foundation.NSObject;
import org.rococoa.cocoa.foundation.NSURL;


/**
 * MLModel.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2022-10-15 nsano initial version <br>
 */
public abstract class MLModel extends NSObject {

    static {
        CoreMLLibrary.library.toString();
    }

    private static final Logger logger = Logger.getLogger(MLModel.class.getName());

    public static final _Class CLASS = Rococoa.createClass("MLModel", _Class.class);

    public interface _Class extends ObjCClass {
        /** Creates a Core ML model instance from a compiled model file. */
        MLModel modelWithContentsOfURL_error(NSURL url, ObjCObjectByReference/*NSError*/ error);
        /** Creates a Core ML model instance from a compiled model file and a custom configuration. */
        MLModel modelWithContentsOfURL_configuration_error(NSURL url, MLModelConfiguration configuration, ObjCObjectByReference/*NSError*/ error);
        MLModel alloc();
        @Deprecated
        NSURL compileModelAtURL_error(NSURL modelURL, ObjCObjectByReference/*NSError*/ error);
    }

    public static MLModel modelWithContentsOfURL(NSURL url) {
        ObjCObjectByReference errorRef = new ObjCObjectByReference();
        MLModel model = CLASS.modelWithContentsOfURL_error(url, errorRef);
        NSError error = errorRef.getValueAs(NSError.class);
        if (error != null) {
            throw new IllegalStateException(error.description());
        }
        return model;
    }

    public static MLModel modelWithContentsOfURLConfiguration(NSURL url, MLModelConfiguration configuration) {
        ObjCObjectByReference errorRef = new ObjCObjectByReference();
        MLModel model = CLASS.modelWithContentsOfURL_configuration_error(url, configuration, errorRef);
        NSError error = errorRef.getValueAs(NSError.class);
        if (error != null) {
            throw new IllegalStateException(error.description());
        }
        return model;
    }

    /** Model information you use at runtime during development, which Xcode also displays in its Core ML model editor view. */
    public abstract MLModelDescription modelDescription();

    /** */
    public static NSURL compileModelAtURL(NSURL modelURL) {
        ObjCObjectByReference errorRef = new ObjCObjectByReference();
        NSURL url = CLASS.compileModelAtURL_error(modelURL, errorRef);
        NSError error = errorRef.getValueAs(NSError.class);
        if (error != null) {
            throw new IllegalStateException(error.description());
        }
        return url;
    }

    public static MLModel fromPath(String path) {
        NSURL url = NSURL.fileURLWithPath(path);
        NSURL compiledUrl = MLModel.compileModelAtURL(url);
logger.fine("compiledUrl: " + compiledUrl);
        return MLModel.modelWithContentsOfURL(compiledUrl);
    }
}
