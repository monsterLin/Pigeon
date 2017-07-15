package com.monsterlin.pigeon.ui;

import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.monsterlin.pigeon.R;
import com.monsterlin.pigeon.base.BaseActivity;
import com.monsterlin.pigeon.bean.User;
import com.monsterlin.pigeon.common.AppManager;
import com.monsterlin.pigeon.constant.BmobConfig;
import com.monsterlin.pigeon.utils.SPUtils;
import com.monsterlin.pigeon.utils.ToastUtils;
import com.monsterlin.pigeon.widget.CountDownButtonHelper;
import com.monsterlin.pigeon.widget.LoadingDialog;
import com.orhanobut.logger.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * @author : monsterLin
 * @version : 1.0
 * @email : monster941025@gmail.com
 * @github : https://github.com/monsterLin
 * @time : 2017/7/10
 * @desc : 注册和重置密码界面
 */
public class RegisterOrResetActivity extends BaseActivity {

    private Toolbar mToolBar;
    private TextInputLayout mTelNumWrapper, mPasswordWrapper, mRePasswordWrapper, mSmsWrapper;
    private Button mBtn, mBtnGetSmsCode;
    private EditText mEdtNum, mEdtSmsCode, mEdtPassword, mEdtRePassword;

    private boolean isTel, isTruePassword;
    private String telNumString, smsCodeString, passwordString, rePassWordString;

    private LoadingDialog dialog;

    private int sign; //标志位，来确定是用户注册还是忘记密码  0 : 注册  ； 1 ：忘记密码

    @Override
    public int getLayoutId() {
        return R.layout.activity_register_or_reset;
    }

    @Override
    public void initViews() {
        sign = getIntent().getIntExtra("sign", 0);

        mToolBar = findView(R.id.common_toolbar);

        mTelNumWrapper = findView(R.id.telNumWrapper);
        mTelNumWrapper.setHint("手机号");

        mEdtNum = findView(R.id.edt_num);
        mEdtSmsCode = findView(R.id.edt_smsCode);
        mEdtPassword = findView(R.id.edt_password);
        mEdtRePassword = findView(R.id.edt_rePassword);

        mBtn = findView(R.id.btn);
        mBtnGetSmsCode = findView(R.id.btn_getSmsCode);

        mPasswordWrapper = findView(R.id.passwordWrapper);
        mPasswordWrapper.setHint("密码");
        mRePasswordWrapper = findView(R.id.rePasswordWrapper);
        mRePasswordWrapper.setHint("确认密码");

        mSmsWrapper = findView(R.id.smsWrapper);
        mSmsWrapper.setHint("验证码");

        dialog = new LoadingDialog(this);

        if (sign == 0) {
            initToolBar(mToolBar, "注册", true);
            mBtn.setText("注册");
        } else if (sign == 1) {
            initToolBar(mToolBar, "重置密码", true);
            mBtn.setText("重置密码");
        }
    }

    @Override
    public void initListener() {
        setOnClick(mBtnGetSmsCode);
        setOnClick(mBtn);
    }

    @Override
    public void initData() {

    }

    @Override
    public void processClick(View v) {
        switch (v.getId()) {
            case R.id.btn_getSmsCode:
                telNumString = mEdtNum.getText().toString();
                isTel = verifyTel(telNumString);

                if (!TextUtils.isEmpty(telNumString) && isTel) {
                    loadSMS();
                    BmobSMS.requestSMSCode(telNumString, BmobConfig.SMSTEMPLATE, new QueryListener<Integer>() {
                        @Override
                        public void done(Integer smsCode, BmobException e) {
                            if (e == null) {
                                Logger.i("本次验证码为：" + smsCode);
                                ToastUtils.showToast(RegisterOrResetActivity.this, "验证码发送完毕");
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

            case R.id.btn:
                telNumString = mEdtNum.getText().toString();
                smsCodeString = mEdtSmsCode.getText().toString();
                passwordString = mEdtPassword.getText().toString();
                rePassWordString = mEdtRePassword.getText().toString();

                isTel = verifyTel(telNumString); //判断是否为正确的手机号
                isTruePassword = verifyTwoPassword(passwordString, rePassWordString);//进行两次输入的密码校验

                if (sign == 0) {
                    registerUser();
                } else if (sign == 1) {
                    resetPassword();
                }


                break;
        }
    }

    /**
     * 重置密码
     */
    private void resetPassword() {
        if (isTel && isTruePassword) {
            dialog.showDialog();
            BmobUser.resetPasswordBySMSCode(smsCodeString, rePassWordString, new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        dialog.dismissDialog();
                        ToastUtils.showToast(RegisterOrResetActivity.this, "重置密码成功");
                        AppManager.getAppManager().finishActivity();
                    } else {
                        dialog.dismissDialog();
                        ToastUtils.showToast(RegisterOrResetActivity.this, e.getMessage());
                    }
                }
            });

        } else {
            dialog.dismissDialog();
            ToastUtils.showToast(RegisterOrResetActivity.this, "请检查输入信息");
        }
    }

    /**
     * 用户注册
     */
    private void registerUser() {
        /*
         * 在这里要进行的操作包括：
         * 1. 表单验证
         * 2. 验证码的验证
         * 3. 注册信息
         * 4. 注册完成后的登陆
         * 5. 登陆成功的跳转
         */
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

                        SPUtils.putBoolean("isCreateFamily",false);
                        AppManager.getAppManager().finishActivity(); //结束当前Activity
                        nextActivity(GuideFamilyActivity.class);
                        AppManager.getAppManager().finishActivity(LoginActivity.class);  //结束指定的Activity

                    } else {
                        dialog.dismissDialog();
                        ToastUtils.showToast(RegisterOrResetActivity.this, e.getMessage());
                    }
                }
            });
        } else {
            dialog.dismissDialog();
            ToastUtils.showToast(RegisterOrResetActivity.this, "请检查输入信息");
        }
    }

    /**
     * 检验两次密码是否相同
     *
     * @param passwordString   第一次输入的密码
     * @param rePassWordString 第二次输入的密码
     * @return boolean
     */
    private boolean verifyTwoPassword(String passwordString, String rePassWordString) {
        boolean isPass1 = verifyPassword(passwordString);
        boolean isPass2 = verifyPassword(rePassWordString);

        return isPass1 && isPass2 && passwordString.equals(rePassWordString);
    }

    /**
     * 检验密码的合法性
     *
     * @param passwordString 密码
     * @return boolean
     */
    private boolean verifyPassword(String passwordString) {
        return !TextUtils.isEmpty(passwordString) && passwordString.length() >= 6;
    }

    /**
     * 使用正则表达式验证手机号
     * 移动：139   138   137   136   135   134   147   150   151   152   157   158    159   178  182   183   184   187   188  
     * 联通：130   131   132   155   156   185   186   145   176  
     * 电信：133   153   177   173   180   181   189 
     * 虚拟运营商：170  171
     * @param telNumString 手机号
     * @return boolean
     */
    private boolean verifyTel(String telNumString) {
        if (!TextUtils.isEmpty(telNumString)) {
            String regExp = "^((13[0-9])|(15[^4])|(18[0-9])|(17[0-8])|(147,145))\\d{8}$";
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
