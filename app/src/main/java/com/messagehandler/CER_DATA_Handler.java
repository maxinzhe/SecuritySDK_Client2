package com.messagehandler;

import android.content.Context;

import com.ccit.security.sdk.clientDemo.OnTalkAcitivty;
import com.ccit.security.sdk.clientDemo.R;
import com.mApplication;
import com.messagemodel.BasicModel;
import com.sklois.demo.test.CertAct;
import com.sklois.haiyunKms.SoftLibs;
import com.sklois.util.CertUtil;
import com.sklois.util.RootCertFileUtil;
import com.sklois.util.SDFileHelper;

import java.io.IOException;
import java.security.cert.CertificateException;

import udpReliable.JsonModel;

/**
 * Created by Xinzhe on 2016/4/26.
 */
public class CER_DATA_Handler extends BasicMessageHandler {
    public CER_DATA_Handler(BasicModel basicModel) {
        super(basicModel);
    }
    String peerCert=basicModel.string1;
    String rootCert=OnTalkAcitivty.rootCertFilewithoutHeadandTail;
    /**
     * 1.save the cert into a file.
     * 2.check the cert with the root cert
     * 3.
     */
    @Override
    public void run() {

        super.run();

        /**
         * 1.save the cert
         */


        try {
            new SDFileHelper().writeFileSdcardFile(OnTalkAcitivty.rootCertPath,peerCert);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            mApplication.peerCertificate=new CertUtil(peerCert).getCertObj();
        } catch (CertificateException e) {
            e.printStackTrace();
        }

        /**
         * 2.check the cert
         */


        boolean bRet2 =  SoftLibs.getInstance().VerifyCertByIssuerCert(peerCert, rootCert);
        final String akey;
        String akeyEncrypted;
        //
        if(bRet2==true){
           //start symkey config
            if(mApplication.isAcive==true){
               /*
               1.get the "a" key
               2.encrypt a key
               2.send the a encrypted to peer
                */
                akey= mApplication.dh.getDH_a();
                mApplication.akey=akey;

                new MessagePacketerOfPeer(){

                    @Override
                    protected void setModel() {
                        super.setModel();
                        basicModel.type= JsonModel.MESSAGE_TYPE.TYPE_DH_A;
                        byte[] string1byte=SoftLibs.getInstance().EncryptByPubkey(SoftLibs.ASYM_ALGO_RSA_2048,new String(mApplication.peerCertificate.getPublicKey().getEncoded()),akey.getBytes());
                        basicModel.string1=new String(string1byte);
                        basicModel.string1=akey;
                    }
                }.send();

            }else{
                //do nothing but wait for a
            }


        }else{
            //send wrong message to server
            new MessagePacketerOfClient(){
                @Override
                protected  void setModel() {
                    super.setModel();
                    modeltosend.type= JsonModel.MESSAGE_TYPE.TYPE_CER_WRONG;
                }

            }.send();
        }
    }
}
