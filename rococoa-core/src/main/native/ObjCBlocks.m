#include "org_rococoa_ObjCBlocks.h"

#include <objc/objc.h>
#include <Block.h>

#define PTR_TO_JLONG(ptr) ((jlong)(size_t)(ptr))
#define JLONG_TO_PTR(jl) ((void*)(size_t)(jl))

/*
https://clang.llvm.org/docs/Block-ABI-Apple.html
http://cocoawithlove.com/2009/10/how-blocks-are-implemented-and.html
http://www.opensource.apple.com/source/libclosure/libclosure-38/BlockImplementation.txt
*/

enum {
    // Set to true on blocks that have captures (and thus are not true
    // global blocks) but are known not to escape for various other
    // reasons. For backward compatibility with old runtimes, whenever
    // BLOCK_IS_NOESCAPE is set, BLOCK_IS_GLOBAL is set too. Copying a
    // non-escaping block returns the original block and releasing such a
    // block is a no-op, which is exactly how global blocks are handled.
    BLOCK_IS_NOESCAPE      =  (1 << 23),

    BLOCK_HAS_COPY_DISPOSE =  (1 << 25),
    BLOCK_HAS_CTOR =          (1 << 26), // helpers have C++ code
    BLOCK_IS_GLOBAL =         (1 << 28),
    BLOCK_HAS_STRET =         (1 << 29), // IFF BLOCK_HAS_SIGNATURE
    BLOCK_HAS_DESCRIPTOR =    (1 << 29),
};

typedef struct _block_descriptor {
    unsigned long int reserved;                   // NULL
    unsigned long int block_size;                 // sizeof(struct _block_literal_1)
    // optional helper functions
    void (*copy_helper)(void *dst, void *src);    // IFF (1<<25)
    void (*dispose_helper)(void *src);            // IFF (1<<25)
    // required ABI.2010.3.16
     const char *signature;                       // IFF (1<<30)
 } _block_descriptor;

typedef struct _block_literal_1 {
    void* isa;// initialized to &_NSConcreteStackBlock or &_NSConcreteGlobalBlock
    int flags;
    int reserved; 
    void (*invoke)(struct _block_literal_1*, ...);
    struct _block_descriptor* descriptor;
} _block_literal;

const void* createObjCBlock() {
    void (^block)() = ^{
        // do nothing
    };
    return Block_copy(block);
}

jlong Java_org_rococoa_ObjCBlocks_getObjCBlockFunctionPointer(JNIEnv* env, jclass cl, jlong jblock)
{
    _block_literal* block = (_block_literal*)JLONG_TO_PTR(jblock);
    return PTR_TO_JLONG(block->invoke);
}

jlong Java_org_rococoa_ObjCBlocks_createObjCBlockWithFunctionPointer(JNIEnv* env, jclass cl, jlong fptr)
{
    _block_literal* block = (_block_literal*)createObjCBlock();
    block->invoke = JLONG_TO_PTR(fptr);
    return PTR_TO_JLONG(block);
}

void Java_org_rococoa_ObjCBlocks_releaseObjCBlock(JNIEnv* env, jclass cl, jlong jblock) {
    _block_literal* block = (_block_literal*)JLONG_TO_PTR(jblock);
    Block_release(block);
}
