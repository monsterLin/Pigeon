package com.monsterlin.pigeon.ui.brower;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;

import com.monsterlin.pigeon.R;
import com.monsterlin.pigeon.base.BaseActivity;
import com.monsterlin.pigeon.utils.ToastUtils;
import com.monsterlin.pigeon.widget.ProgressWebView;

/**
 * @author : monsterLin
 * @version : 1.0
 * @email : monster941025@gmail.com
 * @github : https://github.com/monsterLin
 * @time : 2017/7/23
 * @desc : 浏览器
 */
public class BrowerActivity extends BaseActivity {

    private Toolbar mToolBar;
    private String urlString;

    private ProgressWebView mProWebView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_web_brower;
    }

    @Override
    public void initViews() {
        mToolBar = findView(R.id.common_toolbar);
        initToolBar(mToolBar, "飞鸽", true);
        mProWebView = findView(R.id.brower_pwv);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        urlString = getIntent().getStringExtra("url");
        if (!TextUtils.isEmpty(urlString)){
            mProWebView.getSettings().setJavaScriptEnabled(true);
            mProWebView.loadUrl(urlString);
        }else {
            ToastUtils.showToast(this,"无数据显示");
        }

    }

    @Override
    public void processClick(View v) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK)
        {
            if(mProWebView.canGoBack())
            {
                mProWebView.goBack();//返回上一页面
                return true;
            }
            else
            {
                System.exit(0);//退出程序
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
