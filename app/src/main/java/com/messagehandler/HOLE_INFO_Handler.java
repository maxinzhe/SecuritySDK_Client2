package com.messagehandler;

import android.content.ServiceConnection;
import android.util.Log;

import com.messagemodel.BasicModel;
import com.servcie.ServiceConstant;
import com.servcie.VoipService;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import udpReliable.JsonModel;
import udpReliable.UdpSender;

/**
 * Created by Xinzhe on 2016/4/26.
 */
public class HOLE_INFO_Handler extends BasicMessageHandler{

    public HOLE_INFO_Handler(BasicModel basicModel) {
        super(basicModel);
    }

    @Override
    public void run() {
        super.run();

        /**
         * set the p2p address and start p2p to the peer
         */
        try {
            ServiceConstant.THREAD_TO_PEAR_REMOTE_ADDRESS= InetAddress.getByName(basicModel.string1);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        ServiceConstant.THREAD_TO_PEAR_REMOTE_PORT=basicModel.port;


        new MessagePacketerOfClient(){
            @Override
            protected void setAddress() {
                super.setAddress();

            }

            @Override
            protected  void setModel() {
                super.setModel();
                modeltosend.type= JsonModel.MESSAGE_TYPE.TYPE_HOLE_P2P;

            }
        }.send();
    }
}
