package com.sklois.util;

import java.io.ByteArrayInputStream;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

/**
 * Created by Xinzhe on 2016/4/27.
 */
public class CertUtil {
    CertificateFactory certificateFactory;
    Certificate certificate;



    public CertUtil(byte[] base64decodedbyte) throws CertificateException {

        try {
            certificateFactory = CertificateFactory.getInstance("X.509");
        } catch (CertificateException e) {
            e.printStackTrace();
        }

        certificate=certificateFactory.generateCertificate(new ByteArrayInputStream(base64decodedbyte));
    }
    public CertUtil(String base64cert) throws CertificateException {
        byte[] decodedcert=new CertCodeUtil().getBase64Decode(base64cert);

        try {
            certificateFactory = CertificateFactory.getInstance("X.509");
        } catch (CertificateException e) {
            e.printStackTrace();
        }

        certificate=certificateFactory.generateCertificate(new ByteArrayInputStream(decodedcert));

    }
    public Certificate getCertObj(){
        return certificate;
    }
    public  byte[] getPublicKey(){
        return certificate.getPublicKey().getEncoded();
    }
}
