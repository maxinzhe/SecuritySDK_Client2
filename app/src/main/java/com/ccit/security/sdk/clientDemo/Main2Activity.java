package com.ccit.security.sdk.clientDemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

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
                        modeltosend.type= JsonModel.MESSAGE_TYPE.TYPE_LOGIN;
                        modeltosend.string1=mApplication.deviceId;
                        modeltosend.string2=mApplication.deviceName;
                    }
                }.send();
            }
        }.start();

    }
    private void sendLogoffMessage(){

        new Thread(){
            @Override
            public void run() {
                super.run();
                new MessagePacketerOfClient(){
                    @Override
                    protected void setModel() {
                        super.setModel();
                        modeltosend.type= JsonModel.MESSAGE_TYPE.TYPE_LOGOFF;
                        modeltosend.string1=mApplication.deviceId;
                        modeltosend.string2=mApplication.deviceName;
                    }
                }.send();
            }
        }.start();
    }
    private void setDeviceInfo(){
        final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(MainActivity.TELEPHONY_SERVICE);
        mApplication.deviceId=tm.getDeviceId();
        mApplication.deviceName= Build.MODEL;
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
        Log.i("test","onClick in activity");
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
                AlertDialog.Builder builder=new QuitDialog(this);
                builder.show();
                sendLogoffMessage();
        }
    }
}
