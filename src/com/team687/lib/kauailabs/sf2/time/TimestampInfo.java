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

package com.team687.lib.kauailabs.sf2.time;

public class TimestampInfo {

    public enum Scope {
	Sensor, /* Timestamp is unique to this sensor. */
	Processor, /* Timestamp is unique to the sensor's Host Processor. */
	SynchNetwork /* Timestamp is network-synchronized. */
    };

    public enum Basis {
	Epoch, /* Timestamp is num of nanoseconds since Jan. 1, 1970 (UTC) */
	SinceLastReboot /*
			 * Timestamp is num of nanoseconds since last sensor/processor reboot.
			 */
    };

    Basis basis;
    Scope scope;
    double resolution_secs;
    double accuracy_secs; /* +/- */
    double drift_secs_per_hour;
    double average_latency_secs;
    Timestamp default_timestamp;

    public TimestampInfo(Scope scope, Basis basis, double resolution_secs, double accuracy_secs,
	    double drift_secs_per_hour, double average_latency_secs, Timestamp default_timestamp) {
	this.scope = scope;
	this.basis = basis;
	this.resolution_secs = resolution_secs;
	this.accuracy_secs = accuracy_secs;
	this.drift_secs_per_hour = drift_secs_per_hour;
	this.default_timestamp = default_timestamp;
	this.average_latency_secs = average_latency_secs;
    }

    public Scope getScope() {
	return scope;
    }

    public Basis getBasis() {
	return basis;
    }

    public double getTimestampResolutionSecs() {
	return resolution_secs;
    }

    public double getTimestampAccuracyPlusMinusSecs() {
	return accuracy_secs;
    }

    public double getTimestampDriftSecsPerHour() {
	return drift_secs_per_hour;
    }

    public double getAverageLatencySecs() {
	return average_latency_secs;
    }

    public Timestamp getDefaultTimestamp() {
	return default_timestamp;
    }
}
