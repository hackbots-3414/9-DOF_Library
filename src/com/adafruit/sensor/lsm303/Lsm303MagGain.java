package com.adafruit.sensor.lsm303;

public enum Lsm303MagGain
{
	LSM303_MAGGAIN_1_3(0x20),  // +/- 1.3
	LSM303_MAGGAIN_1_9(0x40),  // +/- 1.9
	LSM303_MAGGAIN_2_5(0x60),  // +/- 2.5
	LSM303_MAGGAIN_4_0(0x80),  // +/- 4.0
	LSM303_MAGGAIN_4_7(0xA0),  // +/- 4.7
	LSM303_MAGGAIN_5_6(0xC0),  // +/- 5.6
	LSM303_MAGGAIN_8_1(0xE0);   // +/- 8.1

	private int gain;
	
	Lsm303MagGain(int gain)
	{
		this.gain = gain;
	}
	
	public int getGain()
	{
		return this.gain;
	}
}
