/*
 * Copyright (c) 2024 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.gamecontroller;

import java.util.logging.Logger;

import javax.crypto.Mac;

import org.rococoa.ObjCClass;
import org.rococoa.Rococoa;
import org.rococoa.cocoa.foundation.NSArray;
import org.rococoa.cocoa.foundation.NSDictionary;
import org.rococoa.cocoa.foundation.NSObject;
import org.rococoa.cocoa.foundation.NSSet;
import org.rococoa.cocoa.foundation.NSString;


/**
 * GCPhysicalInputProfile.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2024-03-27 nsano initial version <br>
 */
public abstract class GCPhysicalInputProfile extends NSObject {

    static {
        GameControllerLibrary.library.toString();
    }

    private static final Logger logger = Logger.getLogger(GCPhysicalInputProfile.class.getName());

    public static final _Class CLASS = Rococoa.createClass("GCPhysicalInputProfile", _Class.class);

    public interface _Class extends ObjCClass {
        GCPhysicalInputProfile alloc();
    }

    public abstract GCPhysicalInputProfile init();

    /** The elements in the profile as key-value pairs for lookup by name. */
    public abstract NSDictionary /* <NSString, GCControllerElement> */ elements();

    /** The buttons in the profile as key-value pairs for lookup by name. */
    public abstract NSDictionary /* <NSString, GCControllerButtonInput> */ buttons();

    /** The axes in the profile as key-value pairs for lookup by name. */
    public abstract NSDictionary /* <NSString, GCControllerAxisInput> */ axes();

    /** The directional pads in the profile as key-value pairs for lookup by name. */
    public abstract NSDictionary /* <NSString, GCControllerDirectionPad> */ dpads();

    /** The touchpads in the profile as key-value pairs for lookup by name. */
    public abstract NSDictionary /* <NSString, GCControllerTouchpad> */ touchpads();

    /** The elements in the profile. */
    public abstract NSSet /* <GCControllerElement> */ allElements();
    /** The buttons in the profile. */
    public abstract NSSet /* <GCControllerButtonInput> */ allButtons();
    /** The axes in the profile. */
    public abstract NSSet /* <GCControllerAxisInput> */ allAxes();
    /** The directional pads in the profile. */
    public abstract NSSet /* <GCControllerDirectionPad> */ allDpads();
    /** The touchpads in the profile. */
    public abstract NSSet /* <GCControllerTouchpad> */ allTouchpads();

    /** Returns the element that the key specifies. */
    public abstract GCControllerElement objectForKeyedSubscript(NSString key);
}
