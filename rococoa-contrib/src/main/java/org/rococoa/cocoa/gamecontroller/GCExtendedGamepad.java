/*
 * Copyright (c) 2024 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.gamecontroller;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.crypto.Mac;

import org.rococoa.ID;
import org.rococoa.ObjCBlock;
import org.rococoa.ObjCBlocks.BlockLiteral;
import org.rococoa.ObjCClass;
import org.rococoa.Rococoa;
import org.rococoa.cocoa.foundation.NSArray;
import org.rococoa.cocoa.foundation.NSDictionary;
import org.rococoa.cocoa.foundation.NSObject;
import org.rococoa.cocoa.foundation.NSString;


/**
 * A controller profile that supports the extended set of gamepad controls.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2024-03-27 nsano initial version <br>
 */
public abstract class GCExtendedGamepad extends GCPhysicalInputProfile {

    static {
        GameControllerLibrary.library.toString();
    }

    private static final Logger logger = Logger.getLogger(GCExtendedGamepad.class.getName());

    public static final _Class CLASS = Rococoa.createClass("GCExtendedGamepad", _Class.class);

    public interface _Class extends ObjCClass {
        GCExtendedGamepad alloc();
    }

    public abstract GCExtendedGamepad init();

    /** The controller for the profile. */
    public abstract GCController controller();

    /** The signature for the block that the profile calls when an element’s value changes. */
    public interface GCExtendedGamepadValueChangedHandler extends ObjCBlock {
        void callback(BlockLiteral literal, ID /* GCExtendedGamepad */ gamepad, ID /* GCControllerElement */ element);
    }

    /** The block that the profile calls when an element’s value changes. */
    public abstract BlockLiteral /* GCExtendedGamepadValueChangedHandler */ valueChangedHandler();
    public abstract void setValueChangedHandler(BlockLiteral /* GCExtendedGamepadValueChangedHandler */ handler);

    /** The controller’s left shoulder button element. */
    public abstract GCControllerButtonInput leftShoulder();
    /** The controller’s right shoulder button element. */
    public abstract GCControllerButtonInput rightShoulder();

    // Getting trigger inputs

    /** The controller’s left trigger element. */
    public abstract GCControllerButtonInput leftTrigger();
    /** The controller’s right trigger element. */
    public abstract GCControllerButtonInput rightTrigger();

    // Getting face button inputs

    /** The primary menu button element that players use to enter the main menu and pause the game. */
    public abstract GCControllerButtonInput buttonMenu();
    /** The controller’s secondary menu button element. */
    public abstract GCControllerButtonInput buttonOptions();
    /** The main menu button element that players use to enter the secondary menu and pause the game. */
    public abstract GCControllerButtonInput buttonHome();
    /** The bottom face button that uses A or another indicator as its label. */
    public abstract GCControllerButtonInput buttonA();
    /** The right face button that uses B or another indicator as its label. */
    public abstract GCControllerButtonInput buttonB();
    /** The left face button that uses X or another indicator as its label. */
    public abstract GCControllerButtonInput buttonX();
    /** The top face button that uses Y or another indicator as its label. */
    public abstract GCControllerButtonInput buttonY();

    // Getting directional pad inputs

    /** The controller’s directional pad element. */
    public abstract GCControllerDirectionPad dpad();

    // Getting thumbstick and thumbstick button inputs

    /** The controller’s left thumbstick element. */
    public abstract GCControllerDirectionPad leftThumbstick();
    /** The controller’s right thumbstick element. */
    public abstract GCControllerDirectionPad rightThumbstick();
    /** The button on the left thumbstick of the controller. */
    public abstract GCControllerButtonInput leftThumbstickButton();
    /** The button on the right thumbstick of the controller. */
    public abstract GCControllerButtonInput rightThumbstickButton();

    /** The motion input profile. */
    public abstract GCMotion motion();
}
