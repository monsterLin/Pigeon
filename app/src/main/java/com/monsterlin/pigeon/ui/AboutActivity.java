package com.monsterlin.pigeon.ui;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.monsterlin.pigeon.R;
import com.monsterlin.pigeon.base.BaseActivity;
import com.monsterlin.pigeon.utils.ToastUtils;

/**
 * @author : danry
 * @version : 1.0
 * @email : cdanry@163.com
 * @github : https://github.com/Danry-sky
 * @time : 2017/7/10
 * @desc : 关于程序
 */
public class AboutActivity extends BaseActivity {

    private Toolbar mToolBar;
    private TextView mTvVersion, mTvRecommend, mTvWebsite, mTvUpgrade, mTvCheck;

    @Override
    public int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    public void initViews() {
        mToolBar = findView(R.id.common_toolbar);
        initToolBar(mToolBar, "关于飞鸽", true);
        mTvVersion = findView(R.id.about_tv_version);
        mTvRecommend = findView(R.id.about_tv_recommend);
        mTvVersion = findView(R.id.about_tv_version);
        mTvUpgrade = findView(R.id.about_tv_upgrade);
        mTvCheck = findView(R.id.about_tv_check);

    }

    @Override
    public void initListener() {
        setOnClick(mTvUpgrade);
        setOnClick(mTvCheck);
    }

    @Override
    public void initData() {

    }

    @Override
    public void processClick(View v) {
        switch (v.getId()) {
            case R.id.about_tv_recommend:
                break;
            case R.id.about_tv_website:
                break;
            case R.id.about_tv_upgrade:
                nextActivity(UpgradeExplainActivity.class);
                break;
            case R.id.about_tv_check:
                ToastUtils.showToast(this, "已是最新版");
                break;
        }
    }
}
