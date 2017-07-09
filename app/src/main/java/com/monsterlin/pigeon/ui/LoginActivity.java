package com.monsterlin.pigeon.ui;

import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.EditText;

import com.monsterlin.pigeon.R;
import com.monsterlin.pigeon.base.BaseActivity;

/**
 * @author : monsterLin
 * @version : 1.0
 * @email : monster941025@gmail.com
 * @github : https://github.com/monsterLin
 * @time : 2017/7/9
 * @desc : 登陆界面
 */
public class LoginActivity extends BaseActivity {

    private TextInputLayout mUserNameWrapper, mUserPassWrapper;
    private EditText mEdtUserName, mEdtUserPass;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initViews() {
        mUserNameWrapper = findView(R.id.login_userNameWrapper);
        mUserPassWrapper = findView(R.id.login_userPassWrapper);

        mEdtUserName = findView(R.id.login_edt_userName);
        mEdtUserPass = findView(R.id.login_edt_userPass);

        mUserNameWrapper.setHint("飞鸽号");
        mUserPassWrapper.setHint("密码");

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
