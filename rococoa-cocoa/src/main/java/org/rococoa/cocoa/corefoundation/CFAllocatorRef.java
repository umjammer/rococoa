/*
 * Copyright (c) 2023 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.corefoundation;

import com.sun.jna.Pointer;
import com.sun.jna.PointerType;
import org.rococoa.ID;


public class CFAllocatorRef extends PointerType {

    /**
     * This is a synonym for NULL.
     * @see "https://developer.apple.com/documentation/corefoundation/kcfallocatordefault"
     */
    public static final CFAllocatorRef kCFAllocatorDefault = new CFAllocatorRef(Pointer.NULL);

    public CFAllocatorRef(Pointer address) {
        super(address);
    }

    public CFAllocatorRef() {
        super();
    }
}