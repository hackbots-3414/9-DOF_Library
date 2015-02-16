package com.adafruit.sensor.lsm303;

import com.adafruit.sensor.Sensor;
import com.adafruit.sensor.SensorEvent;

public class AdafruitLsm303AccelUnified extends AdafruitLsm303Unified
{
    private Lsm303AccelData _accelData;   // Last read accelerometer data will be available here

    public AdafruitLsm303AccelUnified(int sensorID)
    {
		super(sensorID);
    }

    public boolean begin()
    {
		// Enable I2C
		Wire.begin();
		
		// Enable the accelerometer (100Hz)
		write8(LSM303_ADDRESS_ACCEL, Lsm303AccelRegisters.LSM303_REGISTER_ACCEL_CTRL_REG1_A.getRegister(), 0x57);
		 
		return true;
   }

   public SensorEvent getEvent()
   {
	   	// Read new data
	   	read();

   		float x = _accelData.x * _lsm303Accel_MG_LSB * SENSORS_GRAVITY_STANDARD;
   		float y = _accelData.y * _lsm303Accel_MG_LSB * SENSORS_GRAVITY_STANDARD;
   		float z = _accelData.z * _lsm303Accel_MG_LSB * SENSORS_GRAVITY_STANDARD;
	   	SensorEvent event = new SensorEvent(this._sensorID, SENSOR_TYPE_ACCELEROMETER, mills(), new SensorVector(x,y,z));
   }

   public Sensor getSensor() 
   {
	   return new Sensor("LSM303",1, this._sensorID, SENSOR_TYPE_ACCELEROMETER, 0, 0.0f, 0.0f, 0.0f, 0);
   }

   private void read()
   {
     // Read the accelerometer
   	Wire.beginTransmission((byte)LSM303_ADDRESS_ACCEL);
   	Wire.send(LSM303_REGISTER_ACCEL_OUT_X_L_A | 0x80);
   	Wire.endTransmission();
   	Wire.requestFrom((byte)LSM303_ADDRESS_ACCEL, (byte)6);

   	// Wait around until enough data is available
   	while (Wire.available() < 6);

       Integer xlo = Wire.receive();
       Integer xhi = Wire.receive();
       Integer ylo = Wire.receive();
       Integer yhi = Wire.receive();
       Integer zlo = Wire.receive();
       Integer zhi = Wire.receive();

       // Shift values to create properly formed integer (low byte first)
       _accelData.setX((int)(xlo | (xhi << 8)) >> 4);
       _accelData.setY((int)(ylo | (yhi << 8)) >> 4);
       _accelData.setZ((int)(zlo | (zhi << 8)) >> 4);
   }
}
