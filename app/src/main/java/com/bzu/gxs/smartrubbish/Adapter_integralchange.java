package com.bzu.gxs.smartrubbish;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2018/5/12.
 */

public class Adapter_integralchange extends BaseAdapter{
    private Context mContext2;
    private List<DataInfo> mlist2=null;
//    private String time;
    public Adapter_integralchange(Context context){
        this.mContext2=context;
        mlist2=new ArrayList<DataInfo>();

    }
    public void clearItem() {
        mlist2.clear();
    }
    public void addItem(DataInfo item) {
        mlist2.add(item);
    }
    @Override
    public int getCount() {
        return mlist2.size();
    }

    @Override
    public DataInfo getItem(int position) {
        DataInfo item = null;
        if (position >= 0 && getCount() > position) {
            item = mlist2.get(position);
        }
        return item;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder vh;
        if (convertView == null){
            convertView = LayoutInflater.from(mContext2).inflate(R.layout.integralchangedata,parent,false);
            vh = new ViewHolder();
            vh.xiugaitime = (TextView) convertView.findViewById(R.id.xiugaitime);
            vh.laiyuan=(TextView) convertView.findViewById(R.id.laiyuan);
            vh.qingkuang=(TextView) convertView.findViewById(R.id.qingkuang);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        DataInfo info = getItem(position);
        //时间格式转换

//        time=info.getDevicetimestamp();


        /*
        int b = Integer.parseInt(time.substring(9,11));
        b=b+8;
        int c=Integer.parseInt(time.substring(11,13));
        c=c+4;
        int d=Integer.parseInt(time.substring(13,15));
        d=d+4;
        String TIME= time.substring(0,4)+"-"+time.substring(4,6)+"-"+time.substring(6,8)+"  "+b+":"+time.substring(11,13)+":"+time.substring(13,15);
        Log.i("TIME2",TIME);*/
        /////////////////////////////////////////////////////////////////////////////////////////////////////
        vh.xiugaitime.setText(info.getXiugaitime());//xinlv
        vh.laiyuan.setText(info.getLaiyuan());
        vh.qingkuang.setText(info.getQingkuang());
        //////////////////////////////////////////////////////////////////////////////////////////////////////
        return convertView;
    }

    class ViewHolder{

        TextView xiugaitime;
        TextView laiyuan;
        TextView qingkuang;
    }
}
