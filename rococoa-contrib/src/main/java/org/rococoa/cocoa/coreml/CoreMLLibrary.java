/*
 * Copyright (c) 2022 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.coreml;

import com.sun.jna.Callback;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import org.rococoa.cocoa.CGFloat;
import org.rococoa.cocoa.coregraphics.CGRect;


/**
 * CoreMLLibrary.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2022-10-15 nsano initial version <br>
 */
public interface CoreMLLibrary extends Library {

    CoreMLLibrary library = Native.load("CoreML", CoreMLLibrary.class);

}
