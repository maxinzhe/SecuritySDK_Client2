package com.ccit.security.sdk.clientDemo;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * Created by Xinzhe on 2016/5/3.
 */
public class TestLinear  extends LinearLayout implements Checkable{


    private TextView textViewId;
    private TextView textViewName;
    private RadioButton mRadioButton;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        textViewId=(TextView)findViewById(R.id.id_list_view);
        textViewId=(TextView)findViewById(R.id.name_list_view);
        mRadioButton=(RadioButton)findViewById(R.id.radio_button_list_view);
    }

    public TestLinear(Context context) {
        super(context);
    }

    public TestLinear(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public TestLinear(Context context, AttributeSet attrs) {
        super(context, attrs);
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


