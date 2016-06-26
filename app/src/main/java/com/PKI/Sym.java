package com.PKI;

/**
 * Created by Xinzhe on 2016/5/30.
 */

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;

public class Sym {
    public static String DES = "AES"; // optional value AES/DES/DESede

    public static String CIPHER_ALGORITHM = "AES"; // optional value AES/DES/DESede


    public static Key getSecretKey(String key) throws Exception{
        SecretKey securekey = null;
        if(key == null){
            key = "";
        }
        KeyGenerator keyGenerator = KeyGenerator.getInstance(DES);
        SecureRandom sr=SecureRandom.getInstance("SHA1PRNG","Crypto");
        sr.setSeed(key.getBytes());
        keyGenerator.init(128,sr);
        securekey = keyGenerator.generateKey();
        return securekey;
    }

    public static byte[] encrypt(byte[] data,String key) throws Exception {

       // SecureRandom sr = new SecureRandom();
        Key securekey = getSecretKey(key);

       // SecretKey sk=new SecretKeySpec(key.getBytes(),DES);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, securekey);
        byte[] bt = cipher.doFinal(data);
        return bt;
    }


    public static byte[] detrypt(byte[] message,String key) throws Exception{
       // SecureRandom sr = new SecureRandom();
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        Key securekey = getSecretKey(key);
      // SecretKey ks=new SecretKeySpec(key.getBytes(),DES);
        cipher.init(Cipher.DECRYPT_MODE, securekey);
        byte[] res = message;
        res = cipher.doFinal(res);
        return res;
    }

    public static void main(String[] args)throws Exception{
        byte[] message = "password".getBytes();
        String key = "";
        byte[] entryptedMsg = encrypt(message,key);
        System.out.println("encrypted message is below :");
        System.out.println(entryptedMsg);

        byte[] decryptedMsg = detrypt(entryptedMsg,key);
        System.out.println("decrypted message is below :");
        System.out.println(decryptedMsg);
    }
}
