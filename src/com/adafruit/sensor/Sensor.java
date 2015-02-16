package com.adafruit.sensor;

public class Sensor
{
    private String name;                // sensor name
    private int version;                // version of the hardware + driver
    private int sensorId;              // unique sensor identifier
    private int type;                   // this sensor's type (ex. SENSOR_TYPE_LIGHT)
    private float maxValue;            // maximum value of this sensor's value in SI units
    private float minValue;            // minimum value of this sensor's value in SI units
    private float resolution;			// smallest difference between two values reported by this sensor
    private long minDelay;             // min delay in microseconds between events. zero = not a constant rate

    public Sensor(String name, int version, int sensorId, int type, float maxValue, float minValue, float resolution, long minDelay)
    {
    	this.name = name;
    	this.version = version;
    	this.sensorId = sensorId;
    	this.type = type;
    	this.maxValue = maxValue;
    	this.minValue = minValue;
    	this.resolution = resolution;
    	this.minDelay = minDelay;
    }
    
    public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public int getVersion()
	{
		return version;
	}
	public void setVersion(int version)
	{
		this.version = version;
	}
	public int getSensorId()
	{
		return sensorId;
	}
	public void setSensorId(int sensorId)
	{
		this.sensorId = sensorId;
	}
	public int getType()
	{
		return type;
	}
	public void setType(int type)
	{
		this.type = type;
	}
	public float getMaxValue()
	{
		return maxValue;
	}
	public void setMaxValue(float maxValue)
	{
		this.maxValue = maxValue;
	}
	public float getMinValue()
	{
		return minValue;
	}
	public void setMinValue(float minValue)
	{
		this.minValue = minValue;
	}
	public float getResolution()
	{
		return resolution;
	}
	public void setResolution(float resolution)
	{
		this.resolution = resolution;
	}
	public long getMinDelay()
	{
		return minDelay;
	}
	public void setMinDelay(long minDelay)
	{
		this.minDelay = minDelay;
	}

}
