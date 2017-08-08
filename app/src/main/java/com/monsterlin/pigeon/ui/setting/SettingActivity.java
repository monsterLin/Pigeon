package com.monsterlin.pigeon.ui.setting;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.monsterlin.pigeon.R;
import com.monsterlin.pigeon.base.BaseActivity;
import com.monsterlin.pigeon.common.AppManager;
import com.monsterlin.pigeon.constant.FamilyConfig;
import com.monsterlin.pigeon.ui.app.AboutActivity;
import com.monsterlin.pigeon.ui.app.AdviceActivity;
import com.monsterlin.pigeon.ui.brower.BrowerActivity;
import com.monsterlin.pigeon.utils.SPUtils;
import com.monsterlin.pigeon.utils.ToastUtils;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;

/**
 * @author : monsterLin
 * @version : 1.0
 * @email : monster941025@gmail.com
 * @github : https://github.com/monsterLin
 * @time : 2017/8/7
 * @desc : 设置界面
 */
public class SettingActivity extends BaseActivity {

    private Toolbar mToolBar;
    private TextView mTvClear, mTvAdvide, mTvLicense, mTvAbout;

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initViews() {
        mToolBar = findView(R.id.common_toolbar);
        initToolBar(mToolBar, "设置", true);
        mTvClear = findView(R.id.setting_tv_clear);
        mTvAdvide = findView(R.id.setting_tv_advice);
        mTvLicense = findView(R.id.setting_tv_license);
        mTvAbout = findView(R.id.setting_tv_about);
    }

    @Override
    public void initListener() {
        setOnClick(mTvClear);
        setOnClick(mTvAdvide);
        setOnClick(mTvLicense);
        setOnClick(mTvAbout);
    }

    @Override
    public void initData() {

    }

    @Override
    public void processClick(View v) {
        switch (v.getId()) {
            case R.id.setting_tv_clear:
                BmobQuery.clearAllCachedResults();  //清除缓存
                ToastUtils.showToast(this, "清除换成成功");
                break;
            case R.id.setting_tv_advice:
                nextActivity(AdviceActivity.class);
                break;
            case R.id.setting_tv_license:
                Bundle b = new Bundle();
                b.putString("url", "https://www.baidu.com");
                nextActivity(BrowerActivity.class, b);
                break;
            case R.id.setting_tv_about:
                nextActivity(AboutActivity.class);
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_setting, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_exit:
                BmobUser.logOut();
                SPUtils.putBoolean(FamilyConfig.SPEXIST, false); //设置保存到本地的sp家庭数据
                AppManager.getAppManager().AppExit(this);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
