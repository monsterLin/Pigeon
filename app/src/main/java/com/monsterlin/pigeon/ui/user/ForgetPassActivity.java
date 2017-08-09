package com.monsterlin.pigeon.ui;

import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.monsterlin.pigeon.R;
import com.monsterlin.pigeon.base.BaseActivity;

/**
 * @author : monsterLin
 * @version : 1.0
 * @email : monster941025@gmail.com
 * @github : https://github.com/monsterLin
 * @time : 2017/7/10
 * @desc : 找回密码
 */
public class ForgetPassActivity extends BaseActivity {

    private Toolbar mToolBar;
    private EditText mEdtTelNum;
    private TextInputLayout mTelNumWrapper;
    private Button mBtnForgetPass;

    @Override
    public int getLayoutId() {
        return R.layout.activity_forgetpass;
    }

    @Override
    public void initViews() {
        mToolBar = findView(R.id.common_toolbar);
        mEdtTelNum = findView(R.id.forgetPass_edt_num);
        mTelNumWrapper = findView(R.id.forgetPass_telNumWrapper);
        mTelNumWrapper.setHint("手机号");
        mBtnForgetPass = findView(R.id.forgetPass_btn);
        initToolBar(mToolBar,"找回密码",true);

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
