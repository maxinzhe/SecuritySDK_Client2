package com.messagehandler;

import com.ccit.security.sdk.clientDemo.AnswerWindow;
import com.mApplication;
import com.messagemodel.BasicModel;

/**
 * Created by Xinzhe on 2016/6/19.
 */
public class HANG_UP_Handler extends  BasicMessageHandler{
    public HANG_UP_Handler(BasicModel basicModel) {
        super(basicModel);
    }

    @Override
    public void run() {
        super.run();
        /**
         * 1.关闭本端的录音线程
         * 2.返回activity到拨打界面:如果是被动接听界面，就退出activity，如果是主动界面，就调用函数重新置位。
         * 3.置位所有的修改的变量。
         */

        if(mApplication.recordThread!=null&&mApplication.recordThread.isAlive()){
            mApplication.recordThread.interrupt();
            mApplication.setHangupFlag();
        }
        if(mApplication.voice_dialog_state!=mApplication.DIALOG_STAE_READY){
            if(mApplication.voice_dialog_state==mApplication.DIALOG_STAE_POSITIVE){
                mApplication.handler.sendEmptyMessage(10);// call initview()in CallActivity
            }else if(mApplication.voice_dialog_state==mApplication.DIALOG_STAE_NEGTIVE){
                mApplication.answer_window_handler.sendEmptyMessage(6);//to finish the answer activity
            }

        }

    }
}
