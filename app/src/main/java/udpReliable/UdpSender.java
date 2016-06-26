package udpReliable;

import android.util.Log;

import com.mApplication;
import com.messagehandler.BasicMessageHandler;
import com.messagemodel.BasicModel;
import com.servcie.ServiceConstant;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by Xinzhe on 2016/4/25.
 */
public class UdpSender {
    static private DatagramSocket socket;
    private int LOCAL_PORT,REMOTE_PORT;
    private InetAddress LOCAL_ADDRESS,REMOTE_ADDRESS;
    public boolean ready=false;

    public UdpSender(DatagramSocket localSocket,InetAddress REMOTE_ADDRESS,int REMOTE_PORT) throws SocketException {

        this.socket=localSocket;
        this.REMOTE_ADDRESS=REMOTE_ADDRESS;
        this.REMOTE_PORT=REMOTE_PORT;


    }

    public  void sendDategram(byte[] data) throws IOException {
        byte[]databyte=data;


        DatagramPacket packet=new DatagramPacket(databyte,databyte.length,REMOTE_ADDRESS,REMOTE_PORT);
        socket.send(packet);
        Log.i("test","data send address"+packet.getAddress()+" "+packet.getPort()+" "+new String(packet.getData()));
        ready=false;

    }
    public void sendDategram(String data,InetAddress remoteAddress,int remoteport){

    }


}
