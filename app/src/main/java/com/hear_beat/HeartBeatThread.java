package com.hear_beat;

import com.mApplication;
import com.messagehandler.MessagePacketerOfClient;

import udpReliable.JsonModel;

/**
 * Created by Xinzhe on 2016/6/19.
 */
public class HeartBeatThread extends Thread {

    @Override
    public void run() {
        super.run();

        while(!isInterrupted()){

            sendBeatMessage();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }




    private void sendBeatMessage(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                new MessagePacketerOfClient(){
                    @Override
                    protected void setModel() {
                        super.setModel();
                        modeltosend.t = JsonModel.MESSAGE_TYPE.TYPE_BEAT;
                        modeltosend.s1 = mApplication.deviceId;
                        modeltosend.s2 =mApplication.deviceName;
                    }
                }.send();
            }
        }.start();

    }
}
