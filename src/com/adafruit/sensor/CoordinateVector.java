package com.adafruit.sensor;

public class CoordinateVector extends SensorVector
{
	public CoordinateVector(float x, float y, float z)
	{
		super(x, y, z);
	}

	public float getX()
	{
		return this.valueX;
	}
	
	public float getY()
	{
		return this.valueY;
	}
    
	public float getZ()
	{
		return this.valueZ;
	}
}
