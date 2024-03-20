/*
 * Copyright (c) 2022 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.corevideo;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;


/**
 * CoreVideoLibrary.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2022-10-15 nsano initial version <br>
 */
public interface CoreVideoLibrary extends Library {

    CoreVideoLibrary library = Native.load("CoreVideo", CoreVideoLibrary.class);

    int CVPixelBufferGetWidth(Pointer/*CVPixelBufferRef*/ pixelBuffer);

    int CVPixelBufferGetHeight(Pointer/*CVPixelBufferRef*/ pixelBuffer);

    void CVPixelBufferRetain(Pointer/*CVPixelBufferRef*/ pixelBuffer);

    void CVPixelBufferRelease(Pointer/*CVPixelBufferRef*/ pixelBuffer);

    int CVPixelBufferGetDataSize(Pointer/*CVPixelBufferRef*/ pixelBuffer);
}
