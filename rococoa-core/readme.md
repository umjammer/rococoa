# rococoa-core

## How To

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

## References

 * https://github.com/java-native-access/jna/tree/master/contrib/platform/src/com/sun/jna/platform/mac