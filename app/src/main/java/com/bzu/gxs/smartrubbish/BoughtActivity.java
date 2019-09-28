package com.bzu.gxs.smartrubbish;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.bzu.gxs.smartrubbish.Data.user_data;
import com.bzu.gxs.smartrubbish.chenjin.StatusBarUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BoughtActivity extends AppCompatActivity {
    ListView listView;
    private List<MyGoodsdata> mlist = null;//逆序
    private List<MyGoodsdata> mlist2 = null;//正序

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bought);

        //当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的 padding
        StatusBarUtil.setRootViewFitsSystemWindows(this,true);
        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(this);
        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
        if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
            //这样半透明+白=灰, 状态栏的文字能看得清
            StatusBarUtil.setStatusBarColor(this,0x55000000);
        }

        try {
            SearchIntegralChangeRequest();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        initList();

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
////            @Override
////            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////                final MyGoodsdata z=Goods_Config.goodsList.get(position);
////                if (z.getName().equals("小米9透明尊享版12+512G")) {
////                    Toast.makeText(BoughtActivity.this,"you set mi9",Toast.LENGTH_SHORT).show();
//////                    Goods_Config.goodsList.add(new MyGoodsdata("小米9透明尊享版12+512G",R.drawable.mi9,"$ 999999"));
////
////                }
////                if (z.getName().equals("华为P30pro 8+128G")) {
////                    Toast.makeText(BoughtActivity.this,"you set p30",Toast.LENGTH_SHORT).show();
//////                    Goods_Config.goodsList.add(new MyGoodsdata("华为P30pro 8+128G",R.drawable.huaweip30p,"$ 999999"));
////                }
////                if (z.getName().equals("小米MIX3 6+128G")) {
////                    Toast.makeText(BoughtActivity.this,"you set mix3",Toast.LENGTH_SHORT).show();
//////                    Goods_Config.goodsList.add(new MyGoodsdata("小米MIX3 6+128G",R.drawable.mimix3,"$ 888888"));
////
////                }
////            }
////        });
    }

//    private void initList() {
//        Adapter_bought adapter_bought=new Adapter_bought(BoughtActivity.this,R.layout.bought_data,Goods_Config.goodsList);//获取Goods_config里面寸的购买数据
//        ListView listView=(ListView)findViewById(R.id.list_bought);
//        listView.setAdapter(adapter_bought);
//    }


    //以下是获取服务器配置数据
    public void SearchIntegralChangeRequest() throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("poi2","123");
                HttpURLConnection connection=null;
                BufferedReader reader=null;
                String account= user_data.account;
                String password=user_data.pwd;
                Log.i("1user",account);
                if (account.equals("")){
                    Log.i("poi3","123");
                    toast("请输入用户名");
                }else if (password.equals("")){
                    Log.i("poi4","123");
                    toast("请输入密码");
                }else{
                    Log.i("poi5","123");
                    try{
                        String url1 = "http://47.102.201.183:8080/test3/SearchBoughtServlet?account="+account+"&password="+password;
                        //String tag = "Login";
                        URL url=new URL(url1);
                        connection=(HttpURLConnection)url.openConnection();
                        connection.setRequestMethod("GET");
                        connection.setConnectTimeout(8000);
                        connection.setReadTimeout(8000);
                        InputStream in=connection.getInputStream();
                        //下面对获取到的输入流进行读取
                        reader=new BufferedReader(new InputStreamReader(in));
                        StringBuilder response=new StringBuilder();
                        String line;
                        while ((line=reader.readLine())!=null){
                            response.append(line);
                        }
                        BounghtJSON(response.toString());


                    }catch (Exception e){
                        toast("数据库连接失败，请联系我们");
                        Log.i("debugg",Log.getStackTraceString(e));
                        e.printStackTrace();
                    }finally {
                        if(reader!=null){
                            try {
                                reader.close();
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        }
                        if (connection!=null){
                            connection.disconnect();
                        }
                    }
                }

            }
        }).start();
    }

    private void BounghtJSON(String s) throws JSONException {
        JSONObject jsonObject=new JSONObject(s);
        int i=1;
        mlist=new ArrayList<MyGoodsdata>();
        mlist2=new ArrayList<MyGoodsdata>();
        while (true){
            try{
                JSONObject js2=jsonObject.getJSONObject(String.valueOf(i));//如果到第i个数据的时候找不到了 就说明到头了 进入catch
                String goodsname=js2.getString("goodsname");
                String goodsvalue=js2.getString("goodsvalue");
                int imgid;
                if (goodsname.equals("多肉植物")){
                    imgid=R.drawable.duorou;
                }else if (goodsname.equals("仙人球")){
                    imgid=R.drawable.xianrenqiu;
                }else if (goodsname.equals("仙人掌")){
                    imgid=R.drawable.xianrenzhang;
                }else if (goodsname.equals("文竹盆栽")){
                    imgid=R.drawable.wenzhu;
                }
//                else if (goodsname.equals("小米9透明尊享版12+512G")){
////                    imgid=R.drawable.mi9;
////                }else if(goodsname.equals("华为P30pro 8+128G")){
////                    imgid=R.drawable.huaweip30p;
////                }else if (goodsname.equals("小米MIX3 6+128G")){
////                    imgid=R.drawable.mimix3;
////                }
                else {
                    imgid=R.drawable.duorou;
                }

                MyGoodsdata data=new MyGoodsdata(goodsname,imgid,goodsvalue);
                mlist.add(data);
                i++;
            }catch (Exception e){
//                Log.i("debugg",Log.getStackTraceString(e));
                e.printStackTrace();
                break;//这时候退出
            }
        }

//        for (int j = 0; j < mlist.size(); j++) {
//            adapter_integralchange.addItem(mlist.get(j));
//            //mlist集合是用于存放界面中的值  并在跳转时传入item界面
//        }
        for (i = mlist.size() - 1; i>=0; i--){
            mlist2.add(mlist.get(i));
        }
        refreshlist_view();


    }

    private void refreshlist_view(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Adapter_bought adapter_bought=new Adapter_bought(BoughtActivity.this,R.layout.bought_data,mlist2);//获取Goods_config里面寸的购买数据
                ListView listView=(ListView)findViewById(R.id.list_bought);
                listView.setAdapter(adapter_bought);

            }
        });
    }



    private void toast(final String value){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BoughtActivity.this,value,Toast.LENGTH_SHORT).show();
            }
        });
    }



}
