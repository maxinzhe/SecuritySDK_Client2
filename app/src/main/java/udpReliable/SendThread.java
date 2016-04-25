package udpReliable;

/**
 * Created by Xinzhe on 2016/4/16.
 */
public class SendThread extends Thread {
    public boolean sendflag;
    Thread sender;
    public SendThread( boolean sendflag) {
        super();
        this.sendflag=sendflag;
    }

    @Override
    public void run() {
        super.run();
        while(true){
            synchronized (sender){
                if(sendflag==false){
                    try {
                        sender.wait();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //send message  sender.sendMessage
            }
        }
    }
}
