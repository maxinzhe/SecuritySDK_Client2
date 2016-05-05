package com.sklois.util;

import android.content.Context;
import android.util.Log;

import com.sklois.demo.MainActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by Xinzhe on 2016/4/27.
 */
public class AppFileUtil {
    Context context;

   static String certPath="rootCert/root.cer";
    //String certPath="rootCert/srca.cer";
    //String certPath="rootCert/son.cer";
   //static String certPath="rootCert/0410.cer";
    //String certPath="rootCert/360base64.cer";
    //	String certPath="rootCert/360.cer";

    String fileName;

    public AppFileUtil(Context context) {
        fileName = certPath;
        this.context=context;
    }
    public void setFileAppPath(String path){
        fileName=path;
    }
    public String getFileString( ){

        String fileString="";
        try {
            // 建议使用UTF-8和Unix换行符。
            // 文件路径为相对路径，比如：资源文件位于assets/smiley/sample.txt,则打开文件时，
            // getResources().getAssets().open("smiley/sample.txt")
            Log.i("test","fileName:"+fileName);
            BufferedReader bufReader = new BufferedReader(new InputStreamReader(context.getResources().getAssets().open(fileName) ));
            String line="";
            while((line = bufReader.readLine()) != null)
                fileString += line;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileString;
    }
}
