[![GitHub Packages](https://github.com/umjammer/rococoa/actions/workflows/maven-publish.yml/badge.svg)](https://github.com/umjammer?tab=packages&repo_name=rococoa)
[![Java CI](https://github.com/umjammer/rococoa/actions/workflows/maven.yml/badge.svg)](https://github.com/umjammer/rococoa/actions/workflows/maven.yml)
[![CodeQL](https://github.com/umjammer/rococoa/actions/workflows/codeql-analysis.yml/badge.svg)](https://github.com/umjammer/rococoa/actions/workflows/codeql-analysis.yml)
![Java](https://img.shields.io/badge/Java-8-b07219)

#  Welcome to Rococoa

Rococoa is a generic Java binding to the Mac Objective-C object system. It 
allows the creation and use of Objective-C objects in Java, and the 
implementation of Objective-C interfaces in Java.

## Installation

 * https://github.com/umjammer/rococoa/packages/
 * this project uses github packages. add a personal access token to `~/.m2/settings.xml`
 * see https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-apache-maven-registry

## How To

* [Basics](rococoa-core/readme.md#how-to)
* [CGFilter Java2D BufferedImageOp](rococoa-contrib/src/test/java/org/rococoa/cocoa/coreimage/CoreImageTest.java)
* [CoreML](rococoa-contrib/src/test/java/org/rococoa/cocoa/coreml/CoreMLTest.java)
* [iTunes Library](https://github.com/umjammer/vavi-sound-sandbox/tree/master/src/main/java/vavix/rococoa/ituneslibrary)
* [AudioUnit Java MIDI SPI synthesizer](https://github.com/umjammer/vavi-sound-sandbox/tree/master/src/main/java/vavi/sound/midi/rococoa)
* [CoreMidi Java MIDI SPI](https://github.com/umjammer/osxmidi4j)
* [NSSpeechSynthesizer Java Speech API](https://github.com/umjammer/vavi-speech2/tree/master/src/main/java/vavi/speech/rococoa/jsapi2)
* [HEIF Java ImageIO SPI](https://github.com/umjammer/vavi-image-sandbox/tree/master/src/main/java/vavix/imageio/rococoa)
* [MTLibrary](https://github.com/umjammer/vavi-apps-padsynth/tree/main/src/main/java/vavix/rococoa/multitouch)
* [KeyChain Java crypto Keystore SPI](https://github.com/umjammer/vavi-crypto-sandbox/tree/1.0.2/src/main/java/vavix/rococoa/keychain)
* [Vision Detecting Human Body Poses in Images](rococoa-contrib/src/test/java/org/rococoa/cocoa/vision/VisionTest.java)

## Limitation

* obj-c class's method call with float argument don't work
  * i found [the reason](https://github.com/java-native-access/jna/issues/463#issuecomment-1286015013) 
* block

## TODO

* https://github.com/ibinti/bugvm
* NSUrl tags (wip)
* block (wip)
   * http://cocoawithlove.com/2009/10/how-blocks-are-implemented-and.html
   * http://www.opensource.apple.com/source/libclosure/libclosure-38/BlockImplementation.txt
* ~~CIFilter~~ (done)
* https://gitlab.com/axet/apple

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
