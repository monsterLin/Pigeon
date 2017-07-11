package com.monsterlin.pigeon.ui;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.monsterlin.pigeon.R;
import com.monsterlin.pigeon.base.BaseActivity;

/**
 * @author : danry
 * @version : 1.0
 * @email : cdanry@163.com
 * @github : https://github.com/Danry-sky
 * @time : 2017/7/10
 * @desc : 升级说明
 */
public class UpgradeExplainActivity extends BaseActivity {

    private Toolbar mToolBar;

    @Override
    public int getLayoutId() {
        return R.layout.activity_upgrade_explain;
    }

    @Override
    public void initViews() {
        mToolBar = findView(R.id.common_toolbar);
        initToolBar(mToolBar, "升级说明", true);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void processClick(View v) {

    }
}
