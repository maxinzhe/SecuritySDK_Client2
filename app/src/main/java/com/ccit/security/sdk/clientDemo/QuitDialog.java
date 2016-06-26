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

        this.setIcon(R.drawable.exit);
        this.setInverseBackgroundForced(false);
        this.setTitle("退出");
        this.setMessage("确定退出并不接受来电了吗？");
        this.setPositiveButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("test",this.getClass().getName().toString()+"退出");

                ((Main2Activity)context).sendLogoffMessage();
                Intent intent =new Intent(context, VoipService.class);
                context.stopService(intent);
               // Thread.sleep(1000);

                Log.i("test","即将调用kill Process") ;

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        android.os.Process.killProcess(Process.myPid());
                    }
                }).start();

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
