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

package com.team687.lib.kauailabs.sf2.orientation;

import java.lang.Math;
import java.util.ArrayList;

import com.team687.lib.kauailabs.sf2.interpolation.IInterpolate;
import com.team687.lib.kauailabs.sf2.quantity.ICopy;
import com.team687.lib.kauailabs.sf2.quantity.IQuantity;
import com.team687.lib.kauailabs.sf2.quantity.Scalar;
import com.team687.lib.kauailabs.sf2.units.Unit;
import com.team687.lib.kauailabs.sf2.units.Unit.IUnit;

/**
 * The Quaternion class provides methods to operate on a quaternion.
 * <a href="https://en.wikipedia.org/wiki/Quaternion">Quaternions</a> are used
 * among other things to describe rotation in 3D dimensions. This is typically
 * performed using a Unit Quaternion (also known as a "Versor".
 * <p>
 * Provided Quaternion operations include basic mathematic operations as well as
 * a method for interpolation.
 * <p>
 * Quaternions actually describe 4 separate rotations - 3 dimensions of rotation
 * about a coordinate reference frame, and then a fourth rotation of the
 * coodinate reference frame to another reference frame.
 * <p>
 * At their core, Quaternions are comprised of W, X, Y and Z components, which
 * in the case of the Unit Quaternion are expressed in units of Radians which
 * have a range from -2 to 2.
 * <p>
 * From a number theory perspecive, a quaternion is a complex number. A
 * quaternion is a formal sum of a real number and real multiples of the symbols
 * i, j, and k. For example,
 * <p>
 * <t><i>q = W + Xi + Yj + Zk</i></t>
 * <p>
 * A Unit Quaternion can express not only rotations, but also the gravity
 * vector. Therefore, the Quaternion class provides methods for deriving the
 * gravity vector as well as the more commonly-used Yaw, Pitch and Roll angles
 * (also known as Tait/Bryan angles).
 * 
 * @author Scott
 */

public class Quaternion implements IInterpolate<Quaternion>, ICopy<Quaternion>, IQuantity {

    private float w;
    private float x;
    private float y;
    private float z;

    class FloatVectorStruct {
	float x;
	float y;
	float z;
    };

    /**
     * Constructs a Quaternion instance, using default values for a Unit Quaternion.
     */
    public Quaternion() {
	set(1, 0, 0, 0);
    }

    /**
     * Constructs a Quaternion instance, using values from another Quaternion
     * instance.
     * 
     * @param src
     *            - the Quaternion instance used to initialize this Quaternion.
     */
    public Quaternion(final Quaternion src) {
	set(src);
    }

    /**
     * Constructs a Quaternion instance, using the provides w, x, y and z valuese.
     * 
     * @param w
     *            - the Quaternion W component value.
     * @param x
     *            - the Quaternion X component value.
     * @param y
     *            - the Quaternion Y component value.
     * @param z
     *            - the Quaternion Z component value.
     */
    public Quaternion(float w, float x, float y, float z) {
	set(w, x, y, z);
    }

    /**
     * Modifies the Quaternion by setting the component W, X, Y and Z value.
     * 
     * @param w
     *            - the Quaternion W component value.
     * @param x
     *            - the Quaternion X component value.
     * @param y
     *            - the Quaternion Y component value.
     * @param z
     *            - the Quaternion Z component value.
     */
    public void set(float w, float x, float y, float z) {
	this.w = w;
	this.x = x;
	this.y = y;
	this.z = z;
    }

    /**
     * Modifes the Quaternion to be equal to the provided Quaternion.
     * 
     * @param src
     *            - the Quaternion instance used to initialize this Quaternion.
     */
    public void set(final Quaternion src) {
	set(src.w, src.x, src.y, src.z);
    }

    /**
     * Extracts the gravity vector from the Quaternion.
     * 
     * @param v
     *            - the output vector containing the quaternion's gravity component.
     * @param q
     *            - the source quaternion.
     */
    static void getGravity(FloatVectorStruct v, final Quaternion q) {
	v.x = 2 * ((q.x * q.z) - (q.w * q.y));
	v.y = 2 * ((q.w * q.x) + (q.y * q.z));
	v.z = (q.w * q.w) - (q.x * q.x) - (q.y * q.y) + (q.z * q.z);
    }

    /**
     * Extracts the yaw, pitch and roll values from the Quaternion. Returned values
     * are in units of Radians
     * 
     * @param q
     *            - the source quaternion
     * @param gravity
     *            - the gravity component of the quaternion
     * @param ypr
     *            - a FloatVectorStruct containing the yaw/pitch/roll values
     *            extracted from the Quaternion. x: yaw; y: pitch; z: roll.
     */
    static void getYawPitchRoll(final Quaternion q, final FloatVectorStruct gravity, FloatVectorStruct ypr) {
	// yaw: (clockwise rotation, about Z axis)
	ypr.x = (float) Math.atan2((2 * (q.x * q.y)) - (2 * (q.w * q.z)), (2 * (q.w * q.w)) + (2 * (q.x * q.x)) - 1);
	// pitch: (tilt up/down, about X axis)
	ypr.y = (float) Math.atan(gravity.y / Math.sqrt((gravity.x * gravity.x) + (gravity.z * gravity.z)));
	// roll: (tilt left/right, about Y axis)
	ypr.z = (float) Math.atan(gravity.x / Math.sqrt((gravity.y * gravity.y) + (gravity.z * gravity.z)));
    }

    /**
     * Extracts the yaw, pitch and roll values from the Quaternion. Returned values
     * are in units of Radians.
     */
    void getYawPitchRollRadians(FloatVectorStruct ypr) {
	FloatVectorStruct gravity = new FloatVectorStruct();
	getGravity(gravity, this);
	getYawPitchRoll(this, gravity, ypr);
    }

    /**
     * Extracts the yaw angle value from the Quaternion. The Return value is in
     * units of Radians.
     */
    public void getYawRadians(Scalar yaw) {
	FloatVectorStruct ypr = new FloatVectorStruct();
	getYawPitchRollRadians(ypr);
	yaw.set(ypr.x);
    }

    /**
     * Extracts the pitch angle value from the Quaternion. The Return value is in
     * units of Radians.
     */
    public void getPitch(Scalar pitch) {
	FloatVectorStruct ypr = new FloatVectorStruct();
	getYawPitchRollRadians(ypr);
	pitch.set(ypr.y);
    }

    /**
     * Extracts the roll angle value from the Quaternion. The Return value is in
     * units of Radians.
     */
    public void getRoll(Scalar roll) {
	FloatVectorStruct ypr = new FloatVectorStruct();
	getYawPitchRollRadians(ypr);
	roll.set(ypr.z);
    }

    /**
     * Estimates an intermediate Quaternion given Quaternions representing each end
     * of the path, and an interpolation ratio from 0.0 t0 1.0.
     * 
     * Uses Quaternion SLERP (Spherical Linear Interpolation), an algorithm
     * originally introduced by Ken Shoemake in the context of quaternion
     * interpolation for the purpose of animating 3D rotation. This estimation is
     * based upon the assumption of constant-speed motion along a unit-radius great
     * circle arc.
     * 
     * For more info:
     * 
     * http://www.euclideanspace.com/maths/algebra/realNormedAlgebra/quaternions
     * /slerp/index.htm
     */
    public static void slerp(final Quaternion qa, final Quaternion qb, double t, Quaternion out) {
	// Calculate angle between them.
	double cosHalfTheta = qa.w * qb.w + qa.x * qb.x + qa.y * qb.y + qa.z * qb.z;
	// if qa=qb or qa=-qb then theta = 0 and we can return qa
	if (Math.abs(cosHalfTheta) >= 1.0) {
	    out.w = qa.w;
	    out.x = qa.x;
	    out.y = qa.y;
	    out.z = qa.z;
	    return;
	}
	// Calculate temporary values.
	double halfTheta = Math.acos(cosHalfTheta);
	double sinHalfTheta = Math.sqrt(1.0 - cosHalfTheta * cosHalfTheta);
	// if theta = 180 degrees then result is not fully defined
	// we could rotate around any axis normal to qa or qb
	if (Math.abs(sinHalfTheta) < 0.001) {
	    out.w = (qa.w * 0.5f + qb.w * 0.5f);
	    out.x = (qa.x * 0.5f + qb.x * 0.5f);
	    out.y = (qa.y * 0.5f + qb.y * 0.5f);
	    out.z = (qa.z * 0.5f + qb.z * 0.5f);
	    return;
	}
	float ratioA = (float) (Math.sin((1 - t) * halfTheta) / sinHalfTheta);
	float ratioB = (float) (Math.sin(t * halfTheta) / sinHalfTheta);
	// calculate Quaternion.
	out.w = (qa.w * ratioA + qb.w * ratioB);
	out.x = (qa.x * ratioA + qb.x * ratioB);
	out.y = (qa.y * ratioA + qb.y * ratioB);
	out.z = (qa.z * ratioA + qb.z * ratioB);
	return;
    }

    /**
     * Modifies the Quaternion to be its complex conjugate.
     * 
     * The <a href="https://en.wikipedia.org/wiki/Complex_conjugate">complex
     * conjugate</a> of a complex number is the number with equal real part and
     * imaginary part equal in magnitude but opposite in sign.
     */
    public void conjugate() {
	this.x = -this.x;
	this.y = -this.y;
	this.z = -this.z;
    }

    /**
     * Modifies the Quaternion to be its inverse (reciprocal).
     * 
     * The quaternion inverse of a rotation is the opposite rotation, so it can be
     * thought of as a mirror image of the original quaternion
     */
    public void inverse() {
	this.conjugate();
	this.divide(dotProduct(this, this));
    }

    /**
     * Modifies this quaternion (the multiplicand) to be the product of
     * multiplication by a multiplier Quaternion.
     * 
     * <i>Key point: the result of multiplying two Quaternions is to logically add
     * together their respective rotations.</i>
     * 
     * Note that Quaternion multiplication is NOT
     * <a href="https://en.wikipedia.org/wiki/Commutative_property">commutative
     * </a>, in other words when multiplying Quaternions, the result of a * b is NOT
     * the same as b * a.
     * 
     * @param q
     *            - the multiplier quaternion.
     */
    public void multiply(Quaternion q) {
	float w, x, y, z;

	x = this.w * q.x + this.x * q.w + this.y * q.z - this.z * q.y;
	y = this.w * q.y + this.y * q.w + this.z * q.x - this.x * q.z;
	z = this.w * q.z + this.z * q.w + this.x * q.y - this.y * q.x;
	w = this.w * q.w - this.x * q.x - this.y * q.y - this.z * q.z;

	this.w = w;
	this.x = x;
	this.y = y;
	this.z = z;
    }

    /**
     * Modifies a quaternion, scaling it by the provided parameter.
     * 
     * @param s
     *            - the value by which to divide each Quaternion component value.
     */
    public void divide(float s) {
	this.w = this.w / s;
	this.x = this.x / s;
	this.y = this.y / s;
	this.z = this.z / s;
    }

    public float dotProduct(final Quaternion q1, final Quaternion q2) {
	return q1.x * q2.x + q1.y * q2.y + q1.z * q2.z + q1.w * q2.w;
    }

    /**
     * Divides two quaternions. Since Quaternion multiplication is not commutative,
     * to perform this operation, the multiplicand Quaternion is multiplied by the
     * inverse of the multiplier Quaternion.
     * 
     * <i>Key point: the result of dividing two Quaternions is to logically subtract
     * their respective rotations. Thus, use difference() to calculate the amount of
     * 3D rotation between two Quaternions.</i>
     * 
     * @param qa
     *            - the dividend Quaternion
     * @param qb
     *            - the divisor Quaternion
     * @param q_diff
     *            - the resulting quotient Quaternion representing the difference in
     *            rotation
     */
    public static void difference(final Quaternion qa, final Quaternion qb, Quaternion q_diff) {
	q_diff.set(qa.w, qa.x, qa.y, qa.z);
	q_diff.inverse();
	q_diff.multiply(qb);
    }

    /**
     * Accessor for the Quaternion's W component value.
     * 
     * @return Quaternion W component value.
     */
    public float getW() {
	return w;
    }

    /**
     * Accessor for the Quaternion's X component value.
     * 
     * @return Quaternion X component value.
     */
    public float getX() {
	return x;
    }

    /**
     * Accessor for the Quaternion's Y component value.
     * 
     * @return Quaternion Y component value.
     */
    public float getY() {
	return y;
    }

    /**
     * Accessor for the Quaternion's Z component value.
     * 
     * @return Quaternion Z component value.
     */
    public float getZ() {
	return z;
    }

    @Override
    public void interpolate(Quaternion to, double time_ratio, Quaternion out) {
	Quaternion.slerp(this, to, time_ratio, out);
    }

    @Override
    public void copy(Quaternion t) {
	this.w = t.w;
	this.x = t.x;
	this.y = t.y;
	this.z = t.z;
    }

    @Override
    public Quaternion instantiate_copy() {
	return new Quaternion(this);
    }

    static public IUnit[] getUnits() {
	return new IUnit[] { new Unit().new Unitless(), new Unit().new Unitless(), new Unit().new Unitless(),
		new Unit().new Unitless(), };
    }

    @Override
    public boolean getPrintableString(StringBuilder printable_string) {
	return false;
    }

    @Override
    public boolean getContainedQuantities(ArrayList<IQuantity> quantities) {
	quantities.add(new Scalar(w));
	quantities.add(new Scalar(x));
	quantities.add(new Scalar(y));
	quantities.add(new Scalar(z));
	return true;
    }

    @Override
    public boolean getContainedQuantityNames(ArrayList<String> quantity_names) {
	quantity_names.add("W");
	quantity_names.add("X");
	quantity_names.add("Y");
	quantity_names.add("Z");
	return true;
    }
}
