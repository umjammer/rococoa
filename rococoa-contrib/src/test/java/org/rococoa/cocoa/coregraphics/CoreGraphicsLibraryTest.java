/*
 * Copyright (c) 2022 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.coregraphics;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.stream.IntStream;

import com.sun.jna.Callback;
import com.sun.jna.Pointer;
import com.sun.jna.platform.mac.CoreFoundation.CFArrayRef;
import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.rococoa.Foundation;
import org.rococoa.ObjCObject;
import org.rococoa.Rococoa;
import org.rococoa.Selector;
import org.rococoa.cocoa.appkit.NSRunningApplication;
import org.rococoa.cocoa.appkit.NSWorkspace;
import org.rococoa.cocoa.corefoundation.CoreFoundation;
import org.rococoa.cocoa.coreimage.CIImage;
import org.rococoa.cocoa.foundation.NSBundle;
import org.rococoa.cocoa.foundation.NSDictionary;
import org.rococoa.cocoa.foundation.NSNotification;
import org.rococoa.cocoa.foundation.NSNotificationCenter;
import org.rococoa.cocoa.foundation.NSObject;
import org.rococoa.cocoa.foundation.NSString;
import org.rococoa.cocoa.gamecontroller.GCController;
import vavi.util.Debug;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.rococoa.cocoa.coregraphics.CoreGraphicsLibrary.kCGEventFlagMaskCommand;
import static org.rococoa.cocoa.coregraphics.CoreGraphicsLibrary.kCGEventSourceStateHIDSystemState;
import static org.rococoa.cocoa.coregraphics.CoreGraphicsLibrary.kCGHIDEventTap;
import static org.rococoa.cocoa.coregraphics.CoreGraphicsLibrary.kCGNullWindowID;
import static org.rococoa.cocoa.coregraphics.CoreGraphicsLibrary.kCGWindowListOptionOnScreenOnly;
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

    @Test
    @DisplayName("list jvms: NOT rococoa")
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
    static NSRunningApplication getMinecraft() {
        int pid = getMcLauncherPid();
        return NSWorkspace.sharedWorkspace().runningApplications().stream()
                .map(o -> Rococoa.cast(o, NSRunningApplication.class))
                .filter(a -> a.processIdentifier().intValue() == pid)
                .findFirst().get();
    }

    @Test
    @DisplayName("NSRunningApplication")
    @EnabledIfSystemProperty(named = "vavi.test", matches = "ide")
    void test62() throws Exception {
        try {
            NSRunningApplication a = getMinecraft();
Debug.println("MC App: " + a);
            if (!a.active()) {
                a.activateWithOptions(0);
            }
            RococaRobot ide = new RococaRobot();
//        ide.keyClick(kVK_ANSI_);
        } catch (NoSuchElementException e) {
Debug.println(Level.WARNING, "run minecraft before running this test");
        }
    }

    @Test
    @DisplayName("CGKeyCode")
    void test7() throws Exception {
        char[] cc = { 'a', '[', ']' };
        IntStream.range(0, cc.length).mapToObj(i -> cc[i]).forEach(c -> {
            char code = keyCodeForChar(c);
Debug.printf("code for '%c': %02x", c, (int) code);
        });
    }

    @Test
    @DisplayName("CGWindowListCopyWindowInfo")
    @EnabledIfSystemProperty(named = "vavi.test", matches = "ide")
    void test8() throws Exception {
        CFArrayRef array = library.CGWindowListCopyWindowInfo(kCGWindowListOptionOnScreenOnly, kCGNullWindowID);
Debug.println("windows: " + array.getCount());
        NSRunningApplication a = getMinecraft();
        for (int i = 0; i < array.getCount(); i++) {
            NSDictionary dic = Rococoa.toNSDictionary(array.getValueAtIndex(i));
            if (Integer.parseInt(dic.get(NSString.stringWithString("kCGWindowOwnerPID")).toString()) == a.processIdentifier().intValue()) {
Debug.println(dic);
                NSDictionary rect = Rococoa.cast(dic.get(NSString.stringWithString("kCGWindowBounds")), NSDictionary.class);
Debug.println(rect);
            }
        }
    }

    CountDownLatch cdl = new CountDownLatch(1);

    class MyObserver implements Callback {

        public void applicationWasActivated(NSNotification notification) {
            NSWorkspace workspace = Rococoa.cast(notification.object(), NSWorkspace.class);
            NSRunningApplication a = workspace.frontmostApplication();
Debug.println("applicationWasActivated: " + a.bundleIdentifier() + ":" + a.processIdentifier());
        }

        public void applicationWasDeactivated(NSNotification notification) {
            NSWorkspace workspace = Rococoa.cast(notification.object(), NSWorkspace.class);
            NSRunningApplication a = workspace.frontmostApplication();
Debug.println("applicationWasDeactivated: " + a.bundleIdentifier() + ":" + a.processIdentifier());
        }
    }

    @Test
    @EnabledIfSystemProperty(named = "vavi.test", matches = "ide")
    void test9() throws Exception {
        ObjCObject proxy = Rococoa.proxy(new CoreGraphicsLibraryTest.MyObserver());
        Selector sel1 = Foundation.selector("applicationWasActivated:");
        Selector sel2 = Foundation.selector("applicationWasDeactivated:");

        NSNotificationCenter notificationCenter = NSWorkspace.sharedWorkspace().notificationCenter();
        notificationCenter.addObserver_selector_name_object(proxy.id(), sel1, NSWorkspace.NSWorkspaceDidActivateApplicationNotification, null);
        notificationCenter.addObserver_selector_name_object(proxy.id(), sel2, NSWorkspace.NSWorkspaceDidActivateApplicationNotification, null);

        cdl.await();
    }
}
