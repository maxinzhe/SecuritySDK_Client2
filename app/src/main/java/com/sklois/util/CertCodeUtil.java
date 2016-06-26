package com.sklois.util;

import android.util.Base64;
import android.util.Log;

/**
 * Created by Xinzhe on 2016/4/27.
 */
public class CertCodeUtil {


    /**
     *
     * @param base64String  If contains head like"-----cert begin------"and tile than will cut,if no then
     *                      do nothing.
     * @return the decoded cert than can be accepted by the CertBuilder class
     */
    public byte[] getBase64Decode(String base64String){
        //

        return Base64.decode(getBase64CodedwithoutHeadandTile(base64String),Base64.DEFAULT);
    }
    public String getBase64CodedStringfromBinaryBytes(byte [] binary){
        return Base64.encodeToString(binary,Base64.DEFAULT);
    }


    public  String getBase64CodedwithoutHeadandTile(String temp){
        String realcert="";
        String head="-----BEGIN CERTIFICATE-----";
        String tail="-----END CERTIFICATE-----";

        try{

            String[] temp1=temp.split(head);
            if(temp1[0].equals(temp)){
                realcert=temp;
                Log.i("test","have no head so don't cut"+realcert);
            }else{

                String notHead=temp1[1].trim();
                String[] temp2=notHead.split(tail);

                realcert=temp2[0].trim();
                Log.i("test","do cut:the cert without head or tail is: "+realcert);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return realcert;
    }
}
