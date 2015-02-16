package com.adafruit.sensor.lsm303;

import static com.adafruit.sensor.lsm303.Lsm303AccelRegisters.LSM303_REGISTER_ACCEL_CTRL_REG1_A;
import static com.adafruit.sensor.lsm303.Lsm303AccelRegisters.LSM303_REGISTER_ACCEL_OUT_X_L_A;
import static com.adafruit.sensor.lsm303.Lsm303MagRegisters.LSM303_REGISTER_MAG_OUT_X_H_M;
import static com.adafruit.sensor.lsm303.Lsm303MagRegisters.LSM303_REGISTER_MAG_MR_REG_M;
import static com.adafruit.sensor.lsm303.Lsm303MagRegisters.LSM303_REGISTER_MAG_CRB_REG_M;

public class AdafruitLsm303
{
	private final int LSM303_ADDRESS_ACCEL = (0x32 >> 1);         // 0011001x
	private final int LSM303_ADDRESS_MAG = (0x3C >> 1);         // 0011110x
	private final int LSM303_ID = (0b11010100);

	private Lsm303AccelData accelData;    // Last read accelerometer data will be available here
	private Lsm303MagData magData;        // Last read magnetometer data will be available here

	public boolean begin()
	{
		Wire.begin();
		Serial.println("Wire");

		// Enable the accelerometer
		write8(LSM303_ADDRESS_ACCEL, LSM303_REGISTER_ACCEL_CTRL_REG1_A.getRegister(), 0x27);

		// Enable the magnetometer
		write8(LSM303_ADDRESS_MAG, LSM303_REGISTER_MAG_MR_REG_M.getRegister(), 0x00);

		return true;
	}

	public void read()
	{
		// Read the accelerometer
		Wire.beginTransmission((byte) LSM303_ADDRESS_ACCEL);
		Wire.write(LSM303_REGISTER_ACCEL_OUT_X_L_A.getRegister() | 0x80);
		Wire.endTransmission();
		Wire.requestFrom((byte) LSM303_ADDRESS_ACCEL, (byte) 6);

		// Wait around until enough data is available
		while (Wire.available() < 6)
			;

		int xlo = Wire.read();
		int xhi = Wire.read();
		int ylo = Wire.read();
		int yhi = Wire.read();
		int zlo = Wire.read();
		int zhi = Wire.read();

		// Shift values to create properly formed integer (low byte first)
		accelData.x = (xlo | (xhi << 8)) >> 4;
		accelData.y = (ylo | (yhi << 8)) >> 4;
		accelData.z = (zlo | (zhi << 8)) >> 4;

		// Read the magnetometer
		Wire.beginTransmission((byte) LSM303_ADDRESS_MAG);
		Wire.write(LSM303_REGISTER_MAG_OUT_X_H_M.getRegister());
		Wire.endTransmission();
		Wire.requestFrom((byte) LSM303_ADDRESS_MAG, (byte) 6);

		// Wait around until enough data is available
		while (Wire.available() < 6)
			;

		// Note high before low (different than accel)
		// X
		xhi = Wire.read();
		xlo = Wire.read();
		// Z
		zhi = Wire.read();
		zlo = Wire.read();
		// Y
		yhi = Wire.read();
		ylo = Wire.read();

		// Shift values to create properly formed integer (low byte first)
		magData.setX(xlo | (xhi << 8));
		magData.setY(ylo | (yhi << 8));
		magData.setZ(zlo | (zhi << 8));

		// ToDo: Calculate orientation
		magData.setOrientation(0.0f);
	}

	public void setMagGain(Lsm303MagGain gain)
	{
		write8(LSM303_ADDRESS_MAG, LSM303_REGISTER_MAG_CRB_REG_M.getRegister(), gain.getGain());
	}

	private void write8(int address, int reg, int value)
	{
		Wire.beginTransmission(address);
		Wire.write(reg);
		Wire.write(value);
		Wire.endTransmission();
	}

	private byte read8(byte address, byte reg)
	{
		byte value;

		Wire.beginTransmission(address);
		Wire.write(reg);
		Wire.endTransmission();
		Wire.requestFrom(address, (byte) 1);
		value = Wire.read();
		Wire.endTransmission();

		return value;
	}

}
