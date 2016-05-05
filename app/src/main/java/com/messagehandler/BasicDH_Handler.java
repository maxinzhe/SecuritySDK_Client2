package com.messagehandler;

import com.mApplication;
import com.messagemodel.BasicModel;
import com.sklois.haiyunKms.SoftLibs;
import com.voice.ThreadRecord;

import javax.crypto.SecretKey;

import udpReliable.JsonModel;


/**
 * Created by Xinzhe on 2016/4/28.
 */
public class BasicDH_Handler extends BasicMessageHandler {
    String keyString;
    String bkey;
    byte[] privateKeybytes= SoftLibs.getInstance().GetLocalPriKey(SoftLibs.SGD_KEYUSAGE_KEYEXCHANGE, mApplication.deviceId,null);
    String privateKey=new String(privateKeybytes);
    String keyEncrypted;
    String bkeyEncrypted;
    String publicKeyofPeerCert=new String(mApplication.peerCertificate.getPublicKey().getEncoded());

    SecretKey symkey;
    public BasicDH_Handler(BasicModel basicModel) {
        super(basicModel);

    }

    /**
     * 1. save the key to a or b in the mApplicaion
     * 2. for DH_A,encrypt and sent b, for DH_B
     */
    protected void  differABWork(){

    }

    /**
     * 1.sent ok the the server
     * 2.start the voice engine
     * 3.if is the negative end, then start an activity to handle the call.
     */
    public void onSymkeyFinished(){
        new MessagePacketerOfClient(){
            @Override
            protected void  setModel() {
                super.setModel();
                modeltosend.type= JsonModel.MESSAGE_TYPE.TYPE_DH_OK;
            }
        }.send();



        //start an activity to handle the call if negative:2 for the number of start negative activity
        if(!mApplication.isAcive){

            mApplication.handler.sendEmptyMessage(2);
            //start the voice engine in the receiver window button function.
        }else{
            //if this is the active end,then start the voice engine
            mApplication.player.play();
            new ThreadRecord().start();
        }

    }

    @Override
    public void run() {
        super.run();
        /**
         * 0.get encrypted key received and decrypt it with the primary key
         * 1.set the key to dh and get the DH
         */

        keyEncrypted =basicModel.string1;
        byte[] keyDecrypted=SoftLibs.getInstance().DecryptByPriKey(com.sklois.haiyunKms.SoftLibs.ASYM_ALGO_RSA_2048,privateKey, keyEncrypted.getBytes());
        keyString =new String(keyDecrypted);


        differABWork();




        /*
        get and set the symkey
         */
        if(mApplication.isAcive){

           symkey= mApplication.dh.getSymKeyInA();
        }else {
            symkey=mApplication.dh.getSymKeyInB();
        }
        mApplication.symkey=symkey;

        onSymkeyFinished();

    }
}