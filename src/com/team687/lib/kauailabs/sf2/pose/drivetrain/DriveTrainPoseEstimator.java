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

import java.util.ArrayList;

import com.team687.lib.kauailabs.sf2.orientation.Quaternion;
import com.team687.lib.kauailabs.sf2.pose.Pose;
import com.team687.lib.kauailabs.sf2.quantity.IQuantity;
import com.team687.lib.kauailabs.sf2.quantity.Scalar;
import com.team687.lib.kauailabs.sf2.sensor.IProcessorInfo;
import com.team687.lib.kauailabs.sf2.sensor.ISensorDataSource;
import com.team687.lib.kauailabs.sf2.sensor.ISensorDataSubscriber;
import com.team687.lib.kauailabs.sf2.sensor.ISensorInfo;
import com.team687.lib.kauailabs.sf2.sensor.SensorDataSourceInfo;
import com.team687.lib.kauailabs.sf2.time.ThreadsafeInterpolatingTimeHistory;
import com.team687.lib.kauailabs.sf2.time.Timestamp;
import com.team687.lib.kauailabs.sf2.time.TimestampInfo;
import com.team687.lib.kauailabs.sf2.time.TimestampedValue;

public class DriveTrainPoseEstimator implements ISensorDataSubscriber {

    public final int MAX_POSE_HISTORY_LENGTH_NUM_SAMPLES = 1000;

    ISensorDataSource quat_sensor;
    int quaternion_quantity_index;
    ThreadsafeInterpolatingTimeHistory<TimestampedValue<Pose>> pose_history;
    IDriveTrainKinematics drive_model;
    ISensorDataSource[] driveWheelDistanceSensors;
    int drive_wheel_distance_quantity_index;
    ISensorDataSource[] steerWheelAngleSensors;
    int steer_wheel_angle_quantity_index;
    ISensorDataSource[] driveMotorCurrentSensors;
    int drive_motor_current_quantity_index;

    IQuantity[][] drive_wheel_distance_sensor_quantities;
    IQuantity[][] steer_wheel_angle_sensor_quantities;
    IQuantity[][] drive_motor_current_sensor_quantities;

    Timestamp drive_wheel_distance_sensor_timestamp;
    Timestamp steer_wheel_angle_sensor_timestamp;
    Timestamp drive_motor_current_sensor_timestamp;

    volatile ArrayList<TimestampedValue<Scalar>> curr_drive_wheel_distance_inches;
    volatile ArrayList<TimestampedValue<Scalar>> curr_drive_wheel_distance_delta_inches;
    volatile ArrayList<TimestampedValue<Scalar>> curr_drive_motor_current_amps;
    ArrayList<TimestampedValue<Scalar>> last_drive_wheel_distance_inches;
    volatile ArrayList<TimestampedValue<Scalar>> curr_steer_wheel_angle_degrees;
    volatile TimestampedValue<Pose> curr_pose;
    IProcessorInfo processor_info;

    public DriveTrainPoseEstimator(IProcessorInfo processor_info, TimestampInfo quat_sensor_ts_info,
	    IDriveTrainKinematics drive_model, ISensorInfo quat_sensor_info, int quaternion_quantity_index,
	    ISensorInfo[] drive_wheel_distance_sensor_infos, int drive_wheel_distance_quantity_index,
	    ISensorInfo[] steer_wheel_angle_sensor_infos, int steer_wheel_angle_quantity_index,
	    ISensorInfo[] drive_motor_current_sensor_infos, int drive_motor_current_quantity_index,
	    int history_length_num_samples) {
	if ((drive_model == null) || (quat_sensor == null) || (driveWheelDistanceSensors == null)
		|| (history_length_num_samples <= 0)) {
	    throw new IllegalArgumentException("Invalid input parameter.");
	}

	if (drive_model.getDriveTrainParameters().getNumDriveWheels() != driveWheelDistanceSensors.length) {
	    throw new IllegalArgumentException("Size of wheel encoders array does not match "
		    + "the number of drive wheels specified by the DriveTrainKinematics class.");
	}

	for (int i = 0; i < driveWheelDistanceSensors.length; i++) {
	    if (driveWheelDistanceSensors[i] == null) {
		throw new IllegalArgumentException("One or more driveWheelEncoders is null.");
	    }
	}

	this.processor_info = processor_info;
	this.quat_sensor = quat_sensor_info.getSensorDataSource();
	this.quaternion_quantity_index = quaternion_quantity_index;

	drive_wheel_distance_sensor_timestamp = drive_wheel_distance_sensor_infos[0].getSensorTimestampInfo()
		.getDefaultTimestamp();
	steer_wheel_angle_sensor_timestamp = steer_wheel_angle_sensor_infos[0].getSensorTimestampInfo()
		.getDefaultTimestamp();
	drive_motor_current_sensor_timestamp = drive_motor_current_sensor_infos[0].getSensorTimestampInfo()
		.getDefaultTimestamp();

	ISensorDataSource[] data_source_array_template = new ISensorDataSource[0];

	this.driveWheelDistanceSensors = new ISensorDataSource[drive_wheel_distance_sensor_infos.length];
	ArrayList<IQuantity[]> drive_wheel_distance_sensor_quantities_array = new ArrayList<IQuantity[]>();
	for (int i = 0; i < drive_wheel_distance_sensor_infos.length; i++) {
	    this.driveWheelDistanceSensors[i] = drive_wheel_distance_sensor_infos[i].getSensorDataSource();
	    ArrayList<SensorDataSourceInfo> sensor_data_source_infos = new ArrayList<SensorDataSourceInfo>();
	    driveWheelDistanceSensors[i].getSensorDataSourceInfos(sensor_data_source_infos);
	    ArrayList<IQuantity> quantity_list = new ArrayList<IQuantity>();
	    SensorDataSourceInfo.getQuantityArray(
		    (SensorDataSourceInfo[]) sensor_data_source_infos.toArray(data_source_array_template),
		    quantity_list);
	    drive_wheel_distance_sensor_quantities_array
		    .add((IQuantity[]) quantity_list.toArray(new IQuantity[quantity_list.size()]));
	}
	drive_wheel_distance_sensor_quantities = new IQuantity[drive_wheel_distance_sensor_infos.length][drive_wheel_distance_sensor_quantities_array
		.get(0).length];
	drive_wheel_distance_sensor_quantities = drive_wheel_distance_sensor_quantities_array
		.toArray(drive_wheel_distance_sensor_quantities);
	this.drive_wheel_distance_quantity_index = drive_wheel_distance_quantity_index;

	this.steerWheelAngleSensors = new ISensorDataSource[steer_wheel_angle_sensor_infos.length];
	ArrayList<IQuantity[]> steer_wheel_angle_sensor_quantities_array = new ArrayList<IQuantity[]>();
	for (int i = 0; i < steer_wheel_angle_sensor_infos.length; i++) {
	    this.steerWheelAngleSensors[i] = steer_wheel_angle_sensor_infos[i].getSensorDataSource();
	    ArrayList<SensorDataSourceInfo> sensor_data_source_infos = new ArrayList<SensorDataSourceInfo>();
	    steerWheelAngleSensors[i].getSensorDataSourceInfos(sensor_data_source_infos);
	    ArrayList<IQuantity> quantity_list = new ArrayList<IQuantity>();
	    SensorDataSourceInfo.getQuantityArray(
		    (SensorDataSourceInfo[]) sensor_data_source_infos.toArray(data_source_array_template),
		    quantity_list);
	    steer_wheel_angle_sensor_quantities_array
		    .add((IQuantity[]) quantity_list.toArray(new IQuantity[quantity_list.size()]));
	}
	steer_wheel_angle_sensor_quantities = new IQuantity[steer_wheel_angle_sensor_infos.length][steer_wheel_angle_sensor_quantities_array
		.get(0).length];
	steer_wheel_angle_sensor_quantities = steer_wheel_angle_sensor_quantities_array
		.toArray(steer_wheel_angle_sensor_quantities);
	this.steer_wheel_angle_quantity_index = steer_wheel_angle_quantity_index;

	driveMotorCurrentSensors = new ISensorDataSource[drive_motor_current_sensor_infos.length];
	ArrayList<IQuantity[]> drive_motor_current_sensor_quantities_array = new ArrayList<IQuantity[]>();
	for (int i = 0; i < drive_motor_current_sensor_infos.length; i++) {
	    this.driveMotorCurrentSensors[i] = drive_motor_current_sensor_infos[i].getSensorDataSource();
	    ArrayList<SensorDataSourceInfo> sensor_data_source_infos = new ArrayList<SensorDataSourceInfo>();
	    driveMotorCurrentSensors[i].getSensorDataSourceInfos(sensor_data_source_infos);
	    ArrayList<IQuantity> quantity_list = new ArrayList<IQuantity>();
	    SensorDataSourceInfo.getQuantityArray(
		    (SensorDataSourceInfo[]) sensor_data_source_infos.toArray(data_source_array_template),
		    quantity_list);
	    drive_motor_current_sensor_quantities_array
		    .add((IQuantity[]) quantity_list.toArray(new IQuantity[quantity_list.size()]));
	}
	drive_motor_current_sensor_quantities = new IQuantity[drive_motor_current_sensor_infos.length][drive_motor_current_sensor_quantities_array
		.get(0).length];
	drive_motor_current_sensor_quantities = drive_motor_current_sensor_quantities_array
		.toArray(steer_wheel_angle_sensor_quantities);
	this.drive_motor_current_quantity_index = drive_motor_current_quantity_index;

	Scalar temp = new Scalar();
	curr_drive_wheel_distance_inches = new ArrayList<TimestampedValue<Scalar>>();
	curr_drive_wheel_distance_delta_inches = new ArrayList<TimestampedValue<Scalar>>();
	last_drive_wheel_distance_inches = new ArrayList<TimestampedValue<Scalar>>();
	for (int i = 0; i < driveWheelDistanceSensors.length; i++) {
	    curr_drive_wheel_distance_inches.add(new TimestampedValue<Scalar>(temp));
	    curr_drive_wheel_distance_delta_inches.add(new TimestampedValue<Scalar>(temp));
	    last_drive_wheel_distance_inches.add(new TimestampedValue<Scalar>(temp));
	}
	if (steerWheelAngleSensors != null) {
	    curr_steer_wheel_angle_degrees = new ArrayList<TimestampedValue<Scalar>>();
	    for (int i = 0; i < steerWheelAngleSensors.length; i++) {
		curr_steer_wheel_angle_degrees.add(new TimestampedValue<Scalar>(temp));
	    }
	}
	if (driveMotorCurrentSensors != null) {
	    curr_drive_motor_current_amps = new ArrayList<TimestampedValue<Scalar>>();
	    for (int i = 0; i < driveMotorCurrentSensors.length; i++) {
		curr_drive_motor_current_amps.add(new TimestampedValue<Scalar>(temp));
	    }
	}

	curr_pose = new TimestampedValue<Pose>(new Pose());

	if (history_length_num_samples > MAX_POSE_HISTORY_LENGTH_NUM_SAMPLES) {
	    history_length_num_samples = MAX_POSE_HISTORY_LENGTH_NUM_SAMPLES;
	}
	Pose default_pose = new Pose();
	TimestampedValue<Pose> default_ts_pose = new TimestampedValue<Pose>(default_pose);
	this.pose_history = new ThreadsafeInterpolatingTimeHistory<TimestampedValue<Pose>>(default_ts_pose,
		history_length_num_samples, quat_sensor_ts_info, "Pose", Pose.getUnits());
	this.drive_model = drive_model;

	this.quat_sensor.subscribe(this);
    }

    public void reset() {
	synchronized (this) {
	    pose_history.reset();
	    // Clear Encoder Counts
	    for (int i = 0; i < driveWheelDistanceSensors.length; i++) {
		driveWheelDistanceSensors[i].reset(drive_wheel_distance_quantity_index);
		last_drive_wheel_distance_inches.get(i).getValue().set(0);
	    }
	}
    }

    public boolean getCurrentPose(TimestampedValue<Pose> out) {
	return pose_history.getMostRecent(out);
    }

    public boolean getPoseAtTimestamp(long timestamp, TimestampedValue<Pose> out) {
	return pose_history.get(timestamp, out);
    }

    @Override
    public void publish(IQuantity[] quantities, Timestamp timestamp) {
	Quaternion quat = (Quaternion) quantities[quaternion_quantity_index];
	TimestampedValue<Quaternion> tq = new TimestampedValue<Quaternion>(quat, timestamp.getMilliseconds());

	for (int i = 0; i < driveWheelDistanceSensors.length; i++) {
	    driveWheelDistanceSensors[i].getCurrent(drive_wheel_distance_sensor_quantities[i],
		    drive_wheel_distance_sensor_timestamp);
	    curr_drive_wheel_distance_inches.get(i).set(
		    (Scalar) (drive_wheel_distance_sensor_quantities[i][drive_wheel_distance_quantity_index]),
		    drive_wheel_distance_sensor_timestamp.getMilliseconds());
	    curr_drive_wheel_distance_delta_inches.get(i).getValue()
		    .set(curr_drive_wheel_distance_inches.get(i).getValue().get()
			    - last_drive_wheel_distance_inches.get(i).getValue().get());
	}

	if (steerWheelAngleSensors != null) {
	    for (int i = 0; i < steerWheelAngleSensors.length; i++) {
		steerWheelAngleSensors[i].getCurrent(steer_wheel_angle_sensor_quantities[i],
			steer_wheel_angle_sensor_timestamp);
		curr_steer_wheel_angle_degrees.get(i).set(
			(Scalar) (steer_wheel_angle_sensor_quantities[i][steer_wheel_angle_quantity_index]),
			steer_wheel_angle_sensor_timestamp.getMilliseconds());
	    }
	}

	if (driveMotorCurrentSensors != null) {
	    for (int i = 0; i < driveMotorCurrentSensors.length; i++) {
		driveMotorCurrentSensors[i].getCurrent(drive_motor_current_sensor_quantities[i],
			drive_motor_current_sensor_timestamp);
		curr_drive_motor_current_amps.get(i).set(
			(Scalar) (drive_motor_current_sensor_quantities[i][drive_motor_current_quantity_index]),
			drive_motor_current_sensor_timestamp.getMilliseconds());
	    }
	}

	if (pose_history.getValidSampleCount() > 0) {
	    TimestampedValue<Pose> tp = new TimestampedValue<Pose>();
	    if (getCurrentPose(tp)) {
		Timestamp processor_timestamp = new Timestamp();
		processor_info.getProcessorTimestamp(processor_timestamp);
		if (drive_model.step(processor_timestamp, tp, tq, curr_drive_wheel_distance_delta_inches,
			curr_steer_wheel_angle_degrees, curr_drive_motor_current_amps, curr_pose)) {
		    pose_history.add(curr_pose);
		} else {
		    System.out.println("Error invoking DriveTrainKinematics method.");
		}
	    }
	} else {
	    /*
	     * Pose history is empty. Add a new pose (using latest quaternion).
	     */
	    /* The x/y offset values should default to their reset value. */
	    TimestampedValue<Pose> tp = new TimestampedValue<Pose>(new Pose(tq.getValue()));
	    pose_history.add(tp);
	}

	for (int i = 0; i < driveWheelDistanceSensors.length; i++) {
	    last_drive_wheel_distance_inches.get(i).getValue()
		    .set(curr_drive_wheel_distance_inches.get(i).getValue().get());
	}
    }
}
