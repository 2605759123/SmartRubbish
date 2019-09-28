package com.bzu.gxs.smartrubbish.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class MyTextView extends android.support.v7.widget.AppCompatTextView {
    private String cllass="";

    public MyTextView(final Context context) {
        super(context);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,String.valueOf(getText())+"属于"+cllass,Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void setCllass(String cllass){
        this.cllass=cllass;
    }




    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        super.setOnClickListener(l);

    }
}
