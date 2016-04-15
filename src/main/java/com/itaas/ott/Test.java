package com.itaas.ott;

import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Locale;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Test {
	
	private static final String Algorithm = "DESede/ECB/PKCS5Padding";
	
	private static final String DESede = "DESede";
	
	public static void main(String[] args) {
		
		String data ="20926330$AD75B1697FB5EB6345B2D412124030D2$10086$10086$10.164.111$ABCDEFGH$Reserved$CTC";
		
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		final byte[] rawKey = "db90e7eb".getBytes();
		final byte[] keyBytes = new byte[24];
		
		for (int i = 0; i <rawKey.length; i++)
		{
		keyBytes[i] = rawKey[i];
		}

		for (int i = rawKey.length; i <keyBytes.length; i++)
		{
		keyBytes[i] = (byte)0;
		}
		
		
		byte[] encoded = null;
		
		try
		{
		encoded = encrypt(keyBytes, data.getBytes());
		}
		catch (Exception e)
		{
		e.printStackTrace();
		}
		
		String result = byte2hex(encoded);
		System.out.println("string after encrypt::" + byte2hex(encoded));
		
	}
	
	public static byte[] encrypt(byte[] keybyte, byte[] src)
			throws NoSuchAlgorithmException, NoSuchPaddingException, Exception
			{
			SecretKey deskey = new SecretKeySpec(keybyte, DESede);
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			return c1.doFinal(src);
			}
	
	
	public static String byte2hex(byte[] b)
	{
	StringBuffer hs = new StringBuffer();
	String stmp = "";
	for (int n = 0; n <b.length; n++)
	{
	stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
	if (stmp.length() == 1)
	hs.append("0").append(stmp);
	else
	hs.append(stmp);
	}
	return hs.toString().toUpperCase(Locale.getDefault());
	}

}
