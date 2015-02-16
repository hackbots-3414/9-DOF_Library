package com.kauailabs.navx_mxp;

public class BoardID
{
	public byte type;
	public byte hw_rev;
	public byte fw_ver_major;
	public byte fw_ver_minor;
	public short fw_revision;
	public byte unique_id[];

	public BoardID()
	{
		unique_id = new byte[12];
	}
}
