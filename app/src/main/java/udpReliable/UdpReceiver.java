package udpReliable;

import android.util.Log;

import com.messagemodel.BasicModel;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;

/**
 * Created by Xinzhe on 2016/4/25.
 */
public class UdpReceiver {
    int LOCAL_PORT;
    public static  DatagramSocket receiveSocket;
    public UdpReceiver(DatagramSocket localSocket ) throws SocketException {
        receiveSocket=localSocket;

    }
    public byte[] getData() throws IOException {
        byte[] data=new byte[1000];
        DatagramPacket receivePacket=new DatagramPacket(data,data.length);
        receiveSocket.receive(receivePacket);
        byte[] newdata= Arrays.copyOf(data,receivePacket.getLength());

         //BasicModel basicModle=new JsonModel(newdata).getBasicModel();
       //Log.i("test","check the receive data"+basicModle.toString()) ;
        return newdata;
    }
}
