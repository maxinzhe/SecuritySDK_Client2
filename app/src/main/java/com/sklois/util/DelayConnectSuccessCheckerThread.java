package com.sklois.util;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Looper;
import android.util.Log;

import com.dialog.ConnectionFailedDialog;
import com.mApplication;

/**
 * Created by Xinzhe on 2016/7/2.
 */
public class DelayConnectSuccessCheckerThread extends Thread{
    Context activityForDialog;
    public DelayConnectSuccessCheckerThread(Context context) {
        super();
        activityForDialog=context;
    }

    @Override
    public void run() {
        super.run();
       // Looper.prepare();//?????
        Log.i("test","延迟检查接听线程已经启动");
        Log.i("test","延迟开始睡眠");

        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.i("test","延迟检查睡眠苏醒");

        if(mApplication.isVoiceConnected){
          Log.i("test","检查结果为已经接通，线程结束");
            return;

        }else{
            /**
             * 1.show the Dialog to indicate user that connection failed and retry
             * 2.reset the variables
             */
            Log.i("test","检查结果未接通,弹出对话提示框");


            if(mApplication.thread_dh_A_Handler!=null&&mApplication.thread_dh_A_Handler.isAlive()){
                Log.i("test","尝试结束DH 线程");
                mApplication.thread_dh_A_Handler.stop();

            }
            mApplication.UIThreadHandler.sendEmptyMessage(11);
            mApplication.setHangupFlag();//??
        }
    }
}
