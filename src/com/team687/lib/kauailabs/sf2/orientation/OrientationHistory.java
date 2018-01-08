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

package com.team687.lib.kauailabs.sf2.orientation;

import java.util.ArrayList;

import com.team687.lib.kauailabs.sf2.quantity.IQuantity;
import com.team687.lib.kauailabs.sf2.quantity.Scalar;
import com.team687.lib.kauailabs.sf2.sensor.ISensorDataSource;
import com.team687.lib.kauailabs.sf2.sensor.ISensorDataSubscriber;
import com.team687.lib.kauailabs.sf2.sensor.ISensorInfo;
import com.team687.lib.kauailabs.sf2.sensor.SensorDataSourceInfo;
import com.team687.lib.kauailabs.sf2.time.ThreadsafeInterpolatingTimeHistory;
import com.team687.lib.kauailabs.sf2.time.Timestamp;
import com.team687.lib.kauailabs.sf2.time.TimestampedValue;
import com.team687.lib.kauailabs.sf2.units.Unit;

/**
 * The OrientationHistory class implements a timestamped history of orientation
 * data (e.g., from an IMU). The OrientationHistory is populated by data from a
 * "timestamped quaternion" sensor, such as the navX-MXP.
 * 
 * The OrientationHistory buffers the orientation data received over the most
 * current time period between "now" and the size of the time history, and
 * provides methods to retrieve orientation data in the form of
 * TimestampedQuaternion objects. These objects can be looked up based upon a
 * timestamp; if an exact match is found the object is returned direction;
 * otherwise if TimestampedQuaternion objects exist for the times before and
 * after the requested timestamp, a new TimestampedQuaterion object is created
 * via interpolation.
 * 
 * @author Scott
 */
public class OrientationHistory implements ISensorDataSubscriber {

    ISensorDataSource quat_sensor;
    ThreadsafeInterpolatingTimeHistory<TimestampedValue<Quaternion>> orientation_history;
    Scalar temp_s;
    int quaternion_quantity_index;
    int timestamp_quantity_index;
    TimestampedValue<Quaternion> temp_tsq;
    Timestamp system_timestamp;

    public final int MAX_ORIENTATION_HISTORY_LENGTH_NUM_SAMPLES = 1000;

    /**
     * Constructs an OrientationHistory object with a specified size. The
     * OrientationHistory registers for incoming data using the provided
     * ITimestampedQuaternionSensor object.
     * 
     * @param quat_sensor
     *            - the sensor to acquire TimestampedQuaternion objects from.
     * @param history_length_seconds
     *            - the length of the OrientationHistory, in seconds. The actual
     *            length of the OrientationHistory in number of objects is
     *            calculated internally by accessing the sensor's current update
     *            rate. <i>Note: if the sensor update rate is changed, after this
     *            constructor is invoked, the length of the history may no longer
     *            accurately reflect the originally-configured length.</i>
     * @param quat_sensor
     *            - the sensor to use as the source of TimestampedQuaternions
     *            contained in the Orientation History
     * @param history_length_seconds
     *            - the number of seconds the history will represent. This value may
     *            not be larger than @value #MAX_ORIENTATION_HISTORY_IN_SECONDS
     *            seconds.
     */
    public OrientationHistory(ISensorInfo quat_sensor, int history_length_num_samples) {

	this.quat_sensor = quat_sensor.getSensorDataSource();

	int index = 0;
	quaternion_quantity_index = -1;
	timestamp_quantity_index = -1;
	ArrayList<SensorDataSourceInfo> sensor_data_source_infos = new ArrayList<SensorDataSourceInfo>();
	quat_sensor.getSensorDataSource().getSensorDataSourceInfos(sensor_data_source_infos);
	for (SensorDataSourceInfo item : sensor_data_source_infos) {
	    if (item.getName().equalsIgnoreCase("Quaternion")) {
		quaternion_quantity_index = index;
	    }
	    if (item.getName().equalsIgnoreCase("Timestamp")) {
		timestamp_quantity_index = index;
	    }
	    index++;
	}

	if (quaternion_quantity_index == -1) {
	    throw new IllegalArgumentException("The provided ISensorInfo (quat_sensor) object"
		    + "must contain a SensorDataSourceInfo object named 'Quaternion'.");
	}

	if (history_length_num_samples > MAX_ORIENTATION_HISTORY_LENGTH_NUM_SAMPLES) {
	    history_length_num_samples = MAX_ORIENTATION_HISTORY_LENGTH_NUM_SAMPLES;
	}
	Quaternion default_quat = new Quaternion();
	TimestampedValue<Quaternion> default_ts_quat = new TimestampedValue<Quaternion>(default_quat);
	this.orientation_history = new ThreadsafeInterpolatingTimeHistory<TimestampedValue<Quaternion>>(default_ts_quat,
		history_length_num_samples, quat_sensor.getSensorTimestampInfo(),
		sensor_data_source_infos.get(quaternion_quantity_index).getName(),
		sensor_data_source_infos.get(quaternion_quantity_index).getQuantityUnits());

	this.quat_sensor.subscribe(this);

	temp_s = new Scalar();

	temp_tsq = new TimestampedValue<Quaternion>(new Quaternion());

	system_timestamp = new Timestamp();
    }

    /**
     * Reset the OrientationHistory, clearing all existing entries.
     * 
     * @param quat_curr
     */
    public void reset(TimestampedValue<Quaternion> quat_curr) {
	orientation_history.reset();
    }

    /**
     * Retrieves the most recently added Quaternion.
     * 
     * @return
     */
    public boolean getCurrentQuaternion(TimestampedValue<Quaternion> out) {
	return orientation_history.getMostRecent(out);
    }

    /**
     * Retrieves the TimestampedQuaterion at the specified sensor timestamp. If an
     * exact timestamp match occurs, a TimestampedQuaternion representing the actual
     * (measured) data is returned; otherwise a new interpolated
     * TimestampedQuaternion will be estimated, using the nearest
     * preceding/following TimestampedQuaternion and the requested timestamp's ratio
     * of time between them as its basis. If no exact match could be found or
     * interpolated value estimated, null is returned.
     * 
     * @param requested_timestamp
     *            - sensor timestamp to retrieve
     * @return TimestampedQuaternion at requested timestamp, or null.
     */
    public boolean getQuaternionAtTime(long requested_timestamp, TimestampedValue<Quaternion> out) {
	return orientation_history.get(requested_timestamp, out);
    }

    /**
     * Retrieves the yaw angle in degrees at the specified sensor timestamp.
     * <p>
     * Note that this value may be interpolated if a sample at the requested time is
     * not available.
     * 
     * @param requested_timestamp
     * @return Yaw angle (in degrees, range -180 to 180) at the requested timestamp.
     *         If a yaw angle at the specified timestamp could not be
     *         found/interpolated, the value INVALID_ANGLE (NaN) will be returned.
     */
    public float getYawDegreesAtTime(long requested_timestamp) {
	TimestampedValue<Quaternion> match = new TimestampedValue<Quaternion>(new Quaternion());
	if (getQuaternionAtTime(requested_timestamp, match)) {
	    match.getValue().getYawRadians(temp_s);
	    return temp_s.get() * Unit.Angle.Degrees.RADIANS_TO_DEGREES;
	} else {
	    return Float.NaN;
	}
    }

    /**
     * Retrieves the pitch angle in degrees at the specified sensor timestamp.
     * <p>
     * Note that this value may be interpolated if a sample at the requested time is
     * not available.
     * 
     * @param requested_timestamp
     * @return Pitch angle (in degrees, range -180 to 180) at the requested
     *         timestamp. If a pitch angle at the specified timestamp could not be
     *         found/interpolated, the value INVALID_ANGLE (NaN) will be returned.
     */
    public float getPitchDegreesAtTime(long requested_timestamp) {
	TimestampedValue<Quaternion> match = new TimestampedValue<Quaternion>(new Quaternion());
	if (getQuaternionAtTime(requested_timestamp, match)) {
	    match.getValue().getPitch(temp_s);
	    return temp_s.get() * Unit.Angle.Degrees.RADIANS_TO_DEGREES;
	} else {
	    return Float.NaN;
	}
    }

    /**
     * Retrieves the roll angle in degrees at the specified sensor timestamp.
     * <p>
     * Note that this value may be interpolated if a sample at the requested time is
     * not available.
     * 
     * @param requested_timestamp
     * @return Roll angle (in degrees, range -180 to 180) at the requested
     *         timestamp. If a roll angle at the specified timestamp could not be
     *         found/interpolated, the value INVALID_ANGLE (NaN) will be returned.
     */
    public float getRollDegreesAtTime(long requested_timestamp) {
	TimestampedValue<Quaternion> match = new TimestampedValue<Quaternion>(new Quaternion());
	if (getQuaternionAtTime(requested_timestamp, match)) {
	    match.getValue().getRoll(temp_s);
	    return temp_s.get() * Unit.Angle.Degrees.RADIANS_TO_DEGREES;
	} else {
	    return Float.NaN;
	}
    }

    @Override
    public void publish(IQuantity[] curr_values, Timestamp sys_timestamp) {
	Timestamp sensor_timestamp;
	if (timestamp_quantity_index != -1) {
	    sensor_timestamp = ((Timestamp) curr_values[timestamp_quantity_index]);
	} else {
	    sensor_timestamp = sys_timestamp;
	}
	Quaternion q = ((Quaternion) curr_values[quaternion_quantity_index]);
	temp_tsq.set(q, sensor_timestamp.getMilliseconds());
	orientation_history.add(temp_tsq);
    }

    public boolean writeToDirectory(String directory_path) {
	return orientation_history.writeToDirectory(directory_path);
    }

    public boolean writeToFile(String file_path) {
	return orientation_history.writeToFile(file_path);
    }

}
