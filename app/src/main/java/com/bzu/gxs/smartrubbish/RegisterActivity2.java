package com.bzu.gxs.smartrubbish;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bzu.gxs.smartrubbish.chenjin.StatusBarUtil;
import com.dd.processbutton.iml.ActionProcessButton;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity2 extends AppCompatActivity {

    @Bind(R.id.Sign_back_to_signin)
    Button SignBackToSignin;
    @Bind(R.id.btnSignIn)
    ActionProcessButton btnSignIn;
    @Bind(R.id.register_account)
    EditText registerAccount;
    @Bind(R.id.register_passwd)
    EditText registerPasswd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        ButterKnife.bind(this);
        makeBarUtil();//设置透明状态栏
    }

    @OnClick({R.id.Sign_back_to_signin, R.id.btnSignIn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Sign_back_to_signin:
                Intent intent = new Intent(RegisterActivity2.this, LoginActivity2.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btnSignIn:
                btnSignIn.setMode(ActionProcessButton.Mode.ENDLESS);
                btnSignIn.setProgress(50);
                try {
                    RegisterRequest();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
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






    public void RegisterRequest() throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection=null;
                BufferedReader reader=null;
                String account=registerAccount.getText().toString();
                String password=registerPasswd.getText().toString();
                if (account.equals("")){
                    toast("请输入用户名");
                }else if (password.equals("")){
                    toast("请输入密码");
                }else{
                    try{
                        String url1 = "http://47.102.201.183:8080/test3/RegisterServlet?account="+account+"&password="+password;
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
                        result=RegisterJSON(response.toString());
                        Log.i("result",result);
                        if (result.equals("")){
                            //数据库连接不上
                            toast("验证账号密码失败，请检查网络或联系我们");//一般不会这样，在catch处会捕获异常
                            btnSignIn.setProgress(0);
                        }else if (result.equals("注册成功")){
                            //数据库验证注册成功
                            Log.i("poi6","123");
                            toast("注册成功，快去登陆吧~");
                            btnSignIn.setProgress(100);
                        }else if(result.equals("账号已存在")){
                            //数据库验证账号已经存在
                            toast("此账号已经被注册了，换一个试试吧~");
                            btnSignIn.setProgress(0);
                        }
                    }catch (Exception e){
                        toast("验证账号密码失败，请检查网络或联系我们");
                        btnSignIn.setProgress(0);
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

    private void toast(final String toast){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(RegisterActivity2.this,toast,Toast.LENGTH_SHORT).show();
            }
        });
    }


    private String RegisterJSON(String jsondata) {
        try {
            Log.i("jsondata", jsondata);
            JSONObject js = new JSONObject(jsondata);
            String jsonObject = js.getJSONObject("结果").toString();
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
