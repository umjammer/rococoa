/*
 * Copyright (c) 2022 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.coregraphics;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Structure;
import org.rococoa.cocoa.CGFloat;
import org.rococoa.cocoa.foundation.NSSize;


/**
 * CGSize.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2022-09-04 nsano initial version <br>
 */
public class CGSize extends Structure implements Structure.ByValue {

    public CGFloat width;
    public CGFloat height;

    public CGSize() {
        this(0, 0);
    }

    public CGSize(int width, int height) {
        this.width = new CGFloat(width);
        this.height = new CGFloat(height);
        write();
    }

    /** */
    public NSSize toNSSize() {
        return new NSSize(width.doubleValue(), height.doubleValue());
    }

    @Override
    public String toString() {
        return "CGSize{" +
                "width=" + width +
                ", height=" + height +
                '}';
    }

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList("width", "height");
    }
}
