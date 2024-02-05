/*
 * Copyright (c) 2022 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.coregraphics;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.logging.Level;

import com.sun.jna.Pointer;
import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.rococoa.Rococoa;
import org.rococoa.cocoa.CGFloat;
import org.rococoa.cocoa.appkit.NSRunningApplication;
import org.rococoa.cocoa.appkit.NSWorkspace;
import org.rococoa.cocoa.coreimage.CIImage;
import org.rococoa.cocoa.corefoundation.CoreFoundation;
import vavi.util.Debug;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.rococoa.cocoa.coregraphics.CoreGraphicsLibrary.kCGEventFlagMaskCommand;
import static org.rococoa.cocoa.coregraphics.CoreGraphicsLibrary.kCGEventLeftMouseDown;
import static org.rococoa.cocoa.coregraphics.CoreGraphicsLibrary.kCGEventLeftMouseUp;
import static org.rococoa.cocoa.coregraphics.CoreGraphicsLibrary.kCGEventMouseMoved;
import static org.rococoa.cocoa.coregraphics.CoreGraphicsLibrary.kCGEventSourceStateHIDSystemState;
import static org.rococoa.cocoa.coregraphics.CoreGraphicsLibrary.kCGHIDEventTap;
import static org.rococoa.cocoa.coregraphics.CoreGraphicsLibrary.kCGMouseButtonLeft;
import static org.rococoa.cocoa.coregraphics.CoreGraphicsLibrary.keyCodeForChar;
import static org.rococoa.cocoa.coregraphics.CoreGraphicsLibrary.library;


/**
 * CoreGraphicsLibraryTest.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2022-09-11 nsano initial version <br>
 */
public class CoreGraphicsLibraryTest {

    @Test
    void tes1() throws Exception {
        CGRect r = new CGRect(10, 20, 200, 100);
Debug.println("rect: " + r);
        assertEquals(10, r.getPointer().getDouble(0));
        assertEquals(20, r.getPointer().getDouble(8));
        assertEquals(200, r.getPointer().getDouble(16));
        assertEquals(100, r.getPointer().getDouble(24));
    }

    @Test
    void tes12() throws Exception {
        CGPoint p = new CGPoint(10, 20);
Debug.println("point: " + p);
        assertEquals(10, p.getPointer().getDouble(0));
        assertEquals(20, p.getPointer().getDouble(8));
    }

    @Test
    void tes13() throws Exception {
        CGSize s = new CGSize(200, 100);
Debug.println("size: " + s);
        assertEquals(200, s.getPointer().getDouble(0));
        assertEquals(100, s.getPointer().getDouble(8));
    }

    @Test
    void tes2() throws Exception {
        CGImage cgImage = new CGImage(CoreGraphicsLibraryTest.class.getResourceAsStream("/sample1.heic"));
Debug.println("cgImage: " + cgImage.getWidth() + "x" + cgImage.getHeight());
        CGRect r = new CGRect(10, 20, 200, 100);
Debug.println("rect: " + r);

        CIImage ciImage = CIImage.CLASS.imageWithCGImage(cgImage.pointer());
Debug.println("ciImage: " + ciImage);

        Pointer cgImage2 = library.CGImageCreateWithImageInRect(cgImage.pointer(), r);
Debug.println("CGImageCreateWithImageInRect: " + cgImage2);
        int w = library.CGImageGetWidth(cgImage2);
        int h = library.CGImageGetHeight(cgImage2);
Debug.println("w: " + w + ", h: " + h);
        assertEquals(200, w);
        assertEquals(100, h);
    }

    /** @see "https://stackoverflow.com/a/10745616" */
    @Test
    void test3() throws Exception {
        Pointer /* CGEventSourceRef */ src = library.CGEventSourceCreate(kCGEventSourceStateHIDSystemState);

        Pointer /* CGEventRef */ cmdd = library.CGEventCreateKeyboardEvent(src, (char) 0x38, true);
        Pointer /* CGEventRef */ cmdu = library.CGEventCreateKeyboardEvent(src, (char) 0x38, false);
        Pointer /* CGEventRef */ spcd = library.CGEventCreateKeyboardEvent(src, (char) 0x31, true);
        Pointer /* CGEventRef */ spcu = library.CGEventCreateKeyboardEvent(src, (char) 0x31, false);

        library.CGEventSetFlags(spcd, kCGEventFlagMaskCommand);
        library.CGEventSetFlags(spcu, kCGEventFlagMaskCommand);

        int /* CGEventTapLocation */ loc = kCGHIDEventTap; // kCGSessionEventTap also works
        library.CGEventPost(loc, cmdd);
        library.CGEventPost(loc, spcd);
        library.CGEventPost(loc, spcu);
        library.CGEventPost(loc, cmdu);

        CoreFoundation.library.CFRelease(cmdd);
        CoreFoundation.library.CFRelease(cmdu);
        CoreFoundation.library.CFRelease(spcd);
        CoreFoundation.library.CFRelease(spcu);
        CoreFoundation.library.CFRelease(src);
    }

    // NOT rococoa
    @Test
    @DisplayName("list jvms")
    void test5() throws Exception {
        for (VirtualMachineDescriptor descriptor : VirtualMachine.list()) {
            System.out.println(descriptor.id() + ", " + descriptor.displayName());
        }
    }

    /** minecraft launchers descriptor#dusplayName */
    static final String[] mcLaunchers = {
            "net.minecraft.client.main.Main", // mc launcher -> original
            "net.fabricmc.loader.impl.launch.knot.KnotClient", // mc launcher -> fabric
            "org.prismlauncher.EntryPoint" // prism launcher
    };

    /** minecraft launchers pid which displayName contains one of those */
    static int getMcLauncherPid() {
        for (VirtualMachineDescriptor descriptor : VirtualMachine.list()) {
            if (Arrays.asList(mcLaunchers).contains(descriptor.displayName())) {
                return Integer.decode(descriptor.id());
            }
        }
        throw new NoSuchElementException();
    }

    /** returns minecraft application */
    static NSRunningApplication getMc() {
        int pid = getMcLauncherPid();
        return NSWorkspace.sharedWorkspace().runningApplications().stream()
                .map(o -> Rococoa.cast(o, NSRunningApplication.class))
                .filter(a -> a.processIdentifier().intValue() == pid)
                .findFirst().get();
    }

    static class InputDeviceEmulator {

        Pointer /* CGEventSourceRef */ src = library.CGEventSourceCreate(kCGEventSourceStateHIDSystemState);

        InputDeviceEmulator() {
            Runtime.getRuntime().addShutdownHook(new Thread(() -> CoreFoundation.library.CFRelease(src)));
        }

        void keyClick(int code) {
            Pointer /* CGEventRef */ d = library.CGEventCreateKeyboardEvent(src, (char) code, true);
            Pointer /* CGEventRef */ u = library.CGEventCreateKeyboardEvent(src, (char) code, false);

            library.CGEventSetFlags(d, kCGEventFlagMaskCommand);
            library.CGEventSetFlags(u, kCGEventFlagMaskCommand);

            int /* CGEventTapLocation */ loc = kCGHIDEventTap; // kCGSessionEventTap also works
            library.CGEventPost(loc, d);
            library.CGEventPost(loc, u);

            CoreFoundation.library.CFRelease(d);
            CoreFoundation.library.CFRelease(u);
        }

        void mouseMove() {
            // Move to 200x200
            Pointer /* CGEventRef */ move1 = library.CGEventCreateMouseEvent(
                    null, kCGEventMouseMoved,
                    library.CGPointMake(new CGFloat(200), new CGFloat(200)),
                    kCGMouseButtonLeft // ignored
            );
            // Move to 250x250
            Pointer /* CGEventRef */ move2 = library.CGEventCreateMouseEvent(
                    null, kCGEventMouseMoved,
                    library.CGPointMake(new CGFloat(250), new CGFloat(250)),
                    kCGMouseButtonLeft // ignored
            );
            // Left button down at 250x250
            Pointer /* CGEventRef */ click1_down = library.CGEventCreateMouseEvent(
                    null, kCGEventLeftMouseDown,
                    library.CGPointMake(new CGFloat(250), new CGFloat(250)),
                    kCGMouseButtonLeft
            );
            // Left button up at 250x250
            Pointer /* CGEventRef */ click1_up = library.CGEventCreateMouseEvent(
                    null, kCGEventLeftMouseUp,
                    library.CGPointMake(new CGFloat(250), new CGFloat(250)),
                    kCGMouseButtonLeft
            );
            // Now, execute these events with an interval to make them noticeable
            library.CGEventPost(kCGHIDEventTap, move1);
            try { Thread.sleep(1000); } catch (InterruptedException ignore) {}
            library.CGEventPost(kCGHIDEventTap, move2);
            try { Thread.sleep(1000); } catch (InterruptedException ignore) {}
            library.CGEventPost(kCGHIDEventTap, click1_down);
            library.CGEventPost(kCGHIDEventTap, click1_up);
            // Release the events
            CoreFoundation.library.CFRelease(click1_up);
            CoreFoundation.library.CFRelease(click1_down);
            CoreFoundation.library.CFRelease(move2);
            CoreFoundation.library.CFRelease(move1);
        }
    }

    @Test
    void test6() throws Exception {
        try {
            NSRunningApplication a = getMc();
Debug.println("MC App: " + a);
            if (!a.active()) {
                a.activateWithOptions(0);
            }
            InputDeviceEmulator ide = new InputDeviceEmulator();
//        ide.keyClick(kVK_ANSI_);
        } catch (NoSuchElementException e) {
Debug.println(Level.WARNING, "run minecraft before running this test");
        }
    }

    @Test
    @Disabled("TODO cause crash")
    void test7() throws Exception {
        char c = 'a';
        char code = keyCodeForChar(c);
Debug.printf("code for '%x': %02x", c, code);
    }
}
