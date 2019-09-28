package com.bzu.gxs.smartrubbish;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bzu.gxs.smartrubbish.Data.user_data;
import com.bzu.gxs.smartrubbish.utils.ProgressHUD;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class Adapter_goods extends ArrayAdapter<MyGoodsdata> {
    private int resourceId;
    private Context context;
    private Handler handler=null;
    private String data_toast;

    public Adapter_goods(@NonNull Context context, int resource, @NonNull List<MyGoodsdata> objects) {
        super(context, resource, objects);
        resourceId=resource;
        this.context=context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final MyGoodsdata myGoodsdata=getItem(position);
        View view=LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        ImageView imageView=(ImageView)view.findViewById(R.id.iv_goods);
        final TextView textView_name=(TextView)view.findViewById(R.id.tv_goodsname);
        TextView textView_price=(TextView)view.findViewById(R.id.tv_goodsprice);
        Button goodsbuy=(Button)view.findViewById(R.id.btn_goodsbuy);
        imageView.setImageResource(myGoodsdata.getImageId());
        textView_name.setText(myGoodsdata.getName());
        textView_price.setText(myGoodsdata.getPrice());

        initProgressBar();//设置进度圆形
        //创建属于主线程的handler
        handler=new Handler();

        goodsbuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (textView_name.getText().equals("小米9透明尊享版12+512G")) {
//                    if (Integer.valueOf(user_data.integral)<999999){
//                        Toast.makeText(getContext(),"对不起，您的积分不足",Toast.LENGTH_SHORT).show();
//
//                    }else{
//
//
//                        showProgressBar();
////                        //子线程耗时操作
////                        new Thread(new Runnable() {
////                            @Override
////                            public void run() {
////                                SystemClock.sleep(3000);
////                                //UI线程更新UI
////                                handler.post(setpro1);
////
////                            }
////                        }).start();
//
//                        try {
//                            BuyRequest(Integer.valueOf(user_data.integral)-999999,"小米9透明尊享版12+512G",999999,R.drawable.mi9,"小米9透明尊享版12%2B512G");
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//
//
//                }
//                if (textView_name.getText().equals("华为P30pro 8+128G")) {
//                    if (Integer.valueOf(user_data.integral)<999999){
//                        Toast.makeText(getContext(),"对不起，您的积分不足",Toast.LENGTH_SHORT).show();
//
//                    }else{
//                        showProgressBar();
//
//                        try {
//                            BuyRequest(Integer.valueOf(user_data.integral)-999999,"华为P30pro 8+128G",999999,R.drawable.huaweip30p,"华为P30pro 8%2B128G");
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//
//                }
//
//                if (textView_name.getText().equals("小米MIX3 6+128G")) {
//                    if (Integer.valueOf(user_data.integral)<888888){
//                        Toast.makeText(getContext(),"对不起，您的积分不足",Toast.LENGTH_SHORT).show();
//
//                    }else{
//                        showProgressBar();
//
//                        try {
//                            BuyRequest(Integer.valueOf(user_data.integral)-888888,"小米MIX3 6+128G",888888,R.drawable.mimix3,"小米MIX3 6%2B128G");
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//
//
//                    }
//
//                }


                if (textView_name.getText().equals("多肉植物")) {
                    if (Integer.valueOf(user_data.integral)<666){
                        Toast.makeText(getContext(),"对不起，您的积分不足",Toast.LENGTH_SHORT).show();

                    }else{
                        showProgressBar();

                        try {
                            BuyRequest(Integer.valueOf(user_data.integral)-666,"多肉植物",666,R.drawable.duorou,"多肉植物");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }

                }

                if (textView_name.getText().equals("仙人球")) {
                    if (Integer.valueOf(user_data.integral)<766){
                        Toast.makeText(getContext(),"对不起，您的积分不足",Toast.LENGTH_SHORT).show();

                    }else{
                        showProgressBar();

                        try {
                            BuyRequest(Integer.valueOf(user_data.integral)-766,"仙人球",766,R.drawable.xianrenqiu,"仙人球");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }

                }

                if (textView_name.getText().equals("仙人掌")) {
                    if (Integer.valueOf(user_data.integral)<766){
                        Toast.makeText(getContext(),"对不起，您的积分不足",Toast.LENGTH_SHORT).show();

                    }else{
                        showProgressBar();

                        try {
                            BuyRequest(Integer.valueOf(user_data.integral)-766,"仙人掌",766,R.drawable.xianrenzhang,"仙人掌");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }

                }

                if (textView_name.getText().equals("文竹盆栽")) {
                    if (Integer.valueOf(user_data.integral)<866){
                        Toast.makeText(getContext(),"对不起，您的积分不足",Toast.LENGTH_SHORT).show();

                    }else{
                        showProgressBar();

                        try {
                            BuyRequest(Integer.valueOf(user_data.integral)-866,"文竹盆栽",866,R.drawable.wenzhu,"文竹盆栽");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }

                }



            }
        });

        return  view;
    }





    /*==============进度条==============*/
    private ProgressHUD mProgressHUD;

    private void initProgressBar() {
        if (mProgressHUD == null) {
            mProgressHUD = ProgressHUD.newInstance(getContext(), "购买中...", false, null);
        }
        mProgressHUD.setMessage("购买中...");
    }

    public void showProgressBar() {
        if (mProgressHUD != null && mProgressHUD.isShowing())
            return;
//        initProgressBar();
        mProgressHUD.show();
    }

    public void stopProgressBar() {
        if (mProgressHUD != null && mProgressHUD.isShowing()) {
            mProgressHUD.dismiss();
        }
    }

//    Runnable setpro1=new Runnable() {
//        @Override
//        public void run() {
//            stopProgressBar();
//            Toast.makeText(getContext(),"购买MI9成功",Toast.LENGTH_SHORT).show();
//            Goods_Config.goodsList.add(new MyGoodsdata("小米9透明尊享版12+512G",R.drawable.mi9,"$ 999999"));
//            user_data.integral=String.valueOf(Integer.valueOf(user_data.integral)-999999);
//        }
//    };
//
//    Runnable setpro2=new Runnable() {
//        @Override
//        public void run() {
//            stopProgressBar();
//            Toast.makeText(getContext(),"购买P30Pro成功",Toast.LENGTH_SHORT).show();
//            Goods_Config.goodsList.add(new MyGoodsdata("华为P30pro 8+128G",R.drawable.huaweip30p,"$ 999999"));
//            user_data.integral=String.valueOf(Integer.valueOf(user_data.integral)-999999);
//        }
//    };
//
//    Runnable setpro3=new Runnable() {
//        @Override
//        public void run() {
//            stopProgressBar();
//            Toast.makeText(getContext(),"购买MIX3成功",Toast.LENGTH_SHORT).show();
//            Goods_Config.goodsList.add(new MyGoodsdata("小米MIX3 6+128G",R.drawable.mimix3,"$ 888888"));
//            user_data.integral=String.valueOf(Integer.valueOf(user_data.integral)-888888);
//        }
//    };

    Runnable toast=new Runnable() {
        @Override
        public void run() {
            Toast.makeText(getContext(),data_toast,Toast.LENGTH_SHORT).show();
        }
    };


    public void BuyRequest(final int integral, final String name, final int cost_integral, final int img, final String name_nojia) throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection=null;
                BufferedReader reader=null;
                Log.i("dddfff",user_data.account);
                Log.i("dddfff",user_data.pwd);
                String account=user_data.account;
                String password=user_data.pwd;
                if (account.equals("")){
                    data_toast="请尝试重启解决问题";
                    handler.post(toast);
                }else if (password.equals("")){
                    data_toast="请尝试重启解决问题";
                    handler.post(toast);
                }else{
                    try{
                        String url1 = "http://47.102.201.183:8080/test3/BuyGoodsServlet?account="+account+"&password="+password+"&goodsname="+name_nojia+"&goodsvalue="+cost_integral;
                        Log.i("kijuy",url1);
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
                        String result="";
                        result=IntegralJSON(response.toString());
                        Log.i("result",result);
                        if (result.equals("")){
                            //数据库连接不上
                            Log.i("kijuy","1");
                            data_toast="请检查网络或联系我们";
                            handler.post(toast);//一般不会这样，在catch处会捕获异常
                        }else if (result.equals("成功")){
                            //数据库验证注册成功
                            Log.i("poi6","123");
                            stopProgressBar();
                            Goods_Config.goodsList.add(new MyGoodsdata(name,img,"$ "+cost_integral));
                            user_data.integral=String.valueOf(integral);
                            data_toast="购买成功~";
                            handler.post(toast);
                        }else if (result.equals("积分不足")){
                            data_toast="您的积分不足";
                            handler.post(toast);
                        }
                    }catch (Exception e){
                        data_toast="请检查网络或联系我们";
                        Log.i("kijuy","2");
                        handler.post(toast);//一般不会这样，在catch处会捕获异常
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


    private String IntegralJSON(String jsondata) {
        try {
            Log.i("jsondata", jsondata);
            JSONObject js = new JSONObject(jsondata);
            String jsonObject = js.getJSONObject("params").toString();
            Log.i("3232:", jsonObject);
            JSONObject object = new JSONObject(jsonObject);
            String ob = object.optString("Result");
            Log.i("ob", ob);
            return ob;

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }



}