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

package com.team687.lib.kauailabs.sf2.units;

import com.team687.lib.kauailabs.sf2.quantity.Scalar;
import com.team687.lib.kauailabs.sf2.units.Unit.IUnit;

public class Test {
    Scalar displacement_x;
    Scalar deg_per_sec;
    Scalar accel_x;
    Scalar jerk_x;

    public void test() {
	displacement_x = new Scalar(10);
	deg_per_sec = new Scalar(3.5f);
	accel_x = new Scalar(47);
	jerk_x = new Scalar(.004f);
	RateMetersPerSecond units = new RateMetersPerSecond();
	IUnit iunit = units;
	String name = iunit.getName();
	System.out.println("M/S Name:  " + name);
	String abbrev = iunit.getAbbreviation();
	System.out.println("M/S Abbreviation:  " + abbrev);
	if (iunit instanceof Rate) {
	    IUnit numerator = (IUnit) ((Rate) iunit).getNumeratorUnit();
	    System.out.println("Rate numerator is an IUnit:  " + numerator.getName());
	    IUnit denominator = (IUnit) ((Rate) iunit).getDenominatorUnit();
	    System.out.println("Rate denominator is an IUnit:  " + denominator.getName());
	}
	double value = deg_per_sec.get();
	System.out.println("Degress/sec retrieved value:  " + Double.toString(value));
	IUnit meter_units = new Unit().new Angle().new Degrees();
	String meter_unit_name = meter_units.getName();
	System.out.println("M Name:  " + meter_unit_name);
	String meter_unit_abbrev = meter_units.getAbbreviation();
	System.out.println("M Abbreviation:  " + meter_unit_abbrev);
	String s = Double.toString(displacement_x.get());
	System.out.println("Dispacement:  " + s);
    }
}
