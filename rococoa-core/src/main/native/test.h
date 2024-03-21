//
//  test.h
//  rococoa
//
//  Created by Duncan McGregor on 03/12/2007.
//  Copyright 2007 __MyCompanyName__. All rights reserved.
//

#import <Cocoa/Cocoa.h>

struct block_descriptor_basic {
    unsigned long int reserved;
    unsigned long int size;
    void* _Nullable rest[1];
};

struct block_literal {
    void* isa;
    int   flags;
    int   reserved;
    void (*invoke)(void*, ...);
    struct block_descriptor_basic* descriptor;
};

typedef struct TestIntDoubleStruct {
	int anInt;
	double aDouble;
} TestIntDoubleStruct;

typedef struct TestStructOfStruct {
	double aDouble;
	TestIntDoubleStruct aStruct;
} TestStructOfStruct;

typedef struct TestFloatFloatStruct {
	float a;
	float b;
} TestFloatFloatStruct;

typedef struct TestIntFloatStruct {
	int a;
	float b;
} TestIntFloatStruct;

typedef struct TestIntIntStruct {
	int a;
	int b;
} TestIntIntStruct;

typedef struct TestIntLongStruct {
	int a;
	int64_t b;
} TestIntLongStruct;

TestIntDoubleStruct createIntDoubleStruct(int a, double b);

double addFieldsOfStructByValue(TestIntDoubleStruct s);

typedef int (^MyBlock)(int);
typedef long (^MyBlockI)(id);
typedef id (^MyBlockS)(id);
typedef id (^MyBlockS2)(id, int);

@interface TestShunt : NSObject

- (TestIntDoubleStruct) testReturnStructByValue: (int) a and: (double) b;

- (double) testAddFieldsOfStructByValue: (TestIntDoubleStruct) s;

- (TestStructOfStruct) testReturnStructOfStructByValue: (int) a and: (double) b;

- (double) testPassStructOfStructByValue: (TestStructOfStruct) s;

- (void) testNSNumberByReference: (NSNumber**) fillMeIn with: (int) aValue;

- (BOOL) valueIsYES:(BOOL) a;

- (BOOL) valueIsNO:(BOOL) a;

- (bool) isMainThread;

- (int) testBlock: (int) number operation: (MyBlock) operationBlock;

- (long) testBlockI: (id) id operation: (MyBlockI) operationBlock;

- (id) testBlockS: (id) s operation: (MyBlockS) operationBlock;

- (id) testBlockS2: (id) s times: (int) n operation: (MyBlockS2) operationBlock;

- (void) testBlockX;

@end
