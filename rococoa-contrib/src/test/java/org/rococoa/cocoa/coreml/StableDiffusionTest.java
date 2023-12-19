/*
 * Copyright (c) 2023 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.coreml;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.CountDownLatch;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.sun.jna.Pointer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.rococoa.cocoa.coregraphics.CGImage;
import org.rococoa.cocoa.vision.VNCoreMLModel;
import org.rococoa.cocoa.vision.VNCoreMLRequest;
import org.rococoa.cocoa.vision.VNImageRequestHandler;
import vavi.util.Debug;


/**
 * StableDiffusionTest.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2023/06/13 nsano initial version <br>
 * @see "https://github.com/john-rocky/CoreML-Models#stable-diffusion-v1-5"
 */
class StableDiffusionTest {

    @Test
    @EnabledIfSystemProperty(named = "vavi.test", matches = "ide")
    void test1() throws Exception {

long t = System.currentTimeMillis();
Debug.println("MLModel: loading...");
        MLModel mlModel = MLModel.fromPath("/Users/nsano/Downloads/bsrgan.mlmodel"); // no good for japanese
Debug.println("MLModel: " + mlModel.modelDescription());

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

        CGImage filteredImage = new CGImage((Pointer) request.result());
Debug.println((System.currentTimeMillis() - t) + " ms");
Debug.println("cgImage: " + filteredImage);
        show(filteredImage.toBufferedImage());
    }

    /** using cdl because junit stops awt thread suddenly */
    void show(BufferedImage image) throws Exception {
        CountDownLatch cdl = new CountDownLatch(1);
        JFrame frame = new JFrame();
        frame.addWindowListener(new WindowAdapter() {
            @Override public void windowClosing(WindowEvent e) { cdl.countDown(); }
        });
        JPanel panel = new JPanel() {
            public void paintComponent(Graphics g) {
                g.drawImage(image, 0, 0, this);
            }
        };
        panel.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
        frame.setContentPane(new JScrollPane(panel));
        frame.setTitle("CoreML");
        frame.pack();
        frame.setVisible(true);
        cdl.await();
    }
}