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

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.team687.lib.kauailabs.sf2.sensor.IProcessorInfo;
import com.team687.lib.kauailabs.sf2.time.Timestamp;
import com.team687.lib.kauailabs.sf2.time.Timestamp.TimestampResolution;

import edu.wpi.first.wpilibj.Timer;

public class RoboRIO implements IProcessorInfo {

    @Override
    public String getName() {
	try {
	    return InetAddress.getLocalHost().getHostName();
	} catch (UnknownHostException e) {
	    return "RoboRIO";
	}
    }

    @Override
    public void getProcessorTimestamp(Timestamp t) {
	t.setResolution(TimestampResolution.Millisecond);
	t.fromSeconds(Timer.getFPGATimestamp());
    }

}
