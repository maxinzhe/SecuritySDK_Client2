package com.servcie;

import com.mApplication;
import com.messagehandler.CER_DATA_Handler;
import com.messagehandler.DH_A_Handler;
import com.messagehandler.DH_B_Handler;
import com.messagehandler.HANG_UP_Handler;
import com.messagehandler.HOLE_P2P_Handler;
import com.messagemodel.BasicModel;
import com.voice.VOICE_DATA_Handler;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;

import udpReliable.JsonModel;
import udpReliable.UdpReceiver;

/**
 * Created by Xinzhe on 2016/4/26.
 */
public class ThreadReceiverOfP2P extends ThreadReceiver {
    public ThreadReceiverOfP2P() throws SocketException {
        super();
    }

    @Override
    protected void initUdpReceiver() {
        super.initUdpReceiver();
        try {
            receiver=new UdpReceiver(mApplication.udpSocketOfPeer);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    void initTypeList() {
        typeList=new ArrayList<JsonModel.MESSAGE_TYPE>();

        typeList.add(0, JsonModel.MESSAGE_TYPE.TYPE_HOLE_P2P);
        typeList.add(1, JsonModel.MESSAGE_TYPE.TYPE_CER_DATA);
        typeList.add(2, JsonModel.MESSAGE_TYPE.TYPE_DH_A);
        typeList.add(3, JsonModel.MESSAGE_TYPE.TYPE_DH_B);
        typeList.add(4, JsonModel.MESSAGE_TYPE.TYPE_VOICE);
        typeList.add(5,JsonModel.MESSAGE_TYPE.TYPE_HANG_UP);
    }

    @Override
    void handleMessage0(BasicModel basicModel) {
        new HOLE_P2P_Handler(basicModel).start();
    }

    @Override
    void handleMessage1(BasicModel basicModel) {
        new CER_DATA_Handler(basicModel).start();
    }

    @Override
    void handleMessage2(BasicModel basicModel) {
       new DH_A_Handler(basicModel) .start();
    }

    @Override
    void handleMessage3(BasicModel basicModel) {
        try {
            new DH_B_Handler(basicModel).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    void handleMessage4(BasicModel basicModel) {
        new VOICE_DATA_Handler(basicModel, mApplication.player).start();
    }

    @Override
    void handleMessage5(BasicModel basicModel) {
        new HANG_UP_Handler(basicModel).start();
    }

    @Override
    void handleMessage6(BasicModel basicModel) {

    }

    @Override
    void handleMessage7(BasicModel basicModel) {

    }

    @Override
    void handleMessage8(BasicModel basicModel) {

    }
}
