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

package com.team687.lib.kauailabs.sf2.sensor;

import java.util.ArrayList;

import com.team687.lib.kauailabs.sf2.quantity.IQuantity;
import com.team687.lib.kauailabs.sf2.time.Timestamp;

/**
 * The ITimestampedQuaternionSensor interface should be implemented by any
 * sensor which generates timestamped Quaternions (e.g., the navX-MXP).
 * 
 * @author Scott
 *
 */
public interface ISensorDataSource {
    /**
     * Subscribes the provided subscriber object for callbacks whenever new
     * TimestampedQuaternion data is received by the sensor.
     * 
     * @param subscriber
     *            - object implementing the ISensorDataSubscriber<T> interface which
     *            will be called back whenever new data arrives.
     * @return true if registration was successful; false otherwise.
     */
    boolean subscribe(ISensorDataSubscriber subscriber);

    /**
     * Unsubscribes the previously-registered subscriber object for callbacks
     * whenever new Sensor Data is received by the sensor. The subscriber should
     * have been previously registered for callbacks via the subscribe() method.
     * 
     * @param subscriber
     *            - object implementing the ISensorDataSubscriber<T> interface,
     *            which up success will no longer be called back whenever new data
     *            arrives.
     * @return true if unsubscription was successful; false otherwise.
     */
    boolean unsubscribe(ISensorDataSubscriber subscriber);

    /**
     * Retrieves the most recently-received Sensor Data value.
     * 
     * @param tq
     * @return true if valid Sensor was received. false if data is invalid (e.g.,
     *         the sensor is no longer connected).
     */
    boolean getCurrent(IQuantity[] quantities, Timestamp curr_ts);

    /**
     * For those quantities which can be reset to their default (e.g., an IMU Yaw,
     * or an encoder counter), this method will cause the reset to occur.
     * 
     * If the quantity is reset successful, true is returned. If the quantity could
     * not be reset, or if the quantity does not support resetting, false is
     * returned.
     * 
     * @param quantity_index
     * @return
     */
    boolean reset(int quantity_index);

    void getSensorDataSourceInfos(ArrayList<SensorDataSourceInfo> out);

}
