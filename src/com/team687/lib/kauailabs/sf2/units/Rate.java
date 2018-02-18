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

import com.team687.lib.kauailabs.sf2.units.Unit.IUnit;

public class Rate implements IUnit {

    IUnit numerator;
    IUnit denominator;

    public Rate(IUnit numerator, IUnit denominator) {
	this.numerator = numerator;
	this.denominator = denominator;
    }

    @Override
    public String getName() {
	String numerator_name = numerator.getName();
	String denominator_name = denominator.getName();
	if (denominator_name.charAt(denominator_name.length() - 1) == 's') {
	    int index = denominator_name.lastIndexOf('s');
	    denominator_name = denominator_name.substring(0, index);
	}
	return numerator_name + "/" + denominator_name;
    }

    @Override
    public String getAbbreviation() {
	String numerator_abbrev = numerator.getAbbreviation();
	String denominator_abbrev = denominator.getAbbreviation();
	return numerator_abbrev + "/" + denominator_abbrev;
    }

    public IUnit getNumeratorUnit() {
	return numerator;
    }

    public IUnit getDenominatorUnit() {
	return denominator;
    }
}
