package com.messagehandler;

import com.ccit.security.sdk.clientDemo.OnTalkAcitivty;
import com.messagemodel.BasicModel;
import com.sklois.haiyunKms.SoftLibs;

import udpReliable.JsonModel;

/**
 * Created by Xinzhe on 2016/4/26.
 */
public class HOLE_P2P_Handler extends BasicMessageHandler{
    public HOLE_P2P_Handler(BasicModel basicModel) {
        super(basicModel);
    }
    /*
    1.send the success message to server
    2.start to send the exchange cert
     */

    @Override
    public void run() {
        super.run();

        new MessagePacketerOfClient(){
            @Override
            protected void setModel() {
                super.setModel();
                modeltosend.type= JsonModel.MESSAGE_TYPE.TYPE_P2P_OK;
            }

        }.send();
        new MessagePacketerOfPeer(){
            @Override
            protected void setModel() {
                super.setModel();
                modeltosend.type= JsonModel.MESSAGE_TYPE.TYPE_CER_DATA;
                modeltosend.string1= SoftLibs.getInstance().ReadLocalCert(OnTalkAcitivty.deviceId,SoftLibs.SGD_KEYUSAGE_KEYEXCHANGE);

            }
        }.send();
    }

}
