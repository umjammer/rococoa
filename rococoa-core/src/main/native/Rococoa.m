#include "Rococoa.h"
#include <jni.h>
#include <Block.h>

static JavaVM *g_jvm;

struct Block_literal_with_callback {
    void *isa;
    int flags;
    int reserved;
    void (*invoke)(void *, ...);
    struct Block_descriptor_1 {
        unsigned long int reserved;
        unsigned long int size;
        void (*copy_helper)(void *dst, const void *src);
        void (*dispose_helper)(const void *src);
    } *descriptor;
    jobject java_callback;
};

JNIEXPORT void JNICALL Java_org_rococoa_internal_RococoaNative_cache_1jvm(JNIEnv *env, jclass clazz) {
    (*env)->GetJavaVM(env, &g_jvm);
}

JNIEXPORT jobject JNICALL Java_org_rococoa_internal_RococoaNative_getJObject(JNIEnv *env, jclass clazz, jobject o) {
    return o;
}

static void block_copy_helper(struct Block_literal_with_callback *dst, const struct Block_literal_with_callback *src) {
    JNIEnv* env;
    (*g_jvm)->AttachCurrentThread(g_jvm, (void **)&env, NULL);
    dst->java_callback = (*env)->NewGlobalRef(env, src->java_callback);
}

static void block_dispose_helper(const struct Block_literal_with_callback *src) {
    JNIEnv* env;
    (*g_jvm)->AttachCurrentThread(g_jvm, (void **)&env, NULL);
    (*env)->DeleteGlobalRef(env, src->java_callback);
}

const void* get_NSConcreteStackBlock() {
    return (const void*)&_NSConcreteStackBlock;
}

const void* get_block_copy_helper() {
    return &block_copy_helper;
}

const void* get_block_dispose_helper() {
    return &block_dispose_helper;
}

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
