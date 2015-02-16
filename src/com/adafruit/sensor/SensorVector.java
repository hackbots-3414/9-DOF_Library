package com.adafruit.sensor;

public abstract class SensorVector
{
	private float valueX;
	private float valueY;
	private float valueZ;
	private int status;
	
	public SensorVector(float valueX, float valueY, float valueZ)
	{
		this.valueX = valueX;
		this.valueY = valueY;
		this.valueZ = valueZ;
	}

    public void setStatus(int status)
    {
    	this.status = status;
    }
    
    public int getStatus()
    {
    	return this.status;
    }

	public float getValueX()
	{
		return valueX;
	}

	public void setValueX(float valueX)
	{
		this.valueX = valueX;
	}

	public float getValueY()
	{
		return valueY;
	}

	public void setValueY(float valueY)
	{
		this.valueY = valueY;
	}

	public float getValueZ()
	{
		return valueZ;
	}

	public void setValueZ(float valueZ)
	{
		this.valueZ = valueZ;
	}
}
