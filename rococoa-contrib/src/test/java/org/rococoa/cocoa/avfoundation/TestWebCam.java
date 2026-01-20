/*
 * Copyright (c) 2023 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.avfoundation;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.github.eduramiba.webcamcapture.drivers.NativeDriver;
import com.github.eduramiba.webcamcapture.drivers.WebcamDeviceExtended;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamDevice;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.rococoa.cocoa.coregraphics.CGImage;
import org.rococoa.cocoa.coregraphics.CGPoint;
import org.rococoa.cocoa.vision.VNDetectHumanHandPoseRequest;
import org.rococoa.cocoa.vision.VNImageRequestHandler;
import vavi.util.Debug;

import static org.rococoa.cocoa.vision.VNHumanHandPoseObservation.All;
import static org.rococoa.cocoa.vision.VNHumanHandPoseObservation.IndexTip;
import static org.rococoa.cocoa.vision.VNHumanHandPoseObservation.LittleTip;
import static org.rococoa.cocoa.vision.VNHumanHandPoseObservation.MiddleTip;
import static org.rococoa.cocoa.vision.VNHumanHandPoseObservation.RingTip;
import static org.rococoa.cocoa.vision.VNHumanHandPoseObservation.ThumbTip;


/**
 * TestWebCam.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2023-12-18 nsano initial version <br>
 */
public class TestWebCam {

    static {
        Webcam.setDriver(new NativeDriver());
    }

    @Test
    void test1() throws Exception {
        List<Webcam> webcams = Webcam.getWebcams();
webcams.forEach(System.err::println);
    }

    @Test
    @EnabledIfSystemProperty(named = "vavi.test", matches = "ide")
    void test2() throws Exception {
        ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();

        // using cdl cause junit stops awt thread suddenly
        CountDownLatch cdl = new CountDownLatch(1);

        Webcam camera = Webcam.getWebcams().stream()
                .filter(wc -> wc.getName().contains("Studio Display"))
                .findFirst().get();

        WebcamDevice device = camera.getDevice();
Debug.printf("Found camera: %s, device = %s", camera, device);

        int width = device.getResolution().width;
        int height = device.getResolution().height;
        AtomicReference<BufferedImage> image = new AtomicReference<>();

        JFrame frame = new JFrame();
        frame.addWindowListener(new WindowAdapter() {
            @Override public void windowClosing(WindowEvent e) { cdl.countDown(); }
        });
        JPanel panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                if (image.get() != null) {
                    g.drawImage(image.get(), 0, 0, this);
                }
            }
        };
        panel.setPreferredSize(new Dimension(width, height));
        frame.setContentPane(panel);
        frame.setTitle("WebCam");
        frame.pack();
        frame.setVisible(true);

        camera.getLock().disable();
        camera.open();

        ses.scheduleAtFixedRate(() -> {
                if (device instanceof WebcamDeviceExtended webcamDeviceExtended) {
                    BufferedImage shot = webcamDeviceExtended.getImage();

                    image.set(shot);
//Debug.println("capture image: " + webcamDeviceExtended.getLastFrameTimestamp());
                    panel.repaint();
                }
        }, 0, 16, TimeUnit.MILLISECONDS);

        cdl.await();
    }

    /** it's better to open the hand in front of a camera before a camera starting for good detection */
    @Test
    @EnabledIfSystemProperty(named = "vavi.test", matches = "ide")
    void test3() throws Exception {
        ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();

        // using cdl cause junit stops awt thread suddenly
        CountDownLatch cdl = new CountDownLatch(1);

        Webcam camera = Webcam.getWebcams().stream()
                .filter(wc -> wc.getName().contains("Studio Display"))
                .findFirst().get();

        WebcamDevice device = camera.getDevice();
Debug.printf("Found camera: %s, device = %s", camera, device);

        int width = device.getResolution().width;
        int height = device.getResolution().height;
        AtomicReference<BufferedImage> image = new AtomicReference<>();

        JFrame frame = new JFrame();
        frame.addWindowListener(new WindowAdapter() {
            @Override public void windowClosing(WindowEvent e) { cdl.countDown(); }
        });
        JPanel panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                if (image.get() != null) {
                    g.drawImage(image.get(), 0, 0, this);
                }
            }
        };
        panel.setPreferredSize(new Dimension(width, height));
        frame.setContentPane(panel);
        frame.setTitle("WebCam");
        frame.pack();
        frame.setVisible(true);

        camera.getLock().disable();
        camera.open();

        VNDetectHumanHandPoseRequest request = VNDetectHumanHandPoseRequest.newRequest();
        request.setMaximumHandCount(1);

        ses.scheduleAtFixedRate(() -> {
            try {
                if (device instanceof WebcamDeviceExtended webcamDeviceExtended) {
                    BufferedImage shot = webcamDeviceExtended.getImage();

                    CGImage cgImage = new CGImage(shot);

                    VNImageRequestHandler handler = VNImageRequestHandler.initWithCGImage(cgImage.pointer());
long t = System.currentTimeMillis();
                    handler.performRequests(request);

                    Graphics2D g = shot.createGraphics();

                    final int W = 10;
                    request.result(o -> {
                        CGPoint[] points = (CGPoint[]) o;
Debug.println("points: " + points.length);
Debug.println((System.currentTimeMillis() - t) + " ms");
                        AtomicInteger i = new AtomicInteger(1);
                        Arrays.stream(points).forEach(p -> {
                            Point np = cgImage.normalize(p);
                            g.setColor(Color.green);
                            g.setStroke(new BasicStroke(W));
                            g.drawArc(np.x - W, np.y - W, 2 * W, 2 * W, 0, 360);
                            g.setFont(new Font("Dialog", Font.PLAIN, 32));
                            g.drawString(String.valueOf(i.getAndIncrement()), np.x - 2 * W, np.y - 2 * W);
                        });
                    }, All, ThumbTip, IndexTip, MiddleTip, RingTip, LittleTip);

                    image.set(shot);
//Debug.println("capture image: " + webcamDeviceExtended.getLastFrameTimestamp());
                    panel.repaint();
                }
            } catch (IOException e) {
Debug.println(e);
            }
        }, 0, 16, TimeUnit.MILLISECONDS);

        cdl.await();
    }
}
