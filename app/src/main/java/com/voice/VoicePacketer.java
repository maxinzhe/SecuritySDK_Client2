package com.voice;

import com.mApplication;
import com.messagehandler.MessagePacketer;
import com.servcie.ServiceConstant;

import java.io.IOException;
import java.net.SocketException;

import udpReliable.UdpSender;

/**
 * Created by Xinzhe on 2016/5/29.
 */
public class VoicePacketer  {
    byte[] datatosend;
    UdpSender sender;
    VoicePacketer(byte [] voiceData) {
        //super();
         this.setAddress();
        this.setModel();
        datatosend=voiceData;
    }


    protected void setAddress() {
        try {
            sender=new UdpSender(mApplication.udpSocketOfPeer, ServiceConstant.THREAD_TO_PEAR_REMOTE_ADDRESS,ServiceConstant.THREAD_TO_PEAR_REMOTE_PORT);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    protected void setModel() {

    }


    public void send() {
        try {
            sender.sendDategram(datatosend);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
