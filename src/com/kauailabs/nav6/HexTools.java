package com.kauailabs.nav6;

public class HexTools
{
	final protected static byte[] hexArray = new byte[] { (byte) '0', (byte) '1', (byte) '2', (byte) '3', (byte) '4', (byte) '5', (byte) '6',
			(byte) '7', (byte) '8', (byte) '9', (byte) 'A', (byte) 'B', (byte) 'C', (byte) 'D', (byte) 'E', (byte) 'F' };

	public static void byteToHex(byte thebyte, byte[] dest, int offset)
	{
		int v = thebyte & 0xFF;
		dest[offset + 0] = hexArray[v >> 4];
		dest[offset + 1] = hexArray[v & 0x0F];
	}

}
