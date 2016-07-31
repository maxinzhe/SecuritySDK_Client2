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
     *  isVoiceConnected is used to show that this end has accepted the voice data from the other end,
     *  the value will be check after a time delay since Call button has been pressed.
     */
    public static volatile boolean isVoiceConnected=false;



    public static final int DIALOG_STAE_NEGTIVE=0;
    public static final int DIALOG_STAE_POSITIVE=1;
    public static final int DIALOG_STAE_READY=2;

    public static Thread recordThread;

    public  volatile static boolean stopRecording=false;

    /**
     * data watch view
     */
    public static volatile  int voice_dialog_state=DIALOG_STAE_READY;

    public static volatile boolean  visualDialogIsPaused=false;
    public     static volatile boolean isPlayDecryptedOrEncrypted=true;
    volatile  static public EditText decryptedEditText;
     volatile static public  EditText encryptedEditText;
    /**
     * device info
     */
    public static  SecretKey symkey;
    public static String deviceId;
    public static String deviceName;


    public static Application instance;

    public static String targetCertString;
    public static Certificate peerCertificate;

    public static DH dh=new DH();
    public static boolean isAcive=false;

    public static String akey;
    public static String bkey;
    public static String symKeyString;

    public static int SAMPLE_RATE=8000;

    public static int bufferSize;
    public static AudioTrack player;

    public static Thread thread_dh_A_Handler;
    public static void setCallingFlag(){
        stopRecording=false;
    }
    public static void setHangupFlag(){
        stopRecording=true;
    }
    public static List<Map<String,String >> contactsList;

    /**
     * to udp sockets
     */
    public static DatagramSocket udpSocketOfPeer;
    public static DatagramSocket udpSocketOfClient;



    public static volatile Handler UIThreadHandler;

    public static volatile Handler answer_window_handler;



    public static String targetId;
    public static String targetName;

    private void initPlay(){
        bufferSize = android.media.AudioTrack.getMinBufferSize(SAMPLE_RATE,
            AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
        int bufferLength=2*bufferSize;//use double space to handle the situation of encrypted noise data will over flow.

        // 获得音轨对象
         player = new AudioTrack(AudioManager.STREAM_VOICE_CALL,
                SAMPLE_RATE,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                bufferLength,
                AudioTrack.MODE_STREAM);

        // 设置喇叭音量
        player.setStereoVolume(1.0f, 1.0f);

        // player.play();
        // 开始播放声音

    }
    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        try {
            udpSocketOfClient=new DatagramSocket(new ServiceConstant().THREAD_TO_SERVER_LOCAL_PORT);
            udpSocketOfPeer=new DatagramSocket(new ServiceConstant().THREAD_TO_PEAR_LOCAL_PORT);
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
        Log.i("mApplication","mApplication onTerminate");
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
