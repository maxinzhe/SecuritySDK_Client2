package com.servcie;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.hear_beat.HeartBeatThread;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class VoipService extends Service {
    private Thread threadtoServer;
    private Thread threadtoPeer;
    public ServiceConstant serviceConstant;
    private final IBinder mBinder=new LocalBinder();
    public VoipService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mBinder;
    }
    public class LocalBinder extends Binder {
        public VoipService getService(){

            return VoipService.this;
        }
    }
    @Override
    public void onCreate() {
        super.onCreate();

       new HeartBeatThread().start();//start the heart beat thread util process killed.

        Log.i("test",this.getClass().getName()+" 中的service onCreate");

        try {
            threadtoServer =new ThreadReceiverOfClientFromServer();

            Log.i("test","start the Server receive thread");
        } catch (SocketException e) {
            e.printStackTrace();
        }

        try {
            threadtoPeer=new ThreadReceiverOfP2P();
            Log.i("test","start the P2P receive thread");
        } catch (SocketException e) {
            e.printStackTrace();
        }

        startThreadtoServer();

        try {
            serviceConstant=new ServiceConstant() ;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(threadtoServer.isAlive()){
            threadtoServer.interrupt();
            Log.i("test","threadtoServer.interrupted");
        }
        if(threadtoPeer.isAlive()){

            threadtoServer.interrupt();
            Log.i("test","threadtoPeer.interrupted");
        }
    }

    public  void startThreadtoServer(){
        if((threadtoServer!=null)&&(threadtoServer.isAlive())){
            threadtoServer.interrupt();
            Log.i("test","startThreadtoServer: thread toServer is already running ,so interrupt it");
        }
        threadtoServer.start();
        Log.i("test"," receive thread started");

        if((threadtoPeer!=null)&&(threadtoPeer.isAlive())){
            threadtoPeer.interrupt();
            Log.i("test","startThreadtoPeer: thread toPeer is already running ,so interrupt it");
        }
        threadtoPeer.start();
        Log.i("test","receive thread started");
    }
    public  void stopThreadtoServer(){
        if(threadtoServer.isAlive()){
            threadtoServer.interrupt();
            Log.i("test","stopThreadtoServer: thread toServer interrupt it");
        }
        Log.i("test",this.getClass().getName()+" stopped the ThreadtoServer");

    }

    public  void stopThreadtoPeer(){
        if(threadtoPeer.isAlive()){
            threadtoPeer.interrupt();
            Log.i("test","stopThreadtoServer: thread toPeer interrupt it");
        }
        Log.i("test",this.getClass().getName()+" stopped the ThreadtoPeer");

    }
    public  void startThreadtoPear(){

        if(threadtoPeer.isAlive()){
            threadtoServer.interrupt();
            Log.i("test","startThreadtoPeer: thread toPeer is already running ,so interrupt it");
        }
        threadtoPeer.start();
        Log.i("test",this.getClass().getName()+" 中的service  before startThreadtoPeer");

    }


}
