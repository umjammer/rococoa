
# rococoa-contrib

## Features

### 🗣 ~~NSSpeechSynthesizer~~ deprecated, use [AVSpeechSynthesizer](https://github.com/umjammer/vavi-sound-sandbox/tree/master/src/main/java/vavix/rococoa/avfoundation)

 * https://github.com/umjammer/vavi-speech/tree/master/src/main/java/vavi/speech/rococoa
 * https://github.com/umjammer/vavi-speech2/tree/master/src/main/java/vavi/speech/rococoa/jsapi2

### 🖼️ CoreImage

 * CGImage class ... utility for interoperability with BufferedImage
   * TODO screen density problem 

### 🖼️ CoreImage Filter

 * [BufferedImageOp](https://docs.oracle.com/javase/8/docs/api/java/awt/image/BufferedImageOp.html) using CIFilter
 * https://cifilter.io/

### 👁️ CoreML

 * make loading models (that is manual-less else swift) easy
 * see [sample](src/test/java/org/rococoa/cocoa/coreml/CoreMLTest.java)
 * TODO
   * color will be different form originals
   * input size is strictly fixed as 512x512 ... it's useless for comics (mostly height is over 800 pixels and not square)

### 👁️ Vision Face and Body Detection

 * body pose detection ... [sample](src/test/java/org/rococoa/cocoa/vision/VisionTest.java)
 * hand pose detection ... [sample](src/test/java/org/rococoa/cocoa/avfoundation/TestWebCam.java)

### 🧿 WebCam

 * https://qiita.com/pome-ta/items/bcac9d3209caa60f70b6
 * https://github.com/sarxos/webcam-capture
   * https://github.com/eduramiba/webcam-capture-driver-native (aarch64)
     * https://github.com/eduramiba/libvideocapture-avfoundation (aarch64)