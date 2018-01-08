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

import java.util.ArrayList;

import com.team687.lib.kauailabs.sf2.quantity.IQuantity;

public class Timestamp implements IQuantity {

    public enum TimestampResolution {
	Second, Millisecond, Microsecond, Nanosecond
    };

    long timestamp;
    TimestampResolution resolution;

    public Timestamp() {
	this.timestamp = 0;
	this.resolution = TimestampResolution.Millisecond;
    }

    public Timestamp(Timestamp ts_copy) {
	this.timestamp = ts_copy.timestamp;
	this.resolution = ts_copy.resolution;
    }

    public Timestamp(long timestamp, TimestampResolution resolution) {
	this.timestamp = timestamp;
	this.resolution = resolution;
    }

    public Timestamp(double seconds, TimestampResolution resolution) {
	this.resolution = resolution;
	this.fromSeconds(seconds);
    }

    public long getTimestamp() {
	return this.timestamp;
    }

    public void setTimestamp(long new_timestamp) {
	this.timestamp = new_timestamp;
    }

    public void setResolution(TimestampResolution r) {
	this.resolution = r;
    }

    public TimestampResolution getResolution() {
	return this.resolution;
    }

    public static final long MILLISECONDS_PER_SECOND = 1000;
    public static final long MICROSECONDS_PER_SECOND = MILLISECONDS_PER_SECOND * 1000;
    public static final long NANOSECONDS_PER_SECOND = MICROSECONDS_PER_SECOND * 1000;
    public static final long NANOSECONDS_PER_MICROSECOND = 1000;
    public static final long MICROSECONDS_PER_MILLISECOND = 1000;
    public static final long NANOSECONDS_PER_MILLISECOND = NANOSECONDS_PER_MICROSECOND * 1000;

    public long getNanoseconds() {
	switch (resolution) {
	case Second:
	    return timestamp * NANOSECONDS_PER_SECOND;
	case Millisecond:
	    return timestamp * NANOSECONDS_PER_MILLISECOND;
	case Microsecond:
	    return timestamp * NANOSECONDS_PER_MICROSECOND;
	case Nanosecond:
	default:
	    return timestamp;
	}
    }

    public long getMicroseconds() {
	switch (resolution) {
	case Second:
	    return timestamp * MICROSECONDS_PER_SECOND;
	case Millisecond:
	    return timestamp * MICROSECONDS_PER_MILLISECOND;
	case Microsecond:
	default:
	    return timestamp;
	case Nanosecond:
	    return timestamp / NANOSECONDS_PER_MICROSECOND;
	}
    }

    public long getMilliseconds() {
	switch (resolution) {
	case Second:
	    return timestamp * MILLISECONDS_PER_SECOND;
	case Millisecond:
	default:
	    return timestamp;
	case Microsecond:
	    return timestamp / MICROSECONDS_PER_MILLISECOND;
	case Nanosecond:
	    return timestamp / NANOSECONDS_PER_MILLISECOND;
	}
    }

    public double getSeconds() {
	switch (resolution) {
	case Second:
	    return (double) timestamp;
	case Millisecond:
	default:
	    return ((double) timestamp) / MILLISECONDS_PER_SECOND;
	case Microsecond:
	    return ((double) timestamp) / MICROSECONDS_PER_SECOND;
	case Nanosecond:
	    return ((double) timestamp) / NANOSECONDS_PER_SECOND;
	}
    }

    public void fromSeconds(double seconds) {
	switch (resolution) {
	case Second:
	    timestamp = (long) seconds;
	    break;
	case Millisecond:
	default:
	    timestamp = (long) (seconds * MILLISECONDS_PER_SECOND);
	    break;
	case Microsecond:
	    timestamp = (long) (seconds * MICROSECONDS_PER_SECOND);
	    break;
	case Nanosecond:
	    timestamp = (long) (seconds * NANOSECONDS_PER_SECOND);
	    break;
	}
    }

    @Override
    public boolean getPrintableString(StringBuilder printable_string) {
	printable_string.append(timestamp);
	return true;
    }

    @Override
    public boolean getContainedQuantities(ArrayList<IQuantity> quantities) {
	return false;
    }

    @Override
    public boolean getContainedQuantityNames(ArrayList<String> quantity_names) {
	return false;
    }
}
