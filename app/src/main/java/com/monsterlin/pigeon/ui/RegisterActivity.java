package com.monsterlin.pigeon.ui;

import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.monsterlin.pigeon.MainActivity;
import com.monsterlin.pigeon.R;
import com.monsterlin.pigeon.base.BaseActivity;
import com.monsterlin.pigeon.bean.User;
import com.monsterlin.pigeon.common.AppManager;
import com.monsterlin.pigeon.constant.BmobConfig;
import com.monsterlin.pigeon.utils.ToastUtils;
import com.monsterlin.pigeon.widget.CountDownButtonHelper;
import com.monsterlin.pigeon.widget.LoadingDialog;
import com.orhanobut.logger.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

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
    private TextInputLayout mTelNumWrapper, mPasswordWrapper, mRePasswordWrapper, mSmsWrapper;
    private Button mBtnRegister, mBtnGetSmsCode;
    private EditText mEdtNum, mEdtSmsCode, mEdtPassword, mEdtRePassword;

    private boolean isTel, isTruePassword;
    private String telNumString, smsCodeString, passwordString, rePassWordString;

    private LoadingDialog dialog;

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
        mEdtSmsCode = findView(R.id.register_edt_smsCode);
        mEdtPassword = findView(R.id.register_edt_password);
        mEdtRePassword = findView(R.id.register_edt_rePassword);

        mBtnRegister = findView(R.id.register_btn);
        mBtnGetSmsCode = findView(R.id.register_btn_getSmsCode);

        mPasswordWrapper = findView(R.id.register_passwordWrapper);
        mPasswordWrapper.setHint("密码");
        mRePasswordWrapper = findView(R.id.register_rePasswordWrapper);
        mRePasswordWrapper.setHint("确认密码");

        mSmsWrapper = findView(R.id.register_smsWrapper);
        mSmsWrapper.setHint("验证码");

        dialog = new LoadingDialog(this);
    }

    @Override
    public void initListener() {
        setOnClick(mBtnGetSmsCode);
        setOnClick(mBtnRegister);
    }

    @Override
    public void initData() {

    }

    @Override
    public void processClick(View v) {
        switch (v.getId()) {
            case R.id.register_btn_getSmsCode:
                telNumString = mEdtNum.getText().toString();
                isTel = vertifyTel(telNumString);

                if (!TextUtils.isEmpty(telNumString) && isTel) {
                    loadSMS();
                    BmobSMS.requestSMSCode(telNumString, BmobConfig.SMSTEMPLATE, new QueryListener<Integer>() {
                        @Override
                        public void done(Integer smsCode, BmobException e) {
                            if (e == null) {
                                Logger.i("本次验证码为：" + smsCode);
                                ToastUtils.showToast(RegisterActivity.this, "验证码发送完毕");
                                mEdtSmsCode.setText(smsCode);
                            } else {
                                Logger.e(e.getMessage());
                            }
                        }
                    });
                } else {
                    ToastUtils.showToast(this, "请正确输入手机号");
                }

                break;

            case R.id.register_btn:
                /**
                 * 在这里要进行的操作包括：
                 * 1. 表单验证
                 * 2. 验证码的验证
                 * 3. 注册信息
                 * 4. 注册完成后的登陆
                 * 5. 登陆成功的跳转
                 */

                telNumString = mEdtNum.getText().toString();
                smsCodeString = mEdtSmsCode.getText().toString();
                passwordString = mEdtPassword.getText().toString();
                rePassWordString = mEdtRePassword.getText().toString();

                isTel = vertifyTel(telNumString); //判断是否为正确的手机号
                isTruePassword = vertifyTwoPassword(passwordString, rePassWordString);//进行两次输入的密码校验

                if (isTel && isTruePassword) {
                    dialog.showDialog();
                    User user = new User();
                    user.setPassword(rePassWordString);
                    user.setMobilePhoneNumber(telNumString);
                    user.signOrLogin(smsCodeString, new SaveListener<User>() {
                        @Override
                        public void done(User user, BmobException e) {
                            if (user != null) {
                                //注册并且登陆成功
                                dialog.dismissDialog();
                                //TODO 跳到选择个人类型的页面
                                nextActivity(MainActivity.class);
                                AppManager.getAppManager().finishActivity(LoginActivity.class);  //结束指定的Activity
                                AppManager.getAppManager().finishActivity(); //结束当前Activity

                            } else {
                                dialog.dismissDialog();
                                ToastUtils.showToast(RegisterActivity.this, e.getMessage());
                            }
                        }
                    });
                } else {
                    dialog.dismissDialog();
                    ToastUtils.showToast(RegisterActivity.this, "请检查输入信息");
                }

                break;
        }
    }

    /**
     * 检验两次密码是否相同
     *
     * @param passwordString   第一次输入的密码
     * @param rePassWordString 第二次输入的密码
     * @return
     */
    private boolean vertifyTwoPassword(String passwordString, String rePassWordString) {
        boolean isPass1 = vertifyPassword(passwordString);
        boolean isPass2 = vertifyPassword(rePassWordString);

        if (isPass1 && isPass2) {
            if (passwordString.equals(rePassWordString)) {
                return true;
            }
            return false;
        }
        return false;
    }

    /**
     * 检验密码的合法性
     *
     * @param passwordString 密码
     * @return
     */
    private boolean vertifyPassword(String passwordString) {
        if (!TextUtils.isEmpty(passwordString) && passwordString.length() >= 6) {
            return true;
        }
        return false;
    }

    /**
     * 使用正则表达式验证手机号
     *
     * @param telNumString 手机号
     * @return
     */
    private boolean vertifyTel(String telNumString) {
        if (!TextUtils.isEmpty(telNumString)) {
            String regExp = "^[1]([3][0-9]{1}|59|58|88|89)[0-9]{8}$"; //手机号的正则表达式
            Pattern p = Pattern.compile(regExp);
            Matcher m = p.matcher(telNumString);
            return m.find();
        }
        return false;

    }

    /**
     * 控制获取验证码的按钮
     */
    private void loadSMS() {
        CountDownButtonHelper helper = new CountDownButtonHelper(mBtnGetSmsCode, "倒计时", 60, 1);
        helper.setOnFinishListener(new CountDownButtonHelper.OnFinishListener() {
            @Override
            public void finish() {
                mBtnGetSmsCode.setText("再次获取");
            }
        });
        helper.start();
    }
}
