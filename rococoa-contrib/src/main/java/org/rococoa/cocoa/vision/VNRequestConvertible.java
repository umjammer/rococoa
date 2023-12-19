/*
 * Copyright (c) 2023 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.vision;


import org.rococoa.cocoa.foundation.NSObject;


/**
 * VNRequestConvertible.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2023-12-19 nsano initial version <br>
 */
public interface VNRequestConvertible<I extends VNObservation, O> {

    /** */
    O convert(I observation, Object... args);

    /** */
    boolean isKindOfClass(NSObject object);

    /** */
    I cast(NSObject object);
}
