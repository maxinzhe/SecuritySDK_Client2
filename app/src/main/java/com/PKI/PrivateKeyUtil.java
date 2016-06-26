package com.PKI;

import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Xinzhe on 2016/5/22.
 */
public class PrivateKeyUtil {
    String fileName;
    public PrivateKeyUtil(String fileName) {
       this.fileName=fileName+"privateKey";

    }
    public void writePrivateKeytoSD(String privateKeyString) throws FileNotFoundException {

        File f = Environment.getExternalStorageDirectory();//获取SD卡目录
        File fileDir = new File(f,fileName+".txt");
        FileOutputStream os = new FileOutputStream(fileDir);
        Log.i("test","要写入的文件的内容是:"+privateKeyString);

        try {
            os.write(privateKeyString.getBytes());
            os.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Log.i("test","文件写入完毕");
    }


    public String readPrivateKeyfromSD() throws IOException {

        Log.i("test","准备读取文件");
        File f=Environment.getExternalStorageDirectory();
        File fileDir=new File(f,fileName+".txt");
        FileInputStream is=new FileInputStream(fileDir);
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        byte[] array=new byte[1024];
        int len=-1;
        while((len=is.read(array))!=-1){
            bos.write(array,0,len);
        }
        bos.close();
        is.close();
        Log.i("test","读取文件的结果为"+bos.toString()) ;
        return bos.toString();
    }
}
