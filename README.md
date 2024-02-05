[![GitHub Packages](https://github.com/umjammer/rococoa/actions/workflows/maven-publish.yml/badge.svg)](https://github.com/umjammer?tab=packages&repo_name=rococoa)
[![Java CI](https://github.com/umjammer/rococoa/actions/workflows/maven.yml/badge.svg)](https://github.com/umjammer/rococoa/actions/workflows/maven.yml)
[![CodeQL](https://github.com/umjammer/rococoa/actions/workflows/codeql-analysis.yml/badge.svg)](https://github.com/umjammer/rococoa/actions/workflows/codeql-analysis.yml)
![Java](https://img.shields.io/badge/Java-17-b07219)

#  Welcome to Rococoa

Rococoa is a generic Java binding to the Mac Objective-C object system. It 
allows the creation and use of Objective-C objects in Java, and the 
implementation of Objective-C interfaces in Java.

## ⚠ Caution

 * this project will **quit** supporting **intel** chips
 * supported macos will be **after Ventura** also

## Limitation

* ~~obj-c class's method call with float argument doesn't work~~ works → new limitation: arguments should be less equal 8
  * ~~[the reason](https://github.com/java-native-access/jna/issues/463#issuecomment-1286015013)~~ -> actually https://github.com/java-native-access/jna/issues/1476#issuecomment-1292804072
  * https://www.mikeash.com/pyblog/objc_msgsends-new-prototype.html
* obj-c block
* methods has **varargs don't** work (works less equal 8?)

## Installation

 * https://github.com/umjammer/rococoa/packages/
 * this project uses github packages. add a personal access token to `~/.m2/settings.xml`
 * see https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-apache-maven-registry

## How To

* [Basics](rococoa-core/readme.md#How-To)
* [CIFilter Java2D BufferedImageOp](rococoa-contrib/src/test/java/org/rococoa/cocoa/coreimage/CoreImageTest.java)
* [CoreML](rococoa-contrib/src/test/java/org/rococoa/cocoa/coreml/CoreMLTest.java)
* [iTunes Library](https://github.com/umjammer/vavi-sound-sandbox/tree/master/src/main/java/vavix/rococoa/ituneslibrary)
* [AudioUnit Java MIDI SPI synthesizer](https://github.com/umjammer/vavi-sound-sandbox/tree/master/src/main/java/vavi/sound/midi/rococoa)
* [CoreMidi Java MIDI SPI](https://github.com/umjammer/osxmidi4j)
* [NSSpeechSynthesizer Java Speech API](https://github.com/umjammer/vavi-speech2/tree/master/src/main/java/vavi/speech/rococoa/jsapi2)
* [HEIF Java ImageIO SPI](https://github.com/umjammer/vavi-image-sandbox/tree/master/src/main/java/vavix/imageio/rococoa)
* [MTLibrary](https://github.com/umjammer/vavi-apps-padsynth/tree/main/src/main/java/vavix/rococoa/multitouch)
* [KeyChain Java crypto Keystore SPI](https://github.com/umjammer/vavi-crypto-sandbox/tree/1.0.2/src/main/java/vavix/rococoa/keychain)
* [Vision Detecting Human Body Poses in Images](rococoa-contrib/src/test/java/org/rococoa/cocoa/vision/VisionTest.java)

## TODO

* NSUrl tags (wip)
* obj-block (wip)
   * https://github.com/nativelibs4java/BridJ/
   * http://cocoawithlove.com/2009/10/how-blocks-are-implemented-and.html
   * http://www.opensource.apple.com/source/libclosure/libclosure-38/BlockImplementation.txt
   * https://clang.llvm.org/docs/Block-ABI-Apple.html
   * https://github.com/ronaldoussoren/pyobjc/blob/77b98382e52818690449111cd2e23cd469b53cf5/pyobjc-core/Modules/objc/block_support.m
   * https://docs.rs/block/latest/block/
* ~~CIFilter~~ (done)
  * CGImage fails around density related 
* ~~`cglib` is mostly [suspended](https://github.com/cglib/cglib#readme)~~
   * ~~`cglib` recommends [ByteBuddy](https://bytebuddy.net/) that is based on `asm` same as the `cglib`~~ (done)
   * cache classes (ByteBuddy)
* ~~clean up logging~~
* native library loading
  * https://github.com/scijava/native-lib-loader
* dynamic method creation
  * invokedinamic?
  * ByteBuddy's method interception???
* CGController
  * https://stackoverflow.com/a/65999820
* activate application
  * https://developer.apple.com/documentation/appkit/nsrunningapplication?language=objc
* separate same parts of jna-platform (like jna-platform-extended)
* deprecate rococoa-contrib

## References
 
* https://github.com/ibinti/bugvm
* https://gitlab.com/axet/apple
* https://github.com/multi-os-engine/moe-mac-core
* https://github.com/dthommes/jcocoa
* https://github.com/allertonm/Couverjure
* https://github.com/shannah/Java-Objective-C-Bridge (minecraft uses)
* gamepad
  * http://eleccelerator.com/wiki/index.php?title=DualShock_4
  * https://github.com/born2snipe/gamepad4j/blob/master/gamepad4j-desktop/src/main/c/macos/Gamepad_macosx.c
  * port [hidapi](https://github.com/libusb/hidapi) mac part

----

# Original

## Fair Warning

Rococoa is very much work in progress. Much is subject to change. A lot isn't
good enough not to change. But given the recent deprecation of the Java-Cocoa 
bridge, it's the best I've got. Just mind your head. And please give
[feedback](https://rococoa.dev.java.net/servlets/ProjectMailingListList)

Oh, one big warning. Rococoa on PPC passes all but one of its tests, but that
shows that it has an 
[issue](https://github.com/umjammer/rococoa/issues)
returning longs from Objective-C methods. Please do
try it on PPC, and 
[let me know](https://rococoa.dev.java.net/servlets/ProjectMailingListList)
any other problems.

 * [Take the Whistlestop Tour](https://github.com/iterate-ch/rococoa/blob/master/Building.md)
 * [How To](https://github.com/iterate-ch/rococoa/blob/wiki/HowTo.md)
 * [Quicktime](https://github.com/iterate-ch/rococoa/blob/wiki/Quicktime.md)
 * [Limitations](https://github.com/iterate-ch/rococoa/blob/wiki/Limitations.md)
 * [Help Wanted](https://github.com/iterate-ch/rococoa/blob/wiki/HelpWanted.md)
