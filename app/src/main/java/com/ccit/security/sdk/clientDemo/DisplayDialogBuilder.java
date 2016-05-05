package com.ccit.security.sdk.clientDemo;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.mApplication;

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
        initView();
        this.setView(view);
        this.setIcon(R.drawable.anylysis);
        this.setTitle("解密对比");
       // this.setMessage("helloMessage");



        new AsyncTask<Void,String,Void>(){
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... params) {
                int i=0;
                while(i<100){

                    publishProgress("new data from background"+(i++)+"\n");
                  Log.i("test","in the thread of doInBackground")  ;
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
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
