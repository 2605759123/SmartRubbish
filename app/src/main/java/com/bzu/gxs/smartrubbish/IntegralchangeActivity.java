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

public class IntegralchangeActivity extends AppCompatActivity {
    private Adapter_integralchange adapter_integralchange;
    private List<DataInfo> mlist = null;
//    List<SearchIntegral_Data> list_data=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integralchange);
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

        setlist();

    }

    private void setlist() {
        adapter_integralchange = new Adapter_integralchange( IntegralchangeActivity.this);//创建下面的数据视图
        adapter_integralchange.clearItem();
        try {
            SearchIntegralChangeRequest();
        } catch (IOException e) {
            e.printStackTrace();
        }



//        initlist();
//        for (int j = 0; j < mlist.size(); j++) {
//            adapter_integralchange.addItem(mlist.get(j));
//            //mlist集合是用于存放界面中的值  并在跳转时传入item界面
//        }
//        ListView listView=(ListView)findViewById(R.id.list_view2);
//        listView.setAdapter(adapter_integralchange);




//        CharSequence dateText = DateFormat.format("yyyy-MM-dd kk:mm:ss", new Date());
//        String TIME=dateText.toString().substring(11);
//        makedataText2.setText(TIME);
    }




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
                        String url1 = "http://47.102.201.183:8080/test3/SearchIntegralServlet?account="+account+"&password="+password+"&uid="+user_data.uid;
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
                        IntegralchangeJSON(response.toString());


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

    private void IntegralchangeJSON(String s) throws JSONException {
        JSONObject jsonObject=new JSONObject(s);
        int i=1;
        mlist=new ArrayList<DataInfo>();
        while (true){
            try{
                JSONObject js2=jsonObject.getJSONObject(String.valueOf(i));//如果到第i个数据的时候找不到了 就说明到头了 进入catch
                String chancetime=js2.getString("chancetime");
                String integralchance=js2.getString("integralchance");
                String chancereason=js2.getString("chancereason");
                if (chancereason.equals("扔垃圾")){
                    chancereason="感谢您支持公益事业";
                }else{
                    chancereason="购买"+chancereason;
                }

                if (Integer.valueOf(integralchance)>0){
                    integralchance="本次变化+"+integralchance+"分";
                }else{
                    integralchance="本次变化"+integralchance+"分";
                }
                DataInfo data=new DataInfo(chancetime,chancereason,integralchance);
                mlist.add(data);
                i++;
            }catch (Exception e){
//                Log.i("debugg",Log.getStackTraceString(e));
                e.printStackTrace();
                break;//这时候退出
            }
        }

        for (int j = mlist.size()-1; j >=0; j--) {
            adapter_integralchange.addItem(mlist.get(j));
            //mlist集合是用于存放界面中的值  并在跳转时传入item界面
        }

//        for (int j = 0; j < mlist.size(); j++) {
//            adapter_integralchange.addItem(mlist.get(j));
//            //mlist集合是用于存放界面中的值  并在跳转时传入item界面
//        }
        refreshlist_view();


    }

    private void refreshlist_view(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ListView listView=(ListView)findViewById(R.id.list_view2);
                listView.setAdapter(adapter_integralchange);
            }
        });
    }



    private void toast(final String value){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(IntegralchangeActivity.this,value,Toast.LENGTH_SHORT).show();
            }
        });
    }



}
