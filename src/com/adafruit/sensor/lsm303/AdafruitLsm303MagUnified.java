package com.adafruit.sensor.lsm303;

import com.adafruit.sensor.Sensor;
import com.adafruit.sensor.SensorEvent;

public class AdafruitLsm303MagUnified extends AdafruitLsm303Unified
{
	private Lsm303MagGain _magGain;
	private Lsm303MagData _magData;     // Last read magnetometer data will be available here

	private boolean _autoRangeEnabled = false;

	public AdafruitLsm303MagUnified(int sensorID)
	{
		super(sensorID);
		_autoRangeEnabled = false;
	}

	public boolean begin()
	{
		// Enable I2C
		Wire.begin();

		// Enable the magnetometer
		write8(LSM303_ADDRESS_MAG, LSM303_REGISTER_MAG_MR_REG_M, 0x00);

		// Set the gain to a known level
		setMagGain(Lsm303MagGain.LSM303_MAGGAIN_1_3);

		return true;
	}

	public void enableAutoRange(boolean enabled)
	{
		this._autoRangeEnabled = enabled;
	}

	public void setMagGain(Lsm303MagGain gain)
	{
		write8(LSM303_ADDRESS_MAG, Lsm303MagRegisters.LSM303_REGISTER_MAG_CRB_REG_M, (byte) gain.getGain());

		_magGain = gain;

		switch (gain) {
			case LSM303_MAGGAIN_1_3:
				_lsm303Mag_Gauss_LSB_XY = 1100;
				_lsm303Mag_Gauss_LSB_Z = 980;
				break;
			case LSM303_MAGGAIN_1_9:
				_lsm303Mag_Gauss_LSB_XY = 855;
				_lsm303Mag_Gauss_LSB_Z = 760;
				break;
			case LSM303_MAGGAIN_2_5:
				_lsm303Mag_Gauss_LSB_XY = 670;
				_lsm303Mag_Gauss_LSB_Z = 600;
				break;
			case LSM303_MAGGAIN_4_0:
				_lsm303Mag_Gauss_LSB_XY = 450;
				_lsm303Mag_Gauss_LSB_Z = 400;
				break;
			case LSM303_MAGGAIN_4_7:
				_lsm303Mag_Gauss_LSB_XY = 400;
				_lsm303Mag_Gauss_LSB_Z = 255;
				break;
			case LSM303_MAGGAIN_5_6:
				_lsm303Mag_Gauss_LSB_XY = 330;
				_lsm303Mag_Gauss_LSB_Z = 295;
				break;
			case LSM303_MAGGAIN_8_1:
				_lsm303Mag_Gauss_LSB_XY = 230;
				_lsm303Mag_Gauss_LSB_Z = 205;
				break;
		}
	}

	@Override
	public SensorEvent getEvent()
	{
		boolean readingValid = false;

		SensorEvent event = new SensorEvent();

		while (!readingValid)
		{
			/* Read new data */
			read();

			/* Make sure the sensor isn't saturating if auto-ranging is enabled */
			if (!_autoRangeEnabled)
			{
				readingValid = true;
			} else
			{
				Serial.print(_magData.x);
				Serial.print(" ");
				Serial.print(_magData.y);
				Serial.print(" ");
				Serial.print(_magData.z);
				Serial.println(" ");
				/* Check if the sensor is saturating or not */
				if ((_magData.getX() >= 4090) | (_magData.getX() <= -4090) | (_magData.getY() >= 4090) | (_magData.getY() <= -4090)
						| (_magData.getZ() >= 4090) | (_magData.getZ() <= -4090))
				{
					/* Saturating .... increase the range if we can */
					switch (_magGain) {
						case LSM303_MAGGAIN_5_6:
							setMagGain(LSM303_MAGGAIN_8_1);
							readingValid = false;
							Serial.println("Changing range to +/- 8.1");
							break;
						case LSM303_MAGGAIN_4_7:
							setMagGain(LSM303_MAGGAIN_5_6);
							readingValid = false;
							Serial.println("Changing range to +/- 5.6");
							break;
						case LSM303_MAGGAIN_4_0:
							setMagGain(LSM303_MAGGAIN_4_7);
							readingValid = false;
							Serial.println("Changing range to +/- 4.7");
							break;
						case LSM303_MAGGAIN_2_5:
							setMagGain(LSM303_MAGGAIN_4_0);
							readingValid = false;
							Serial.println("Changing range to +/- 4.0");
							break;
						case LSM303_MAGGAIN_1_9:
							setMagGain(LSM303_MAGGAIN_2_5);
							readingValid = false;
							Serial.println("Changing range to +/- 2.5");
							break;
						case LSM303_MAGGAIN_1_3:
							setMagGain(LSM303_MAGGAIN_1_9);
							readingValid = false;
							Serial.println("Changing range to +/- 1.9");
							break;
						default:
							readingValid = true;
							break;
					}
				} else
				{
					/* All values are withing range */
					readingValid = true;
				}
			}
		}

		event.setVersion(1);
		event.setSensorId(this._sensorID);
		event.setType(SENSOR_TYPE_MAGNETIC_FIELD);
		event.getTimestamp(millis());
		event.magnetic.x = _magData.x / _lsm303Mag_Gauss_LSB_XY * SENSORS_GAUSS_TO_MICROTESLA;
		event.magnetic.y = _magData.y / _lsm303Mag_Gauss_LSB_XY * SENSORS_GAUSS_TO_MICROTESLA;
		event.magnetic.z = _magData.z / _lsm303Mag_Gauss_LSB_Z * SENSORS_GAUSS_TO_MICROTESLA;
	}

	public Sensor getSensor()
	{
		return new Sensor("LSM303", 1, this._sensorID, SENSOR_TYPE_MAGNETIC_FIELD, 0, 0.0f, 0.0f, 0.0f);
	}

	public void read()
	{
		// Read the magnetometer
		Wire.beginTransmission((byte) LSM303_ADDRESS_MAG);
		Wire.send(LSM303_REGISTER_MAG_OUT_X_H_M);
		Wire.endTransmission();
		Wire.requestFrom((byte) LSM303_ADDRESS_MAG, (byte) 6);

		// Wait around until enough data is available
		while (Wire.available() < 6)
			;

		// Note high before low (different than accel)
		Integer xhi = Wire.receive();
		Integer xlo = Wire.receive();
		Integer zhi = Wire.receive();
		Integer zlo = Wire.receive();
		Integer yhi = Wire.receive();
		Integer ylo = Wire.receive();

		// Shift values to create properly formed integer (low byte first)
		_magData.setX((Integer) (xlo | ((Integer) xhi << 8)));
		_magData.setY((Integer) (ylo | ((Integer) yhi << 8)));
		_magData.setZ((Integer) (zlo | ((Integer) zhi << 8)));

		// ToDo: Calculate orientation
		_magData.setOrientation(0.0f);
	}
}
