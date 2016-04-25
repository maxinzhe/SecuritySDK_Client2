package udpReliable;

/**
 * Created by Xinzhe on 2016/4/16.
 */
public class SenderUnlock extends Thread {
    SendThread sendThread;
    boolean sendflag;
    @Override
    public void run() {
        super.run();
       synchronized (sendThread){
           sendflag=true;
           sendThread.notify();

       }
    }

    public SenderUnlock(SendThread sendThread,boolean sendflag) {
        super();
        this.sendThread=sendThread;
        this.sendflag=sendflag;
    }
}
