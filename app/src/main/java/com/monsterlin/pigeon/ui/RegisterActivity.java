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
 * @desc : 手机号注册界面
 */
public class RegisterActivity extends BaseActivity {

    private Toolbar mToolBar;
    private TextInputLayout mTelNumWrapper;
    private Button mBtnRegister;
    private EditText mEdtNum;

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initViews() {
        mToolBar = findView(R.id.common_toolbar);
        initToolBar(mToolBar, "注册", true);
        mTelNumWrapper = findView(R.id.register_telNumWrapper);
        mTelNumWrapper.setHint("手机号");
        mEdtNum = findView(R.id.register_edt_num);
        mBtnRegister = findView(R.id.register_btn);
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
