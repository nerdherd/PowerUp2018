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

import com.team687.lib.kauailabs.sf2.interpolation.IInterpolate;
import com.team687.lib.kauailabs.sf2.interpolation.IValueInterpolator;
import com.team687.lib.kauailabs.sf2.quantity.ICopy;
import com.team687.lib.kauailabs.sf2.quantity.IQuantity;
import com.team687.lib.kauailabs.sf2.time.ITimestampedValue;

/**
 * The TimestampedValue generic class encapsulates a value class via generic,
 * and adds additional variables and methods enabling a time-history of values
 * to be constructed.
 * 
 * @author Scott
 */
public class TimestampedValue<T extends ICopy<T> & IInterpolate<T> & IQuantity>
	implements ITimestampedValue, ICopy<TimestampedValue<T>>, IValueInterpolator<TimestampedValue<T>> {

    private T value;
    private long timestamp;
    byte flags;

    final static byte valid_flag = 0x01;
    final static byte interpolated_flag = 0x02;

    public TimestampedValue() {
	timestamp = 0;
	flags = 0;
    }

    /**
     * Default constructor for a TimestampedValue<T>; initializes all values to
     * reasonable defaults.
     */
    public TimestampedValue(T value) {
	this.value = value.instantiate_copy();
	timestamp = 0;
	flags = 0;
    }

    /**
     * Constructor allowing a TimestampedValue<T> to be created from a T object and
     * a timestamp.
     * 
     * @param src
     *            - source T object
     * @param timestamp
     *            - timestamp representing the time at which the source value is
     *            valid.
     */
    public TimestampedValue(T src, long timestamp) {
	this.value = src.instantiate_copy();
	setTimestamp(timestamp);
	this.flags = 0;
    }

    /**
     * Copy constructor; initializes all values to that of the source
     * TimestampedValue<T>.
     * 
     * @param src
     *            - source TimestampedValue<T>
     */
    public TimestampedValue(TimestampedValue<T> src) {
	this.value = src.getValue().instantiate_copy();
	this.timestamp = src.timestamp;
	this.flags = src.flags;
    }

    /**
     * Returns the timestamp for this TimestampedValue.
     */
    public long getTimestamp() {
	return timestamp;
    }

    public void setTimestamp(long timestamp) {
	this.timestamp = timestamp;
    }

    /**
     * Initalizes this TimestampedValue to be equal to the source TimestampedValue
     * 
     * @param src
     *            - source TimestampedValue
     */
    public void set(TimestampedValue<T> src) {
	this.value.copy(src.value);
	this.timestamp = src.timestamp;
	this.flags = src.flags;
    }

    /**
     * Initializes this TimestampedValue to be equal to the source value object and
     * a timestamp.
     * 
     * @param src
     *            - source Quaternion
     * @param timestamp
     *            - timestamp representing the time at which the source value object
     *            is valid.
     */
    public void set(T src, long timestamp) {
	this.value.copy(src);
	this.timestamp = timestamp;
	this.flags = valid_flag; /* Interpolated = false */
    }

    public T getValue() {
	return value;
    }

    /**
     * If true, this TimestampedValue<T> was interpolated, otherwise it is an actual
     * (measured) TimestampedValue<T>
     */
    @Override
    public boolean getInterpolated() {
	return ((flags & interpolated_flag) != 0);
    }

    /**
     * Modifies this TimestampedValue<T>'s interpolated state; if true, this
     * TimestampedValue<T> was interpolated; otherwise, it is an actual (measured)
     * TimestampedValue<T>.
     */
    @Override
    public void setInterpolated(boolean interpolated) {
	if (interpolated) {
	    flags |= interpolated_flag;
	} else {
	    flags &= ~interpolated_flag;
	}
    }

    /**
     * Modifies this TimestampedValue (representing the "from" value) to represent a
     * new value and Timestamp value which is located at a ratio (in time) between
     * itself and a "to" TimestampedValue.
     * <p>
     * This actual method of interpolation used depends upon the value type (T).
     */
    @Override
    public void interpolate(TimestampedValue<T> to, double time_ratio, TimestampedValue<T> out) {
	TimestampedValue<T> from = this;
	this.value.interpolate(to.value, time_ratio, out.getValue());
	float delta_t = to.getTimestamp() - from.getTimestamp();
	delta_t *= time_ratio;
	out.setTimestamp((long) delta_t);
    }

    /**
     * Initalizes this TimestampedValue to be equal to the source TimestampedValue
     * 
     * @param src
     *            - source TimestampedValue
     */
    @Override
    public void copy(TimestampedValue<T> t) {
	this.set(t);
    }

    /**
     * Returns whether this TimestampedValue is valid or not. This can be used when
     * the TimestampedValue is stored within a statically-allocated data structure,
     * allowing reuse of the same object without requiring the
     * destruction/reconstruction of a new object.
     */
    @Override
    public boolean getValid() {
	return ((flags & valid_flag) != 0);
    }

    /**
     * Sets whether this TimestampedValue is valid or not. This can be used when the
     * TimestampedValue is stored within a statically-allocated data structure,
     * allowing reuse of the same object without requiring the
     * destruction/reconstruction of a new object.
     */
    @Override
    public void setValid(boolean valid) {
	if (valid) {
	    flags |= valid_flag;
	} else {
	    flags &= ~valid_flag;
	}
    }

    @Override
    public TimestampedValue<T> instantiate_copy() {
	TimestampedValue<T> new_tsv = new TimestampedValue<T>();
	new_tsv.value = this.value.instantiate_copy();
	return new_tsv;
    }

    @Override
    public IQuantity getQuantity() {
	return this.value;
    }
}
