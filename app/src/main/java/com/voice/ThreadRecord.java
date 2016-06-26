package com.voice;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Base64;
import android.util.Log;

import com.PKI.Sym;
import com.mApplication;
import com.messagehandler.MessagePacketerOfPeer;
import com.sklois.haiyunKms.SoftLibs;

import java.util.Arrays;

import udpReliable.JsonModel;

/**
 * Created by Xinzhe on 2016/4/29.
 */
public class ThreadRecord extends Thread{
    int bufferSize;
    static int SAMPLE_RATE=mApplication.SAMPLE_RATE;

    int  bufferLength;
    AudioRecord recorder;

    private void initRecorder(){

          bufferSize= AudioRecord.getMinBufferSize(mApplication.SAMPLE_RATE, AudioFormat.CHANNEL_IN_MONO,
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
        while(!mApplication.stopRecording){

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

                    byte [] effectiveAll = Arrays.copyOfRange(readBuffer,0,length);
                    Log.i("test","(加密以前)的实际的长度为："+effectiveAll.length);
                    //final byte[] encryptedVoice= SoftLibs.getInstance().SymEncrypt(SoftLibs.SYM_AES, mApplication.symKeyString,effective);
                    //因为限制发送1000，而大于1000的部分多为1020等等。这里就分割成500
                    int mod=650;
                    int effectivelength=effectiveAll.length;
                    int numLeft=effectivelength%mod;
                    int numMul=effectivelength/mod;
                    byte[][] effectiveArray = new byte[numLeft + 1][];
                    for(int i=0;i<numMul;i++){

                        effectiveArray[i]= Arrays.copyOfRange(effectiveAll,i*100,(i+1)*100);
                    }
                    if(numLeft>0) {
                        effectiveArray[numMul]=Arrays.copyOfRange(effectiveAll,numMul*100,effectivelength);
                    }

                    for(int i=0;i<=numMul;i++){
                            byte[] effective=effectiveArray[i];

                        byte[] encryptedVoice=null;
                        try {
                            if(effective==null){
                                continue;
                            }
                            encryptedVoice = Sym.encrypt(effective, mApplication.symKeyString);
                            Log.i("test","加密以后的实际的长度为："+encryptedVoice.length);
                        } catch (Exception e) {

                            e.printStackTrace();
                            if(encryptedVoice==null){
                                continue;
                            }
                        }
                      //  try {
                      //      byte[] tempdecrypted=Sym.detrypt(encryptedVoice,mApplication.symKeyString);
                      //      String temp= Base64.encodeToString(tempdecrypted,Base64.DEFAULT);
                      //      Log.i("ThreadRecord","本地解密以后的长度为： "+tempdecrypted.length+" \n内容为 "+temp);
                      //  } catch (Exception e) {
                      //      Log.i("ThreadRecord","本地解密失败");
                      //      e.printStackTrace();
                      //  }
                        //加密以后base64编码
                        final String voiceBase64=Base64.encodeToString(encryptedVoice,Base64.DEFAULT);
                        //                   new MessagePacketerOfPeer(){
                        //                       @Override
                        //                       protected void setModel() {
                        //                           super.setModel();
                        //                           modeltosend.t = JsonModel.MESSAGE_TYPE.TYPE_VOICE;
                        //                           modeltosend.s1=voiceBase64;
                        //                       }
                        //                   }.send();

                        Log.i("ThreadRecord","要发送的加密后音频的Base64为"+voiceBase64);
                        new VoicePacketer(encryptedVoice){
                            @Override
                            protected void setModel() {

                            }
                        }.send();

                    }

                    /* Thread.sleep(20000); */
                }
        }
    }
}
