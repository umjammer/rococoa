/*
 * Copyright (c) 2024 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.rococoa.cocoa.foundation.NSArray;

import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * TestCase.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2024-03-26 nsano initial version <br>
 */
public class TestCase {

    @Test
    @Disabled
    void test1() throws Exception {
//        MethodHandle methodHandleFieldDirect = lookup.unreflectGetter(fieldName);
//        CallSite callSiteField = new ConstantCallSite(methodHandleFieldDirect);
//        methodHandleFieldDirect = callSiteField.dynamicInvoker();
//        name = (String) methodHandleFieldDirect.invokeExact(new Employee());
//
//
////Lookup invoke dynamic
//        methodType = MethodType.methodType(String.class);
//        methodHandle = lookup.findVirtual(Employee.class, "getName", methodType);
//        CallSite callSiteMethod = new ConstantCallSite(methodHandleFieldDirect);
//        methodHandle = callSiteMethod.dynamicInvoker();
    }

    NSArray array() {
        return null;
    }

    @Test
    void test2() throws Exception {
        NSArray array = array();
        assertThrows(NullPointerException.class, () -> {
            List<?> list = array.toList();
        });
    }
}
