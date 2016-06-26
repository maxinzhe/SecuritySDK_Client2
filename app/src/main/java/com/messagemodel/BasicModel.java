package com.messagemodel;

import java.util.HashMap;
import java.util.Map;

import udpReliable.JsonModel;

/**
 * Created by Xinzhe on 2016/4/26.
 */
public class BasicModel {
    public
    JsonModel.MESSAGE_TYPE t;
    public byte[][] dhKey;
    public String[] dhKeyS;
    public byte[] data;
    public String s1 ="";
    public String s2 ="";
    public int port;
    public Map<String,String> list=new HashMap<String,String>();

}
