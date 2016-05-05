package com.messagehandler;

import android.util.Log;

import com.mApplication;
import com.messagemodel.BasicModel;
import com.servcie.ServiceConstant;

import java.net.SocketException;
import java.net.UnknownHostException;

import udpReliable.UdpSender;

/**
 * Created by Xinzhe on 2016/4/26.
 */
public class MessagePacketerOfClient extends MessagePacketer{
    /**
     * construct modeltosent to setModel
     */
    @Override
    protected void setModel() {
        modeltosend=new BasicModel();
    }

    @Override
    protected void setAddress() {

        try {
            sender=new UdpSender(mApplication.udpSocketOfClient,new  ServiceConstant().THREAD_TO_SERVER_REMOTE_ADDRESS,ServiceConstant.THREAD_TO_SERVER_REMOTE_PORT);
        } catch (SocketException e) {
            e.printStackTrace();
            Log.i("test","send UDP faild");

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }
}
