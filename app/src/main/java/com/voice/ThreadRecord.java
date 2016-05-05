package com.voice;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

import com.mApplication;
import com.messagehandler.MessagePacketerOfPeer;
import com.sklois.haiyunKms.SoftLibs;

import java.lang.ref.SoftReference;
import java.util.Arrays;

import udpReliable.JsonModel;

/**
 * Created by Xinzhe on 2016/4/29.
 */
public class ThreadRecord extends Thread{
    int bufferSize;
    static int SAMPLE_RATE=8000;

    int  bufferLength;
    AudioRecord recorder;
    private void initRecorder(){

          bufferSize= AudioRecord.getMinBufferSize(SAMPLE_RATE, AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT);
         bufferLength= bufferSize;
        Log.e("test", "录音缓冲区大小" + bufferSize);


        // 获得录音机对象
        recorder = new AudioRecord(MediaRecorder.AudioSource.MIC,
                SAMPLE_RATE,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                bufferLength);



    }
    public ThreadRecord() {
        super();
        initRecorder();
    }

    @Override
    public void run() {
        super.run();

        recorder.startRecording();// 开始录音
        byte[] readBuffer = new byte[bufferLength];// 录音缓冲区

        int length = 0;
        while(!isInterrupted()){

            /**
             * 1.get the data from recorder
             * 2.encrypt the data
             * 3.send the data
             */

                length = recorder.read(readBuffer, 0, bufferLength);// 从mic读取音频数据
                //   Log.i("test","recorder.read length"+length);
                //   Log.e("test", "在Send的while循环中");
                if (length > 0 ) {
                    Log.i("test", "在Send的while循环的read循环中");
                    Log.i("test","要发送的这个数据包读取的实际的长度为："+length);
                    byte [] effective= Arrays.copyOfRange(readBuffer,0,length);
                    final byte[] encryptedVoice= SoftLibs.getInstance().SymEncrypt(SoftLibs.SYM_AES, new String(mApplication.symkey.getEncoded()),effective);
                    new MessagePacketerOfPeer(){
                        @Override
                        protected void setModel() {
                            super.setModel();
                            modeltosend.type= JsonModel.MESSAGE_TYPE.TYPE_VOICE;
                            modeltosend.data=encryptedVoice;
                        }
                    }.send();
            }
        }
    }
}
