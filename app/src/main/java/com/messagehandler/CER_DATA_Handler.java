package com.messagehandler;

import android.util.Log;

import com.PKI.RSACoder;
import com.mApplication;
import com.messagemodel.BasicModel;
import com.sklois.haiyunKms.SoftLibs;
import com.sklois.util.CertCodeUtil;
import com.sklois.util.CertUtil;
import com.sklois.util.RootCertFileUtil;

import java.security.cert.CertificateException;

import udpReliable.JsonModel;

/**
 * Created by Xinzhe on 2016/4/26.
 */
public class CER_DATA_Handler extends BasicMessageHandler {
    String peerCert=basicModel.s1;
    String rootCert;

    String rootCertFilewithHeadandTail;
    String rootCertFilewithoutHeadandTail;
    public CER_DATA_Handler(BasicModel basicModel) {
        super(basicModel);

        rootCertFilewithHeadandTail =new RootCertFileUtil(mApplication.instance).getFileString();
       // rootCertFilewithHeadandTail =new RootCertFileUtil().getFileString();

        rootCertFilewithoutHeadandTail=new CertCodeUtil().getBase64CodedwithoutHeadandTile(rootCertFilewithHeadandTail);
        rootCert=rootCertFilewithoutHeadandTail;
        Log.i("test CERT_DATA_Handler","rootCert: "+rootCert);
        Log.i("test CERT_DATA_Handler","peerCert: "+peerCert);
    }

    /**
     * 1.save the cert into a file.
     * 2.check the cert with the root cert
     * 3.
     */
    @Override
    public void run() {

        super.run();

        Log.i("test CER_DATA_Handler","Set the thread priority");
        android.os.Process.setThreadPriority(-20);
        /**
         * 1.save the cert
         */

        mApplication.targetCertString=peerCert;
        //try {
        //    new SDFileHelper().writeFileSdcardFile(OnTalkAcitivty.rootCertPath,peerCert);
        //    Log.i("test CER_DATA_Handler","write the peerCert"+peerCert);
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}

        try {
            mApplication.peerCertificate=new CertUtil(peerCert).getCertObj();
            Log.i("test CER_DATA_Handler","create the certObj: "+new String(mApplication.peerCertificate.getEncoded()));
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
            Log.i("test CER_DATA_Handler","根证书验证签名结果为真");
            if(mApplication.isAcive==true){
               /*
               1.get the "a" key
               2.encrypt a key
               2.send the a encrypted to peer

                */
                Log.i("test CER_DATA_Handler","本端是主动发起通信端");
            // for the dh a got too slow ,so change the priority of the thread.

                Log.i("test CER_DATA_Handler","正在协商DH字段a……");
                akey= mApplication.dh.getDH_a();
                //Log.i("test CER_DATA_Handler","产生DH协商字段a为"+akey);
                //akey="MIIBpzCCARsGCSqGSIb3DQEDATCCAQwCgYEA/X9TgR11EilS30qcLuzk5/YRt1I870QAwx4/gLZRJmlFXUAiUftZPY1Y+r/F9bow9subVWzXgTuAHTRv8mZgt2uZUKWkn5/oBHsQIsJPu6nX/rfGG/g7V+fGqKYVDwT7g/bTxR7DAjVUE1oWkTL2dfOuK2HXKu/yIgMZndFIAccCgYEA9+GghdabPd7LvKtcNrhXuXmUr7v6OuqC+VdMCz0HgmdRWVeOutRZT+ZxBxCBgLRJFnEj6EwoFhO3zwkyjMim4TwWeotUfI0o4KOuHiuzpnWRbqN/C/ohNWLx+2J6ASQ7zKTxvqhRkImog9/hWuWfBpKLZl6Ae1UlZAFMO/7PSSoCAgIAA4GFAAKBgQDJwRQx+9cvcIqB++1TKUzXuI/5UYXx7UPbclFwyVUuAC30aHyXWVCScXcylR5t23BKCxFdDnulT8VlWEKMBmOE8LZ7TkBqBE6E4xWiW7a2h0E1nmt7Iy5cNWKymgxWU9gpSgYmeA4NMVLP6YSCUZdLwQS0fRXSKvb0EPemb++v6Q==";
                Log.i("test CER_DATA_Handler","DH字段a获取完毕！");
                Log.i("test CER_DATA_Handler","产生DH协商字段a为"+akey);
                mApplication.akey=akey;
                Log.i("test CER_DATA_Handler","产生DH协商字段a为"+akey);

                final byte[] akeyBytes=new CertCodeUtil().getBase64Decode(akey);
                Log.i("test","解码后akey的长度为:"+akeyBytes.length);
                new MessagePacketerOfPeer(){

                    @Override
                    protected void setModel() {
                        super.setModel();
                        modeltosend.t = JsonModel.MESSAGE_TYPE.TYPE_DH_A;


                       Log.i("test CER_DATA_Handler","the peer cert is: "+mApplication.peerCertificate.toString());
                        String publicKeyString=new CertCodeUtil().getBase64CodedStringfromBinaryBytes(mApplication.peerCertificate.getPublicKey().getEncoded());
                        //(X509Certificate)mApplication.peerCertificate.getPublicKey().

                        Log.i("test CER_DATA_Handler","the peer publicKey is: "+publicKeyString);
                        //byte[] encodedStrings=SoftLibs.getInstance().EncryptByPubkey(SoftLibs.ASYM_ALGO_RSA_2048,publicKeyString,akey.getBytes());
                        String []encodedStrings=null;
                        try {
                            encodedStrings = RSACoder.encryptLongDatawithPublicKeyandBase64Coded(akeyBytes,publicKeyString);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Log.i("CER_DATA_Handler","akey 已经加密");
                       // modeltosend.s1=akey;
                        modeltosend.dhKeyS =encodedStrings;
                        //modeltosend.dhKey=encodedStrings;
                        //modeltosend.data=encodedStrings;
                    }
                }.send();

            }else{
                                //do nothing but wait for a
                Log.i("test CER_DATA_Handler","本端是被动接听端");
            }


        }else{
            //send wrong message to server
            new MessagePacketerOfClient(){
                @Override
                protected  void setModel() {
                    super.setModel();
                    modeltosend.t = JsonModel.MESSAGE_TYPE.TYPE_CER_WRONG;
                    modeltosend.s1 =mApplication.deviceId;
                    modeltosend.s2 =mApplication.targetId;
                }

            }.send();
            Log.i("test CER_DATA_Handler","根证书验证签名结果为假");
        }
    }
}
