/*
 * Copyright (c) 2022 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package org.rococoa.cocoa.vision;

import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.util.Arrays;
import java.util.stream.IntStream;

import org.rococoa.ObjCClass;
import org.rococoa.ObjCObjectByReference;
import org.rococoa.Rococoa;
import org.rococoa.cocoa.coregraphics.CGPoint;
import org.rococoa.cocoa.foundation.NSArray;
import org.rococoa.cocoa.foundation.NSDictionary;
import org.rococoa.cocoa.foundation.NSError;
import org.rococoa.cocoa.foundation.NSObject;

import static java.lang.System.getLogger;


/**
 * An observation that provides the body points the analysis recognized.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2022-10-16 nsano initial version <br>
 */
public abstract class VNHumanBodyPoseObservation extends VNRecognizedPointsObservation {

    static {
        VisionLibrary.library.toString();
    }

    private static final Logger logger = getLogger(VNHumanBodyPoseObservation.class.getName());

    private static final _Class CLASS = Rococoa.createClass("VNHumanBodyPoseObservation", _Class.class);

    private interface _Class extends ObjCClass {
        VNHumanBodyPoseObservation alloc();
    }

    public abstract VNHumanBodyPoseObservation init();

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
    public abstract NSDictionary recognizedPointsForJointsGroupName_error(String /* VNHumanBodyPoseObservationJointsGroupName */ jointsGroupName, ObjCObjectByReference/*NSError*/ error);

    public static final String Nose = "Nose";
    public static final String LeftEye = "left_eye_joint";
    public static final String RightEye = "right_eye_joint";
    public static final String LeftEar = "left_ear_joint";
    public static final String RightEar = "right_ear_joint";
    public static final String LeftShoulder = "left_shoulder_1_joint";
    public static final String RightShoulder = "right_shoulder_1_joint";
    public static final String Neck = "neck_1_joint";
    public static final String LeftElbow = "left_forearm_joint";
    public static final String RightElbow = "right_forearm_joint";
    public static final String LeftWrist = "LeftWrist";
    public static final String RightWrist = "RightWrist";
    public static final String LeftHip = "left_upLeg_joint";
    public static final String RightHip = "right_upLeg_joint";
    public static final String Root = "root";
    public static final String LeftKnee = "left_leg_joint";
    public static final String RightKnee = "right_leg_joint";
    public static final String LeftAnkle = "left_foot_joint";
    public static final String RightAnkle = "right_foot_joint";

    public static final String RecognizedPointGroupKeyAll = "VNIPOAll";
    public static final String BodyLandmarkRegionKeyFace = "VNBLKFACE";
    public static final String BodyLandmarkRegionKeyTorso = "VNBLKTORSO";
    public static final String BodyLandmarkRegionKeyLeftArm = "VNBLKLARM";
    public static final String BodyLandmarkRegionKeyRightArm = "VNBLKRARM";
    public static final String BodyLandmarkRegionKeyLeftLeg = "VNBLKLLEG";
    public static final String BodyLandmarkRegionKeyRightLeg = "VNBLKRLEG";

    // for getting strings above
    private static boolean done = false;
    private void debug() {
        if (done) return;
        NSArray names = availableJointNames();
        for (int i = 0; i < names.count(); i++) {
logger.log(Level.DEBUG, "joint key: " + names.objectAtIndex(i));
        }
        names = availableJointsGroupNames();
        for (int i = 0; i < names.count(); i++) {
logger.log(Level.DEBUG, "group key: " + names.objectAtIndex(i));
        }
        done = true;
    }

    public static class Convertible implements VNRequestConvertible<VNHumanBodyPoseObservation, CGPoint[]> {

        /**
         * @param args 0: groupName, 1...: torsoJointNames
         */
        @Override
        public CGPoint[] convert(VNHumanBodyPoseObservation observation, Object... args) {
            String groupName = (String) args[0];
            // Torso joint names in a clockwise ordering.
            String[] torsoJointNames = Arrays.stream(Arrays.copyOfRange(args, 1, args.length)).map(String.class::cast).toArray(String[]::new);
//observation.debug();

            ObjCObjectByReference errorRef = new ObjCObjectByReference();
            NSDictionary recognizedPoints = observation.recognizedPointsForJointsGroupName_error(groupName, errorRef);
            NSError error = errorRef.getValueAs(NSError.class);
            if (error != null) {
                throw new IllegalStateException(error.description());
            }
            CGPoint[] imagePoints = new CGPoint[torsoJointNames.length];
            IntStream.range(0, torsoJointNames.length).forEach(i -> {
                VNRecognizedPoint point = Rococoa.cast(recognizedPoints.objectForKey(torsoJointNames[i]), VNRecognizedPoint.class);
                imagePoints[i] = point.confidence() > 0 ? point.location() : null;
            });
            return imagePoints;
        }

        @Override
        public boolean isKindOfClass(NSObject object) {
            return object.isKindOfClass(VNHumanBodyPoseObservation.CLASS);
        }

        @Override
        public VNHumanBodyPoseObservation cast(NSObject object) {
            return Rococoa.cast(object, VNHumanBodyPoseObservation.class);
        }
    }
}
