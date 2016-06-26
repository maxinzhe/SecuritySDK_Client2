package com.ccit.security.sdk.clientDemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mApplication;
import com.messagehandler.HANG_UP_Handler;
import com.messagehandler.MessagePacketerOfPeer;
import com.voice.ThreadRecord;
import com.
import udpReliable.JsonModel;

public class AnswerWindow extends Activity {
    Button hangup_button;
    Button watch_data_button;
    TextView text_name_textView;
    Button acceptButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_window);
        mApplication.voice_dialog_state=mApplication.DIALOG_STAE_NEGTIVE;

        Handler handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what==6){//exit this activity to return to the call activity
                    AnswerWindow.this.finish();
                }
            }
        };
        mApplication.answer_window_handler=handler;
        initViews();
    }
    private void initViews(){
        watch_data_button=(Button)findViewById(R.id.button_watch_data);
        text_name_textView=(TextView)findViewById(R.id.text_view_callername);
        Intent intent=getIntent();
        String callName=intent.getExtras().getString("caller_name");
        text_name_textView.setText(callName);

        hangup_button=(Button)findViewById(R.id.button_hangup_answer);
        hangup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mApplication.setHangupFlag();
                mApplication.player.stop();
                if(mApplication.recordThread!=null&&mApplication.recordThread.isAlive()){
                    mApplication.recordThread.interrupt();
                }
                new MessagePacketerOfPeer(){
                    @Override
                    protected void setModel() {
                        super.setModel();
                        modeltosend.t= JsonModel.MESSAGE_TYPE.TYPE_HANG_UP;
                    }
                }.send();

                AnswerWindow.this.finish();

            }
        });

        acceptButton=(Button)findViewById(R.id.button_acceptcall);
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 1.start the voice stream in this end
                 * 2.set self invisible
                 */
                //??

                mApplication.player.play();
                mApplication.recordThread= new ThreadRecord();
                mApplication.recordThread.start();
                acceptButton.setVisibility(View.INVISIBLE);
            }
        });
    }
}
