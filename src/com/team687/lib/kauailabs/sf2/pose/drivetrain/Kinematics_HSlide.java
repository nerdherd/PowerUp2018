/* ============================================
SF2 source code is placed under the MIT license
Copyright (c) 2017 Kauai Labs

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
===============================================
*/

package com.team687.lib.kauailabs.sf2.pose.drivetrain;

import java.util.List;

import com.team687.lib.kauailabs.sf2.orientation.Quaternion;
import com.team687.lib.kauailabs.sf2.pose.Pose;
import com.team687.lib.kauailabs.sf2.quantity.Scalar;
import com.team687.lib.kauailabs.sf2.time.Timestamp;
import com.team687.lib.kauailabs.sf2.time.TimestampedValue;

public class Kinematics_HSlide implements IDriveTrainKinematics {

    TimestampedValue<Quaternion> last_quat;
    DriveTrainParameters drive_params;

    final int X = 0;
    final int Y = 1;
    final int ROT = 2;

    static int LF_WHEEL = 0;
    static int RF_WHEEL = 1;
    static int RR_WHEEL = 2;
    static int LR_WHEEL = 3;
    static int MIDDLE_WHEEL = 4;

    static int NUM_WHEELS = 5;

    TimestampedValue<Quaternion> q_diff_temp;
    float enc_based_pose_change[];
    Scalar temp;

    /*
     * HSlideKinematics requires 5 wheels w/encoders: 0 - Left Front wheel 1 - Right
     * Front wheel 2 - Right Rear wheel 3 - Left Rear wheel 4 - Middle (Slide) wheel
     */
    public Kinematics_HSlide(DriveTrainParameters drive_params) {

	this.last_quat = new TimestampedValue<Quaternion>(new Quaternion());
	this.drive_params = drive_params;
	/* Allocate memory for working variables. */
	this.enc_based_pose_change = new float[3];
	this.q_diff_temp = new TimestampedValue<Quaternion>(new Quaternion());

	if (this.drive_params.getNumDriveWheels() != NUM_WHEELS) {
	    throw new IllegalArgumentException("HSlideKinematics requires" + "exactly 5 wheels be used.");
	}
	temp = new Scalar();
    }

    @Override
    public DriveTrainParameters getDriveTrainParameters() {
	return this.drive_params;
    }

    @Override
    /*
     * Note: Input drive wheel distances are in units of inches since the last time
     * step()
     */
    /* was invoked. */
    /*
     * Note: Input drive/steer wheel values are ordered from left front corner,
     * increasing clockwise
     */
    /*
     * Return: Returns a TimestampedPose object representing the change in pose
     * since pose_last.
     */
    /*
     * Note: the individual drive/steer wheel values are assumed to be measured
     * coincident with the
     */
    /* current TimestampedQuaternion. */
    public boolean step(Timestamp system_timestamp, TimestampedValue<Pose> pose_last,
	    TimestampedValue<Quaternion> quat_curr, List<TimestampedValue<Scalar>> drive_wheel_distance_delta_curr,
	    List<TimestampedValue<Scalar>> steer_wheel_angle_degrees_curr,
	    List<TimestampedValue<Scalar>> drive_motor_current_amps_curr, TimestampedValue<Pose> pose_curr_out) {

	Quaternion.difference(quat_curr.getValue(), pose_last.getValue().getOrientation(), q_diff_temp.getValue());
	long delta_t = quat_curr.getTimestamp() - pose_last.getTimestamp();
	q_diff_temp.set(q_diff_temp.getValue(), delta_t);

	enc_based_pose_change[X] = drive_wheel_distance_delta_curr.get(MIDDLE_WHEEL).getValue().get();
	enc_based_pose_change[Y] = (drive_wheel_distance_delta_curr.get(LF_WHEEL).getValue().get()
		+ drive_wheel_distance_delta_curr.get(RF_WHEEL).getValue().get()
		+ drive_wheel_distance_delta_curr.get(RR_WHEEL).getValue().get()
		+ drive_wheel_distance_delta_curr.get(LR_WHEEL).getValue().get()) / 4;

	q_diff_temp.getValue().getYawRadians(temp);
	double theta = temp.get();

	/*
	 * Adjust X/Y offset deltas, currently in body frame, to be in World frame - by
	 * rotating these values by the current body rotation
	 */

	enc_based_pose_change[X] *= (float) Math.sin(theta);
	enc_based_pose_change[Y] *= (float) Math.cos(theta);

	/* Use Encoder-derived values for Translational Motion */
	pose_curr_out.getValue().addOffsets(enc_based_pose_change[X], enc_based_pose_change[Y]);

	/* Use IMU-derived values for orientation. */
	pose_curr_out.getValue().getOrientation().copy(quat_curr.getValue());

	return true;
    }
}
