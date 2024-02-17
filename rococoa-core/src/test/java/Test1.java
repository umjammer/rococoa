/*
 * Copyright (c) 2023 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.invoke.CallSite;
import java.lang.invoke.ConstantCallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.rococoa.cocoa.foundation.NSString;
import vavi.util.Debug;

import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Test1.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2023-06-30 nsano initial version <br>
 */
public class Test1 {

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

    @Test
//    @Disabled
    void test2() throws Exception {
        String jh = System.getProperty("java.home");
Debug.println("JAVA_HOME: " + jh);
        ProcessBuilder pb = new ProcessBuilder().command("mvn", "-P", "test1", "antrun:run", "-Dvalue=umjammer");
        Map<String, String> env = pb.environment();
        env.put("JAVA_HOME", jh);
        pb.redirectErrorStream(true);
        CountDownLatch cdl = new CountDownLatch(1);
        Process p = pb.start();
        AtomicReference<String> result = new AtomicReference<>();
        Executors.newSingleThreadExecutor().submit(() -> {
            int r = 0;
            try { r = p.waitFor(); } catch (InterruptedException ignored) {}
Debug.println("result: " + r);

            try (Scanner s = new Scanner(p.getInputStream())) {
                while (s.hasNextLine()) {
                    String line = s.nextLine();
System.out.println(line);
                    if (line.contains("[java]")) {
                        result.set(line);
                    }
                }
            }

            cdl.countDown();
        });
        cdl.await();
        assertTrue(result.get().contains("Hello world! umjammer"), result.get());
    }

    /** */
    public static void main(String[] args) {
        System.err.println(NSString.stringWithString("Hello world! " + (args.length > 0 ? args[0] : "")).toString());
    }
}
