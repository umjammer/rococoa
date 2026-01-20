/*
 * Copyright 2007, 2008 Duncan McGregor
 *
 * This file is part of Rococoa, a library to allow Java to talk to Cocoa.
 *
 * Rococoa is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Rococoa is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Rococoa.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.rococoa.internal;

import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.concurrent.Callable;

import com.sun.jna.Pointer;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Empty;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperMethod;
import net.bytebuddy.implementation.bind.annotation.This;
import org.rococoa.Foundation;
import org.rococoa.ID;
import org.rococoa.IDByReference;
import org.rococoa.ObjCObject;
import org.rococoa.ObjCObjectByReference;
import org.rococoa.ReleaseInFinalize;
import org.rococoa.ReturnType;
import org.rococoa.Rococoa;
import org.rococoa.RococoaException;
import org.rococoa.RunOnMainThread;

import static java.lang.System.getLogger;


/**
 * Listens to invocations of methods on a Java NSObject, and forwards them to
 * its Objective-C counterpart.
 *
 * @author duncan
 */
@SuppressWarnings("nls")
public class ObjCObjectInvocationHandler implements InvocationHandler {

    private static final int FINALIZE_AUTORELEASE_BATCH_SIZE = 1000;

    private static final Logger logger = getLogger("org.rococoa.proxy");

    static final Method OBJECT_TOSTRING;
    static final Method OBJECT_HASHCODE;
    static final Method OBJECT_EQUALS;
    static final Method OCOBJECT_ID;

    static {
        try {
            OBJECT_TOSTRING = Object.class.getMethod("toString");
            OBJECT_HASHCODE = Object.class.getMethod("hashCode");
            OBJECT_EQUALS = Object.class.getMethod("equals", Object.class);
            OCOBJECT_ID = ObjCObject.class.getMethod("id");
        }
        catch (Exception x) {
            throw new RococoaException("Error retrieving method", x);
        }
    }

    private ID ocInstance;
    private final String javaClassName;
    private final boolean invokeAllMethodsOnMainThread;

    private static final List<Runnable> finalizers = new ArrayList<>();

    static {
        // TODO this cause Concurrent Modification Exception, but using old for cause crash. WTF???
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try { finalizers.forEach(Runnable::run); } catch (ConcurrentModificationException ignore) {}
        }));
    }

    public ObjCObjectInvocationHandler(ID ocInstance, Class<? extends ObjCObject> javaClass, boolean retain) {
        this.ocInstance = ocInstance;
        javaClassName = javaClass.getSimpleName();
        invokeAllMethodsOnMainThread = shouldInvokeMethodsOnMainThread(javaClass);
        boolean releaseOnFinalize = shouldReleaseInFinalize(javaClass);

logger.log(Level.TRACE, String.format("Creating NSObjectInvocationHandler for id %s, javaclass %s. retain = %s, retainCount = %s",
 ocInstance, javaClass, retain, Foundation.cfGetRetainCount(ocInstance).intValue()));

        if (ocInstance.isNull()) {
            throw new NullPointerException();
        }

        if (retain) {
            if (releaseOnFinalize) {
                if (callAcrossToMainThread()) {
                    Foundation.runOnMainThread(() -> Foundation.cfRetain(ocInstance));
                } else {
                    Foundation.cfRetain(ocInstance);
                }
            }
        }

        finalizers.add(() -> {
            if (callAcrossToMainThread()) {
                Foundation.runOnMainThread(this::release);
            } else {
                AutoreleaseBatcher autoreleaseBatcher = AutoreleaseBatcher.forThread(FINALIZE_AUTORELEASE_BATCH_SIZE);
                release();
                autoreleaseBatcher.operate();
            }
        });
    }

    private static boolean shouldReleaseInFinalize(Class<? extends ObjCObject> javaClass) {
        // Almost everything should be released in finalize, except wrappers for
        // NSAutoreleasePool.
        ReleaseInFinalize annotation = javaClass.getAnnotation(ReleaseInFinalize.class);
        if (annotation == null) {
            return true;
        }
        return annotation.value();
    }

    // must be run on appropriate thread
    private void release() {
        if (ocInstance.isNull()) {
            return;
        }
logger.log(Level.TRACE, String.format("finalizing [%s %s], releasing with retain count = %s",
 javaClassName, ocInstance, Foundation.cfGetRetainCount(ocInstance).intValue()));
        Foundation.cfRelease(ocInstance);
    }

    /**
     * Callback from java.lang.reflect proxy
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args)  throws Exception {
logger.log(Level.TRACE, String.format("JavaProxy:invoking [%s %s].%s(%s)", javaClassName, ocInstance, method.getName(), new VarArgsUnpacker(args)));
        if (isSpecialMethod(method)) {
            return invokeSpecialMethod(method, args);
        }
        return invokeCocoa(method, args);
    }

    /**
     * Callback from ByteBuddy proxy
     */
    @RuntimeType
    public Object intercept(@This Object proxy, @Origin Method method, @AllArguments Object[] args, @SuperMethod(nullIfImpossible = true) Method superMethod, @Empty Object defaultValue) throws Throwable {
logger.log(Level.TRACE, String.format("ByteBuddyProxy:invoking [%s %s].%s(%s)", javaClassName, ocInstance, method.getName(), new VarArgsUnpacker(args)));
        if (isSpecialMethod(method)) {
            return invokeSpecialMethod(method, args);
        }
        if (!Modifier.isAbstract(method.getModifiers())) {
            // method is not abstract, so a Java override has been provided, which we call
logger.log(Level.TRACE, String.format("superMethod.invoke [%s %s].%s(%s)", javaClassName, ocInstance, method.getName(), new VarArgsUnpacker(args)));
            try {
                return superMethod.invoke(proxy, args);
            } catch (Throwable t) {
logger.log(Level.WARNING, String.format("superMethod.invoke [%s %s].%s(%s) failure", javaClassName, ocInstance, method.getName(), new VarArgsUnpacker(args)), t);
                throw t;
            }
        }
        // normal case
        return invokeCocoa(method, args);
    }

    private static boolean isSpecialMethod(Method method) {
        return (OBJECT_TOSTRING.equals(method) ||
                OBJECT_EQUALS.equals(method) ||
                OCOBJECT_ID.equals(method));
    }

    private Object invokeSpecialMethod(Method method, Object[] args) {
logger.log(Level.TRACE, String.format("invokeSpecialMethod [%s %s].%s(%s)", javaClassName, ocInstance, method.getName(), new VarArgsUnpacker(args)));
        if (OBJECT_TOSTRING.equals(method)) {
            return invokeDescription();
        }
        if (OBJECT_EQUALS.equals(method)) {
            if (args[0] == null) {
                return false;
            }
            if (args[0] instanceof ObjCObject) {
                return invokeIsEqual(((ObjCObject) args[0]).id());
            }
            return false;
        }
        if (OCOBJECT_ID.equals(method)) {
            return ocInstance;
        }
        throw new IllegalArgumentException("Not a special method " + method);
    }

    private Object invokeDescription() {
        return sendOnThisOrMainThread(null, ocInstance, "description", String.class);
    }

    private Object invokeIsEqual(ID another) {
        return sendOnThisOrMainThread(null, ocInstance, "isEqual:", Boolean.class, another);
    }

    private Object invokeCocoa(Method method, Object[] args) {
logger.log(Level.TRACE, String.format("invokeCocoa [%s %s].%s(%s)", javaClassName, ocInstance, method.getName(), new VarArgsUnpacker(args)));
        String selectorName = selectorNameFor(method);
        Class<?> returnType = returnTypeFor(method);
        Object[] marshalledArgs = marshallArgsFor(args);

        Object result = sendOnThisOrMainThread(method, ocInstance, selectorName, returnType, marshalledArgs);
        if (method.getName().startsWith("init")) {
            handleInitMethod(result);
        }
        fillInReferences(args, marshalledArgs);

        if (result instanceof Pointer && method.getReturnType().equals(String.class)) {
            // special case for return char*
            return ((Pointer) result).getString(0);
        }
        if (result instanceof ID) {
            if (((ID) result).isNull()) {
                return null;
            }
        }
        return result;
    }

    private void handleInitMethod(Object result) {        
        // Normally init methods return self, but on error they may return nil.
        // In this case the ObjC object for which this is the handler is considered
        // freed and should not be released when we are finalized.
        if (result != null) {
            return;
        }
        ocInstance = ID.fromLong(0);        
    }

    private Object sendOnThisOrMainThread(Method method, ID id, String selectorName, Class<?> returnType, Object... args) {
        if (callAcrossToMainThreadFor(method)) {
            return Foundation.callOnMainThread(
                    (Callable<Object>) () -> Foundation.send(id, selectorName, returnType, args));
        } else {
            return Foundation.send(id, selectorName, returnType, args);
        }
    }

    /**
     * We need to make sure that we have filled in all NSObjectByReferences
     * so that they are retained.
     */
    private static void fillInReferences(Object[] args, Object[] marshalledArgs) {
        if (args == null) {
            return;
        }
        for (int i = 0; i < args.length; i++) {
            Object original = args[i];
            Object marshalled = marshalledArgs[i];
            if (marshalled instanceof IDByReference) {
                if (!(original instanceof ObjCObjectByReference)) {
                    logger.log(Level.ERROR, "Bad marshalling");
                    continue;
                }
                ((ObjCObjectByReference) original).setObject(
                   Rococoa.wrap(((IDByReference) marshalled).getValue(), ObjCObject.class));
            }
        }
    }

    private static Class<?> returnTypeFor(Method method) {
        ReturnType annotation = method.getAnnotation(ReturnType.class);
        if (annotation == null) {
            return method.getReturnType();
        }
        else {
            return annotation.value();
        }
    }

    private static Object[] marshallArgsFor(Object[] args) {
        if (args == null) {
            return null;
        }
        List<Object> result = new ArrayList<>(args.length);
        for (Object arg : args) {
            Object marshalled = marshall(arg);
            if (marshalled instanceof Object[]) {
                // flatten varags, it would never(?) make sense to pass Object[] to Cocoa
                result.addAll(Arrays.asList((Object[]) marshalled));
            } else {
                result.add(marshalled);
            }
        }
        return result.toArray(new Object[0]);
    }

    private static Object marshall(Object arg) {
        // Note that this is not the only marshalling that is done.
        // RococoaTypeMapper also gets involved.
        if (arg == null) {
            return null;
        }
        if (arg instanceof ObjCObjectByReference) {
			// Forward conversion (another backwards conversion will take place in fillInReferences)
            IDByReference idref = new IDByReference();
			ObjCObject ob = ((ObjCObjectByReference)arg).getValueAs(ObjCObject.class);
			if (ob != null) {
				idref.setValue(ob.id());
            }
			return idref;
		}
        return arg;
    }

    private static String selectorNameFor(Method method) {
        String methodName = method.getName();
        if (methodName.endsWith("_")) {
            // lets us append _ to allow Java keywords as method names
            methodName = methodName.substring(0, methodName.length() - 1);
        }
        if (method.getParameterTypes().length == 0) {
            return methodName;
        }
        String[] parts = methodName.split("_");
        StringBuilder result = new StringBuilder();
        for (String part : parts) {
            result.append(part).append(":");
        }
        return result.toString();
    }
    
    private static boolean shouldInvokeMethodsOnMainThread(AnnotatedElement element) {
        return element != null && element.getAnnotation(RunOnMainThread.class) != null;
    }

    private boolean callAcrossToMainThread() {
        return callAcrossToMainThreadFor(null);
    }

    private boolean callAcrossToMainThreadFor(Method m) {
        return (invokeAllMethodsOnMainThread || shouldInvokeMethodsOnMainThread(m) ) && !Foundation.isMainThread() ;
    }
}
