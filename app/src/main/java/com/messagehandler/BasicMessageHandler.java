package com.messagehandler;

import com.messagemodel.BasicModel;

/**
 * Created by Xinzhe on 2016/4/26.
 */
public class BasicMessageHandler extends Thread{
    BasicModel basicModel;

    public BasicMessageHandler(BasicModel basicModel) {
        super();
        basicModel=basicModel;
    }

    @Override
    public void run() {
        super.run();

    }
}
