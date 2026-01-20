/*
 * Copyright (c) 2024 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.gamecontroller;

import org.rococoa.ObjCClass;
import org.rococoa.Rococoa;
import org.rococoa.cocoa.foundation.NSObject;
import org.rococoa.cocoa.foundation.NSSet;


/**
 * GCControllerElement.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2024-03-27 nsano initial version <br>
 */
public abstract class GCControllerElement extends NSObject {

    static {
        GameControllerLibrary.library.toString();
    }

    public static final _Class CLASS = Rococoa.createClass("GCControllerElement", _Class.class);

    public interface _Class extends ObjCClass {
        GCControllerElement alloc();
    }

    public abstract GCControllerElement init();

    // Accessing input values

    /**A Boolean value that indicates whether the element provides analog data. */
    public abstract boolean analog();

    // Getting a localized name

    /** The localized name for the element or the remapped element. */
    public abstract String localizedName();
    /**The element’s localized name, not the remapped name. */
    public abstract String unmappedLocalizedName();

    // Displaying a symbol

    /**A system symbol for the element or the remapped element. */
    public abstract String sfSymbolsName();
    /**The element’s system symbol, not the remapped symbol. */
    public abstract String unmappedSfSymbolsName();

    // Accessing elements by key

    /**The element’s aliases you use when accessing it with the subscript notation. */
    public abstract NSSet /* <NSString> */ aliases();

    // Getting the containing element

    /**The enclosing element for this element. */
    public abstract GCControllerElement collection();
}
