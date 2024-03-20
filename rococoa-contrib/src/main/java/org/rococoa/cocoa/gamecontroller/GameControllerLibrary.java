/*
 * Copyright (c) 2023 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.gamecontroller;

import com.sun.jna.Library;
import com.sun.jna.Native;


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
