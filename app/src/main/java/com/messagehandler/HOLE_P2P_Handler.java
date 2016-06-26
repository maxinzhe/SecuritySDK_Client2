package com.messagehandler;

import android.util.Log;

import com.mApplication;
import com.messagemodel.BasicModel;
import com.sklois.haiyunKms.SoftLibs;

import udpReliable.JsonModel;

/**
 * Created by Xinzhe on 2016/4/26.
 */
public class HOLE_P2P_Handler extends BasicMessageHandler{
    public HOLE_P2P_Handler(BasicModel basicModel) {
        super(basicModel);
    }
    /*
    1.send the success message to server
    2.start to send the exchange cert
     */

    @Override
    public void run() {
        super.run();

        new MessagePacketerOfClient(){
            @Override
            protected void setModel() {
                super.setModel();
                modeltosend.t = JsonModel.MESSAGE_TYPE.TYPE_P2P_OK;
            }

        }.send();
        new MessagePacketerOfPeer(){
            @Override
            protected void setModel() {
                super.setModel();
                modeltosend.t = JsonModel.MESSAGE_TYPE.TYPE_CER_DATA;
                try{
                    modeltosend.s1 = SoftLibs.getInstance().ReadLocalCert(mApplication.deviceId,SoftLibs.SGD_KEYUSAGE_SIGN);
                    //下面使用本地证书验证是否可以进行非对称加密
                  //  Certificate cert=new CertUtil(modeltosend.s1).getCertObj();
                 //   byte[] publicKeybyte=cert.getPublicKey().getEncoded();
                 //   String publicKeyString= new CertCodeUtil().getBase64CodedStringfromBinaryBytes(publicKeybyte);
                  //  Log.i("test HOLE_P2P_Handler","测试本地证书中获取的publicKey为: "+publicKeyString);

                    // byte [] encrpytedbyte=SoftLibs.getInstance().EncryptByPubkey(SoftLibs.ASYM_ALGO_RSA_1024,publicKeyString,"我是明文".getBytes());
                   // byte[] encrpytedbyte=RSACoder.encryptByPublicKey("我是明文".getBytes(),publicKeyString);
                //    String encryptedBase64=new CertCodeUtil().getBase64CodedStringfromBinaryBytes(encrpytedbyte);
                 //   Log.i("test HOLE_P2P_Handle","加密过的密文为"+encryptedBase64);

                    //下面获取本地私钥
                  //  PrivateKeyUtil privateKeyUtil=new PrivateKeyUtil(mApplication.deviceId);
                  //  String privateKeyString=privateKeyUtil.readPrivateKeyfromSD();
                  //  Log.i("test HOLE_P2P_Handle","获取的私钥为"+privateKeyString);
                    //下面使用私钥进行解密

                   // byte[] decryptedbytes=SoftLibs.getInstance().DecryptByPriKey(SoftLibs.ASYM_ALGO_RSA_1024,privateKeyString,encrpytedbyte);
                   // byte[] decryptedbytes=RSACoder.decryptByPrivateKey(encrpytedbyte,privateKeyString);
                 //   Log.i("test HOLE_P2P_Handle","解密还原的明文为: "+new String(decryptedbytes));




                    Log.i("test cert","read local cert:"+modeltosend.s1);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }
                .send();
    }

}
