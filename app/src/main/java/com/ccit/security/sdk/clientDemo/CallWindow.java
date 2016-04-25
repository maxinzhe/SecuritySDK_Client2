package com.ccit.security.sdk.clientDemo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;

import com.servcie.VoipService;

public class CallWindow extends Activity {
    IBinder localBinder;
    Button callButton;

    Button hangupButton;
    VoipService mService;
    boolean mBound=false;
    ServiceConnection mConnection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_window);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mBound){
            unbindService(mConnection);
            mBound=false;
        }
    }

    private void  initView(){
        mConnection=  new ServiceConnection() {
                    @Override
                    public void onServiceConnected(ComponentName name, IBinder service) {
                       VoipService.LocalBinder binder= (VoipService.LocalBinder)service;
                        mService=binder.getService();
                        mBound=true;
                    }

                    @Override
                    public void onServiceDisconnected(ComponentName name) {
                        mBound=false;
                    }
                };
        callButton=(Button)findViewById(R.id.imageButton2);
        callButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CallWindow.this, VoipService.class);
                CallWindow.this.bindService(intent,mConnection, Context.BIND_AUTO_CREATE);

                mService.startThreadtoPear();
                //start P2P make hole,cert exchange and voice data.
               // mService.startThreadtoPear();

             //   Intent intentTalk=new Intent(CallWindow.this,OnTalkAcitivty.class);
               // startActivity(intentTalk);
                setContentView(R.layout.activity_on_talk_acitivty);
                initView2();

            }
        });
    }

    private void initView2(){
      hangupButton=(Button)  findViewById(R.id.hangup_button);
        hangupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mService.stopThreadtoPeer();

                setContentView(R.layout.activity_call_window);
                initView();
            }
        });
    }
}
