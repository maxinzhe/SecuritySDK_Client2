package com.ccit.security.sdk.clientDemo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;

import com.servcie.VoipService;

/**
 * Created by Xinzhe on 2016/4/25.
 */
public class QuitDialog  extends AlertDialog.Builder{
    public QuitDialog(final Context context) {
        super(context);
        this.setIcon(R.drawable.ic_luncher2);
        this.setTitle("退出");
        this.setMessage("确定退出并不接受来电了吗？");
        this.setPositiveButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("test",this.getClass().getName().toString()+"退出");
                Intent intent =new Intent(context, VoipService.class);
                context.stopService(intent);

                Log.i("test","即将调用kill Process") ;
                android.os.Process.killProcess(Process.myPid());

            }
        });
        this.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context,"取消",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
