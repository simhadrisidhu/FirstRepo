package com.itaas.ott;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Locale;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class DESUtil {

	private static final String Algorithm = "DESede/ECB/PKCS5Padding";// DESede/ECB/PKCS5Padding;DESede

	private static final String DESede = "DESede";

	public static byte[] encrypt(byte[] keybyte, byte[] src)
			throws NoSuchAlgorithmException, NoSuchPaddingException, Exception {
		SecretKey deskey = new SecretKeySpec(keybyte, DESede);
		Cipher c1 = Cipher.getInstance(Algorithm);
		c1.init(Cipher.ENCRYPT_MODE, deskey);
		return c1.doFinal(src);

	}

	public static byte[] decrypt(byte[] keybyte, byte[] src)
			throws NoSuchAlgorithmException, NoSuchPaddingException, Exception {

		SecretKey deskey = new SecretKeySpec(keybyte, DESede);
		Cipher c1 = Cipher.getInstance(Algorithm);
		c1.init(002, deskey);
		return c1.doFinal(src);
	}

	public static String byte2hex(byte[] b) {
		StringBuffer hs = new StringBuffer();
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs.append("0").append(stmp);
			else
				hs.append(stmp);
		}
		return hs.toString().toUpperCase(Locale.getDefault());
	}

	public static byte[] hex2byte(String hexStr) {
		if (hexStr.length() % 2 != 0) {
			// AppLogger.error("hex2bytes's hexStr length is not even.");
			return null;
		}

		byte[] toBytes = new byte[hexStr.length() / 2];
		for (int i = 0, j = 0; i < hexStr.length(); j++, i = i + 2) {
			int tmpa = Integer.decode("0X" + hexStr.charAt(i) + hexStr.charAt(i + 1)).intValue();
			toBytes[j] = (byte) (tmpa & 0XFF);
		}
		return toBytes;
	}

	public static void main(String args[]) throws NoSuchAlgorithmException {
		
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		String md5Pwd = md5Pwd();
		
		System.out.println("md5Pwd =:"+md5Pwd);
		
		final byte[] rawKey = md5Pwd.getBytes();
		final byte[] keyBytes = new byte[24];
		
		for (int i = 0; i < rawKey.length; i++) {
			keyBytes[i] = rawKey[i];
		}

		for (int i = rawKey.length; i < keyBytes.length; i++) {
			keyBytes[i] = (byte) 0;
		}

		// Random+"$"+EncryToken+"$"+userid+"$"+terminalid+"$"+terminalip+"$"+mac+"$"+Reserved+"$"+"CTC"
		
		String szSrc = "20926330$FA68501CC6A491B065C18B73262A6999$ott19$00:00:00:00:00:00$192.168.21.1$00:00:00:00:00:00$Reserved$CTC";
		
		System.out.println("string before encrypt:" + szSrc);
		byte[] encoded = null;

		try {
			encoded = encrypt(keyBytes, szSrc.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		String result = byte2hex(encoded);
		System.out.println("string after encrypt::" + result);
		//return result;

		/*byte[] srcBytes = null;

		try {
			srcBytes = decrypt(keyBytes, encoded);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("string before decode: :" + (new String(srcBytes)));*/
	}
	
	private static String md5Pwd()throws NoSuchAlgorithmException{
		
		String plainPwd ="123123";
		byte[] id = plainPwd.getBytes();

		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(id);
		md.update("99991231".getBytes());  			// "99991231" mentioned in XML-API DOC

		byte[] buffer = md.digest();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i <buffer.length; i++) {
		sb.append(Integer.toHexString((int) buffer[i] & 0xff));
		}
		String md5Pwd = sb.substring(0, 8); 		// only use first 8 characters
		
		return md5Pwd;
	}
}
