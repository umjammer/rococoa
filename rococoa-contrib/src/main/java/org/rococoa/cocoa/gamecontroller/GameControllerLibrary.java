/*
 * Copyright (c) 2023 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.gamecontroller;

import com.sun.jna.Callback;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import org.rococoa.cocoa.CGFloat;
import org.rococoa.cocoa.coregraphics.CGRect;


/**
 * GameControllerLibrary.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2023-05-18 nsano initial version <br>
 * @see "https://developer.apple.com/documentation/gamecontroller?language=objc"
 */
public interface GameControllerLibrary extends Library {

    GameControllerLibrary library = Native.load("GameController", GameControllerLibrary.class);

}
