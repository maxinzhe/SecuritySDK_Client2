package com.messagehandler;

import android.util.Log;

import com.messagemodel.BasicModel;
import com.servcie.ServiceConstant;

import java.io.IOException;
import java.net.SocketException;

import udpReliable.JsonModel;
import udpReliable.UdpSender;

/**
 * Created by Xinzhe on 2016/4/26.
 */
public abstract class MessagePacketer {
    protected BasicModel modeltosend;
    protected  UdpSender sender=null;
    protected byte[] datatosend;
    /**
     * set the address to send:construct the UdpSender
     */
   abstract protected void setAddress();

    /**
     * set content for the model to be sent
     */
    protected abstract void setModel();

    protected MessagePacketer(){
        setAddress();
        setModel();

        datatosend=new JsonModel(modeltosend).getBytes();
        Log.i("test MessagePacketer","data to send is: "+new String(datatosend));

    }
    public void send()   {
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    sender.sendDategram(datatosend);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }.start();

    }
}
