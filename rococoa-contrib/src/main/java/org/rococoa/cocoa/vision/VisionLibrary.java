/*
 * Copyright (c) 2022 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.vision;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import org.rococoa.cocoa.coregraphics.CGPoint;


/**
 * Apply computer vision algorithms to perform a variety of tasks on input images and video.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2022-10-15 nsano initial version <br>
 */
public interface VisionLibrary extends Library {

    VisionLibrary library = Native.load("Vision", VisionLibrary.class);

    /** Projects a point in normalized coordinates into image coordinates. */
    CGPoint VNImagePointForNormalizedPoint(CGPoint normalizedPoint, NativeLong imageWidth, NativeLong imageHeight);
}
