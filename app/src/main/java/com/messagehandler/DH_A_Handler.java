package com.messagehandler;

import com.mApplication;
import com.messagemodel.BasicModel;
import com.sklois.haiyunKms.SoftLibs;

import java.security.Security;

import javax.crypto.SecretKey;

import udpReliable.JsonModel;

/**
 * Created by Xinzhe on 2016/4/28.
 */
public class DH_A_Handler extends BasicMessageHandler {
    String akey;
    String bkey;
    byte[] privateKeybytes= SoftLibs.getInstance().GetLocalPriKey(SoftLibs.SGD_KEYUSAGE_KEYEXCHANGE,mApplication.deviceId,null);
    String privateKey=new String(privateKeybytes);
    String akeyEncrypted;
    String bkeyEncrypted;
    String publicKeyofPeerCert=new String(mApplication.peerCertificate.getPublicKey().getEncoded());

    SecretKey symkey;
    public DH_A_Handler(BasicModel basicModel) {
        super(basicModel);

    }

    @Override
    public void run() {
        super.run();
        /**
         * 0.get encrypted key a and decrypt it with the primary key
         * 1.set the a key and get the b key
         * 2.save b key
         * 3.encrypt b key
         * 4.send b encrypted to peer
         * 5.get and set the symkey
         */

        akeyEncrypted=basicModel.string1;
        byte[] akeyDecrypted=SoftLibs.getInstance().DecryptByPriKey(com.sklois.haiyunKms.SoftLibs.ASYM_ALGO_RSA_2048,privateKey,akeyEncrypted.getBytes());
        akey=new String(akeyDecrypted);
        mApplication.akey=akey;

        bkey= mApplication.dh.getDH_b(akey);
        mApplication.bkey=this.bkey;

        /**
         * encrypt b key using the public key
         */
       byte[] bkeyEncryptedbytes=SoftLibs.getInstance().EncryptByPubkey(SoftLibs.ASYM_ALGO_RSA_2048,publicKeyofPeerCert,bkey.getBytes());
        bkeyEncrypted=new String(bkeyEncryptedbytes);


        /*
        get and set the symkey
         */
        if(mApplication.isAcive){

           symkey= mApplication.dh.getSymKeyInA();
        }else {
            symkey=mApplication.dh.getSymKeyInB();
        }
        mApplication.symkey=symkey;

        new MessagePacketerOfPeer(){
            @Override
            protected void setModel() {
                super.setModel();
                modeltosend.type= JsonModel.MESSAGE_TYPE.TYPE_DH_B;
                modeltosend.string1=DH_A_Handler.this.bkeyEncrypted;
            }
        }.send();

    }
}
