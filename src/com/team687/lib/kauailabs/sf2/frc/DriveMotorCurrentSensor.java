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

package com.team687.lib.kauailabs.sf2.frc;

import com.team687.lib.kauailabs.sf2.sensor.IDriveMotorCurrentSensor;

//import edu.wpi.first.wpilibj.CANJaguar;
//import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.PowerDistributionPanel;

public class DriveMotorCurrentSensor implements IDriveMotorCurrentSensor {

    PowerDistributionPanel pdp;
    int pdp_channel;

    // CANTalon talon;
    // CANJaguar jaguar;
    public enum SensorType {
	PowerDistributionPanel, CANTalon, CANJaguar
    };

    SensorType type;

    public DriveMotorCurrentSensor(PowerDistributionPanel pdp, int channel) {
	this.type = SensorType.PowerDistributionPanel;
	this.pdp = pdp;
	this.pdp_channel = channel;
    }

    /*
     * public DriveMotorCurrentSensor(CANTalon talon) { this.type =
     * SensorType.CANTalon; this.talon = talon; }
     * 
     * public DriveMotorCurrentSensor(CANJaguar jaguar) { this.type =
     * SensorType.CANTalon; this.jaguar = jaguar; }
     */

    @Override
    public double getCurrentAmps() {
	switch (type) {
	case PowerDistributionPanel:
	    return pdp.getCurrent(pdp_channel);
	// case CANTalon:
	// return talon.getOutputCurrent();
	// case CANJaguar:
	// return jaguar.getOutputCurrent();
	default:
	    return 0;
	}
    }

}
