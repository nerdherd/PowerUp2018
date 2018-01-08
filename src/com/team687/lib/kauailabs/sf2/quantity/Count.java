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

package com.team687.lib.kauailabs.sf2.quantity;

import java.util.ArrayList;

import com.team687.lib.kauailabs.sf2.interpolation.IInterpolate;

public class Count implements IInterpolate<Count>, ICopy<Count>, IQuantity {
    long count;

    public long get() {
	return count;
    }

    public void set(long value) {
	count = value;
    }

    public Count(long value) {
	this.count = value;
    }

    public Count() {
    }

    @Override
    public void copy(Count t) {
	set(t.get());
    }

    @Override
    public Count instantiate_copy() {
	return new Count(this.count);
    }

    @Override
    /* time_ratio: interpolation ratio from 0.0 to 1.0. */
    public void interpolate(Count to, double time_ratio, Count out) {
	long delta = to.count - this.count;
	long interpolated_value = this.count + delta;
	out.set(interpolated_value);
    }

    @Override
    public boolean getPrintableString(StringBuilder printable_string) {
	printable_string.append(count);
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
