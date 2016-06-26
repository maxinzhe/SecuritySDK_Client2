package com.ccit.security.sdk.clientDemo;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.mApplication;
import com.voice.VOICE_DATA_Handler;

/**
 * Created by Xinzhe on 2016/5/4.
 */
public class DisplayDialogBuilder extends AlertDialog.Builder  {
    EditText encryptedwatchDataText;
    EditText decryptedwatchDataText;
    ToggleButton playDecryptedToggle;
    ToggleButton pauseDisplayToggle;
    View view;


    public DisplayDialogBuilder(Context context) {
        super(context);
        //LayoutInflater inflater=LayoutInflater.from(context);
       // inflater.inflate(R.layout.activity_main,null);

        view=View.inflate(context,R.layout.data_watch_layout,null);

        this.setView(view);
        this.setIcon(R.drawable.anylysis);
        this.setTitle("解密对比");


        initView();
        mApplication.handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case VOICE_DATA_Handler.SHOW_VOICE_DATA_TO_DECRYP:
                        Log.i("Display","已经获取到显示未解密数据的handler"+(String)msg.obj);
                       decryptedwatchDataText.setText((String)msg.obj);

                        break;
                    case VOICE_DATA_Handler.SHOW_VOICE_DATA_TO_ENCRYP:
                        Log.i("Display","已经获取到已经解密数据的handler"+(String)msg.obj);
                        encryptedwatchDataText.setText((String)msg.obj);

                        break;

                }

            }
        };
       // this.setMessage("helloMessage");


        //这里应该注册一个handle进行ui数据更新



        new AsyncTask<Void,String,Void>(){
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... params) {
           //     int i=0;
           //     while(i<100){

           //         publishProgress("new data from background"+(i++)+"\n");
           //       Log.i("test","in the thread of doInBackground")  ;
           //         try {
           //             Thread.sleep(100);
           //         } catch (InterruptedException e) {
           //             e.printStackTrace();
           //         }
           //     }
              return null;
            }

            @Override
            protected void onProgressUpdate(String... values) {
                super.onProgressUpdate(values);
                encryptedwatchDataText.append(values[0]);
                Log.i("test","in the thread of onProgressUpdate");
            }
        }.execute();
    }

    private void initView(){
       encryptedwatchDataText =(EditText)view.findViewById(R.id.encrypted_editText);
        decryptedwatchDataText=(EditText)view.findViewById(R.id.decrypted_editText) ;
        mApplication.encryptedEditText= encryptedwatchDataText;
        mApplication.decryptedEditText=decryptedwatchDataText;

        pauseDisplayToggle =(ToggleButton)view.findViewById(R.id.toggleButton_pause);
        playDecryptedToggle=(ToggleButton)view.findViewById(R.id.toggleButton_play);
        pauseDisplayToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    mApplication.visualDialogIsPaused=isChecked;
            }
        });
        playDecryptedToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    mApplication.isPlayDecryptedOrEncrypted=isChecked;

            }
        });
    }
}
