/*
 * Copyright (c) 2022 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.internal;

import java.util.logging.Logger;

import com.sun.jna.FromNativeContext;
import com.sun.jna.FromNativeConverter;
import com.sun.jna.ToNativeContext;
import com.sun.jna.ToNativeConverter;
import org.rococoa.cocoa.CGFloat;


/**
 * Converts {@code java.lang.Float} to native by mapping to {@code org.rococa.cocoa.foundation.CGFloat} as defined by:
 * <code>
 * #define CGFloat Native.Long
 * </code>
 *
 * @author <a href="mailto:harald.kuhr@gmail.com">Harald Kuhr</a>
 * @author last modified by $Author: haraldk$
 * @version $Id: BoolConverter.java,v 1.0 Feb 19, 2010 8:44:39 PM haraldk Exp$
 */
public class FloatConverter implements ToNativeConverter, FromNativeConverter {

    private static final Logger logger = Logger.getLogger(FloatConverter.class.getName());

    @Override
    public Object toNative(Object value, ToNativeContext context) {
logger.fine("toNative: " + value + ", " + context);
        return switch (CGFloat.SIZE) {
            case 4 -> value;
            case 8 -> {
                logger.fine("toNative: " + value + " -> " + value);
                yield (double) value;
            }
            default -> throw new AssertionError("Unknown Native.LONG_SIZE: " + CGFloat.SIZE);
        };
    }

    @Override
    public Object fromNative(Object value, FromNativeContext context) {
        return switch (CGFloat.SIZE) {
            case 4 -> value;
            case 8 -> {
                logger.fine("fromNative: " + value + " -> " + ((Double) value).floatValue());
                yield ((Double) value).floatValue();
            }
            default -> throw new AssertionError("Unknown Native.LONG_SIZE: " + CGFloat.SIZE);
        };
    }

    @Override
    public Class<?> nativeType() {
        return switch (CGFloat.SIZE) {
            case 4 -> float.class;
            case 8 -> double.class;
            default -> throw new AssertionError("Unknown Native.LONG_SIZE: " + CGFloat.SIZE);
        };
    }
}
