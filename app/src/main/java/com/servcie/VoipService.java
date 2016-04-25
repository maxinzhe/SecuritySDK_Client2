package com.servcie;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.net.InetAddress;
import java.net.ServerSocket;

public class VoipService extends Service {
    private Thread threadtoServer;
    private Thread threadtoPeer;

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
        Log.i("test",this.getClass().getName()+" 中的service onCreate");

        threadtoServer =new ThreadtoServer();

        threadtoPeer=new ThreadtoPear();

        startThreadtoServer();
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
        if(threadtoServer.isAlive()){
            threadtoServer.interrupt();
            Log.i("test","startThreadtoServer: thread toServer is already running ,so interrupt it");
        }
        threadtoServer.start();
        Log.i("test",this.getClass().getName()+" 中的service  before startThreadtoServer");

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


    private abstract class  ServiceConstant{
        int THREAD_TO_SERVER_LOCAL_PORT;
        int THREAD_TO_SERVER_REMOTE_PORT;

        InetAddress THREAD_TO_SERVER_LOCAL_ADDRESS;
        InetAddress THREAD_TO_SERVER_REMOTE_ADDRESS;

        int THREAD_TO_PEAR_LOCAL_PORT;
        int THREAD_TO_PEAR_REMOTE_PORT;


        InetAddress THREAD_TO_PEAR_LOCAL_ADDRESS;
        InetAddress THREAD_TO_PEAR_REMOTE_ADDRESS;
        public ServiceConstant() {

        }
    }
}
