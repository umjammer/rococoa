/*
 * Copyright (c) 2022 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.vision;

import java.util.Arrays;
import java.util.logging.Logger;
import java.util.stream.IntStream;

import org.rococoa.ObjCClass;
import org.rococoa.ObjCObjectByReference;
import org.rococoa.Rococoa;
import org.rococoa.cocoa.coregraphics.CGPoint;
import org.rococoa.cocoa.foundation.NSArray;
import org.rococoa.cocoa.foundation.NSDictionary;
import org.rococoa.cocoa.foundation.NSError;
import org.rococoa.cocoa.foundation.NSObject;


/**
 * An observation that provides the hand points the analysis recognized.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2023-12-18 nsano initial version <br>
 */
public abstract class VNHumanHandPoseObservation extends VNRecognizedPointsObservation {

    static {
        VisionLibrary.library.toString();
    }

    private static final Logger logger = Logger.getLogger(VNHumanHandPoseObservation.class.getName());

    public static final _Class CLASS = Rococoa.createClass("VNHumanHandPoseObservation", _Class.class);

    public interface _Class extends ObjCClass {
        VNHumanHandPoseObservation alloc();
    }

    public abstract VNHumanHandPoseObservation init();

    /** The names of the available joints in the observation. */
    public abstract NSArray availableJointNames();

    /** The available joint group names in the observation. */
    public abstract NSArray availableJointsGroupNames();

    /** Retrieves the recognized point for a joint name. */
    public abstract VNRecognizedPoint recognizedPointForJointName_error(String /* String */ jointName, ObjCObjectByReference /* NSError */ error);

    /**
     * Retrieves the recognized points associated with the joint group name.
     * @return NSDictionary&lt;String, VNRecognizedPoint&gt;
     */
    public abstract NSDictionary recognizedPointsForJointsGroupName_error(String /* VNHumanHandPoseObservationJointsGroupName */ jointsGroupName, ObjCObjectByReference /* NSError */ error);

    // VNHumanHandPoseObservationJointName
    public static final String ThumbTip = "VNHLKTTIP";
    public static final String ThumbIP = "VNHLKTIP";
    public static final String ThumbMP = "VNHLKTMP";
    public static final String ThumbCMC = "VNHLKTCMC";
    public static final String IndexTip = "VNHLKITIP";
    public static final String IndexDIP = "VNHLKIDIP";
    public static final String IndexPIP = "VNHLKIPIP";
    public static final String IndexMCP = "VNHLKIMCP";
    public static final String MiddleTip = "VNHLKMTIP";
    public static final String MiddleDIP = "VNHLKMDIP";
    public static final String MiddlePIP = "VNHLKMPIP";
    public static final String MiddleMCP = "VNHLKMMCP";
    public static final String RingTip = "VNHLKRTIP";
    public static final String RingDIP = "VNHLKRDIP";
    public static final String RingPIP = "VNHLKRPIP";
    public static final String RingMCP = "VNHLKRMCP";
    public static final String LittleTip = "VNHLKPTIP";
    public static final String LittleDIP = "VNHLKPDIP";
    public static final String LittlePIP = "VNHLKPPIP";
    public static final String LittleMCP = "VNHLKPMCP";
    public static final String Wrist = "VNHLKWRI";

    // VNHumanHandPoseObservationJointsGroupName
    public static final String All = "VNIPOAll";
    public static final String Thumb = "VNHLRKT";
    public static final String Middle = "VNHLRKM";
    public static final String IndexFinger = "VNHLRKI";
    public static final String LittleFinger = "VNHLRKP";
    public static final String RingFinger = "VNHLRKR";

    // for getting strings above
    private static boolean done = false;
    private void debug() {
        if (done) return;
        NSArray names = availableJointNames();
        for (int i = 0; i < names.count(); i++) {
logger.fine("joint key: " + names.objectAtIndex(i));
        }
        names = availableJointsGroupNames();
        for (int i = 0; i < names.count(); i++) {
logger.fine("group key: " + names.objectAtIndex(i));
        }
        done = true;
    }

    /** */
    public static class Convertible implements VNRequestConvertible<VNHumanHandPoseObservation, CGPoint[]> {

        /**
         * @param args 0: groupName, 1...: jointNames
         */
        @Override
        public CGPoint[] convert(VNHumanHandPoseObservation observation, Object... args) {
            String groupName = (String) args[0];
            String[] jointNames = Arrays.stream(Arrays.copyOfRange(args, 1, args.length)).map(String.class::cast).toArray(String[]::new);
//try { observation.debug(); } catch (Exception e) { e.printStackTrace(); }

            ObjCObjectByReference errorRef = new ObjCObjectByReference();
            NSDictionary recognizedPoints = observation.recognizedPointsForJointsGroupName_error(groupName, errorRef);
            NSError error = errorRef.getValueAs(NSError.class);
            if (error != null) {
                throw new IllegalStateException(error.description());
            }
            CGPoint[] imagePoints = new CGPoint[jointNames.length];
            IntStream.range(0, jointNames.length).forEach(i -> {
                VNRecognizedPoint point = Rococoa.cast(recognizedPoints.objectForKey(jointNames[i]), VNRecognizedPoint.class);
                imagePoints[i] = point.confidence() > 0 ? point.location() : null;
            });
            return imagePoints;
        }

        @Override
        public boolean isKindOfClass(NSObject object) {
            return object.isKindOfClass(VNHumanHandPoseObservation.CLASS);
        }

        @Override
        public VNHumanHandPoseObservation cast(NSObject object) {
            return Rococoa.cast(object, VNHumanHandPoseObservation.class);
        }
    }
}
