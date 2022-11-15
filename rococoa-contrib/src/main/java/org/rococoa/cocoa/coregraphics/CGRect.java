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
 * CGRect.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2022-09-04 nsano initial version <br>
 */
public class CGRect extends Structure implements Structure.ByValue {

    public CGPoint origin;
    public CGSize size;

    public CGRect() {
        this.origin = new CGPoint();
        this.size = new CGSize();
    }

    public CGRect(int x, int y, int width, int height) {
        this.origin = new CGPoint(x, y);
        this.size = new CGSize(width, height);
    }

    @Override
    public String toString() {
        return "CGRect{" +
                "origin=" + origin +
                ", size=" + size +
                '}';
    }

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList("origin", "size");
    }
}
