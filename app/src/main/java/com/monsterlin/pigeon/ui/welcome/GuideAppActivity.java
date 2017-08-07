package com.monsterlin.pigeon.ui.welcome;

import android.view.View;
import android.widget.TextView;

import com.monsterlin.pigeon.R;
import com.monsterlin.pigeon.base.BaseActivity;
import com.monsterlin.pigeon.common.AppManager;
import com.monsterlin.pigeon.constant.AppConfig;
import com.monsterlin.pigeon.ui.user.LoginActivity;
import com.monsterlin.pigeon.utils.SPUtils;

/**
 * @author : monsterLin
 * @version : 1.0
 * @email : monster941025@gmail.com
 * @github : https://github.com/monsterLin
 * @time : 2017/8/7
 * @desc : 引导页
 */
public class GuideAppActivity extends BaseActivity {

    private TextView mTvGo ;

    @Override
    public int getLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    public void initViews() {
        mTvGo=findView(R.id.guide_tv_go);
    }

    @Override
    public void initListener() {
        setOnClick(mTvGo);
    }

    @Override
    public void initData() {

    }

    @Override
    public void processClick(View v) {
        switch (v.getId()){
            case R.id.guide_tv_go:
                AppManager.getAppManager().finishActivity();
                SPUtils.putBoolean(AppConfig.SPFIST, false);
                nextActivity(LoginActivity.class);
                break;
        }
    }
}
