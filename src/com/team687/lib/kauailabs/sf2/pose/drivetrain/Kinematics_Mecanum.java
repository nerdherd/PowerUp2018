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

public class Kinematics_Mecanum implements IDriveTrainKinematics {

    TimestampedValue<Quaternion> last_quat;
    DriveTrainParameters drive_params;
    float fwdMatrix[][];

    final int X = 0;
    final int Y = 1;
    final int ROT = 2;

    final int NUM_WHEELS = 4;

    float temp_distance_deltas[];
    TimestampedValue<Quaternion> q_diff_temp;
    float enc_based_pose_change[];
    Scalar temp;

    public Kinematics_Mecanum(DriveTrainParameters drive_params) {

	this.drive_params = drive_params;

	if (this.drive_params.getNumDriveWheels() != NUM_WHEELS) {
	    throw new IllegalArgumentException("MecanumKinematics requires" + "exactly 4 wheels be used.");
	}

	this.last_quat = new TimestampedValue<Quaternion>(new Quaternion());

	float wheelRadius = (float) this.drive_params.getDriveWheelDiameterInches() / 2;
	// Calculate Rotational Coefficient
	float cRotK = (float) (((this.drive_params.getWidthInches() + this.drive_params.getLengthInches()) / 2)
		/ wheelRadius);

	/* Note that this matrix assumes all rollers are at 45 degrees. */
	fwdMatrix = new float[][] { { +1 / 4, +1 / 4, +1 / 4, +1 / 4 }, { -1 / 4, +1 / 4, -1 / 4, +1 / 4 },
		{ -1 / (4 * cRotK), -1 / (4 * cRotK), +1 / (4 * cRotK), +1 / (4 * cRotK) } };

	/* Allocate memory for working variables. */
	this.enc_based_pose_change = new float[3];
	this.q_diff_temp = new TimestampedValue<Quaternion>(new Quaternion());
	this.temp_distance_deltas = new float[NUM_WHEELS];
	temp = new Scalar();
    }

    @Override
    public DriveTrainParameters getDriveTrainParameters() {
	return this.drive_params;
    }

    @Override
    /* Note: Input drive/steer wheel are in encoder ticks. */
    /*
     * Note: Input drive/steer wheel values are ordered from left front corner,
     * increasing clockwise
     */
    /*
     * Return: Returns a TimestampedPose object representing the change in pose
     * since pose_last.
     */
    /*
     * Note: the individual drive/steer wheel velocities are assumed to be at the
     * same timestamp
     */
    /* as the current TimestampedQuaternion. */
    public boolean step(Timestamp system_timestamp, TimestampedValue<Pose> pose_last,
	    TimestampedValue<Quaternion> quat_curr, List<TimestampedValue<Scalar>> drive_wheel_distance_delta_curr,
	    List<TimestampedValue<Scalar>> steer_wheel_angle_degrees_curr,
	    List<TimestampedValue<Scalar>> drive_motor_current_amps_curr, TimestampedValue<Pose> pose_curr_out) {

	Quaternion.difference(quat_curr.getValue(), pose_last.getValue().getOrientation(), q_diff_temp.getValue());
	long delta_t = quat_curr.getTimestamp() - pose_last.getTimestamp();
	q_diff_temp.set(q_diff_temp.getValue(), delta_t);

	for (int i = 0; i < NUM_WHEELS; i++) {
	    temp_distance_deltas[i] = drive_wheel_distance_delta_curr.get(i).getValue().get();
	}
	mecanumForwardKinematics(temp_distance_deltas, enc_based_pose_change);

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

    /* Note 1/4 term implies equal weight distribution across all four wheels */
    /*
     * Note that all roller bearings are assumed to have equivalent friction
     * characteristics
     */
    /* Note output body velocities are in same units as wheel_velocities_in, */
    /* which is to say enc_ticks/delta_t. */
    /* Note: Input values are ordered from left front corner, clockwise */
    /*
     * Note: Output values are ordered LinearX (Strafe), LinearY (Forward), RotZ
     * (Rotate)
     */
    void mecanumForwardKinematics(float wheel_velocities_in[], float body_velocity_out[]) {
	for (int i = 0; i < 3; i++) {
	    body_velocity_out[i] = 0;
	    for (int wheel = 0; wheel < 4; wheel++) {
		body_velocity_out[i] += wheel_velocities_in[wheel] * fwdMatrix[i][wheel];
	    }
	}
    }
}
