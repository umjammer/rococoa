/*
 * Copyright (c) 2024 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.rococoa.ObjCBlocks.BlockLiteral;
import org.rococoa.cocoa.foundation.NSString;
import vavi.util.Debug;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.rococoa.ObjCBlocks.block;


/**
 * ObjCBlocksTest.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2024-02-26 nsano initial version <br>
 */
class ObjCBlocksTest {

    private interface TestShunt extends ObjCObject {

        int testBlock_operation(int number, BlockLiteral operationBlock);

        long testBlockI_operation(ID id, BlockLiteral operationBlock);

        ID testBlockS_operation(String s, BlockLiteral operationBlock);

        ID testBlockS2_times_operation(String s, int n, BlockLiteral operationBlock);

        void testBlockX();
    }

    public interface MyBlock extends ObjCBlock {
        int apply(BlockLiteral literal, int n);
    }

    public interface MyBlockI extends ObjCBlock {
        long apply(BlockLiteral literal, ID id);
    }

    public interface MyBlockS extends ObjCBlock {
        ID apply(BlockLiteral literal, ID s);
    }

    public interface MyBlockS2 extends ObjCBlock {
        ID apply(BlockLiteral literal, ID s, int n);
    }

    static TestShunt shunt;

    static MyBlock myBlock = (l, n) -> 1000 + n;
    static MyBlockI myBlockI = (l, i) -> i.longValue();
    static MyBlockS myBlockS = (l, i) -> {
        NSString s = Rococoa.wrap(i, NSString.class);
        return NSString.stringWithString(s.toString() + "++").id();
    };
    static MyBlockS2 myBlockS2 = (l, i, n) -> {
        NSString s = Rococoa.wrap(i, NSString.class);
        return NSString.stringWithString(s.toString().repeat(n)).id();
    };

    @BeforeAll
    static void setup() {
        shunt = Rococoa.create("TestShunt", TestShunt.class);
    }

    @Test
    public void testBlock() {
        BlockLiteral literal = new BlockLiteral();
//Debug.println(literal);
//Debug.println(literal.descriptor);
        literal.flags = ObjCBlocks.BLOCK_HAS_COPY_DISPOSE;
        literal.invoke = (MyBlock) ((l, n) -> { Debug.println("hello block " + n); return 1000 * n; });
        literal.write();
        int r = shunt.testBlock_operation(40, literal);
        assertEquals(40000, r);
    }

    @Test
    public void testBlock2() {
        int r = shunt.testBlock_operation(234, block(myBlock));
Debug.println(r);
        assertEquals(1234, r);
    }

    @Test
    public void testBlockI() {
        ID id = new ID(314159265358979L);
        long r = shunt.testBlockI_operation(id, block(myBlockI));
Debug.println(r);
        assertEquals(314159265358979L, r);
    }

    @Test
    public void testBlockS() {
        ID r = shunt.testBlockS_operation("umjammer", block(myBlockS));
Debug.println(Rococoa.wrap(r, NSString.class).toString());
        assertEquals("umjammer++", Rococoa.wrap(r, NSString.class).toString());
    }

    @Test
    public void testBlockS2() {
        ID r = shunt.testBlockS2_times_operation("vavi", 3, block(myBlockS2));
Debug.println(Rococoa.wrap(r, NSString.class).toString());
        assertEquals("vavivavivavi", Rococoa.wrap(r, NSString.class).toString());
    }

    @Test
    public void testBlockX() {
        shunt.testBlockX();
    }
}