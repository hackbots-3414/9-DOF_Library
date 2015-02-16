package com.adafruit.sensor;

public class OrientationVector extends SensorVector
{
	public OrientationVector(float roll, float pitch, float heading)
	{
		super(roll, pitch, heading);
	}

	/**
	 * Rotation around the longitudinal axis (the plane body, 'X axis'). 
	 * Roll is positive and increasing when moving downward. -90°<=roll<=90° 
	 */
    public float getRoll()
    {
    	return this.valueX;
    }

    /**
     * Rotation around the lateral axis (the wing span, 'Y axis'). 
     * Pitch is positive and increasing when moving upwards. -180°<=pitch<=180°)
     */
    public float getPitch()
    {
    	return this.valueY;
    }
    
    /**
     * Angle between the longitudinal axis (the plane body) and magnetic north, 
     * measured clockwise when viewing from the top of the device. 0-359°
     */
    public float getHeading()
    {
    	return this.valueZ;
    }

}
