package com.monsterlin.pigeon.ui;

import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.monsterlin.pigeon.R;
import com.monsterlin.pigeon.base.BaseActivity;
import com.monsterlin.pigeon.constant.BmobConfig;
import com.monsterlin.pigeon.utils.ToastUtils;
import com.orhanobut.logger.Logger;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

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
        setOnClick(mBtnRegister);
    }

    @Override
    public void initData() {

    }

    @Override
    public void processClick(View v) {
        switch (v.getId()) {
            case R.id.register_btn:
                String telNumString = mEdtNum.getText().toString();
                if (!TextUtils.isEmpty(telNumString)) {
                    BmobSMS.requestSMSCode(telNumString, BmobConfig.SMSTEMPLATE, new QueryListener<Integer>() {
                        @Override
                        public void done(Integer smsCode, BmobException e) {
                            if (e == null) {
                                Logger.i("本次验证码为："+smsCode);
                            } else {
                                Logger.e(e.getMessage());
                            }
                        }
                    });
                } else {
                    ToastUtils.showToast(this, "请正确输入手机号");
                }
                break;
        }
    }
}
