package com.bzu.gxs.smartrubbish.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bzu.gxs.smartrubbish.MySettingdata;
import com.bzu.gxs.smartrubbish.R;

import java.util.List;

public class Adapter extends ArrayAdapter<MySettingdata> {
    private int resourceId;
    private Context context;
    private String nowzhuangtai;

    public Adapter(@NonNull Context context, int resource, @NonNull List<MySettingdata> objects) {
        super(context, resource, objects);
        resourceId=resource;
        this.context=context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final MySettingdata mySettingdata=getItem(position);
        View view=LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        ImageView imageView=(ImageView)view.findViewById(R.id.whatimage);
        TextView textView=(TextView)view.findViewById(R.id.whatname);
        imageView.setImageResource(mySettingdata.getImageId());
        textView.setText(mySettingdata.getName());
//        if (mySettingdata.getName().equals("退出登陆")){
//            switchButton.setVisibility(view.INVISIBLE);
//        }

        return  view;
    }




}