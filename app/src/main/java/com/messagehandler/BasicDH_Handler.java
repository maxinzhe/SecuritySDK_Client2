package com.messagehandler;

import android.util.Base64;
import android.util.Log;

import com.PKI.PrivateKeyUtil;
import com.PKI.RSACoder;
import com.mApplication;
import com.messagemodel.BasicModel;
import com.sklois.util.CertCodeUtil;
import com.voice.ThreadRecord;

import java.io.IOException;

import javax.crypto.SecretKey;

import udpReliable.JsonModel;


/**
 * Created by Xinzhe on 2016/4/28.
 */
public class BasicDH_Handler extends BasicMessageHandler {
    String keyString;
    String bkey;
    //byte[] privateKeybytes= SoftLibs.getInstance().GetLocalPriKey(SoftLibs.SGD_KEYUSAGE_KEYEXCHANGE, mApplication.deviceId,null);
    String privateKey;
    String keyEncrypted;
    String bkeyEncrypted;
    String publicKeyofPeerCert=new String(mApplication.peerCertificate.getPublicKey().getEncoded());

    SecretKey symkey;
    public BasicDH_Handler(BasicModel basicModel) throws IOException {
        super(basicModel);

        privateKey = new PrivateKeyUtil(mApplication.deviceId).readPrivateKeyfromSD();
    }

    /**
     * 1. save the key to a or b in the mApplicaion
     * 2. for DH_A,encrypt and sent b, for DH_fwwgbwbw
     */
    protected void  differABWork(){

    }

    /**
     * 1.sent ok the the server
     * 2.start the voice engine
     * 3.if is the negative end, then start an activity to handle the call.
     */

    public void onSymkeyFinished(){
    Log.i("BasicDH_Handler","onSymkeyFinshied");
        new MessagePacketerOfClient(){
            @Override
            protected void  setModel() {
                super.setModel();
                modeltosend.t = JsonModel.MESSAGE_TYPE.TYPE_DH_OK;
            }
        }.send();



        //start an activity to handle the call if negative:2 for the number of start negative activity
        if(!mApplication.isAcive){//the end receive b is active, so never execute these

            mApplication.UIThreadHandler.sendEmptyMessage(2);

            Log.i("test BasicDH_Handler","被动端发出handleMessage弹出来电画面");
            //start the voice engine in the receiver window button function.
        }else{
            //if this is the active end,then start the voice engine
            mApplication.player.play();
            new ThreadRecord().start();
            Log.i("test BasicDH_Handler","主动端开启播放和接收线程");
        }

    }

    @Override
    public void run() {
        super.run();
        /**
         * 0.get encrypted key received and decrypt it with the primary key
         * 1.set the key to dh and get the DH
         */

       // keyEncrypted =basicModel.s1;
        String [] keyEncryptedString=basicModel.dhKeyS;
        byte[] keyDecryptedbytes=null;
        try {
             keyDecryptedbytes= RSACoder.decrypteLongDatawithPrivateKeyafterBase64Decoded(keyEncryptedString,privateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String BkeyString=new CertCodeUtil().getBase64CodedStringfromBinaryBytes(keyDecryptedbytes);
        Log.i("test BasicDH_Handler","received encrypted key is"+BkeyString);

        Log.i("test BasicDH_Handler","received decrypted key is"+keyString);
        mApplication.dh.setDH_b_forA(BkeyString);

        differABWork();




        /*
        get and set the symkey
         */

           symkey= mApplication.dh.getSymKeyInA();
            byte[] symkeybytes=symkey.getEncoded();
            String symkeyString= Base64.encodeToString(symkeybytes,Base64.DEFAULT);
            mApplication.symkey=symkey;
            mApplication.symKeyString=symkeyString;
            Log.i("test BasicDH_Handler","主动发送端对称密钥symkey为 "+symkeyString);


        onSymkeyFinished();

    }
}