package com.voice;

import android.media.AudioTrack;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;

import com.PKI.Sym;
import com.mApplication;
import com.messagehandler.BasicMessageHandler;
import com.messagemodel.BasicModel;

import java.util.Arrays;

/**
 * Created by Xinzhe on 2016/4/29.
 */
public class VOICE_DATA_Handler extends BasicMessageHandler {

    public static final int SHOW_VOICE_DATA_TO_ENCRYP =10;
    public static final int SHOW_VOICE_DATA_TO_DECRYP =11;
    AudioTrack player;
    String encryptedVoiceString;
    byte[] encryptedVoice;
    byte [] decryptedVoice;
    public VOICE_DATA_Handler(BasicModel basicModel, AudioTrack player) {//stop using
        super(basicModel);

        encryptedVoiceString=basicModel.s1;
        this.player=player;
        encryptedVoice= Base64.decode(encryptedVoiceString,Base64.DEFAULT);
    }
    public VOICE_DATA_Handler(byte[] voiceEncrypted,AudioTrack player){
        mApplication.isVoiceConnected=true;
        //change the CallActivity UI
        mApplication.answer_window_handler.sendEmptyMessage(12);
        Log.i("test","接通标识获得置位true");
        this.player=player;
        String voiceEncryptedString=Base64.encodeToString(voiceEncrypted,Base64.DEFAULT);
        Log.i("VOIDCE_DATA_Handler","接到的声音数据 EncryptedBase64  "+voiceEncryptedString);
        encryptedVoice=voiceEncrypted;
    }

    /**
     * 1.Set the play engine
     */


    @Override
    public void run() {
        super.run();
            /**
             * 1.receive
             * 2.decrypt
             * 3.play
             */
            //decryptedVoice=SoftLibs.getInstance().SymDecrypt(SoftLibs.SYM_AES, mApplication.symKeyString,encryptedVoice);
        try {
            decryptedVoice= Sym.detrypt(encryptedVoice,mApplication.symKeyString);
        } catch (Exception e) {
            e.printStackTrace();
            if(decryptedVoice==null){
                return;
            }
        }
        if(!mApplication.visualDialogIsPaused){
                Log.i("VOICE_DATA_Handler","visulDialogIsResume");
                setVisualOnDialog();
        }

            Log.e("test","Play接收到的数据包解码播放");

          //  Log.e("test","接收到的音频数据base64为"+encryptedVoiceString);
            if(mApplication.isPlayDecryptedOrEncrypted){

                Log.e("test","正在播放解密音频数据，数据长度为："+decryptedVoice.length);
                player.write(decryptedVoice, 0,decryptedVoice.length);// 播放音频数据
            }else{

                Log.e("test","正在播放未解密音频数据");
                player.write(encryptedVoice, 0,encryptedVoice.length);// 播放音频数据
            }


    }

    private void setVisualOnDialog(){//这里是不可以直接进行ui更新的，要使用handler进行传递
        Handler handler=mApplication.UIThreadHandler;//之前的那个uihandler.
        if(mApplication.encryptedEditText!=null){
            Message message=new Message();
            message.what= SHOW_VOICE_DATA_TO_ENCRYP;

            byte tempcut[]= Arrays.copyOf(encryptedVoice,10);
            String cutString=Base64.encodeToString(tempcut,Base64.DEFAULT);

            message.obj=cutString;//这里最好给剪裁一下
            handler.sendMessage(message);

           // mApplication.encryptedEditText.setText(new String(encryptedVoice));
        }
        if(mApplication.decryptedEditText!=null){
            Message message=new Message();
            message.what= SHOW_VOICE_DATA_TO_DECRYP;
            byte tempcut[]= Arrays.copyOf(decryptedVoice,10);
            String cutString=Base64.encodeToString(tempcut,Base64.DEFAULT);

            message.obj=cutString;//这里最好给剪裁一下
            handler.sendMessage(message);
           // mApplication.decryptedEditText.setText(new String(decryptedVoice));
        }
    }
}
