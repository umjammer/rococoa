/*
 * Copyright (c) 2024 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.coregraphics;

import com.sun.jna.Pointer;
import org.rococoa.cocoa.corefoundation.CoreFoundation;

import static org.rococoa.cocoa.coregraphics.CoreGraphicsLibrary.kCGEventFlagMaskCommand;
import static org.rococoa.cocoa.coregraphics.CoreGraphicsLibrary.kCGEventLeftMouseDown;
import static org.rococoa.cocoa.coregraphics.CoreGraphicsLibrary.kCGEventLeftMouseUp;
import static org.rococoa.cocoa.coregraphics.CoreGraphicsLibrary.kCGEventMouseMoved;
import static org.rococoa.cocoa.coregraphics.CoreGraphicsLibrary.kCGEventSourceStateHIDSystemState;
import static org.rococoa.cocoa.coregraphics.CoreGraphicsLibrary.kCGHIDEventTap;
import static org.rococoa.cocoa.coregraphics.CoreGraphicsLibrary.kCGMouseButtonLeft;
import static org.rococoa.cocoa.coregraphics.CoreGraphicsLibrary.library;


/**  */
public class RococaRobot {

    private final Pointer /* CGEventSourceRef */ src = library.CGEventSourceCreate(kCGEventSourceStateHIDSystemState);

    public RococaRobot() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> CoreFoundation.library.CFRelease(src)));
    }

    /**  */
    public void keyPress(int code) {
        Pointer /* CGEventRef */ d = library.CGEventCreateKeyboardEvent(src, (char) code, true);

        library.CGEventSetFlags(d, kCGEventFlagMaskCommand);

        int /* CGEventTapLocation */ loc = kCGHIDEventTap; // kCGSessionEventTap also works
        library.CGEventPost(loc, d);

        CoreFoundation.library.CFRelease(d);
    }

    /**  */
    public void keyRelease(int code) {
        Pointer /* CGEventRef */ u = library.CGEventCreateKeyboardEvent(src, (char) code, false);

        library.CGEventSetFlags(u, kCGEventFlagMaskCommand);

        int /* CGEventTapLocation */ loc = kCGHIDEventTap; // kCGSessionEventTap also works
        library.CGEventPost(loc, u);

        CoreFoundation.library.CFRelease(u);
    }

    private CGPoint prev;

    /**  */
    public void mouseMove(int x, int y) {
        prev = new CGPoint(x, y);
        Pointer /* CGEventRef */ move = library.CGEventCreateMouseEvent(
                null, kCGEventMouseMoved,
                prev,
                kCGMouseButtonLeft // ignored
        );
        // Now, execute these events with an interval to make them noticeable
        library.CGEventPost(kCGHIDEventTap, move);
        CoreFoundation.library.CFRelease(move);
    }

    /**  */
    public void mousePress(int buttons) {
        Pointer /* CGEventRef */ click_down = library.CGEventCreateMouseEvent(
                null, kCGEventLeftMouseDown,
                prev,
                kCGMouseButtonLeft
        );
        library.CGEventPost(kCGHIDEventTap, click_down);
        CoreFoundation.library.CFRelease(click_down);
    }

    /**  */
    public void mouseRelease(int buttons) {
        Pointer /* CGEventRef */ click_up = library.CGEventCreateMouseEvent(
                null, kCGEventLeftMouseUp,
                prev,
                kCGMouseButtonLeft
        );
        library.CGEventPost(kCGHIDEventTap, click_up);
        CoreFoundation.library.CFRelease(click_up);
    }
}
