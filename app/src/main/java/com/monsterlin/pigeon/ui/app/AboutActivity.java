package com.monsterlin.pigeon.ui.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.monsterlin.pigeon.R;
import com.monsterlin.pigeon.base.BaseActivity;
import com.monsterlin.pigeon.ui.brower.BrowerActivity;
import com.monsterlin.pigeon.utils.ToastUtils;

/**
 * @author : monsterLin
 * @version : 1.0
 * @email : monster941025@gmail.com
 * @github : https://github.com/monsterLin
 * @time : 2017/8/5
 * @desc : 关于手机
 */
public class AboutActivity extends BaseActivity {

    private Toolbar mToolBar;
    private TextView mTvVersion, mTvRecommend, mTvWebsite, mTvUpgrade, mTvCheck ,mTvThanks;

    private String recommendString ;

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
        mTvWebsite = findView(R.id.about_tv_website);
        mTvUpgrade = findView(R.id.about_tv_upgrade);
        mTvCheck = findView(R.id.about_tv_check);
        mTvThanks=findView(R.id.about_tv_thanks);

    }

    @Override
    public void initListener() {
        setOnClick(mTvRecommend);
        setOnClick(mTvWebsite);
        setOnClick(mTvUpgrade);
        setOnClick(mTvThanks);
        setOnClick(mTvCheck);
    }

    @Override
    public void initData() {
        mTvVersion.setText(getAppInfo());
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()) {
            case R.id.about_tv_recommend:
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, recommendString+"11");
                startActivity(Intent.createChooser(shareIntent, "推荐"));
                break;
            case R.id.about_tv_website:
                Bundle b = new Bundle();
                b.putString("url","http://pigeon.monsterlin.com/");
                nextActivity(BrowerActivity.class,b);
                break;
            case R.id.about_tv_upgrade:
                nextActivity(UpgradeExplainActivity.class);
                break;
            case R.id.about_tv_thanks:
                nextActivity(ThanksActivity.class);
                break;
            case R.id.about_tv_check:
                ToastUtils.showToast(this, "已是最新版");
                break;
        }
    }



    /**
     * 得到当前的版本号
     * @return 版本号
     */
    public String getAppInfo() {
        try {
            String pkName = this.getPackageName();
            String versionName = this.getPackageManager().getPackageInfo(
                    pkName, 0).versionName;
            return versionName;
        } catch (Exception e) {
        }
        return null;
    }
}
