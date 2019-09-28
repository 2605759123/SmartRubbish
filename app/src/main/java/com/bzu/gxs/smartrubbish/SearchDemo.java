package com.bzu.gxs.smartrubbish;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bzu.gxs.smartrubbish.Data.public_data;
import com.bzu.gxs.smartrubbish.FlowLayout.FlowLayout;
import com.bzu.gxs.smartrubbish.chenjin.StatusBarUtil;
import com.bzu.gxs.smartrubbish.views.MyTextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import scut.carson_ho.searchview.ICallBack;
import scut.carson_ho.searchview.SearchView;
import scut.carson_ho.searchview.bCallBack;

/**
 * Created by Carson_Ho on 17/8/11.
 */

public class SearchDemo extends AppCompatActivity {
    private FlowLayout flowLayout;
    private List<String> list=new ArrayList<>();

    // 1. 初始化搜索框变量
    private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 2. 绑定视图
        setContentView(R.layout.activity_search);



        //当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的 padding
//        StatusBarUtil.setRootViewFitsSystemWindows(this,true);
        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(this);
        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
        if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
            //这样半透明+白=灰, 状态栏的文字能看得清
            StatusBarUtil.setStatusBarColor(this,0x55000000);
        }



        // 3. 绑定组件
        searchView = (SearchView) findViewById(R.id.search_view);

        // 4. 设置点击搜索按键后的操作（通过回调接口）
        // 参数 = 搜索框输入的内容
        searchView.setOnClickSearch(new ICallBack() {
            @Override
            public void SearchAciton(String string) {
                if (public_data.message.containsKey(string)){
                    //如果有这个垃圾
                    Toast.makeText(SearchDemo.this,string+"是"+public_data.message.get(string),Toast.LENGTH_SHORT).show();
//                    System.out.println(string+"是"+public_data.message.get(string));
                }else{
                    Toast.makeText(SearchDemo.this,"没有搜索到哦，给我们反馈一下吧~",Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 5. 设置点击返回按键后的操作（通过回调接口）
        searchView.setOnClickBack(new bCallBack() {
            @Override
            public void BackAciton() {
                finish();
            }
        });



        flowLayout = (FlowLayout) findViewById(R.id.flow);


//往容器内添加TextView数据
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10, 5, 10, 5);
        if (flowLayout != null) {
            flowLayout.removeAllViews();
        }

        for(Map.Entry<String,String> entry:public_data.message.entrySet()){
//            System.out.println("key= "+entry.getKey()+" and value= "+entry.getValue());
            MyTextView tv = new MyTextView(this);
            tv.setPadding(28, 10, 28, 10);
            tv.setText(entry.getKey());
            tv.setSingleLine();
            tv.setTextColor(0xFFFFFFFF);
            if (entry.getValue().equals("可回收物")){
                tv.setBackgroundResource(R.drawable.liushibujv10);
            }else if (entry.getValue().equals("有害垃圾")){
                tv.setBackgroundResource(R.drawable.liushibujv_youhai_red);
            }else if (entry.getValue().equals("餐余垃圾")){
                tv.setBackgroundResource(R.drawable.liushibujv_canyu);
            }else{
                //其他垃圾
                tv.setBackgroundResource(R.drawable.liushibujv_qita);
            }

            tv.setLayoutParams(layoutParams);
            tv.setCllass(entry.getValue());
            flowLayout.addView(tv, layoutParams);
        }
//
//        for (int i = 0; i < public_data.message.size(); i++) {
//            MyTextView tv = new MyTextView(this);
//            tv.setPadding(28, 10, 28, 10);
//            tv.setText(list.get(i));
//            tv.setMaxEms(10);
//            tv.setSingleLine();
//            tv.setBackgroundResource(R.drawable.liushibujv6);
//            tv.setLayoutParams(layoutParams);
//            flowLayout.addView(tv, layoutParams);
//        }




    }
}