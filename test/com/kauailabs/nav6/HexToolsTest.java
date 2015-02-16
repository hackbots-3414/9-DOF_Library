package com.kauailabs.nav6;

import static org.junit.Assert.*;

import org.junit.Test;

public class HexToolsTest
{

	@Test
	public void testByteToHex()
	{
		byte[] dest = new byte[20];
		HexTools.byteToHex((byte)'A', dest, 0);
		assertEquals((byte)52, dest[0]);
		assertEquals((byte)49, dest[1]);
	}

}
