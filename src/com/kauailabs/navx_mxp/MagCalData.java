package com.kauailabs.navx_mxp;

class MagCalData
{
	byte action;
	public short mag_bias[]; /* 3 Values */
	public float mag_xform[][]; /* 3 x 3 Values */
	public float earth_mag_field_norm;

	public MagCalData()
	{
		mag_bias = new short[3];
		mag_xform = new float[3][3];
	}
}
