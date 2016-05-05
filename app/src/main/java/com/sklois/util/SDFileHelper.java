package com.sklois.util;

import android.os.Environment;

import org.apache.http.util.EncodingUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Xinzhe on 2016/4/27.
 */
public class SDFileHelper {
    public SDFileHelper() {
    }

    /**
     *
     * @param fileName Just the the path from the sd root+fileName
     * @param write_str  the data to write
     * @throws IOException
     */
    public void writeFileSdcardFile(String fileName,String write_str) throws IOException {
        File file=new File(getDefaultFilePath(),fileName);
        try{

            FileOutputStream fout = new FileOutputStream(file);
            byte [] bytes = write_str.getBytes();

            fout.write(bytes);
            fout.close();
        }

        catch(Exception e){
            e.printStackTrace();
        }
    }


    //读SD中的文件

    /**
     *
     * @param fileName:path from the root of sd
     * @return
     * @throws IOException
     */
    public String readFileSdcardFile(String fileName) throws IOException{
        String res="";
        File file =new File(getDefaultFilePath(),fileName);
        try{
            FileInputStream fin = new FileInputStream(file);

            int length = fin.available();

            byte [] buffer = new byte[length];
            fin.read(buffer);

            res = EncodingUtils.getString(buffer, "UTF-8");

            fin.close();
        }

        catch(Exception e){
            e.printStackTrace();
        }
        return res;
    }
    private  File getDefaultFilePath() {
        File rootPath = Environment.getExternalStorageDirectory();
        return rootPath;
    }
}
