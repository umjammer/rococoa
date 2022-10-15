/*
 * Copyright (c) 2022 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.vision;

import com.sun.jna.Library;
import com.sun.jna.Native;


/**
 * VisionLibrary.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2022-10-15 nsano initial version <br>
 */
public interface VisionLibrary extends Library {

    VisionLibrary library = Native.load("Vision", VisionLibrary.class);

}
