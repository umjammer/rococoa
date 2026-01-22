package org.rococoa.cocoa.foundation;

import org.rococoa.ObjCClass;
import org.rococoa.Rococoa;

public abstract class NSProcessInfo extends NSObject {

    private static _class_ CLASS = Rococoa.createClass("NSProcessInfo", _class_.class);

    private static abstract class _class_ implements ObjCClass {
        public abstract NSProcessInfo processInfo();
    }

    public static NSProcessInfo processInfo() {
        return CLASS.processInfo();
    }

    public abstract int processIdentifier();
}
