package com.adafruit.sensor;

public abstract class SensorVector
{
	// Used to return a vector in a common format.
	protected float value1;
	protected float value2;
	protected float value3;
	private int status;
	
	public SensorVector(float value1, float value2, float value3)
	{
		this.value1 = value1;
		this.value2 = value2;
		this.value3 = value3;
	}

    public void setStatus(int status)
    {
    	this.status = status;
    }
    
    public int getStatus()
    {
    	return this.status;
    }
}
