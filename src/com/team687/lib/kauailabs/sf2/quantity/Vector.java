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

public class Vector implements IInterpolate<Vector>, ICopy<Vector>, IQuantity {
    Scalar direction;
    Scalar magnitude;

    private Vector() {
    }

    public Vector(Scalar direction, Scalar magnitude) {
	this.direction = direction;
	this.magnitude = magnitude;
    }

    public float getDirection() {
	return direction.get();
    }

    public float getMagnitude() {
	return magnitude.get();
    }

    @Override
    public void copy(Vector t) {
	this.direction.copy(t.direction);
	this.magnitude.copy(t.magnitude);
    }

    @Override
    public Vector instantiate_copy() {
	Vector v = new Vector();
	v.copy(this);
	return v;
    }

    @Override
    public void interpolate(Vector to, double time_ratio, Vector out) {
	Scalar direction = this.direction.instantiate_copy();
	Scalar magnitude = this.magnitude.instantiate_copy();
	out.direction = direction;
	out.magnitude = magnitude;
    }

    @Override
    public boolean getPrintableString(StringBuilder printable_string) {
	return false;
    }

    @Override
    public boolean getContainedQuantities(ArrayList<IQuantity> quantities) {
	quantities.add(direction);
	quantities.add(magnitude);
	return true;
    }

    @Override
    public boolean getContainedQuantityNames(ArrayList<String> quantity_names) {
	quantity_names.add("Direction");
	quantity_names.add("Magnitude");
	return true;
    }
}
