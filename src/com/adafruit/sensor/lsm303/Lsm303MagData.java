package com.adafruit.sensor.lsm303;

public class Lsm303MagData
{
    private float x;
    private float y;
    private float z;
    private float orientation;
    
	public float getX()
	{
		return x;
	}
	public void setX(float x)
	{
		this.x = x;
	}
	public float getY()
	{
		return y;
	}
	public void setY(float y)
	{
		this.y = y;
	}
	public float getZ()
	{
		return z;
	}
	public void setZ(float z)
	{
		this.z = z;
	}
	public float getOrientation()
	{
		return orientation;
	}
	public void setOrientation(float orientation)
	{
		this.orientation = orientation;
	}
}
