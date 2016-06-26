package DH;


import android.util.Base64;

import com.google.common.collect.Maps;
import com.google.common.collect.ObjectArrays;
        //  import sun.misc.BASE64Decoder;
      //  import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;


/**
 * Created by xiang.li on 2015/3/4.
 * DH 加解密工具类
 */
public class DH {
    /**
     * 定义加密方式
     */
    private static final String KEY_DH = "DH";
    /**
     * 默认密钥字节数
     */

    private static final int KEY_SIZE = 512;
    /**
     * DH加密下需要一种对称加密算法对数据加密，这里我们使用DES，也可以使用其他对称加密算法
     */
    private static final String KEY_DH_DES = "AES";
    private static final String KEY_DH_PUBLICKEY = "DHPublicKey";
    private static final String KEY_DH_PRIVATEKEY = "DHPrivateKey";


    String privateKeyA,privateKeyB,publicKeyA,publicKeyB;

    /**
     * 初始化甲方密钥
     * @return
     */
    public static Map<String, Object> initA() {
        Map<String, Object> map = null;
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance(KEY_DH);
            generator.initialize(KEY_SIZE);
            KeyPair keyPair = generator.generateKeyPair();
            // 甲方公钥
            DHPublicKey publicKey = (DHPublicKey) keyPair.getPublic();
            // 甲方私钥
            DHPrivateKey privateKey = (DHPrivateKey) keyPair.getPrivate();
            map = Maps.newHashMap();
            map.put(KEY_DH_PUBLICKEY, publicKey);
            map.put(KEY_DH_PRIVATEKEY, privateKey);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 初始化乙方密钥
     * @param key 甲方密钥
     * @return
     */
    public static Map<String, Object> init(String key) {
        Map<String, Object> map = null;
        try {
            // 解析甲方密钥
            byte[] bytes = decryptBase64(key);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);
            KeyFactory factory = KeyFactory.getInstance(KEY_DH);
            PublicKey publicKey = factory.generatePublic(keySpec);

            // 由甲方公钥构建乙方密钥
            DHParameterSpec spec = ((DHPublicKey) publicKey).getParams();
            KeyPairGenerator generator = KeyPairGenerator.getInstance(KEY_DH);
            generator.initialize(spec);
            KeyPair keyPair = generator.generateKeyPair();
            // 乙方公钥
            DHPublicKey dhPublicKey = (DHPublicKey) keyPair.getPublic();
            // 乙方私钥
            DHPrivateKey dhPrivateKey = (DHPrivateKey) keyPair.getPrivate();
            map = Maps.newHashMap();
            map.put(KEY_DH_PUBLICKEY, dhPublicKey);
            map.put(KEY_DH_PRIVATEKEY, dhPrivateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * DH 加密
     * @param data 带加密数据
     * @param publicKey 甲方公钥
     * @param privateKey 乙方私钥
     * @return
     */
    public static byte[] encryptDH(byte[] data, String publicKey, String privateKey) {
        byte[] bytes = null;
        try {
            // 生成本地密钥
            SecretKey secretKey = getSecretKey(publicKey, privateKey);
            // 数据加密
            Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            bytes = cipher.doFinal(data);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    /**
     * DH 解密
     * @param data 待解密数据
     * @param publicKey 乙方公钥
     * @param privateKey 甲方私钥
     * @return
     */
    public static byte[] decryptDH(byte[] data, String publicKey, String privateKey) {
        byte[] bytes = null;
        try {
            // 生成本地密钥
            SecretKey secretKey = getSecretKey(publicKey, privateKey);
            // 数据解密
            Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            bytes = cipher.doFinal(data);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    /**
     * 取得私钥
     * @param map
     * @return
     */
    public static String getPrivateKey(Map<String, Object> map) {
        String str = "";
        try {
            Key key = (Key) map.get(KEY_DH_PRIVATEKEY);
            str = encryptBase64(key.getEncoded());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 取得公钥
     * @param map
     * @return
     */
    public static String getPublicKey(Map<String, Object> map) {
        String str = "";
        try {
            Key key = (Key) map.get(KEY_DH_PUBLICKEY);
            str = encryptBase64(key.getEncoded());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 构建本地密钥
     * @param publicKey 公钥
     * @param privateKey 私钥
     * @return
     */
    private static SecretKey getSecretKey(String publicKey, String privateKey) {
        SecretKey secretKey = null;
        try {

            KeyFactory factory = KeyFactory.getInstance(KEY_DH);
            // 初始化公钥
            byte[] publicBytes = decryptBase64(publicKey);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
            PublicKey localPublicKey = factory.generatePublic(keySpec);

            // 初始化私钥
            byte[] privateBytes = decryptBase64(privateKey);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(privateBytes);
            PrivateKey localPrivateKey = factory.generatePrivate(spec);

            KeyAgreement agreement = KeyAgreement.getInstance(factory.getAlgorithm());
            agreement.init(localPrivateKey);
            agreement.doPhase(localPublicKey, true);

            // 生成本地密钥
            secretKey = agreement.generateSecret(KEY_DH_DES);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return secretKey;
    }

    /**
     * BASE64 解密
     * @param key 需要解密的字符串
     * @return 字节数组
     * @throws Exception
     */
    public static byte[] decryptBase64(String key) throws Exception {
        return Base64.decode(key,Base64.DEFAULT);
    }

    /**
     * BASE64 加密
     * @param key 需要加密的字节数组
     * @return 字符串
     * @throws Exception
     */
    public static String encryptBase64(byte[] key) throws Exception {
        return (Base64.encodeToString(key,Base64.DEFAULT));
    }

  //  /**
  //   * 测试方法
  //   * @param args
  //   */
//    public static void main(String[] args) {
//        // 生成甲方密钥对
//        Map<String, Object> mapA = init();
//        String publicKeyA = getPublicKey(mapA);
//        String privateKeyA = getPrivateKey(mapA);
//        System.out.println("甲方公钥:\n" + publicKeyA);
//        System.out.println("甲方私钥:\n" + privateKeyA);
//
//
//        // 由甲方公钥产生本地密钥对//即乙接到甲传来的
//        Map<String, Object> mapB = init(publicKeyA);
//        String publicKeyB = getPublicKey(mapB);
//        String privateKeyB = getPrivateKey(mapB);
//        System.out.println("乙方公钥:\n" + publicKeyB);
//        System.out.println("乙方私钥:\n" + privateKeyB);
//
//        String word = "abc";
//        System.out.println("原文: " + word);
//
//        // 由甲方公钥，乙方私钥构建密文,现在在乙方内部
//        byte[] encWord = encryptDH(word.getBytes(), publicKeyA, privateKeyB);

        // 由乙方公钥，甲方私钥解密，现在在甲方内部
  //      byte[] decWord = decryptDH(encWord, publicKeyB,privateKeyA);
  //      System.out.println("解密: " + new String(decWord));

    //}

    public String getDH_a(){
        Map<String,Object> mapA=initA();
         privateKeyA=getPrivateKey(mapA);
         publicKeyA=getPublicKey(mapA);
        return publicKeyA;
    }
    public void setDH_b_forA(String publicKeyB){
        this.publicKeyB=publicKeyB;
    }
    public String getDH_b(String publicA){
        this.publicKeyA=publicA;
        Map<String,Object> mapB=init(publicA);
         privateKeyB=getPrivateKey(mapB);
         publicKeyB=getPublicKey(mapB);
        return publicKeyB;
    }
    public SecretKey getSymKeyInA(){
        return getSecretKey(publicKeyB,privateKeyA);
    }
    public SecretKey getSymKeyInB(){
        return getSecretKey(publicKeyA,privateKeyB);
    }
}
