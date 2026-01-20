# rococoa-core

## How To

### Class

```java

/** the object root is NSObject */
public abstract class FooClass extends NSObject {

    static {
        FooLibrary.instance.toString(); // to make sure library is loaded (optional)
    }

    private static final _Class CLASS;

    /**
     * '-' methods: - init*:
     */
    public abstract void initWithBar(Bar bar);

    /** '+' methods */
    public interface _Class extends ObjCClass {
        /** + alloc: */
        FooClass alloc();

        /** + withString: */
        FooClass withString(String string);
    }

    /**
     * property getter: someProperty
     */
    public abstract int someProperty();

    /**
     * property setter: someProperty
     */
    public abstract void setSomeProperty(int some);

    /**
     * '-' methods: - doSomething:bar:
     */
    public abstract void doSomething_bar(Bar bar);
 }
```

### Error Handling

```java
    ObjCObjectByReference errorRef = new ObjCObjectByReference();
    ... foo.bar_error(..., errorRef);
    NSError error = errorRef.getValueAs(NSError.class);
    if (error != null) {
        throw new BuzException(error.description());
    }
```
### Run on main thread

```java
   Foundation.runOnMainThread(() -> { ... });
```

### Cast

```java
    if (objcObject.isKindOfClass(fooObjcClass))
        FooObjcObject fooObjcObject = Rococoa.cast(objcObject, FooObjcClass.class);
```

### ID to Object convetion

```java
    FooObjcObject fooObjcObject = Rococoa.wrap(id, FooObjcClass.class);
```


## References

 * https://github.com/java-native-access/jna/tree/master/contrib/platform/src/com/sun/jna/platform/mac