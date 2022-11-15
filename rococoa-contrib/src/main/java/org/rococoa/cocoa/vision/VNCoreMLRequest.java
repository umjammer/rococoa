/*
 * Copyright (c) 2022 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.vision;

import java.util.logging.Logger;

import com.sun.jna.Pointer;
import org.rococoa.ID;
import org.rococoa.ObjCBlock;
import org.rococoa.ObjCClass;
import org.rococoa.ObjCObjectByReference;
import org.rococoa.Rococoa;


/**
 * An image analysis request that uses a Core ML model to process images.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2022-10-15 nsano initial version <br>
 */
public abstract class VNCoreMLRequest extends VNImageBasedRequest {

    private static final Logger logger = Logger.getLogger(VNCoreMLRequest.class.getName());

    public static final _Class CLASS = Rococoa.createClass("VNCoreMLRequest", _Class.class);

    public interface _Class extends ObjCClass {
        VNCoreMLRequest alloc();
    }

    /** Creates a model container to be used with VNCoreMLRequest based on a Core ML model. */
    public abstract VNCoreMLRequest initWithModel(VNCoreMLModel model);

    /**
     * A type alias to encapsulate the syntax for the completion handler block
     * that's invoked after the request has finished processing.
     */
    public interface VNRequestCompletionHandler extends ObjCBlock {
        void apply(ID/*VNRequest*/ requestId, ObjCObjectByReference errorRef);
    }

    /**
     * Creates a model container to be used with VNCoreMLRequest
     * based on a Core ML model, with an optional completion handler.
     */
    public abstract VNCoreMLRequest initWithModel_completionHandler(VNCoreMLModel model, ID/*VNRequestCompletionHandler*/ completionHandler);

    /** utility */
    public static VNCoreMLRequest newRequest(VNCoreMLModel model) {
        return CLASS.alloc().initWithModel(model);
    }

    /**
     * The Core ML model on which the request is based, wrapped in a VNCoreMLModel.
     */
    public abstract VNCoreMLModel model();
}
