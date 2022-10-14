/*
 * Copyright (c) 2022 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.foundation;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;


/**
 * NSURLTest.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (umjammer)
 * @version 0.00 2022/01/24 umjammer initial version <br>
 * @see "https://www.titanwolf.org/Network/q/efacb857-5744-469d-ad0a-60ac06d61c73/y"
 */
class NSURLTest {

    @Test
    @Disabled("not work")
    void test() throws Exception {
        Path path = Paths.get(System.getProperty("user.dir"), "pom.xml");
        NSURL url = NSURL.fileURLWithPath(path.toString());
url.getTags().forEach(System.err::println);
    }
}

/* */
