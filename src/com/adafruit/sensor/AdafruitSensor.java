package com.adafruit.sensor;

public abstract class AdafruitSensor
{
	private boolean _autoRange;
    private int _sensorID;
	
	// Constants
	public float SENSORS_GRAVITY_EARTH = 9.80665f;			// Earth's gravity in m/s^2
	public float SENSORS_GRAVITY_MOON = 1.6f;				// The moon's gravity in m/s^2
	public float SENSORS_GRAVITY_SUN = 275.0f;				// The sun's gravity in m/s^2
	public float SENSORS_GRAVITY_STANDARD = SENSORS_GRAVITY_EARTH;
	public float SENSORS_MAGFIELD_EARTH_MAX = 60.0f;		// Maximum magnetic field on Earth's surface
	public float SENSORS_MAGFIELD_EARTH_MIN = 30.0f;		// Minimum magnetic field on Earth's surface
	public float SENSORS_PRESSURE_SEALEVELHPA = 1013.25f;	// Average sea level pressure is 1013.25 hPa
	public float SENSORS_DPS_TO_RADS = 0.017453293f;		// Degrees/s to rad/s multiplier
	public int SENSORS_GAUSS_TO_MICROTESLA = 100;			// Gauss to micro-Tesla multiplier

	public AdafruitSensor()
	{
	}

	public void enableAutoRange(boolean enabled)
	{
		this._autoRange = enabled;
	}
	
	public boolean isAutoRangeEnabled()
	{
		return this._autoRange;
	}

	public int getSensorID()
	{
		return _sensorID;
	}

	public void setSensorID(int _sensorID)
	{
		this._sensorID = _sensorID;
	}

	public abstract SensorEvent getEvent();

	public abstract Sensor getSensor();
}
