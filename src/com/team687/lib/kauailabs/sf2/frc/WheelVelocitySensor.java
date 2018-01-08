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

import com.team687.lib.kauailabs.sf2.sensor.IWheelVelocitySensor;

//import edu.wpi.first.wpilibj.CANJaguar;
//import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Encoder;

public class WheelVelocitySensor implements IWheelVelocitySensor {
    private enum EncoderType {
	WPIEncoder, CANTalon, CANJaguar
    };

    EncoderType type;

    Encoder encoder;
    // CANTalon talon_srx;
    // CANJaguar jaguar;

    int positions_per_wheel_revolution;
    double wheel_diameter_inches;
    int ticks_per_count;

    /*
     * The provided Encoder object must be correctly configured to calculate
     * distance in inches, as shown in the following examples:
     * 
     * Example 1: A 256 Count Quadrature Encoder (which thus generates 1024 ticks
     * per revolution) which is mounted directly on the drive shaft of a 6" wheel:
     * 
     * Encoder e; // Use constructor which specifies Encoding Type == 4x double
     * wheel_diameter_inches = 6; int counts_per_rev = 256; e.setDistancePerPulse(
     * wheel_diameter_inches / counts_per_rev ); WPILibEncoder wle = new
     * WPILibEncoder(e);
     * 
     * Example 2: A 7 Count Quadrature Encoder (which thus generates 28 ticks per
     * revolution) which is mounted on a motor with a 188:1 gear ratio, which is
     * coupled to a drive shaft that drives a 6" wheel.
     * 
     * Encoder e; // Use constructor which specifies EncodingType == 4x double
     * wheel_diamater_inches = 6; double gear_ratio = 188; int counts_per_rev = 7;
     * e.setDistancePerPulse( wheel_diameter_inches / counts_per_rev / gear_ratio );
     * WPILibEncoder wle = new WPILibEncoder(e);
     */
    public WheelVelocitySensor(Encoder e) {
	type = EncoderType.WPIEncoder;
	this.encoder = e;
    }

    /*
     * The provided CANTalon object must be correctly configured to calculate
     * distance in inches using a sensor input, as shown in the following examples:
     * 
     * Example 1: A TalonSRX is connected to a A 256 Count Quadrature Encoder (which
     * thus generates 1024 ticks per revolution) which is mounted directly on the
     * drive shaft of a 6" wheel:
     * 
     * double wheel_diameter_inches = 6; int counts_per_rev = 256; int
     * ticks_per_count = 4; CANTalon talon; // Construct CANTalon WPILibEncoder wle
     * = new WPILibEncoder(talon, ticks_per_count, counts_per_rev,
     * wheel_diameter_inches);
     * 
     * Example 2: A 7 Count Quadrature Encoder (which thus generates 28 ticks per
     * revolution) which is mounted on a motor with a 188:1 gear ratio:
     * 
     * double wheel_diamater_inches = 6; double gear_ratio = 188; int counts_per_rev
     * = 7; int ticks_per_count = 4; CANTalon talon; // Construct CANTalon
     * WPILibEncoder wle = new WPILibEncoder( talon, ticks_per_count, counts_per_rev
     * * gear_ratio, wheel_diamater_inches);
     */
    /*
     * public WheelVelocitySensor( CANTalon t, int ticks_per_count, int
     * positions_per_wheel_revolution, double wheel_diameter_inches ) { type =
     * EncoderType.CANTalon; this.talon_srx = t; this.positions_per_wheel_revolution
     * = positions_per_wheel_revolution; this.wheel_diameter_inches =
     * wheel_diameter_inches; this.ticks_per_count = ticks_per_count; }
     */
    /*
     * The provided CANJaguar object must be correctly configured to calculate
     * distance in inches using a sensor input, as shown in the following examples:
     * 
     * Example 1: A Jaguar is connected to a A 256 Count Quadrature Encoder (which
     * thus generates 1024 ticks per revolution) which is mounted directly on the
     * drive shaft of a 6" wheel:
     * 
     * CANJaguar jaguar; // Construct CANJaguar
     * j.setVoltageMode(CANJaguar.kQuadEncoder, 256); // Alternatively, Current,
     * Position or Speed Mode jaguar.configEncoderCodesPerRev(256); double
     * wheel_diameter_inches = 6; WPILibEncoder wle = new WPILibEncoder(jaguar,
     * wheel_diameter_inches);
     * 
     * Example 2: A 7 Count Quadrature Encoder (which thus generates 28 ticks per
     * revolution) which is mounted on a motor with a 188:1 gear ratio:
     * 
     * double wheel_diamater_inches = 6; double gear_ratio = 188; int counts_per_rev
     * = 7; CANJaguar jaguar; // Construct CANJaguar
     * j.setVoltageMode(CANJaguar.kQuadEncoder, counts_per_rev * gear_ratio); //
     * Alternatively, Current, Position or Speed Mode
     * jaguar.configEncoderCodesPerRev(counts_per_rev * gear_ratio); WPILibEncoder
     * wle = new WPILibEncoder(jaguar, wheel_diameter_inches);
     */
    /*
     * public WheelVelocitySensor( CANJaguar j ) { type = EncoderType.CANJaguar;
     * this.jaguar = j; }
     */
    @Override
    public void reset() {
    }

    @Override
    public double getCurrentDistanceInches() {
	switch (type) {
	case WPIEncoder:
	    /*
	     * Encoder positions are in distance units, based on configuration
	     */
	    return this.encoder.getDistance();
	/*
	 * case CANTalon: // Talon Positions are in units of raw quadrature "ticks".
	 * Thus, to convert to // distance, the ticks_per_count and "distance per count"
	 * must be provided. double counts = this.talon_srx.getEncPosition() /
	 * ticks_per_count; double distance = (wheel_diameter_inches /
	 * positions_per_wheel_revolution) * counts; return distance; case CANJaguar:
	 * //Jaguar positions are in distance units, based on configuration return
	 * this.jaguar.getPosition();
	 */
	default:
	    return 0;
	}
    }
}
