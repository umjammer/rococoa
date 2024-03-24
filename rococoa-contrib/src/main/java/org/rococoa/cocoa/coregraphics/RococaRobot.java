/*
 * Copyright (c) 2024 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.coregraphics;

import java.awt.MouseInfo;
import java.awt.Point;

import com.sun.jna.Pointer;
import org.rococoa.cocoa.corefoundation.CoreFoundation;

import static org.rococoa.carbon.CarbonCoreLibrary.kVK_Command;
import static org.rococoa.carbon.CarbonCoreLibrary.kVK_Control;
import static org.rococoa.carbon.CarbonCoreLibrary.kVK_Option;
import static org.rococoa.carbon.CarbonCoreLibrary.kVK_Shift;
import static org.rococoa.cocoa.coregraphics.CoreGraphicsLibrary.kCGEventFlagMaskAlternate;
import static org.rococoa.cocoa.coregraphics.CoreGraphicsLibrary.kCGEventFlagMaskCommand;
import static org.rococoa.cocoa.coregraphics.CoreGraphicsLibrary.kCGEventFlagMaskControl;
import static org.rococoa.cocoa.coregraphics.CoreGraphicsLibrary.kCGEventFlagMaskShift;
import static org.rococoa.cocoa.coregraphics.CoreGraphicsLibrary.kCGEventLeftMouseDown;
import static org.rococoa.cocoa.coregraphics.CoreGraphicsLibrary.kCGEventLeftMouseUp;
import static org.rococoa.cocoa.coregraphics.CoreGraphicsLibrary.kCGEventMouseMoved;
import static org.rococoa.cocoa.coregraphics.CoreGraphicsLibrary.kCGEventRightMouseDown;
import static org.rococoa.cocoa.coregraphics.CoreGraphicsLibrary.kCGEventRightMouseUp;
import static org.rococoa.cocoa.coregraphics.CoreGraphicsLibrary.kCGEventScrollWheel;
import static org.rococoa.cocoa.coregraphics.CoreGraphicsLibrary.kCGEventSourceStateHIDSystemState;
import static org.rococoa.cocoa.coregraphics.CoreGraphicsLibrary.kCGHIDEventTap;
import static org.rococoa.cocoa.coregraphics.CoreGraphicsLibrary.kCGMouseEventDeltaX;
import static org.rococoa.cocoa.coregraphics.CoreGraphicsLibrary.kCGMouseEventDeltaY;
import static org.rococoa.cocoa.coregraphics.CoreGraphicsLibrary.kCGScrollEventUnitPixel;
import static org.rococoa.cocoa.coregraphics.CoreGraphicsLibrary.library;


/**
 * RococaRobot. like {@link java.awt.Robot}.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2024-03-22 nsano initial version <br>
 */
public class RococaRobot {

    /** HID event source */
    private final Pointer /* CGEventSourceRef */ src = library.CGEventSourceCreate(kCGEventSourceStateHIDSystemState);

    /** Constructs a Robot object. */
    public RococaRobot() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> CoreFoundation.library.CFRelease(src)));

        Point p = MouseInfo.getPointerInfo().getLocation();
        prev = new CGPoint(p.x, p.y);
    }

    /** */
    private boolean command;
    /** */
    private boolean option;
    /** */
    private boolean control;
    /** */
    private boolean shift;

    /**
     * Presses a given key. The key should be released using the keyRelease method.
     * @param code get by karabiner-eventviewer etc.
     */
    public void keyPress(int code) {
        if (code == kVK_Command) {
            command = true;
        } else if (code == kVK_Option) {
            option = true;
        } else if (code == kVK_Control) {
            control = true;
        } else if (code == kVK_Shift) {
            shift = true;
        } else {
            Pointer /* CGEventRef */ event = library.CGEventCreateKeyboardEvent(src, (char) code, true);

            long flags = 0;
            if (command) flags |= kCGEventFlagMaskCommand;
            if (option) flags |= kCGEventFlagMaskAlternate;
            if (control) flags |= kCGEventFlagMaskControl;
            if (shift) flags |= kCGEventFlagMaskShift;
            library.CGEventSetFlags(event, flags);

            library.CGEventPost(kCGHIDEventTap, event);

            CoreFoundation.library.CFRelease(event);
        }
    }

    /**
     * Presses a given key. The key should be released using the keyRelease method.
     * @param code get by karabiner-eventviewer etc.
     */
    public void keyPress2(int code) {
        Pointer /* CGEventRef */ event = library.CGEventCreateKeyboardEvent(src, (char) code, true);

        library.CGEventPost(kCGHIDEventTap, event);

        CoreFoundation.library.CFRelease(event);
    }

    /**
     * Releases a given key.
     * @param code get by karabiner-eventviewer etc.
     */
    public void keyRelease(int code) {
        if (code == kVK_Command) {
            command = false;
        } else if (code == kVK_Option) {
            option = false;
        } else if (code == kVK_Control) {
            control = false;
        } else if (code == kVK_Shift) {
            command = false;
        } else {
            Pointer /* CGEventRef */ event = library.CGEventCreateKeyboardEvent(src, (char) code, false);

            long flags = 0;
            if (command) flags |= kCGEventFlagMaskCommand;
            if (option) flags |= kCGEventFlagMaskAlternate;
            if (control) flags |= kCGEventFlagMaskControl;
            if (shift) flags |= kCGEventFlagMaskShift;
            library.CGEventSetFlags(event, flags);

            library.CGEventPost(kCGHIDEventTap, event);

            CoreFoundation.library.CFRelease(event);
        }
    }

    /**
     * Releases a given key.
     * @param code get by karabiner-eventviewer etc.
     */
    public void keyRelease2(int code) {
        Pointer /* CGEventRef */ event = library.CGEventCreateKeyboardEvent(src, (char) code, false);

        library.CGEventPost(kCGHIDEventTap, event);

        CoreFoundation.library.CFRelease(event);
    }

    /** the previous pointer */
    private CGPoint prev;

    /** Moves mouse pointer to given screen coordinates with moving motion. */
    public void mouseMove(int x, int y) {
        int dx = x - prev.x.intValue();
        int dy = y - prev.y.intValue();
        prev = new CGPoint(x, y);
        Pointer /* CGEventRef */ event = library.CGEventCreateMouseEvent(
                null, kCGEventMouseMoved,
                prev,
                0 // ignored
        );
        // Now, execute these events with an interval to make them noticeable
        library.CGEventSetIntegerValueField(event, kCGMouseEventDeltaX, dx);
        library.CGEventSetIntegerValueField(event, kCGMouseEventDeltaY, dy);
        library.CGEventPost(kCGHIDEventTap, event);
        CoreFoundation.library.CFRelease(event);
    }

    /** Moves mouse pointer to given screen coordinates w/o moving motion means like teleportation. */
    public void mouseMove0(int x, int y) {
        prev = new CGPoint(x, y);
        Pointer /* CGEventRef */ event = library.CGEventCreateMouseEvent(
                null, kCGEventMouseMoved,
                prev,
                0 // ignored
        );
        // Now, execute these events with an interval to make them noticeable
        library.CGEventPost(kCGHIDEventTap, event);
        CoreFoundation.library.CFRelease(event);
    }

    /** Moves mouse pointer to given deltas. */
    public void mouseMove2(int dx, int dy) {
        Pointer /* CGEventRef */ event = library.CGEventCreateMouseEvent(
                null, kCGEventMouseMoved,
                prev,
                0 // ignored
        );
        // Now, execute these events with an interval to make them noticeable
        library.CGEventSetIntegerValueField(event, kCGMouseEventDeltaX, dx);
        library.CGEventSetIntegerValueField(event, kCGMouseEventDeltaY, dy);
        library.CGEventPost(kCGHIDEventTap, event);
        CoreFoundation.library.CFRelease(event);
    }

    /**
     * Presses one or more mouse buttons. The mouse buttons should be released using the mouseRelease(int) method.
     * @param buttons kCGMouseButtonLeft, kCGMouseButtonRight
     */
    public void mousePress(int buttons) {
        int[] events = {kCGEventLeftMouseDown, kCGEventRightMouseDown};
        Pointer /* CGEventRef */ event = library.CGEventCreateMouseEvent(
                null, events[buttons],
                prev,
                buttons
        );
        library.CGEventPost(kCGHIDEventTap, event);
        CoreFoundation.library.CFRelease(event);
    }

    /**
     * Presses one or more mouse buttons. The mouse buttons should be released using the mouseRelease(int) method.
     * @param buttons kCGMouseButtonLeft, kCGMouseButtonRight
     */
    public void mousePress2(int buttons, int x, int y) {
        prev = new CGPoint(x, y);
        int[] events = {kCGEventLeftMouseDown, kCGEventRightMouseDown};
        Pointer /* CGEventRef */ event = library.CGEventCreateMouseEvent(
                null, events[buttons],
                prev,
                buttons
        );
        library.CGEventPost(kCGHIDEventTap, event);
        CoreFoundation.library.CFRelease(event);
    }

    /**
     * Releases one or more mouse buttons using previous point.
     * @param buttons kCGMouseButtonLeft, kCGMouseButtonRight
     */
    public void mouseRelease(int buttons) {
        int[] events = {kCGEventLeftMouseUp, kCGEventRightMouseUp};
        Pointer /* CGEventRef */ event = library.CGEventCreateMouseEvent(
                null, events[buttons],
                prev,
                buttons
        );
        library.CGEventPost(kCGHIDEventTap, event);
        CoreFoundation.library.CFRelease(event);
    }
    /**
     * Releases one or more mouse buttons.
     * @param buttons kCGMouseButtonLeft, kCGMouseButtonRight
     */
    public void mouseRelease2(int buttons, int x, int y) {
        prev = new CGPoint(x, y);
        int[] events = {kCGEventLeftMouseUp, kCGEventRightMouseUp};
        Pointer /* CGEventRef */ event = library.CGEventCreateMouseEvent(
                null, events[buttons],
                prev,
                buttons
        );
        library.CGEventPost(kCGHIDEventTap, event);
        CoreFoundation.library.CFRelease(event);
    }

    /** Rotates the scroll wheel on wheel-equipped mice. */
    public void mouseWheel(int wheelAmt) {
        Pointer /* CGEventRef */ event = library.CGEventCreateScrollWheelEvent2(
                null, kCGScrollEventUnitPixel, 1, wheelAmt, 0, 0);
        library.CGEventSetType(event, kCGEventScrollWheel);
        library.CGEventPost(kCGHIDEventTap, event);
        CoreFoundation.library.CFRelease(event);
    }
}
