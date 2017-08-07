package com.monsterlin.pigeon.ui.setting;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.monsterlin.pigeon.R;
import com.monsterlin.pigeon.base.BaseActivity;

/**
 * @author : monsterLin
 * @version : 1.0
 * @email : monster941025@gmail.com
 * @github : https://github.com/monsterLin
 * @time : 2017/8/7
 * @desc : 设置界面
 */
public class SettingActivity extends BaseActivity {

    private Toolbar mToolBar ;

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initViews() {
        mToolBar=findView(R.id.common_toolbar);
        initToolBar(mToolBar,"设置",true);
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
