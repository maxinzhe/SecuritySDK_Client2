package com.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.ccit.security.sdk.clientDemo.Main2Activity;
import com.ccit.security.sdk.clientDemo.R;
import com.mApplication;
import com.servcie.VoipService;

/**
 * Created by Xinzhe on 2016/7/2.
 */
public class ConnectionFailedDialog extends AlertDialog.Builder{

    public ConnectionFailedDialog(final Context context) {
        super(context);

        this.setIcon(R.drawable.exit);
        this.setInverseBackgroundForced(false);
        this.setTitle("连接失败");
        this.setMessage("请尝试重新拨打");

        Log.i("test","建立对提示对话框中");

        this.setNegativeButton("好的", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(context,"好的", Toast.LENGTH_SHORT).show();
                Log.i("test","发送handler之前");

                mApplication.UIThreadHandler.sendEmptyMessage(10);//reset the CallActivity to Contacts list
                Log.i("test","发送handler之后");
            }
        });
    }
}
