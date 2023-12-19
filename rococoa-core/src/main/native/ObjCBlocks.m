#include <stdio.h>

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
    BLOCK_HAS_SIGNATURE =     (1 << 30),
};

typedef struct __block_descriptor_1 {
    uintptr_t reserved;                   // NULL
    uintptr_t block_size;                 // sizeof(struct _block_literal_1)
} __block_descriptor_1;

typedef struct _block_literal_1 {
    void* isa;
    int32_t flags;
    int32_t reserved;
    void (*invoke)(struct _block_literal_1*, ...);
    struct __block_descriptor_1* descriptor;
} _block_literal_1;

const void* createObjCBlock() {
    void (^block)() = ^{
        fprintf(stderr, "hello block\n");
        fflush(stderr);
        // do nothing
    };
    void* r = Block_copy(block);
fprintf(stderr, "hereC: %16lx, %16lx\n", block, r);
fflush(stderr);
    return r;
}

void* getObjCBlockFunctionPointer(void* jblock)
{
    _block_literal_1* block = (struct _block_literal_1*) jblock;
    return block->invoke;
}

typedef void (*invoke)(struct _block_literal_1*, ...);

void* createObjCBlockWithFunctionPointer(id fptr)
{
fprintf(stderr, "here0: %16lx\n", fptr);
fflush(stderr);
    struct _block_literal_1* block = (struct _block_literal_1*)createObjCBlock();
fprintf(stderr, "here1: %16lx, %d, %x\n", block, block->descriptor->block_size, block->flags);
fflush(stderr);
fprintf(stderr, "here1.5: %16lx, %16lx\n", block->invoke, (void (*)(struct _block_literal_1*, ...)) fptr);
fflush(stderr);
    long x = fptr;
fprintf(stderr, "here1.6: %16lx\n", x);
    block->invoke = (void (*)(struct _block_literal_1*, ...)) fptr;
fprintf(stderr, "here2\n");
fflush(stderr);
    return block;
}

void releaseObjCBlock(void* jblock) {
    _block_literal_1* block = (struct _block_literal_1*) jblock;
    Block_release(block);
}
