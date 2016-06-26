package com.messagehandler;

import android.util.Log;

import com.mApplication;
import com.messagemodel.BasicModel;
import com.servcie.ServiceConstant;

import java.net.InetAddress;
import java.net.UnknownHostException;

import udpReliable.JsonModel;

/**
 * Created by Xinzhe on 2016/4/26.
 */
public class HOLE_INFO_Handler extends BasicMessageHandler{

    public HOLE_INFO_Handler(BasicModel basicModel) {
        super(basicModel);
    }
    private String converIpAddress(String ipAddress){
        String[] temp=ipAddress.split("/");
        if(temp[0].equals(ipAddress)){//不含有"/"
            Log.e("test","convertedIpString is: "+ipAddress);
            return ipAddress;

        }else{
            return temp[1];
        }
    }
    @Override
    public void run() {
        super.run();
        if(basicModel.s2!=null){
            Log.i("HOLE_INFO_Handler","收到的对方的设备名为： "+basicModel.s2 );
            mApplication.targetName=basicModel.s2;

        }
        /**
         * set the p2p address and start p2p to the peer
         */
        try {
            ServiceConstant.THREAD_TO_PEAR_REMOTE_ADDRESS= InetAddress.getByName(converIpAddress(basicModel.s1));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        //ServiceConstant.THREAD_TO_PEAR_REMOTE_PORT=basicModel.port;
       // for(int i=0;i<10;i++){

            new MessagePacketerOfPeer(){
                @Override
                protected void setAddress() {
                    super.setAddress();

                }

                @Override
                protected  void setModel() {
                    super.setModel();
                    modeltosend.t = JsonModel.MESSAGE_TYPE.TYPE_HOLE_P2P;

                }
            }.send();
      //  }
    }
}
