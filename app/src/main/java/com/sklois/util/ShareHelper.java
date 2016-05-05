package com.sklois.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Xinzhe on 2016/4/27.
 */
public class ShareHelper {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public ShareHelper(String sharefilename, Context context) {
        sharedPreferences=context.getSharedPreferences(sharefilename,Context.MODE_WORLD_WRITEABLE);
        editor=sharedPreferences.edit();

    }
    public String readShareString(String key){
        return sharedPreferences.getString(key,"");
    }
    public void  writeShareString(String key,String value){
        editor.putString(key,value);
        editor.commit();
    }
}
