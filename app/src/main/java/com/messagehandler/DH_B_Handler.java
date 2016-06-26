package com.messagehandler;

import com.mApplication;
import com.messagemodel.BasicModel;

import java.io.IOException;

/**
 * Created by Xinzhe on 2016/4/28.
 */
public class DH_B_Handler extends BasicDH_Handler{
    public DH_B_Handler(BasicModel basicModel) throws IOException {
        super(basicModel);
    }

    /**
     * 1.set the key a or b of mApplication
     * 2.set the key to DH
     * 3.get the symkey
     */
    @Override
    protected void differABWork() {
        super.differABWork();
        mApplication.bkey=keyString;
//set DHB for A end ;
    }

    @Override
    public void onSymkeyFinished() {
        super.onSymkeyFinished();


    }

    /**
     * 1.decrypt the b key
     * 2.save the b key
     * 3.get and set the symkey
     * 4.send the ok to server
     * 5.start the voice engine
     */


    @Override
    public void run() {
        super.run();



    }
}
