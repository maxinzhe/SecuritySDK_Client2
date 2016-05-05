package com.servcie;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Xinzhe on 2016/4/25.
 */

public class  ServiceConstant{
     public   static int THREAD_TO_SERVER_LOCAL_PORT=8401;
      public    static  int THREAD_TO_SERVER_REMOTE_PORT=8901;

       public   static  InetAddress THREAD_TO_SERVER_LOCAL_ADDRESS;
       public   static  InetAddress THREAD_TO_SERVER_REMOTE_ADDRESS;

       public   static  int THREAD_TO_PEAR_LOCAL_PORT;
       public   static  int THREAD_TO_PEAR_REMOTE_PORT;


       public   static InetAddress THREAD_TO_PEAR_LOCAL_ADDRESS;
       public   static InetAddress THREAD_TO_PEAR_REMOTE_ADDRESS;
        public ServiceConstant() throws UnknownHostException {

            THREAD_TO_SERVER_REMOTE_ADDRESS=InetAddress.getByName("172.29.56.18");
        }
        public ServiceConstant(InetAddress THREAD_TO_PEAR_LOCAL_ADDRESS,int THREAD_TO_PEAR_LOCAL_PORT) throws UnknownHostException {

        }
        public void setPeerAddress(InetAddress THREAD_TO_PEAR_LOCAL_ADDRESS,int THREAD_TO_PEAR_LOCAL_PORT,InetAddress THREAD_TO_PEAR_REMOTE_ADDRESS) throws UnknownHostException {
        }
}
