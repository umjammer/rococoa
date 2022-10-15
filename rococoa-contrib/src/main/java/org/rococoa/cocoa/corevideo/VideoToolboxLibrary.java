/*
 * Copyright (c) 2022 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.corevideo;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;


/**
 * VideoToolboxLibrary.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2022-10-15 nsano initial version <br>
 */
public interface VideoToolboxLibrary extends Library {

    VideoToolboxLibrary library = Native.load("VideoToolbox", VideoToolboxLibrary.class);

    int/*OSStatus*/ VTCreateCGImageFromCVPixelBuffer(Pointer/*CVPixelBufferRef*/ pixelBuffer, Pointer/*CFDictionaryRef*/ options, PointerByReference/*CGImageRef*/ imageOut);
}
