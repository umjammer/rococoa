/*
 * Copyright (c) 2022 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.coreimage;

import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorModel;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.util.Map;

import org.rococoa.cocoa.foundation.NSObject;

import static java.lang.System.getLogger;


/**
 * CIFilterOp.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2022-10-13 nsano initial version <br>
 * @see "https://cifilter.io/"
 */
public class CIFilterOp implements BufferedImageOp {

    private static final Logger logger = getLogger(CIFilterOp.class.getName());

    private RenderingHints hints;

    /**  */
    private final String filterName;

    /**  */
    private final Map<String, NSObject> options;

    /**  */
    public CIFilterOp(String filterName, Map<String, NSObject> options) {
        this.filterName = filterName;
        this.options = options;
    }

    @Override
    public BufferedImage filter(BufferedImage src, BufferedImage dest) {
        try {
            CIImage ciImage = CIImage.newInstance(src);

            CIFilter filter = CIFilter.filterWithName(filterName);

            filter.setValue_forKey(ciImage, CIImage.kCIInputImageKey);
            logger.log(Level.DEBUG, "option: " + CIImage.kCIInputImageKey + " = " + ciImage);
            options.forEach((k, v) -> {
                logger.log(Level.DEBUG, "option: " + k + " = " + v);
                filter.setValue_forKey(v, k);
            });

            CIImage result = filter.outputImage();

            return result.toBufferedImage();

        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public Rectangle2D getBounds2D(BufferedImage src) {
        return new Rectangle(0, 0, src.getWidth(), src.getHeight());
    }

    @Override
    public BufferedImage createCompatibleDestImage(BufferedImage src, ColorModel dstCM) {
        if (dstCM == null) {
            dstCM = src.getColorModel();
        }
        return new BufferedImage(dstCM, dstCM.createCompatibleWritableRaster(src.getWidth(), src.getHeight()), dstCM.isAlphaPremultiplied(), null);
    }

    @Override
    public Point2D getPoint2D(Point2D srcPt, Point2D dstPt) {
        if (dstPt == null) {
            dstPt = new Point2D.Double();
        }
        dstPt.setLocation(srcPt.getX(), srcPt.getY());
        return dstPt;
    }

    @Override
    public RenderingHints getRenderingHints() {
        return hints;
    }
}
