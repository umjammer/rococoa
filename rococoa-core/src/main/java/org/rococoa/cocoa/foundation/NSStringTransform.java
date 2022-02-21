/*
 * Copyright (c) 2022 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.foundation;

import org.rococoa.ObjCClass;
import org.rococoa.Rococoa;
import org.rococoa.internal.FoundationLibrary;


/**
 * NSStringTransform.
 *
 * TODO doesn't work
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (umjammer)
 * @version 0.00 2022/01/19 umjammer initial version <br>
 */
public abstract class NSStringTransform extends NSString {

    public static _Class CLASS = Rococoa.createClass("NSString", _Class.class);

    public interface _Class extends ObjCClass {
        NSStringTransform stringWithString(String string);
    }

    public static NSStringTransform stringWithString(String string) {
        return CLASS.stringWithString(string);
    }

    public static final NSStringTransform NSStringTransformLatinToHiragana = stringWithString(FoundationLibrary.kCFStringTransformLatinHiragana);
}

/* */
