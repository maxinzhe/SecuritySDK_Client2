package com.messagemodel;

import java.util.HashMap;
import java.util.Map;

import udpReliable.JsonModel;

/**
 * Created by Xinzhe on 2016/4/26.
 */
public class BasicModel {
    public
    JsonModel.MESSAGE_TYPE type;
    public byte[] data;
    public String string1="";
    public String string2="";
    public int port;
    public Map<String,String> list=new HashMap<String,String>();

}
