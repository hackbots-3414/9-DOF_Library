package com.adafruit.sensor;


public class SensorEvent
{
    private int version;                          /* must be sizeof(struct sensors_event_t) */
    private int sensorId;                        /* unique sensor identifier */
    private int type;                             /* sensor type */
    private long timestamp;                        /* time is in milliseconds */
	private SensorVector vector;

	public SensorEvent()
	{
		
	}

	public SensorEvent(int version, int sensorId, int type, long timestamp, SensorVector vector)
	{
		this.version = version;
		this.sensorId = sensorId;
		this.type = type;
		this.timestamp = timestamp;
		this.vector = vector;
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

	public long getTimestamp()
	{
		return timestamp;
	}

	public void setTimestamp(long timestamp)
	{
		this.timestamp = timestamp;
	}

	public SensorVector getVector()
	{
		return vector;
	}

	public void setVector(SensorVector vector)
	{
		this.vector = vector;
	}
}
