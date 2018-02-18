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

package com.team687.lib.kauailabs.sf2.pose.drivetrain;

public class DriveTrainParameters {

    int num_drive_wheels;
    int num_steer_wheels;
    float width_inches;
    float length_inches;
    float drivewheel_diameter_inches;
    float enc_ticks_per_drivewheel_revolution;

    static final int DEFAULT_ENC_COUNTS_PER_REV = 256;
    static final int DEFAULT_ENC_TICKS_PER_COUNT = 4;
    static final int DEFAULT_GEAR_RATIO = 1;
    static final int DEFAULT_ENC_TICKS_PER_DRIVEWHEEL_REV = DEFAULT_ENC_COUNTS_PER_REV * DEFAULT_ENC_TICKS_PER_COUNT
	    * DEFAULT_GEAR_RATIO;

    public int getNumDriveWheels() {
	return num_drive_wheels;
    }

    public int getNumSteerWheels() {
	return num_steer_wheels;
    }

    public float getWidthInches() {
	return width_inches;
    }

    public float getLengthInches() {
	return length_inches;
    }

    public float getDriveWheelDiameterInches() {
	return drivewheel_diameter_inches;
    }

    public float getEncTicksPerDriveWheelRevolution() {
	return enc_ticks_per_drivewheel_revolution;
    }

    public DriveTrainParameters() {
	num_drive_wheels = 4;
	num_steer_wheels = 0;
	width_inches = 24;
	length_inches = 18;
	drivewheel_diameter_inches = 6;
	enc_ticks_per_drivewheel_revolution = DEFAULT_ENC_TICKS_PER_DRIVEWHEEL_REV;
    }

    public void setNumDriveWheels(int num_drive_wheels) {
	this.num_drive_wheels = num_drive_wheels;
    }

    public void setNumSteerWheels(int num_steer_wheels) {
	this.num_steer_wheels = num_steer_wheels;
    }

    public void setWidthInches(float width_inches) {
	this.width_inches = width_inches;
    }

    public void setLengthInches(float length_inches) {
	this.width_inches = length_inches;
    }

    public void setDriveWheelDiameterInches(float drivewheel_diameter_inches) {
	this.drivewheel_diameter_inches = drivewheel_diameter_inches;
    }

    public void setEncTicksPerDriveWheelRevolution(float enc_ticks_per_drivewheel_revolution) {
	this.enc_ticks_per_drivewheel_revolution = enc_ticks_per_drivewheel_revolution;
    }

    public float getInchesPerEncTick() {
	return drivewheel_diameter_inches / enc_ticks_per_drivewheel_revolution;
    }
}
