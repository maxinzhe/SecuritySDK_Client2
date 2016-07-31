package com.messagehandler;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.PKI.PrivateKeyUtil;
import com.PKI.RSACoder;
import com.ccit.security.sdk.clientDemo.AnswerWindow;
import com.ccit.security.sdk.clientDemo.CallWindow;
import com.mApplication;
import com.messagemodel.BasicModel;
import com.sklois.util.CertCodeUtil;

import java.io.IOException;

import javax.crypto.SecretKey;

import udpReliable.JsonModel;

/**
 * Created by Xinzhe on 2016/4/28.
 */
public class DH_A_Handler extends BasicMessageHandler {
    String akey;
    String bkey;
    String privateKey;


    {
        Log.i("test DH_A_Handler", "Device id to get privatekey is" +mApplication.deviceId);
    }

    String akeyEncrypted;
    String bkeyEncrypted;
    //byte[][] akeyBytes;
    String [] akeyStrings ;
    byte[] publicKeyofPeerCertBytes = mApplication.peerCertificate.getPublicKey().getEncoded();
    String publicKeyofPeerCertString=new CertCodeUtil().getBase64CodedStringfromBinaryBytes(publicKeyofPeerCertBytes);
    SecretKey symkey;
    public DH_A_Handler(BasicModel basicModel) {
        super(basicModel);
        mApplication.thread_dh_A_Handler=DH_A_Handler.this;
       // byte[] privateKeybytes= SoftLibs.getInstance().GetLocalPriKey(SoftLibs.SGD_KEYUSAGE_KEYEXCHANGE,mApplication.deviceId,"12345678");
        try {

                     privateKey= new PrivateKeyUtil(mApplication.deviceId).readPrivateKeyfromSD();

        } catch (IOException e) {
            e.printStackTrace();
        }

            Log.i("test DH_A_Handler","privateKeyString: "+privateKey);


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
        mApplication.setCallingFlag();
        akeyStrings=basicModel.dhKeyS;
        //akeyEncrypted=basicModel.s1;
        byte [] akeyDecrypted=null;
        try {
            akeyDecrypted=RSACoder.decrypteLongDatawithPrivateKeyafterBase64Decoded(akeyStrings,privateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }

        akey=new CertCodeUtil().getBase64CodedStringfromBinaryBytes(akeyDecrypted);
        Log.i("test DH_A_Handler"," received akeyDecrypted is :"+akey);
        mApplication.akey=akey;

        //mApplication.dh.getDH_b(akey);

        Log.i("test DH_Handler","正在生成dh_B……");
        bkey= mApplication.dh.getDH_b(akey);
        //bkey="MIIBpzCCARsGCSqGSIb3DQEDATCCAQwCgYEA/X9TgR11EilS30qcLuzk5/YRt1I870QAwx4/gLZRJmlFXUAiUftZPY1Y+r/F9bow9subVWzXgTuAHTRv8mZgt2uZUKWkn5/oBHsQIsJPu6nX/rfGG/g7V+fGqKYVDwT7g/bTxR7DAjVUE1oWkTL2dfOuK2HXKu/yIgMZndFIAccCgYEA9+GghdabPd7LvKtcNrhXuXmUr7v6OuqC+VdMCz0HgmdRWVeOutRZT+ZxBxCBgLRJFnEj6EwoFhO3zwkyjMim4TwWeotUfI0o4KOuHiuzpnWRbqN/C/ohNWLx+2J6ASQ7zKTxvqhRkImog9/hWuWfBpKLZl6Ae1UlZAFMO/7PSSoCAgIAA4GFAAKBgQCsWfGs5s8Y9drX0tz7ADxEQ+n3eFFSvgluaucO2pSZYMb7PT1qtPb9b3yjQSamwUrzDa5ZEkX2vYyxN8GaPkyYrZa9J3uwUu/FjI9AYCYh0YQAPpL74iKuk5g7yGJPdtKS7A96mgPuZbwIUIMMo2f5RUb6xoDATeu8xh+v/DKmSw==";
        Log.i("test DH_Handler","dh_B生成完毕:"+bkey);
        byte[] symkeybyte=mApplication.dh.getSymKeyInB().getEncoded();
        String symkeyString= Base64.encodeToString(symkeybyte,Base64.DEFAULT);

        mApplication.symkey=symkey;
        mApplication.symKeyString=symkeyString;
        Log.i("test DH_Handler","本端生成的对称密钥为"+symkeyString);


        mApplication.bkey=this.bkey;

        /**
         * encrypt b key using the public key
         */
        byte[] bkeyBytes=new CertCodeUtil().getBase64Decode(bkey);

        String [] encryptedBkeyStrings=null;
        try {
            encryptedBkeyStrings=RSACoder.encryptLongDatawithPublicKeyandBase64Coded(bkeyBytes,publicKeyofPeerCertString);
        } catch (Exception e) {
            e.printStackTrace();
        }



        /*
        get and set the symkey
         */
        if(mApplication.isAcive){//the end that receive dha is always negative
            Log.e("test DH_A_Handler","这条语句不会被输出");

            Log.i("test DH_A_Handler","The Active End get symkey:"+new CertCodeUtil().getBase64CodedStringfromBinaryBytes(symkey.getEncoded()));
        }else {

            Log.e("test DH_A_Handler","被动端弹出接听activty");

            Intent intent=new Intent(mApplication.getInstance(),AnswerWindow.class) ;
            intent.putExtra("caller_name",mApplication.targetName);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mApplication.getInstance().startActivity(intent);
          //  mApplication.UIThreadHandler.sendEmptyMessage(2);
        }

        final String[] finalEncryptedBkeyStrings = encryptedBkeyStrings;
        new MessagePacketerOfPeer(){
            @Override
            protected void setModel() {
                super.setModel();
                modeltosend.t = JsonModel.MESSAGE_TYPE.TYPE_DH_B;

                modeltosend.dhKeyS= finalEncryptedBkeyStrings;
            }
        }.send();

    }
}
