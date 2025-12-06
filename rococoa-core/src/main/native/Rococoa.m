#include "Rococoa.h"

void callOnMainThread(void (*fn)(void), BOOL waitUntilDone) {
	// NSLog(@"callOnMainThread function at address %p", fn);
	// Pool is required as we're being called from Java, which probably doesn't have a pool to 
	// allocate the NSValue from.
	NSAutoreleasePool * pool = [[NSAutoreleasePool alloc] init];
	// performSelectorOnMainThread is NSObject's method
	[RococoaHelper performSelectorOnMainThread: @selector(callback:) 
		withObject: [NSValue valueWithPointer: fn] waitUntilDone: waitUntilDone];
	[pool release];
}

id bridgeArray(CFArrayRef* cfarray) {
    NSArray* nsarray = (__bridge NSArray*) cfarray;
    return (id) nsarray;
}

id bridgeDictionary(CFDictionaryRef* cfdic) {
    NSDictionary* nsdic = (__bridge NSDictionary*) cfdic;
    return (id) nsdic;
}

@implementation RococoaHelper : NSObject

+ (void) callback: (NSValue*) fnAsValue {
    void (*fn)(void) = [fnAsValue pointerValue]; 
	(*fn)();
} 

@end

#include <Block.h>

extern void * _NSConcreteStackBlock[32];

void* getNSConcreteStackBlock() {
    return (void*) _NSConcreteStackBlock;
}
