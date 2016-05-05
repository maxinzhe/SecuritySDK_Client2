package com.ccit.security.sdk.clientDemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mApplication;
import com.voice.ThreadRecord;

public class AnswerWindow extends Activity {
    Button hangup_button;
    Button watch_data_button;
    TextView text_name_textView;
    Button acceptButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_window);
    }
    private void initViews(){
        hangup_button=(Button)findViewById(R.id.button_hangup_answer);
        watch_data_button=(Button)findViewById(R.id.button_watch_data);
        text_name_textView=(TextView)findViewById(R.id.text_view_callername);
        Intent intent=getIntent();
        String callName=intent.getExtras().getString("caller_name");
        text_name_textView.setText(callName);


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
                new ThreadRecord().start();
                acceptButton.setVisibility(View.INVISIBLE);
            }
        });
    }
}
