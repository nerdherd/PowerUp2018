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

import java.util.ArrayList;

public class Unit {

    public float convertToPrimaryUnits(float value) {
	return value;
    }

    public float convertFromPrimaryUnits(float value) {
	return value;
    }

    public interface IUnit {
	public String getName();

	public String getAbbreviation();
    }

    public interface IUnitFamily {
	public IUnit getPrimaryUnit();

	public IUnit[] getSecondaryUnits(); /* May return null. */
    }

    public interface IUnitDescendant {
	public IUnitFamily getUnitFamily();
    }

    public class Unitless implements IUnit {
	@Override
	public String getName() {
	    return "(Unitless)";
	}

	@Override
	public String getAbbreviation() {
	    return "(u)";
	}
    }

    public class ElectricPotential implements IUnitFamily {
	public class Volts extends Unit implements IUnit {
	    @Override
	    public String getName() {
		return "Volts";
	    }

	    @Override
	    public String getAbbreviation() {
		return "V";
	    }
	}

	@Override
	public IUnit getPrimaryUnit() {
	    return new Volts();
	}

	@Override
	public IUnit[] getSecondaryUnits() {
	    return null;
	}
    }

    public class ElectricCurrent implements IUnitFamily {
	public class Amps extends Unit implements IUnit {
	    @Override
	    public String getName() {
		return "Amps";
	    }

	    @Override
	    public String getAbbreviation() {
		return "A";
	    }
	}

	@Override
	public IUnit getPrimaryUnit() {
	    return new Amps();
	};

	@Override
	public IUnit[] getSecondaryUnits() {
	    return null;
	}
    }

    public class Distance implements IUnitFamily {
	public class DistanceUnit extends Unit implements IUnitDescendant {
	    @Override
	    public IUnitFamily getUnitFamily() {
		return new Distance();
	    }
	}

	public class Meters extends DistanceUnit implements IUnit {
	    @Override
	    public String getName() {
		return "Meters";
	    }

	    @Override
	    public String getAbbreviation() {
		return "m";
	    }
	}

	public class Inches extends DistanceUnit implements IUnit {
	    public static final float METERS_PER_INCH = .0254f;
	    public static final float INCHES_PER_METER = 1f / METERS_PER_INCH;

	    @Override
	    public String getName() {
		return "Inches";
	    }

	    @Override
	    public String getAbbreviation() {
		return "in";
	    }

	    @Override
	    public float convertToPrimaryUnits(float value) {
		return value * METERS_PER_INCH;
	    }

	    @Override
	    public float convertFromPrimaryUnits(float value) {
		return value * INCHES_PER_METER;
	    }
	}

	public class Feet extends DistanceUnit implements IUnit {
	    static final float INCHES_PER_FOOT = 12f;
	    static final float METERS_PER_FOOT = Inches.METERS_PER_INCH * INCHES_PER_FOOT;
	    static final float FEET_PER_METER = 1f / METERS_PER_FOOT;

	    @Override
	    public String getName() {
		return "Feet";
	    }

	    @Override
	    public String getAbbreviation() {
		return "ft";
	    }

	    @Override
	    public float convertToPrimaryUnits(float value) {
		return value * METERS_PER_FOOT;
	    }

	    @Override
	    public float convertFromPrimaryUnits(float value) {
		return value * FEET_PER_METER;
	    }
	}

	public class Millimeters extends DistanceUnit implements IUnit {
	    static final float MILLIMETERS_PER_METER = 1000f;
	    static final float METERS_PER_MILLIMETER = 1f / 1000f;

	    @Override
	    public String getName() {
		return "Millimeters";
	    }

	    @Override
	    public String getAbbreviation() {
		return "m";
	    }

	    @Override
	    public float convertToPrimaryUnits(float value) {
		return value * METERS_PER_MILLIMETER;
	    }

	    @Override
	    public float convertFromPrimaryUnits(float value) {
		return value * MILLIMETERS_PER_METER;
	    }
	}

	public class Centimeters extends DistanceUnit implements IUnit {
	    static final float CENTIMETERS_PER_METER = 100f;
	    static final float METERS_PER_CENTIMETER = 1f / 100f;

	    @Override
	    public String getName() {
		return "Centimeters";
	    }

	    @Override
	    public String getAbbreviation() {
		return "cm";
	    }

	    @Override
	    public float convertToPrimaryUnits(float value) {
		return value * METERS_PER_CENTIMETER;
	    }

	    @Override
	    public float convertFromPrimaryUnits(float value) {
		return value * CENTIMETERS_PER_METER;
	    }
	}

	@Override
	public IUnit getPrimaryUnit() {
	    return new Meters();
	};

	@Override
	public IUnit[] getSecondaryUnits() {
	    ArrayList<IUnit> secondary_units = new ArrayList<IUnit>();
	    secondary_units.add(new Inches());
	    secondary_units.add(new Feet());
	    secondary_units.add(new Millimeters());
	    secondary_units.add(new Centimeters());
	    return (IUnit[]) secondary_units.toArray(new IUnit[secondary_units.size()]);
	}
    }

    public class Angle implements IUnitFamily {
	public abstract class AngleUnit extends Unit implements IUnit, IUnitDescendant {
	    @Override
	    public IUnitFamily getUnitFamily() {
		return new Angle();
	    }
	}

	public class Radians extends AngleUnit {
	    @Override
	    public String getName() {
		return "Radians";
	    }

	    @Override
	    public String getAbbreviation() {
		return "rad";
	    }
	}

	public class Degrees extends AngleUnit {
	    /* Range: -180.0 to 180.0 */
	    public static final float RADIANS_TO_DEGREES = (float) (180.0 / Math.PI);
	    public static final float DEGREES_TO_RADIANS = (float) (Math.PI / 180.0);

	    @Override
	    public String getName() {
		return "Degrees";
	    }

	    @Override
	    public String getAbbreviation() {
		return "deg";
	    }

	    @Override
	    public float convertToPrimaryUnits(float value) {
		return value * DEGREES_TO_RADIANS;
	    }

	    @Override
	    public float convertFromPrimaryUnits(float value) {
		return value * RADIANS_TO_DEGREES;
	    }
	}

	public class CompassHeading extends AngleUnit {
	    /* North is 0 degrees, range 0-360. */
	    public static final float DEGREES_IN_HALF_CIRCLE = 180.0f;

	    @Override
	    public String getName() {
		return "Heading";
	    }

	    @Override
	    public String getAbbreviation() {
		return "deg";
	    }

	    @Override
	    public float convertToPrimaryUnits(float value) {
		return (value - DEGREES_IN_HALF_CIRCLE) * Degrees.DEGREES_TO_RADIANS;
	    }

	    @Override
	    public float convertFromPrimaryUnits(float value) {
		return (value * Degrees.RADIANS_TO_DEGREES) + DEGREES_IN_HALF_CIRCLE;
	    }
	}

	public class Revolutions extends AngleUnit {
	    public static final float REVOLUTIONS_TO_RADIANS = (float) (2.0f * Math.PI);
	    public static final float RADIANS_TO_REVOLUTIONS = 1.0f / REVOLUTIONS_TO_RADIANS;

	    @Override
	    public String getName() {
		return "Revolutions";
	    }

	    @Override
	    public String getAbbreviation() {
		return "ref";
	    }

	    @Override
	    public float convertToPrimaryUnits(float value) {
		return value * REVOLUTIONS_TO_RADIANS;
	    }

	    @Override
	    public float convertFromPrimaryUnits(float value) {
		return value * RADIANS_TO_REVOLUTIONS;
	    }
	}

	@Override
	public IUnit getPrimaryUnit() {
	    return new Radians();
	};

	@Override
	public IUnit[] getSecondaryUnits() {
	    ArrayList<IUnit> secondary_units = new ArrayList<IUnit>();
	    secondary_units.add(new Degrees());
	    secondary_units.add(new CompassHeading());
	    secondary_units.add(new Revolutions());
	    return (IUnit[]) secondary_units.toArray(new IUnit[secondary_units.size()]);
	}
    }

    public class Time implements IUnitFamily {
	public class TimeUnit extends Unit implements IUnitDescendant {
	    @Override
	    public IUnitFamily getUnitFamily() {
		return new Time();
	    }
	}

	public class Nanoseconds extends TimeUnit implements IUnit {
	    public static final float SECONDS_TO_NANOSECONDS = (float) (1.0f * 1000000000);
	    public static final float NANOSECONDS_TO_SECONDS = (float) (1.0f / 1000000000);

	    @Override
	    public String getName() {
		return "Nanoseconds";
	    }

	    @Override
	    public String getAbbreviation() {
		return "ns";
	    }

	    @Override
	    public float convertToPrimaryUnits(float value) {
		return value * NANOSECONDS_TO_SECONDS;
	    }

	    @Override
	    public float convertFromPrimaryUnits(float value) {
		return value * SECONDS_TO_NANOSECONDS;
	    }
	}

	public class Microseconds extends TimeUnit implements IUnit {
	    public static final float SECONDS_TO_MICROSECONDS = (float) (1.0f * 1000000);
	    public static final float MICROSECONDS_TO_SECONDS = (float) (1.0f / 1000000);

	    @Override
	    public String getName() {
		return "Microseconds";
	    }

	    @Override
	    public String getAbbreviation() {
		return "us";
	    }

	    @Override
	    public float convertToPrimaryUnits(float value) {
		return value * MICROSECONDS_TO_SECONDS;
	    }

	    @Override
	    public float convertFromPrimaryUnits(float value) {
		return value * SECONDS_TO_MICROSECONDS;
	    }
	}

	public class Milliseconds extends TimeUnit implements IUnit {
	    public static final float SECONDS_TO_MILLISECONDS = (float) (1.0f * 1000);
	    public static final float MILLISECONDS_TO_SECONDS = (float) (1.0f / 1000);

	    @Override
	    public String getName() {
		return "Milliseconds";
	    }

	    @Override
	    public String getAbbreviation() {
		return "ms";
	    }

	    @Override
	    public float convertToPrimaryUnits(float value) {
		return value * MILLISECONDS_TO_SECONDS;
	    }

	    @Override
	    public float convertFromPrimaryUnits(float value) {
		return value * SECONDS_TO_MILLISECONDS;
	    }
	}

	public class Seconds extends TimeUnit implements IUnit {
	    @Override
	    public String getName() {
		return "Seconds";
	    }

	    @Override
	    public String getAbbreviation() {
		return "sec";
	    }
	}

	public class Hours extends TimeUnit implements IUnit {
	    public static final float HOURS_TO_SECONDS = (float) (60 * 60);
	    public static final float SECONDS_TO_HOURS = (float) (1.0f / HOURS_TO_SECONDS);

	    @Override
	    public String getName() {
		return "Hours";
	    }

	    @Override
	    public String getAbbreviation() {
		return "hr";
	    }

	    @Override
	    public float convertToPrimaryUnits(float value) {
		return value * HOURS_TO_SECONDS;
	    }

	    @Override
	    public float convertFromPrimaryUnits(float value) {
		return value * SECONDS_TO_HOURS;
	    }
	}

	public class Minutes extends TimeUnit implements IUnit {
	    public static final float MINUTES_TO_SECONDS = (float) (60);
	    public static final float SECONDS_TO_MINUTES = (float) (1.0f / MINUTES_TO_SECONDS);

	    @Override
	    public String getName() {
		return "Minutes";
	    }

	    @Override
	    public String getAbbreviation() {
		return "min";
	    }

	    @Override
	    public float convertToPrimaryUnits(float value) {
		return value * MINUTES_TO_SECONDS;
	    }

	    @Override
	    public float convertFromPrimaryUnits(float value) {
		return value * SECONDS_TO_MINUTES;
	    }
	}

	@Override
	public IUnit getPrimaryUnit() {
	    return new Seconds();
	};

	@Override
	public IUnit[] getSecondaryUnits() {
	    ArrayList<IUnit> secondary_units = new ArrayList<IUnit>();
	    secondary_units.add(new Hours());
	    secondary_units.add(new Minutes());
	    secondary_units.add(new Milliseconds());
	    secondary_units.add(new Microseconds());
	    secondary_units.add(new Nanoseconds());
	    return (IUnit[]) secondary_units.toArray(new IUnit[secondary_units.size()]);
	}
    }

    public class SecondDerivative implements IUnitFamily {
	public class SecondDerivativeUnit extends Unit implements IUnitDescendant {
	    @Override
	    public IUnitFamily getUnitFamily() {
		return new SecondDerivative();
	    }
	}

	public class SecondsSquared extends Unit implements IUnit {
	    @Override
	    public String getName() {
		return "Squared Seconds";
	    }

	    @Override
	    public String getAbbreviation() {
		return "s^2";
	    }
	}

	@Override
	public IUnit getPrimaryUnit() {
	    return new SecondsSquared();
	}

	@Override
	public IUnit[] getSecondaryUnits() {
	    return null;
	}
    }

    public class ThirdDerivative implements IUnitFamily {
	public class ThirdDerivativeUnit extends Unit implements IUnitDescendant {
	    @Override
	    public IUnitFamily getUnitFamily() {
		return new ThirdDerivative();
	    }
	}

	public class SecondsCubed extends Unit implements IUnit {
	    @Override
	    public String getName() {
		return "Cubed Seconds";
	    }

	    @Override
	    public String getAbbreviation() {
		return "s^3";
	    }
	}

	@Override
	public IUnit getPrimaryUnit() {
	    return new SecondsCubed();
	}

	@Override
	public IUnit[] getSecondaryUnits() {
	    return null;
	}
    }
}
