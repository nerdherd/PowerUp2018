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
import com.team687.lib.kauailabs.sf2.units.Unit.IUnit;

public class SensorDataSourceInfo {
    String name;
    IUnit[] units;
    IQuantity value;

    public SensorDataSourceInfo(String name, IQuantity value, IUnit[] units) {
	this.name = name;
	this.value = value;
	this.units = units;
    }

    /**
     * Returns the name of this sensor data. This name must be unique among all
     * SensorDataInfos produced by any sensor.
     */
    public String getName() {
	return name;
    }

    /**
     * Returns the class object corresponding to the sensor quantity data type.
     * 
     * @return
     */
    public IQuantity getQuantity() {
	return value;
    }

    /**
     * Returns an array of IUnit objects describing the units of the sensor quantity
     * data type. If a complex quantity data type (which implements the
     * IQuantityContainer interface, multiple IUnit objects may be returned. The
     * count and order of the IUnit corresponds to the IQuantity objects contained
     * within the quantity data type.
     * 
     * @return
     */
    public IUnit[] getQuantityUnits() {
	return units;
    }

    static public void getQuantityArray(SensorDataSourceInfo[] data_source, ArrayList<IQuantity> quantity_list) {
	for (SensorDataSourceInfo data_source_info : data_source) {
	    try {
		quantity_list.add(data_source_info.getQuantity().getClass().newInstance());
	    } catch (InstantiationException | IllegalAccessException e) {
		e.printStackTrace();
		return;
	    }
	}
    }
}
