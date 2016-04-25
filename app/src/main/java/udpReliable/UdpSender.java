package udpReliable;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * Created by Xinzhe on 2016/4/25.
 */
public class UdpSender {
    private DatagramSocket socket;
    private int LOCAL_PORT,REMOTE_PORT;
    private InetAddress LOCAL_ADDRESS,REMOTE_ADDRESS;
    public boolean ready=false;
    public UdpSender(InetAddress LOCAL_ADDRESS,int LOCAL_PORT,InetAddress REMOTE_ADDRESS,int REMOTE_PORT) throws SocketException {
       this.LOCAL_ADDRESS=LOCAL_ADDRESS;
        this.LOCAL_PORT=LOCAL_PORT;
        this.REMOTE_ADDRESS=REMOTE_ADDRESS;
        this.REMOTE_PORT=REMOTE_PORT;
        socket=new DatagramSocket(LOCAL_PORT,LOCAL_ADDRESS);
    }

    public  void sendDategram(String data) throws IOException {
        byte[]databyte=data.getBytes();
        DatagramPacket packet=new DatagramPacket(databyte,databyte.length,REMOTE_ADDRESS,REMOTE_PORT);
        socket.send(packet);
        ready=false;

    }
    public void sendDategram(String data,InetAddress remoteAddress,int remoteport){

    }


}
