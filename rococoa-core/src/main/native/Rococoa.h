#import <Cocoa/Cocoa.h>
#import <CoreFoundation/CoreFoundation.h>
#include <objc/objc-runtime.h>

void callOnMainThread(void (*fn)(void), BOOL waitUntilDone);

id bridgeArray(CFArrayRef* array);
id bridgeDictionary(CFDictionaryRef* dic);

void* getNSConcreteStackBlock();

@interface RococoaHelper : NSObject
+ (void) callback: (NSValue*) fn;
@end
