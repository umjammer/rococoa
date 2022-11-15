/*
 * Copyright (c) 2022 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.vision;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.sun.jna.NativeLong;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.rococoa.cocoa.coregraphics.CGImage;
import org.rococoa.cocoa.coregraphics.CGPoint;
import vavi.util.Debug;


class VisionTest {

    @Test
    @EnabledIfSystemProperty(named = "vavi.test", matches = "ide")
    void test1() throws Exception {

        VNDetectHumanBodyPoseRequest request = VNDetectHumanBodyPoseRequest.newRequest();

        CGImage cgImage = new CGImage(Files.newInputStream(Paths.get("src/test/resources/test.webp")));

        VNImageRequestHandler handler = VNImageRequestHandler.initWithCGImage(cgImage.pointer());
long t = System.currentTimeMillis();
        handler.performRequests(request);

        BufferedImage image = cgImage.toBufferedImage();
        Graphics2D g = image.createGraphics();

        final int W = 10;
        request.result(o -> {
            CGPoint[] points = (CGPoint[]) o;
Debug.println("points: " + points.length);
Debug.println((System.currentTimeMillis() - t) + " ms");
            AtomicInteger i = new AtomicInteger(1);
            Arrays.stream(points).forEach(p -> {
                CGPoint cp = (VisionLibrary.library.VNImagePointForNormalizedPoint(
                    p, new NativeLong(cgImage.getWidth()), new NativeLong(cgImage.getHeight())));
                int x = cp.x.intValue();
                int y = cgImage.getHeight() - cp.y.intValue();
                g.setColor(Color.green);
                g.setStroke(new BasicStroke(W));
                g.drawArc(x - W, y - W, 2 * W, 2 * W, 0, 360);
                g.setFont(new Font("Dialog", Font.PLAIN, 32));
                g.drawString(String.valueOf(i.getAndIncrement()), x - 2 * W, y - 2 * W);
            });
        });
        show(image);
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
        frame.setTitle("Vision");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        while (true) Thread.yield();
    }
}