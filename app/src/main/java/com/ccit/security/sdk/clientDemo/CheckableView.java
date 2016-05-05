package com.ccit.security.sdk.clientDemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * Created by Xinzhe on 2016/5/3.
 */
public class CheckableView extends LinearLayout implements Checkable {


    private TextView textViewId;
    private TextView textViewName;
    private RadioButton mRadioButton;


    public CheckableView(Context context, TextView textViewId, TextView textViewName, RadioButton mRadioButton) {
        super(context);
        this.textViewId = textViewId;
        this.textViewName = textViewName;
        this.mRadioButton = mRadioButton;
    }

    public CheckableView(Context context, AttributeSet attrs, TextView textViewId, TextView textViewName, RadioButton mRadioButton) {
        super(context, attrs);
        this.textViewId = textViewId;
        this.textViewName = textViewName;
        this.mRadioButton = mRadioButton;
    }

    public CheckableView(Context context, AttributeSet attrs, int defStyle, TextView textViewId, TextView textViewName, RadioButton mRadioButton) {
        super(context, attrs, defStyle);
        this.textViewId = textViewId;
        this.textViewName = textViewName;
        this.mRadioButton = mRadioButton;
    }

    CheckableView(Context context){
        super(context);
        View.inflate(context,R.layout.list_view_layout,this);
        textViewId=(TextView)findViewById(R.id.id_list_view);
        textViewName=(TextView)findViewById(R.id.name_list_view);
        mRadioButton=(RadioButton)findViewById(R.id.radio_button_list_view);
    }
    @Override
    public void setChecked(boolean checked) {
        mRadioButton.setChecked(checked);
    }

    @Override
    public boolean isChecked() {
        return mRadioButton.isChecked();
    }

    @Override
    public void toggle() {
        mRadioButton.toggle();
    }
}
