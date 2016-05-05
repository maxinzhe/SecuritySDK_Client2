package com.messagehandler;

import android.os.Handler;
import android.os.Message;

import com.ccit.security.sdk.clientDemo.CallWindow;
import com.mApplication;
import com.messagemodel.BasicModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Xinzhe on 2016/4/30.
 */
public class CONTACTS_LOGOFF_Handler extends CONTACTS_LIST_Handler {
    public CONTACTS_LOGOFF_Handler(BasicModel basicModel) {
        super(basicModel);
    }

    @Override
    protected void initList() {
        super.initList();

        contactsList=new ArrayList<Map<String, String>>();
        HashMap<String,String> item=new HashMap<String, String>();
        for(Map.Entry<String,String> entry:rawlist.entrySet()){
            item.put("id",entry.getKey());
            item.put("name", entry.getValue());
            if(contactsList.contains(item)){
                contactsList.remove(item);
            }
        }

        mApplication.contactsList=contactsList;
    }

    /**
     * 1.delete the off line record from the mApplication.contactList
     * 2.refresh the UI of contactViewList
     */
    @Override
    public void run() {
        super.run();
    }
}
