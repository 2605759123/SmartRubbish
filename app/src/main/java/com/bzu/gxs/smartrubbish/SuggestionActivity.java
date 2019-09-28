package com.bzu.gxs.smartrubbish;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bzu.gxs.smartrubbish.chenjin.StatusBarUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SuggestionActivity extends AppCompatActivity {

    @Bind(R.id.sugg_back)
    ImageView suggBack;
    @Bind(R.id.sugg_go)
    TextView suggGo;
    @Bind(R.id.sugg_text)
    TextView suggText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);
        ButterKnife.bind(this);
        //当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的 padding
        StatusBarUtil.setRootViewFitsSystemWindows(this,true);
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

    @OnClick({R.id.sugg_back, R.id.sugg_go})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sugg_back:
                finish();
                break;
            case R.id.sugg_go:
                Toast.makeText(SuggestionActivity.this,"感谢您的建议",Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }
}
