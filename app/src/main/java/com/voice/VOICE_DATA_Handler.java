package com.voice;

import android.media.AudioTrack;
import android.util.Log;

import com.mApplication;
import com.messagehandler.BasicMessageHandler;
import com.messagemodel.BasicModel;
import com.sklois.haiyunKms.SoftLibs;

import java.util.Arrays;

/**
 * Created by Xinzhe on 2016/4/29.
 */
public class VOICE_DATA_Handler extends BasicMessageHandler {
    AudioTrack player;
    byte [] encryptedVoice;
    byte [] decryptedVoice;
    public VOICE_DATA_Handler(BasicModel basicModel, AudioTrack player) {
        super(basicModel);

        encryptedVoice=basicModel.data;
        this.player=player;
    }

    /**
     * 1.Set the play engine
     */


    @Override
    public void run() {
        super.run();
        while(!isInterrupted()){
            /**
             * 1.receive
             * 2.decrypt
             * 3.play
             */

            decryptedVoice=SoftLibs.getInstance().SymEncrypt(SoftLibs.SYM_AES, new String(mApplication.symkey.getEncoded()),encryptedVoice);
            if(!mApplication.visualDialogIsPaused){

                setVisualOnDialog();
            }

            Log.e("test","Play接收到的数据包解码播放");

            if(mApplication.isPlayDecryptedOrEncrypted){

                player.write(decryptedVoice, 0,decryptedVoice.length);// 播放音频数据
            }else{

                player.write(encryptedVoice, 0,encryptedVoice.length);// 播放音频数据
            }


        }
    }

    private void setVisualOnDialog(){

        if(mApplication.encryptedEditText!=null){
            mApplication.encryptedEditText.setText(new String(encryptedVoice));
        }
        if(mApplication.decryptedEditText!=null){
            mApplication.decryptedEditText.setText(new String(decryptedVoice));
        }
    }
}
