package com.ccit.security.sdk.clientDemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.servcie.VoipService;
import com.sklois.demo.MainActivity;

public class Main2Activity extends Activity implements View.OnClickListener{
    Button imageButton,imageButton2,imageButton3,imageButton4;
    Button quit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
        makeService();


    }
    private void makeService(){
        Intent intent=new Intent(this, VoipService.class);
        startService(intent);
    }
    private void  initView(){
        imageButton=(Button)findViewById(R.id.imageButton);
        imageButton.setOnClickListener(this);
        imageButton2=(Button)findViewById(R.id.imageButton2);
        imageButton2.setOnClickListener(this);
        quit=(Button)findViewById(R.id.quit);



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

        }
    }
}
