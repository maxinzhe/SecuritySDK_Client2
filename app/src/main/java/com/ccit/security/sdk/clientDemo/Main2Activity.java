package com.ccit.security.sdk.clientDemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.mApplication;
import com.messagehandler.MessagePacketerOfClient;
import com.servcie.VoipService;
import com.sklois.demo.MainActivity;

import udpReliable.JsonModel;

public class Main2Activity extends Activity implements View.OnClickListener{
    Button imageButton,imageButton2,imageButton3,imageButton4;
    Button quit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();





        try{        makeService();
            setDeviceInfo();
            sendLoginMessage();
        }catch(Exception e){
            e.printStackTrace();
        }


    }
    private void sendLoginMessage(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                new MessagePacketerOfClient(){
                    @Override
                    protected void setModel() {
                        super.setModel();
                        modeltosend.t = JsonModel.MESSAGE_TYPE.TYPE_LOGIN;
                        modeltosend.s1 =mApplication.deviceId;
                        modeltosend.s2 =mApplication.deviceName;
                    }
                }.send();
            }
        }.start();

    }
    public  void sendLogoffMessage(){

        new Thread(){
            @Override
            public void run() {
                super.run();
                new MessagePacketerOfClient(){
                    @Override
                    protected void setModel() {
                        super.setModel();
                        Log.i("test","发送了下线消息");
                        modeltosend.t = JsonModel.MESSAGE_TYPE.TYPE_LOGOFF;
                        modeltosend.s1 =mApplication.deviceId;
                        modeltosend.s2 =mApplication.deviceName;
                    }
                }.send();
            }
        }.start();
    }
    private void setDeviceInfo(){
        final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(MainActivity.TELEPHONY_SERVICE);
        mApplication.deviceId=tm.getDeviceId();
        if(tm.getDeviceId()==null||tm.getDeviceId().equals("")){

                    String id=getLocalMacAddress();
            id=id.replaceAll(":","");
            mApplication.deviceId=id;
        }
        mApplication.deviceName= Build.MODEL;
    }
    private String getLocalMacAddress(){
        WifiManager wifi=(WifiManager)getSystemService(Context.WIFI_SERVICE);
        WifiInfo info=wifi.getConnectionInfo();
        Log.i("test Mac","Mac Address is "+info.getMacAddress());
        return info.getMacAddress();
    }
    private void makeService(){
        Intent intent=new Intent(this, VoipService.class);
         ComponentName serviceName=startService(intent);
        if(serviceName==null){
            Log.i("test","fall to start the voice service");
        }else{
            Log.i("test","the voice service is started,name :"+serviceName.toString());
        }
    }
    private void  initView(){
        imageButton=(Button)findViewById(R.id.imageButton);
        imageButton.setOnClickListener(this);
        imageButton2=(Button)findViewById(R.id.imageButton2);
        imageButton2.setOnClickListener(this);
        quit=(Button)findViewById(R.id.quit);
        quit.setOnClickListener(this);



    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageButton:
                Intent intent=new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.imageButton2:
                Intent intent2=new Intent(this,CallWindow.class);
                startActivity(intent2);
                break;
            case R.id.quit:
                AlertDialog.Builder builder=new QuitDialog(Main2Activity.this);
                builder.show();
                //sendLogoffMessage();
        }
    }
}
