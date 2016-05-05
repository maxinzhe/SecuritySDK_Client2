package com.messagehandler;

import android.util.Log;

import com.mApplication;
import com.messagemodel.BasicModel;
import com.servcie.ServiceConstant;

import java.net.SocketException;

import udpReliable.UdpSender;

/**
 * Created by Xinzhe on 2016/4/27.
 */
public class MessagePacketerOfPeer extends MessagePacketer{
    @Override
    protected void setAddress() {

        try {
            sender=new UdpSender(mApplication.udpSocketOfPeer, ServiceConstant.THREAD_TO_PEAR_REMOTE_ADDRESS,ServiceConstant.THREAD_TO_PEAR_REMOTE_PORT);
        } catch (SocketException e) {
            e.printStackTrace();
            Log.i("test","send UDP faild");

        }
    }

    @Override
      protected  void setModel() {
        modeltosend=new BasicModel();

    }
}
