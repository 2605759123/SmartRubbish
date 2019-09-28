package com.bzu.gxs.smartrubbish;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bzu.gxs.smartrubbish.Data.public_data;
import com.bzu.gxs.smartrubbish.Data.user_data;
import com.bzu.gxs.smartrubbish.chenjin.StatusBarUtil;
import com.bzu.gxs.smartrubbish.utils.ProgressHUD;
import com.dd.processbutton.iml.ActionProcessButton;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity2 extends AppCompatActivity {

    @Bind(R.id.tosign)
    TextView tosign;
    @Bind(R.id.btnSignIn)
    ActionProcessButton btnSignIn;
    @Bind(R.id.sign_accout)
    EditText signAccout;
    @Bind(R.id.sign_passwd)
    EditText signPasswd;
    @Bind(R.id.cb_auto)
    CheckBox cbAuto;
    @Bind(R.id.cb_mima)
    CheckBox cbMima;

    SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        ButterKnife.bind(this);
//        initProgressBar();//初始化进度条
        makeBarUtil();//设置透明状态栏
        closeAndroidPDialog();
        initsp();
        JudgeCheck();
        Monitor();//监听记住密码和自动登陆
        initquanxian();

    }

    private static final int LOCATION_CODE = 1;
    private LocationManager lm;//【位置管理】
    public void initquanxian(){
        lm = (LocationManager) LoginActivity2.this.getSystemService(LoginActivity2.this.LOCATION_SERVICE);
        boolean ok = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (ok) {//开了定位服务
            if (ContextCompat.checkSelfPermission(LoginActivity2.this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                Log.e("BRG","没有权限");
                // 没有权限，申请权限。
                // 申请授权。
                ActivityCompat.requestPermissions(LoginActivity2.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_CODE);
//                        Toast.makeText(getActivity(), "没有权限", Toast.LENGTH_SHORT).show();

            } else {

                // 有权限了，去放肆吧。
//                        Toast.makeText(getActivity(), "有权限", Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.e("BRG","系统检测到未开启GPS定位服务");
            Toast.makeText(LoginActivity2.this, "系统检测到未开启GPS定位服务", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(intent, 1315);
        }
    }


    private void Monitor() {
        //设置监听
        //监听记住密码多选框按钮事件
        cbMima.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if (cbMima.isChecked()) {
                    Log.i("xuanzhong","记住密码已选中");
                    sp.edit().putBoolean("ISCHECK", true).commit();

                }else {
                    Log.i("xuanzhong","记住密码没有选中");
                    sp.edit().putBoolean("ISCHECK", false).commit();

                }

            }
        });

        //监听自动登录多选框事件
        cbAuto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if (cbAuto.isChecked()) {
                    Log.i("xuanzhong","自动登陆已选中");
                    sp.edit().putBoolean("AUTO_ISCHECK",true).commit();

                } else {
                    Log.i("xuanzhong","自动登陆没有选中");
                    sp.edit().putBoolean("AUTO_ISCHECK", false).commit();
                }
            }
        });
    }

    private void initsp() {
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        signAccout.setText(sp.getString("account",""));
    }

    private void JudgeCheck() {
        //判断记住密码多选框的状态
        if(sp.getBoolean("ISCHECK", false))
        {
            //设置默认是记录密码状态
            cbMima.setChecked(true);
            signAccout.setText(sp.getString("USER_NAME",""));
            signPasswd.setText(sp.getString("PASSWORD", ""));
            //判断自动登陆多选框状态
            if(sp.getBoolean("AUTO_ISCHECK", false))
            {
                //设置默认是自动登录状态
                cbAuto.setChecked(true);
                //跳转界面
                try {
                    SearchRequest();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    @OnClick({R.id.tosign, R.id.btnSignIn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tosign:
                Intent intent = new Intent(LoginActivity2.this, RegisterActivity2.class);
                startActivity(intent);


//                showProgressBar();
//                //子线程耗时操作
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        SystemClock.sleep(3000);
//                        //UI线程更新UI
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                stopProgressBar();
//                            }
//                        });
//                    }
//                }).start();

                break;
            case R.id.btnSignIn:
                btnSignIn.setMode(ActionProcessButton.Mode.ENDLESS);
                btnSignIn.setProgress(50);
                try {
                    SearchRequest();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    /*==============进度条==============*/
    private ProgressHUD mProgressHUD;

    private void initProgressBar() {
        if (mProgressHUD == null) {
            mProgressHUD = ProgressHUD.newInstance(this, "正在登录...", false, null);
        }
        mProgressHUD.setMessage("正在登录...");
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

    private void makeBarUtil() {
        //当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的 padding
        StatusBarUtil.setRootViewFitsSystemWindows(this, true);
        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(this);
        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
        if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
            //这样半透明+白=灰, 状态栏的文字能看得清
            StatusBarUtil.setStatusBarColor(this, 0x55000000);
        }
    }





    private void toast(final String toast){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LoginActivity2.this,toast,Toast.LENGTH_SHORT).show();
            }
        });
    }





    //以下是获取服务器配置数据
    public void SearchRequest() throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("poi2","123");
                HttpURLConnection connection=null;
                BufferedReader reader=null;
                String account=signAccout.getText().toString();
                String password=signPasswd.getText().toString();
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
                        String url1 = "http://47.102.201.183:8080/test3/SearchServlet?account="+account+"&password="+password;
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
                        if (response.toString().equals("{}")){
                            toast("用户名或密码错误");
                            setProgress2(0);
                        }else {
                            IntentionJSON(response.toString());//这里获取了uid，integral以及这个用户分别回收了多少的东西
                            if (user_data.uid.equals("")){
                                setProgress2(0);
                                toast("没有绑定uid");

//                                Intent intent=new Intent(LoginActivity2.this,MainActivity.class);
//                                startActivity(intent);
                            }else{
//                                setProgress2(50);
//                                user_data.account=account;
//                                user_data.pwd=password;
//                                Intent intent=new Intent(LoginActivity2.this,MainActivity.class);
//                                startActivity(intent);
//                                finish();//销毁当前activity
                                SearchObjectRequest();//开始获取object和class数据
                            }
                        }

//                        Login_into();//开始连接ocean


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

    private void setProgress2(final int process){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                btnSignIn.setProgress(process);
            }
        });
    }


    private void IntentionJSON(String jsondata){
        try{
            Log.i("jsondata",jsondata);
            JSONObject js=new JSONObject(jsondata);
            String jsonObject=js.getJSONObject("1").toString();
            Log.i("3232:",jsonObject);
            JSONObject object=new JSONObject(jsonObject);
            user_data.uid=object.optString("uid");
            user_data.integral=object.optString("integral");
            user_data.kehuishou_num=object.optString("kehuishou_num");
            user_data.youhai_num=object.optString("youhai_num");
            user_data.canyu_num=object.optString("canyu_num");
            user_data.qita_num=object.optString("qita_num");



        }catch (Exception e){
            e.printStackTrace();
        }
    }


    //以下是获取服务器配置数据
    /*
    @ning
    {"22":{"object":"受污染与无法再生的纸张"},"23":{"object":"污染或其他不可回收的玻璃"},"24":{"object":"塑料袋与其他受污染的塑料制品"},"25":{"object":"废旧衣物与其他纺织品"},"26":{"object":"破旧陶瓷品"},"27":{"object":"难以自然降解是肉食骨骼"},"28":{"object":"一次性餐具"},"29":{"object":"烟头"},"30":{"object":"灰土"},"10":{"object":"肉碎骨"},"11":{"object":"蛋壳"},"12":{"object":"畜禽产品内脏"},"13":{"object":"镉镍电池"},"14":{"object":"氧化汞电池"},"15":{"object":"铅蓄电池"},"16":{"object":"日光灯管"},"17":{"object":"节能灯"},"18":{"object":"废温度计"},"19":{"object":"废血压计"},"1":{"object":"废纸"},"2":{"object":"废塑料"},"3":{"object":"废金属"},"4":{"object":"废包装物"},"5":{"object":"废旧纺织物"},"6":{"object":"废弃电子产品"},"7":{"object":"废纸塑铝复合包装"},"8":{"object":"蔬菜瓜果垃圾"},"9":{"object":"腐肉"},"20":{"object":"废药品及其包装物"},"21":{"object":"废油漆"}}
     */
    public void SearchObjectRequest() throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection=null;
                BufferedReader reader=null;
                String account=signAccout.getText().toString();
                String password=signPasswd.getText().toString();
                if (account.equals("")){
                    toast("请输入用户名");
                }else if (password.equals("")){
                    toast("请输入密码");
                }else{
                    try{
                        String url1 = "http://47.102.201.183:8080/test3/SearchObjectServlet?account="+account+"&password="+password;
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
                        if (response.toString().equals("{}")){
                            toast("用户名或密码错误");
                            setProgress2(0);
                        }else {
                            ObjectJSON(response.toString());//这里获取了object，class
                            if (user_data.uid.equals("")){
                                setProgress2(0);
                                toast("没有绑定uid");
                            }else{
                                setProgress2(50);
                                user_data.account=account;
                                user_data.pwd=password;
                                Intent intent=new Intent(LoginActivity2.this,MainActivity.class);
                                startActivity(intent);
                                finish();//销毁当前activity
                            }
                        }

//                        Login_into();//开始连接ocean


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


    private void ObjectJSON(String jsondata){
        try{
            int i=1;
            String jsonObject="";
            JSONObject js=new JSONObject(jsondata);
            while(!js.getJSONObject(String.valueOf(i)).toString().equals("")) {
                //当json还存在
                jsonObject=js.getJSONObject(String.valueOf(i)).toString();
                JSONObject object=new JSONObject(jsonObject);
                String object_string=object.optString("object");
                String class_string=object.optString("cllass");//由于服务器class显示不出来 所以用cllass代替了一下
                public_data.message.put(object_string,class_string);

                i++;
            }






        }catch (Exception e){
            e.printStackTrace();
        }
    }


//去掉在安卓9.0的弹窗问题
    private void closeAndroidPDialog(){
        try {
            Class aClass = Class.forName("android.content.pm.PackageParser$Package");
            Constructor declaredConstructor = aClass.getDeclaredConstructor(String.class);
            declaredConstructor.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Class cls = Class.forName("android.app.ActivityThread");
            Method declaredMethod = cls.getDeclaredMethod("currentActivityThread");
            declaredMethod.setAccessible(true);
            Object activityThread = declaredMethod.invoke(null);
            Field mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown");
            mHiddenApiWarningShown.setAccessible(true);
            mHiddenApiWarningShown.setBoolean(activityThread, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
