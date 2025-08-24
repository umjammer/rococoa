/*
 * Copyright (c) 2022 by Naohide Sano, All rights reserved.
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIf;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.rococoa.Rococoa;
import org.rococoa.cocoa.coregraphics.CGImage;
import org.rococoa.cocoa.foundation.NSError;
import org.rococoa.cocoa.vision.VNCoreMLModel;
import org.rococoa.cocoa.vision.VNCoreMLRequest;
import org.rococoa.cocoa.vision.VNImageRequestHandler;
import vavi.util.Debug;
import org.rococoa.Block;
import vavi.util.properties.annotation.Property;
import vavi.util.properties.annotation.PropsEntity;


/**
 * CoreMLTest.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2022/10/15 nsano initial version <br>
 * @see "https://github.com/mworks/mworks/blob/01dade67c468f5fe5942457809cf4a6367294b6c/plugins/core/FaceRecognizer/FaceRecognizer/Helpers/ModelManager.cpp"
 */
@PropsEntity(url = "file:local.properties")
class CoreMLTest {

    static boolean localPropertiesExists() {
        return Files.exists(Paths.get("local.properties"));
    }

    @Property
    String model;

    @Property
    String image;

    @Property
    String model2;

    @Property
    String model3;

    @Property
    String model4;

    @BeforeEach
    void setup() throws Exception {
        if (localPropertiesExists()) {
            PropsEntity.Util.bind(this);
        }
    }

    @Test
    @DisplayName("BSRGAN")
    @EnabledIf("localPropertiesExists")
    @EnabledIfSystemProperty(named = "vavi.test", matches = "ide")
    void test1() throws Exception {

long t = System.currentTimeMillis();
Debug.println("MLModel: loading...");
        MLModel mlModel = MLModel.fromPath(model);
Debug.println("MLModel: " + mlModel.modelDescription());

        VNCoreMLModel model = VNCoreMLModel.fromMLModel(mlModel);
//Debug.println("VNCoreMLModel: " + model.inputImageFeatureName());
Debug.println("prepare done: " + (System.currentTimeMillis() - t) + " ms");

        VNCoreMLRequest request = VNCoreMLRequest.newRequest(model);

        CGImage cgImage = new CGImage(Files.newInputStream(Paths.get(image)));

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
            @Override
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

    @Test
    @EnabledIf("localPropertiesExists")
    @EnabledIfSystemProperty(named = "vavi.test", matches = "ide")
    void test2() throws Exception {
        CountDownLatch cdl = new CountDownLatch(1);
        MLModel mlModel = MLModel.fromPath(model2);
Debug.println(mlModel);
        VNCoreMLModel model = VNCoreMLModel.fromMLModel(mlModel);
Debug.println(model);

        VNCoreMLRequest.VNRequestCompletionHandler handler = (literal, requestId, errorRef) -> {
            NSError error = Rococoa.wrap(errorRef, NSError.class);
            if (error != null) {
                throw new IllegalStateException(error.description());
            }
            Debug.println("here1");
            VNCoreMLRequest request = Rococoa.wrap(requestId, VNCoreMLRequest.class);
            Debug.println("request: " + request);
        };
        VNCoreMLRequest.CLASS.alloc().initWithModel_completionHandler(model, new Block(handler).getLiteral());
Debug.println("here2");
        cdl.await();
    }

    @Test
    @DisplayName("remove")
    @EnabledIf("localPropertiesExists")
    @EnabledIfSystemProperty(named = "vavi.test", matches = "ide")
    void test3() throws Exception {
        MLModel mlModel = MLModel.fromPath(model3);
Debug.println("MLModel: " + mlModel.modelDescription());
    }

    @Test
    @DisplayName("border")
    @EnabledIf("localPropertiesExists")
    @EnabledIfSystemProperty(named = "vavi.test", matches = "ide")
    void test4() throws Exception {
        MLModel mlModel = MLModel.fromPath(model4);
Debug.println("MLModel: " + mlModel.modelDescription());
    }
}