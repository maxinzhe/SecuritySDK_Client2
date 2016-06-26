package com.ccit.security.sdk.clientDemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.mApplication;
import com.sklois.util.CertCodeUtil;
import com.sklois.util.RootCertFileUtil;
import com.sklois.util.ShareHelper;

public class OnTalkAcitivty extends Activity {
    Button hangupButton;
    public static String deviceId;
    public static String deviceModel;
    public static String rootCertPath;
    public static String rootCertFilewithHeadandTail;
    public static String rootCertFilewithoutHeadandTail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_on_talk_acitivty);

        initView();

        deviceId=new ShareHelper("DeviceId",OnTalkAcitivty.this).readShareString("deviceId");
        deviceModel=new ShareHelper("DeviceId",OnTalkAcitivty.this).readShareString("deviceModel");
        rootCertPath=getResources().getString(R.string.root_cer_path);

        rootCertFilewithHeadandTail =new RootCertFileUtil(mApplication.instance).getFileString();

         rootCertFilewithoutHeadandTail=new CertCodeUtil().getBase64CodedwithoutHeadandTile(rootCertFilewithHeadandTail);
        Log.i("test OnTalkActivity","rootCert:"+rootCertFilewithoutHeadandTail);

    }
    private void initView(){
        hangupButton=(Button)findViewById(R.id.hangup_button);
        hangupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
