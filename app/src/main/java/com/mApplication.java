package com;

import android.app.Application;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Handler;
import android.util.Log;
import android.widget.EditText;

import com.servcie.ServiceConstant;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.cert.Certificate;
import java.util.List;
import java.util.Map;

import javax.crypto.SecretKey;

import DH.DH;

/**
 * Created by Xinzhe on 2016/4/28.
 */


public class mApplication extends Application {
    /**
     * data watch view
     */
    public static volatile boolean  visualDialogIsPaused=false;
    public     static volatile boolean isPlayDecryptedOrEncrypted=true;
    static public EditText decryptedEditText;
    static public  EditText encryptedEditText;
    /**
     * device info
     */
    public static  SecretKey symkey;
    public static String deviceId;
    public static String deviceName;


    public static Application instance;

    public static Certificate peerCertificate;

    public static DH dh=new DH();
    public static boolean isAcive=false;

    public static String akey;
    public static String bkey;


    static int SAMPLE_RATE=8000;

    public static int bufferSize = android.media.AudioTrack.getMinBufferSize(SAMPLE_RATE,
            AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);

    public static AudioTrack player;


    public static List<Map<String,String >> contactsList;

    /**
     * to udp sockets
     */
    public static DatagramSocket udpSocketOfPeer;
    public static DatagramSocket udpSocketOfClient;



    public static Handler handler;

    private void initPlay(){

        int bufferLength=bufferSize;

        // 获得音轨对象
         player = new AudioTrack(AudioManager.STREAM_VOICE_CALL,
                SAMPLE_RATE,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                bufferLength,
                AudioTrack.MODE_STREAM);

        // 设置喇叭音量
        player.setStereoVolume(1.0f, 1.0f);

        // 开始播放声音

    }
    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        try {
            udpSocketOfClient=new DatagramSocket(new ServiceConstant().THREAD_TO_SERVER_LOCAL_PORT);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }


        initPlay();


    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        if((udpSocketOfClient!=null)&&(!udpSocketOfClient.isClosed())){
            udpSocketOfClient.close();
        }
        if((udpSocketOfPeer!=null)&&(!udpSocketOfPeer.isClosed())){
            udpSocketOfPeer.close();
        }
    }

    public static Application getInstance(){
        return instance;
    }
    public DH getDH(){
        return dh;
    }

}
