/*
 * Copyright (c) 2022 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.coregraphics;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;


/**
 * CGSize.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2022-09-04 nsano initial version <br>
 */
public class CGSize extends Structure {

    public double width;
    public double height;

    public CGSize() {
    }

    public CGSize(Pointer pointer) {
        super(pointer);
    }

    public CGSize(int width, int height) {
        this.width = width;
        this.height = height;
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
