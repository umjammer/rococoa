/*
 * Copyright (c) 2022 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.vision;

import java.util.logging.Logger;

import org.rococoa.ObjCClass;
import org.rococoa.ObjCObjectByReference;
import org.rococoa.Rococoa;
import org.rococoa.cocoa.coreml.MLModel;
import org.rococoa.cocoa.foundation.NSError;
import org.rococoa.cocoa.foundation.NSObject;


/**
 * VNCoreMLModel.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2022-10-15 nsano initial version <br>
 */
public abstract class VNCoreMLModel extends NSObject {

    static {
        VisionLibrary.library.toString();
    }

    private static final Logger logger = Logger.getLogger(VNCoreMLModel.class.getName());

    public static final _Class CLASS = Rococoa.createClass("VNCoreMLModel", _Class.class);

    public interface _Class extends ObjCClass {
        /**
         * This method may fail if Vision does not support the created CoreML model.
         * For example, a model that does not accept an image as any of its inputs
         * will yield an VNErrorInvalidModel error.
         */
        VNCoreMLModel modelForMLModel_error(MLModel model, ObjCObjectByReference /* NSError */ error);
    }

    /** The name of the MLFeatureValue that Vision sets from the request handler. */
    public abstract String inputImageFeatureName();

    /** utility */
    public static VNCoreMLModel fromMLModel(MLModel mlModel) {
        ObjCObjectByReference errorRef = new ObjCObjectByReference();
        VNCoreMLModel model =  VNCoreMLModel.CLASS.modelForMLModel_error(mlModel, errorRef);
        NSError error = errorRef.getValueAs(NSError.class);
        if (error != null) {
            throw new IllegalStateException(error.description());
        }
        return model;
    }
}
