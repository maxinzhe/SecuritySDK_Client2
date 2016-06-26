package com.PKI;


import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Base64Encoder;

import Decoder.BASE64Encoder;


public class Coder {
	public static byte[]decryptBASE64(String string){
		return Base64.decode(string);

				
	}
	public static String encryptBASE64(byte[] bs){
		return new BASE64Encoder().encode(bs);
	}
}
