package com.adafruit.sensor.l3gd20;

import static com.adafruit.sensor.l3gd20.L3gd20Registers.L3GD20_REGISTER_CTRL_REG1;
import static com.adafruit.sensor.l3gd20.L3gd20Registers.L3GD20_REGISTER_CTRL_REG4;
import static com.adafruit.sensor.l3gd20.L3gd20Registers.L3GD20_REGISTER_OUT_X_L;
import static com.adafruit.sensor.l3gd20.L3gd20Registers.L3GD20_REGISTER_WHO_AM_I;
import edu.wpi.first.wpilibj.I2C;

public class AdafruitL3gd20_I2C
{
	private final int L3GD20_ADDRESS = 0x6B;        		// 1101011
	private final int L3GD20_POLL_TIMEOUT = 100;         	// Maximum number of read attempts
	private final int L3GD20_ID = 0b11010100;
	private final float L3GD20_SENSITIVITY_250DPS = 0.00875f;      // Roughly 22/256 for fixed point match
	private final float L3GD20_SENSITIVITY_500DPS = 0.0175f;       // Roughly 45/256
	private final float L3GD20_SENSITIVITY_2000DPS = 0.070f;       // Roughly 18/256
	private final float L3GD20_DPS_TO_RADS = 0.017453293f;  // degree/s to rad/s multiplier
	
	private L3gd20Data data;    // Last read will be available here
	private byte address;
	private L3gd20Range range;
	private int _miso;
	private int _mosi;
	private int _clk;
	private int _cs;
	private I2C m_i2c;

	/**
	 * Constructor for I<sup>2</sup>C communications
	 */
	public AdafruitL3gd20_I2C(int moduleNumber)
	{
		DigitalModule module = DigitalModule.getInstance(moduleNumber);
		m_i2c = module.getI2C(kAddress);

		// use i2c
		this._cs = -1;		// Signals i2C communications
		this._mosi = -1;
		this._miso = -1;
		this._clk = -1;
	}

	public boolean begin(L3gd20Range range, byte address)
	{
		Wire.begin();

		/*
		 * Make sure we have the correct chip ID since this checks for correct address and that the IC is properly connected
		 */
		if (read8(L3GD20_REGISTER_WHO_AM_I.getRegister()) != L3GD20_ID)
		{
			return false;
		}

		/*
		 * Set CTRL_REG1 (0x20) ==================================================================== BIT Symbol Description Default --- ------
		 * --------------------------------------------- ------- 7-6 DR1/0 Output data rate 00 5-4 BW1/0 Bandwidth selection 00 3 PD 0 = Power-down
		 * mode, 1 = normal/sleep mode 0 2 ZEN Z-axis enable (0 = disabled, 1 = enabled) 1 1 YEN Y-axis enable (0 = disabled, 1 = enabled) 1 0 XEN
		 * X-axis enable (0 = disabled, 1 = enabled) 1
		 */

		/* Switch to normal mode and enable all three channels */
		write8(L3GD20_REGISTER_CTRL_REG1.getRegister(), 0x0F);
		/*
		 * Set CTRL_REG2 (0x21) ==================================================================== BIT Symbol Description Default --- ------
		 * --------------------------------------------- ------- 5-4 HPM1/0 High-pass filter mode selection 00 3-0 HPCF3..0 High-pass filter cutoff
		 * frequency selection 0000
		 */

		/* Nothing to do ... keep default values */

		/*
		 * Set CTRL_REG3 (0x22) ==================================================================== BIT Symbol Description Default --- ------
		 * --------------------------------------------- ------- 7 I1_Int1 Interrupt enable on INT1 (0=disable,1=enable) 0 6 I1_Boot Boot status on
		 * INT1 (0=disable,1=enable) 0 5 H-Lactive Interrupt active config on INT1 (0=high,1=low) 0 4 PP_OD Push-Pull/Open-Drain (0=PP, 1=OD) 0 3
		 * I2_DRDY Data ready on DRDY/INT2 (0=disable,1=enable) 0 2 I2_WTM FIFO wtrmrk int on DRDY/INT2 (0=dsbl,1=enbl) 0 1 I2_ORun FIFO overrun int
		 * on DRDY/INT2 (0=dsbl,1=enbl) 0 0 I2_Empty FIFI empty int on DRDY/INT2 (0=dsbl,1=enbl) 0
		 */

		/* Nothing to do ... keep default values */

		/*
		 * Set CTRL_REG4 (0x23) ==================================================================== BIT Symbol Description Default --- ------
		 * --------------------------------------------- ------- 7 BDU Block Data Update (0=continuous, 1=LSB/MSB) 0 6 BLE Big/Little-Endian (0=Data
		 * LSB, 1=Data MSB) 0 5-4 FS1/0 Full scale selection 00 00 = 250 dps 01 = 500 dps 10 = 2000 dps 11 = 2000 dps 0 SIM SPI Mode (0=4-wire,
		 * 1=3-wire) 0
		 */

		/* Adjust resolution if requested */
		switch (range) 
		{
			case L3DS20_RANGE_250DPS:
				write8(L3GD20_REGISTER_CTRL_REG4, 0x00);
				break;
			case L3DS20_RANGE_500DPS:
				write8(L3GD20_REGISTER_CTRL_REG4, 0x10);
				break;
			case L3DS20_RANGE_2000DPS:
				write8(L3GD20_REGISTER_CTRL_REG4, 0x20);
				break;
		}
		/* ------------------------------------------------------------------ */

		/*
		 * Set CTRL_REG5 (0x24) ==================================================================== BIT Symbol Description Default --- ------
		 * --------------------------------------------- ------- 7 BOOT Reboot memory content (0=normal, 1=reboot) 0 6 FIFO_EN FIFO enable (0=FIFO
		 * disable, 1=enable) 0 4 HPen High-pass filter enable (0=disable,1=enable) 0 3-2 INT1_SEL INT1 Selection config 00 1-0 OUT_SEL Out selection
		 * config 00
		 */

		/* Nothing to do ... keep default values */
		/* ------------------------------------------------------------------ */

		return true;
	}

	void read()
	{ 
		int xhi;
		int xlo;
		int ylo;
		int yhi;
		int zlo;
		int zhi;

		Wire.beginTransmission(address);
		// Make sure to set address auto-increment bit
		Wire.write(L3GD20_REGISTER_OUT_X_L.getRegister() | 0x80);
		Wire.endTransmission();
		Wire.requestFrom(address, (byte)6);
    
		// Wait around until enough data is available
		while (Wire.available() < 6);
    
		xlo = Wire.read();
		xhi = Wire.read();
		ylo = Wire.read();
		yhi = Wire.read();
		zlo = Wire.read();
		zhi = Wire.read();

		// Shift values to create properly formed integer (low byte first)
		data.setX(xlo | (xhi << 8));
		data.setY(ylo | (yhi << 8));
		data.setZ(zlo | (zhi << 8));
	  
		// Compensate values depending on the resolution
		switch(range)
		{
			case L3DS20_RANGE_250DPS:
				data.setX(data.getX() * L3GD20_SENSITIVITY_250DPS);
				data.setY(data.getY() * L3GD20_SENSITIVITY_250DPS);
				data.setZ(data.getZ() * L3GD20_SENSITIVITY_250DPS);
				break;
			case L3DS20_RANGE_500DPS:
				data.setX(data.getX() * L3GD20_SENSITIVITY_500DPS);
				data.setY(data.getY() * L3GD20_SENSITIVITY_500DPS);
				data.setZ(data.getZ() * L3GD20_SENSITIVITY_500DPS);
				break;
			case L3DS20_RANGE_2000DPS:
				data.setX(data.getX() * L3GD20_SENSITIVITY_2000DPS);
				data.setY(data.getY() * L3GD20_SENSITIVITY_2000DPS);
				data.setZ(data.getZ() * L3GD20_SENSITIVITY_2000DPS);
				break;
		}
	}

	/***************************************************************************
	 PRIVATE FUNCTIONS
	 ***************************************************************************/
	private void write8(L3gd20Registers reg, byte value)
	{
		Wire.beginTransmission(address);
		Wire.write(reg.getRegister());
		Wire.write(value);
		Wire.endTransmission();
	}

	private byte read8(L3gd20Registers reg)
	{
		byte value;

		Wire.beginTransmission(address);
		Wire.write(reg.getRegister());
		Wire.endTransmission();
		Wire.requestFrom(address, (byte)1);
		value = Wire.read();
		Wire.endTransmission();

		return value;
	}
}
