package com.adafruit.sensor.lsm303;

import com.adafruit.sensor.AdafruitSensor;
import com.adafruit.sensor.Sensor;
import com.adafruit.sensor.SensorEvent;

public abstract class AdafruitLsm303Unified extends AdafruitSensor
{
    // I2C ADDRESS/BITS
    public final int LSM303_ADDRESS_ACCEL = (0x32 >> 1);		// 0011001x
    public final int LSM303_ADDRESS_MAG = (0x3C >> 1);			// 0011110x
	
    // CHIP ID
    public final int LSM303_ID = 0b11010100;

    static float _lsm303Accel_MG_LSB     = 0.001F;   // 1, 2, 4 or 12 mg per lsb
    static float _lsm303Mag_Gauss_LSB_XY = 1100.0F;  // Varies with gain
    static float _lsm303Mag_Gauss_LSB_Z  = 980.0F;   // Varies with gain

    public AdafruitLsm303Unified(int sensorID)
    {
    	setSensorID(sensorID);
    }
    
    // Abstract away platform differences in Arduino wire library
    private void write8(byte address, byte reg, byte value)
    {
    	Wire.beginTransmission(address);
		Wire.send(reg);
		Wire.send(value);
		Wire.endTransmission();
    }

    private byte read8(byte address, byte reg)
    {
    	byte value;

		Wire.beginTransmission(address);
		Wire.send(reg);
		Wire.endTransmission();
		Wire.requestFrom(address, (byte)1);
		value = Wire.receive();
		Wire.endTransmission();
		
		return value;
    }

	public abstract SensorEvent getEvent();

	public abstract Sensor getSensor();
}
