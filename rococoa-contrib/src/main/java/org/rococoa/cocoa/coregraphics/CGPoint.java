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


/**
 * CGPoint.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2022-09-04 nsano initial version <br>
 */
public class CGPoint extends Structure implements Structure.ByValue {

    public CGFloat x;
    public CGFloat y;

    public CGPoint() {
        this(0, 0);
    }

    public CGPoint(int x, int y) {
        this.x = new CGFloat(x);
        this.y = new CGFloat(y);
        write();
    }

    @Override
    public String toString() {
        return "CGPoint{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList("x", "y");
    }
}
