package com.ccit.security.sdk.clientDemo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.servcie.VoipService;

/**
 * Created by Xinzhe on 2016/5/3.
 */
public class NoContactAlertDialog extends AlertDialog.Builder{


    public NoContactAlertDialog(final Context context) {
        super(context);

        this.setInverseBackgroundForced(false);
        this.setTitle("联系人");
        this.setMessage("请选择联系人再按拨打键");
        this.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
    }
}
