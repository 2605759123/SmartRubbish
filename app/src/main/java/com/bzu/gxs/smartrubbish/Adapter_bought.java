package com.bzu.gxs.smartrubbish;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class Adapter_bought extends ArrayAdapter<MyGoodsdata> {
    private int resourceId;
    private Context context;

    public Adapter_bought(@NonNull Context context, int resource, @NonNull List<MyGoodsdata> objects) {
        super(context, resource, objects);
        resourceId=resource;
        this.context=context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final MyGoodsdata myGoodsdata=getItem(position);
        View view=LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        ImageView imageView=(ImageView)view.findViewById(R.id.iv_bought);
        TextView textView_name=(TextView)view.findViewById(R.id.tv_boughtname);
        TextView textView_price=(TextView)view.findViewById(R.id.tv_boughtprice);
        imageView.setImageResource(myGoodsdata.getImageId());
        textView_name.setText(myGoodsdata.getName());
        textView_price.setText(myGoodsdata.getPrice());

        return  view;
    }




}