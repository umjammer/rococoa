/*
 * Copyright (c) 2022 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.vision;

import java.util.logging.Logger;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;
import org.rococoa.ObjCClass;
import org.rococoa.Rococoa;
import org.rococoa.cocoa.corevideo.VideoToolboxLibrary;
import org.rococoa.cocoa.foundation.NSObject;


/**
 * An object that represents an image that an image analysis request produces.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2022-10-15 nsano initial version <br>
 */
public abstract class VNPixelBufferObservation extends VNImageBasedRequest {

    private static final Logger logger = Logger.getLogger(VNPixelBufferObservation.class.getName());

    public static final _Class CLASS = Rococoa.createClass("VNPixelBufferObservation", _Class.class);

    public interface _Class extends ObjCClass {
        VNPixelBufferObservation alloc();
    }

    /** The image that results from a request with image output. */
    public abstract Pointer/*CVPixelBufferRef*/ pixelBuffer();

    /** A feature name that the CoreML model defines. */
    public abstract String featureName();

    /** @return CGImage Pointer */
    static Pointer convert(VNPixelBufferObservation observation) {
        Pointer pixelBuffer = observation.pixelBuffer();
        PointerByReference imageRef = new PointerByReference();
        VideoToolboxLibrary.library.VTCreateCGImageFromCVPixelBuffer(pixelBuffer, null, imageRef);
        return imageRef.getValue();
    }
}
