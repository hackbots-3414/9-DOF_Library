package com.adafruit.sensor.lsm303;

public enum Lsm303MagRegisters
{
    LSM303_REGISTER_MAG_CRA_REG_M(0x00),
    LSM303_REGISTER_MAG_CRB_REG_M(0x01),
    LSM303_REGISTER_MAG_MR_REG_M(0x02),
    LSM303_REGISTER_MAG_OUT_X_H_M(0x03),
    LSM303_REGISTER_MAG_OUT_X_L_M(0x04),
    LSM303_REGISTER_MAG_OUT_Z_H_M(0x05),
    LSM303_REGISTER_MAG_OUT_Z_L_M(0x06),
    LSM303_REGISTER_MAG_OUT_Y_H_M(0x07),
    LSM303_REGISTER_MAG_OUT_Y_L_M(0x08),
    LSM303_REGISTER_MAG_SR_REG_Mg(0x09),
    LSM303_REGISTER_MAG_IRA_REG_M(0x0A),
    LSM303_REGISTER_MAG_IRB_REG_M(0x0B),
    LSM303_REGISTER_MAG_IRC_REG_M(0x0C),
    LSM303_REGISTER_MAG_TEMP_OUT_H_M(0x31),
    LSM303_REGISTER_MAG_TEMP_OUT_L_M(0x32);

    private int register;
    
    private Lsm303MagRegisters(int register)
    {
    	this.register = register;
    }
    
    public int getRegister()
    {
    	return this.register;
    }
}
