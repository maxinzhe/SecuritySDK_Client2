package com.messagehandler;

import android.os.Handler;
import android.os.Message;

import com.ccit.security.sdk.clientDemo.CallWindow;
import com.mApplication;
import com.messagemodel.BasicModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Xinzhe on 2016/4/30.
 */
public class CONTACTS_LIST_Handler  extends BasicMessageHandler{
    Map<String ,String > rawlist;
    List<Map<String,String>> contactsList;
    public CONTACTS_LIST_Handler(BasicModel basicModel) {
        super(basicModel);
        rawlist =basicModel.list;

    }
    protected void initList(){
        contactsList=new ArrayList<Map<String, String>>();
        HashMap<String,String> item=new HashMap<String, String>();
        for(Map.Entry<String,String> entry:rawlist.entrySet()){
            item.put("id",entry.getKey());
            item.put("name", entry.getValue());
            contactsList.add(item);
        }

        mApplication.contactsList=contactsList;
    }

    @Override
    public void run() {
        super.run();
        initList();

        if(CallWindow.isOnTop){
            Handler handler=CallWindow.handler;
            Message msg=new Message();
            msg.what=0;
            handler.sendMessage(msg);//send a message to refresh the UI
        }
    }
}
