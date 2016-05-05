package com.servcie;

import android.util.Log;

import com.messagemodel.BasicModel;

import org.json.JSONException;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import udpReliable.JsonModel;
import udpReliable.UdpReceiver;

/**
 * Created by Xinzhe on 2016/4/25.
 */
public abstract class ThreadReceiver extends Thread {
    UdpReceiver receiver;
    BasicModel basicModel;
    ArrayList<JsonModel.MESSAGE_TYPE> typeList;
    JsonModel.MESSAGE_TYPE type;

    public ThreadReceiver() throws SocketException {
        super();
    }

    /**
     * initTypeList:assign the typeList with your new ArrayList<MESSAGE_TYPE>
     */
    abstract  void initTypeList();

     private void switchPath(){

          if(type.equals(typeList.get(0))){
               handleMessage0(basicModel);
          }else if(type.equals(typeList.get(1))){

              handleMessage1(basicModel);
          }else if(type.equals(typeList.get(2))){
              handleMessage2(basicModel);

          }else if(type.equals(typeList.get(3))){
              handleMessage3(basicModel);

          }else if(type.equals(typeList.get(4))){
              handleMessage4(basicModel);

          }else if(type.equals(typeList.get(5))){
              handleMessage5(basicModel);

          }else if(type.equals(typeList.get(6))){
              handleMessage6(basicModel);

          }else if(type.equals(typeList.get(7))){
              handleMessage7(basicModel);

          }else if(type.equals(typeList.get(8))){
              handleMessage8(basicModel);

          }else if(type.equals(typeList.get(9))){

          }
        };

    /**
     *
     * Always use a new thread to handle the basicModel
     * @param basicModel please handled in new thread.
     */
    abstract  void handleMessage0(BasicModel basicModel);
    abstract  void handleMessage1(BasicModel basicModel);

    abstract  void handleMessage2(BasicModel basicModel);
    abstract  void handleMessage3(BasicModel basicModel);
    abstract  void handleMessage4(BasicModel basicModel);
    abstract  void handleMessage5(BasicModel basicModel);
    abstract  void handleMessage6(BasicModel basicModel);
    abstract  void handleMessage7(BasicModel basicModel);
    abstract  void handleMessage8(BasicModel basicModel);

    byte[] rawData=null;

    JsonModel jsonModel=null;
    protected void initUdpReceiver(){

    }
    @Override
    public void run() {
        super.run();
        while(!isInterrupted()){
            /*
            1.listen to a port and receive datagram
            2.parse the string data into json object,classify the fields
            3.switch fields into different cases,and handle them
            4.
             */
            Log.i("test","in the loop of Thread Receiver");

            initUdpReceiver();

            try {
                 rawData=receiver.getData();

            } catch (IOException e) {
                e.printStackTrace();
            }

            jsonModel = new JsonModel(rawData);
            type=jsonModel.getType();
            basicModel=jsonModel.getBasicModel();

            switchPath ();

        }
    }
}
