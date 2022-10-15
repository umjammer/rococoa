/*
 * Copyright (c) 2022 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.coreml;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.junit.jupiter.api.Test;
import org.rococoa.cocoa.coregraphics.CGImage;
import org.rococoa.cocoa.vision.VNCoreMLModel;
import org.rococoa.cocoa.vision.VNCoreMLRequest;
import org.rococoa.cocoa.vision.VNImageRequestHandler;
import vavi.util.Debug;


/**
 * CoreMLTest.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2022/10/15 nsano initial version <br>
 * @see "https://github.com/mworks/mworks/blob/01dade67c468f5fe5942457809cf4a6367294b6c/plugins/core/FaceRecognizer/FaceRecognizer/Helpers/ModelManager.cpp"
 */
class CoreMLTest {

    @Test
    void test1() throws Exception {

long t = System.currentTimeMillis();
Debug.println("MLModel: loading...");
        MLModel mlModel = MLModel.fromPath("/Users/nsano/Downloads/realesrganAnime512.mlmodel");
//        MLModel mlModel = MLModel.fromPath("/Users/nsano/Downloads/bsrgan.mlmodel");
//Debug.println("MLModel: " + mlModel.modelDescription());

        VNCoreMLModel model = VNCoreMLModel.fromMLModel(mlModel);
//Debug.println("VNCoreMLModel: " + model.inputImageFeatureName());
Debug.println("prepare done: " + (System.currentTimeMillis() - t) + " ms");

        VNCoreMLRequest request = VNCoreMLRequest.newRequest(model);

//        CGImage cgImage = new CGImage(CoreMLTest.class.getResourceAsStream("/test.jpg"));
        CGImage cgImage = new CGImage(Files.newInputStream(Paths.get("/Users/nsano/src/vavi/vavi-image-dlfilter/tmp/v01_031.jpg")));
//        CGImage cgImage = new CGImage(Files.newInputStream(Paths.get("/Users/nsano/src/vavi/vavi-image-dlfilter/src/test/resources/namacha.jpg")));

        VNImageRequestHandler handler = VNImageRequestHandler.initWithCGImage(cgImage.pointer());
t = System.currentTimeMillis();
        handler.performRequests(request);

        CGImage filteredImage = new CGImage(request.result());
Debug.println((System.currentTimeMillis() - t) + " ms");
Debug.println("cgImage: " + filteredImage);
        show(filteredImage.toBufferedImage());
    }

    /** */
    void show(BufferedImage image) {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel() {
            public void paintComponent(Graphics g) {
                g.drawImage(image, 0, 0, this);
            }
        };
        panel.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
        frame.setContentPane(new JScrollPane(panel));
        frame.setTitle("CoreML");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        while (true) Thread.yield();
    }
}