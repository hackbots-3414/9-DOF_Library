package com.adafruit.sensor;

public class CoordinateVector extends SensorVector
{
	public CoordinateVector(float x, float y, float z)
	{
		super(x, y, z);
	}

	public float getX()
	{
		return this.value1;
	}
	
	public float getY()
	{
		return this.value2;
	}
    
	public float getZ()
	{
		return this.value3;
	}
}
