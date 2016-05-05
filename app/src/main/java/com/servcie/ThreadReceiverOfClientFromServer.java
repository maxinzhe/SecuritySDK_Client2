package com.servcie;

import com.mApplication;
import com.messagehandler.CONTACTS_LIST_Handler;
import com.messagehandler.CONTACTS_LOGOFF_Handler;
import com.messagehandler.CONTACTS_LOGON_Handler;
import com.messagehandler.HOLE_INFO_Handler;
import com.messagemodel.BasicModel;
import com.messagemodel.server2client.CONTACTS_LIST_Model;

import java.net.SocketException;
import java.util.ArrayList;

import udpReliable.JsonModel;
import udpReliable.UdpReceiver;

/**
 * Created by Xinzhe on 2016/4/26.
 */
public class ThreadReceiverOfClientFromServer extends ThreadReceiver {
    public ThreadReceiverOfClientFromServer() throws SocketException {
        super();
    }

    @Override
    protected void initUdpReceiver() {
        super.initUdpReceiver();
        try {
            receiver=new UdpReceiver(mApplication.udpSocketOfClient);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    void initTypeList() {
        typeList=new ArrayList<JsonModel.MESSAGE_TYPE>();

        typeList.add(0, JsonModel.MESSAGE_TYPE.TYPE_CONTACTS_LIST);
        typeList.add(1, JsonModel.MESSAGE_TYPE.TYPE_HOLE_INFO);
        typeList.add(2, JsonModel.MESSAGE_TYPE.TYPE_CONTACTS_OFFLINE);
        typeList.add(3, JsonModel.MESSAGE_TYPE.TYPE_CONTACTS_ONlINE);
    }

    @Override
    void handleMessage0(BasicModel basicModel) {
        new CONTACTS_LIST_Handler(basicModel).start();
    }

    @Override
    void handleMessage1(BasicModel basicModel) {
        new HOLE_INFO_Handler(basicModel).start();
    }

    @Override
    void handleMessage2(BasicModel basicModel) {
        new CONTACTS_LOGOFF_Handler(basicModel).start();
    }

    @Override
    void handleMessage3(BasicModel basicModel) {
        new CONTACTS_LOGON_Handler(basicModel).start();
    }

    @Override
    void handleMessage4(BasicModel basicModel) {

    }

    @Override
    void handleMessage5(BasicModel basicModel) {

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
