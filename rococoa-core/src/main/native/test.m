//
//  test.m
//  rococoa
//
//  Created by Duncan McGregor on 03/12/2007.
//  Copyright 2007 __MyCompanyName__. All rights reserved.
//

#import "test.h"
#import <stdarg.h>
#include <pthread/pthread.h>
#include <AVFoundation/AVFoundation.h>
#include <Block.h>

TestIntDoubleStruct createIntDoubleStruct(int a, double b) {
	TestIntDoubleStruct result = {a, b};
	return result;
}

double addFieldsOfStructByValue(TestIntDoubleStruct s) {
	return s.anInt + s.aDouble;
}

// JNA got broken for this case
double addFieldsOfStructByValueVARARGS(size_t count, ...) {
	va_list vl;
	va_start(vl, count);
	TestIntDoubleStruct s = va_arg(vl, TestIntDoubleStruct);
	double result = addFieldsOfStructByValue(s);
	va_end(vl);
	return result;
}

TestFloatFloatStruct createFloatFloatStruct(float a, float b) {
	TestFloatFloatStruct result = {a, b};
	return result;
}

TestIntFloatStruct createIntFloatStruct(int a, float b) {
	TestIntFloatStruct result = {a, b};
	return result;
}

TestIntLongStruct createIntLongStruct(int a, int64_t b) {
	TestIntLongStruct result = {a, b};
	return result;
}

TestIntIntStruct createIntIntStruct(int a, int b) {
	TestIntIntStruct result = {a, b};
	return result;
}

@implementation TestShunt

- (TestIntDoubleStruct) testReturnStructByValue: (int) a and: (double) b {
	return createIntDoubleStruct(a, b);
}

- (double) testAddFieldsOfStructByValue: (TestIntDoubleStruct) s {
	return addFieldsOfStructByValue(s);
}

- (TestStructOfStruct) testReturnStructOfStructByValue: (int) a and: (double) b {
	TestIntDoubleStruct inside = createIntDoubleStruct(a, b);
	TestStructOfStruct result = {b, inside};
	return result;
}

- (double) testPassStructOfStructByValue: (TestStructOfStruct) s {
	return s.aStruct.aDouble;
}

- (NSNumber*) testNumberFromInt: (int) aValue {
    NSLog(@"Received number %i", aValue);
	NSNumber* number = [NSNumber numberWithInt: aValue];
	return number;
}

- (void) testNSNumberByReference: (NSNumber**) fillMeIn with: (int) aValue {
	NSNumber* number = [NSNumber numberWithInt: aValue];
	*fillMeIn = number;
}

- (void) testCallbackWithReference:(id)delegate {
	if ([delegate respondsToSelector:@selector(callback:)]) {
		NSError* error = nil;
		[delegate callback:&error];
	}
}

- (BOOL) valueIsYES:(BOOL) a {
	return a == YES;
}

- (BOOL) valueIsNO:(BOOL) a {
	return a == NO;
}

- (bool) isMainThread {
	return pthread_main_np();
}

- (float) testPassFloatByValue {
    float f = 3.14;
	return f;
}

union floatint {
   float f;
   int i;
};

- (int) testConvertFloatToInt: (float) f {
    union floatint fi;
    fi.f = f;
fprintf(stderr, "%d\n", fi.i);
fflush(stderr);
	return fi.i;
}

- (BOOL) testGetFloatByValue: (float) f {
fprintf(stderr, "%3.1f\n", f);
fflush(stderr);
	return f == (float) 3.14;
}

- (int) testBlock: (int) number operation: (MyBlock) operationBlock {
    int r = operationBlock(number);
fprintf(stderr, "%s\n", "testBlock");
fflush(stderr);
    return r;
}

- (long) testBlockI: (id) id operation: (MyBlockI) operationBlock {
    long r = operationBlock(id);
fprintf(stderr, "%s\n", "testBlockI");
fflush(stderr);
    return r;
}

- (id) testBlockS: (NSString*) s operation: (MyBlockS) operationBlock {
    id r = operationBlock(s);
fprintf(stderr, "%s\n", "testBlockS");
fflush(stderr);
    return r;
}

- (id) testBlockS2: (id) s times: (int) n operation: (MyBlockS2) operationBlock {
    id r = operationBlock(s, n);
fprintf(stderr, "%s\n", "testBlockS2");
fflush(stderr);
    return r;
}

- (void) testBlockX {
    AVSpeechSynthesizer* synthesizer = [[AVSpeechSynthesizer alloc] init];
    AVSpeechSynthesisVoice* voice = [AVSpeechSynthesisVoice voiceWithLanguage: @"en-US"];
    AVSpeechUtterance* *utterance = [[AVSpeechUtterance alloc] initWithString: @"she sells seashells by the seashore"];
    [utterance setVoice: voice];
    __block AVAudioFile *output = nil;
    AVSpeechSynthesizerBufferCallback callback = ^(AVAudioBuffer* buffer) {
        AVAudioPCMBuffer *pcmBuffer = (AVAudioPCMBuffer*) buffer;
        if (!pcmBuffer) {
            NSLog(@"Error");
            return;
        }
        if (pcmBuffer.frameLength != 0) {
            //append buffer to file
            if (output == nil) {
                output = [[AVAudioFile alloc] initForWriting: [NSURL fileURLWithPath: @"tmp/test.caf"]
                                              settings: pcmBuffer.format.settings
                                              commonFormat: AVAudioPCMFormatInt16
                                              interleaved: NO error: nil];
            }
            [output writeFromBuffer:pcmBuffer error:nil];
fprintf(stderr, "%s, %d\n", "testBlockX inner block", pcmBuffer.frameLength);
fflush(stderr);
        }
    };
//     struct block_literal* block = (struct block_literal*) &callback;
// fprintf(stderr, "block->flags: %08x, %d\n", block->flags, block->descriptor->size);
// fflush(stderr);
    [synthesizer writeUtterance: utterance toBufferCallback: callback];
fprintf(stderr, "%s\n", "testBlockX");
fflush(stderr);
}

@end
