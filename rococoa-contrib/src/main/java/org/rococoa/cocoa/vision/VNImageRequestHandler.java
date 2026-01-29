/*
 * Copyright (c) 2022 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.vision;

import com.sun.jna.Pointer;
import org.rococoa.ObjCClass;
import org.rococoa.ObjCObjectByReference;
import org.rococoa.Rococoa;
import org.rococoa.cocoa.coreimage.CIImage;
import org.rococoa.cocoa.foundation.NSArray;
import org.rococoa.cocoa.foundation.NSData;
import org.rococoa.cocoa.foundation.NSDictionary;
import org.rococoa.cocoa.foundation.NSError;
import org.rococoa.cocoa.foundation.NSObject;
import org.rococoa.cocoa.foundation.NSURL;


/**
 * An object that processes one or more image analysis requests pertaining to a single image.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2022-10-15 nsano initial version <br>
 */
public abstract class VNImageRequestHandler extends NSObject {

    private static final _Class CLASS = Rococoa.createClass("VNImageRequestHandler", _Class.class);

    private interface _Class extends ObjCClass {
        VNImageRequestHandler alloc();
    }

    /** Creates a handler to be used for performing requests on Core Graphics images. */
    public abstract VNImageRequestHandler initWithCGImage_options(Pointer /* CGImageRef */ image, NSDictionary options);
    /** Creates a handler to be used for performing requests on CIImage data. */
    public abstract VNImageRequestHandler initWithCIImage_options(CIImage image, NSDictionary options);
    /** Creates a handler to be used for performing requests on an image contained in an NSData object. */
    public abstract VNImageRequestHandler initWithData_options(NSData imageData, NSDictionary options);
    /** Creates a handler to be used for performing requests on an image at the specified URL. */
    public abstract VNImageRequestHandler initWithURL_options(NSURL imageURL, NSDictionary options);

    /** utility */
    public static VNImageRequestHandler initWithCGImage(Pointer /* CGImageRef */ image) {
        return CLASS.alloc().initWithCGImage_options(image, NSDictionary.emptyDictionary());
    }

    /** Schedules Vision requests to be performed. */
    public abstract boolean performRequests_error(NSArray requests, ObjCObjectByReference /* NSError */ error);

    /** utility */
    public void performRequests(VNImageBasedRequest request) {
        ObjCObjectByReference errorRef = new ObjCObjectByReference();
        NSArray coreMLRequests = NSArray.arrayWithObjects(request);
        performRequests_error(coreMLRequests, errorRef);
        NSError error = errorRef.getValueAs(NSError.class);
        if (error != null) {
            throw new IllegalStateException(error.description());
        }
    }
}
