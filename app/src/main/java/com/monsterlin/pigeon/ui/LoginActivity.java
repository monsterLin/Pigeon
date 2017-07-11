package com.monsterlin.pigeon.ui;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.monsterlin.pigeon.MainActivity;
import com.monsterlin.pigeon.R;
import com.monsterlin.pigeon.base.BaseActivity;
import com.monsterlin.pigeon.bean.User;
import com.monsterlin.pigeon.common.AppManager;
import com.monsterlin.pigeon.utils.ToastUtils;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

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
    private Button mBtnLogin;
    private TextView mTvRegister, mTvForgetPass;
    private ImageView mIvAbout;

    private String userNameString, userPassString;

    private BmobUser bmobUser ;
    private Bundle bundle ;

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

        mUserNameWrapper.setHint("飞鸽号／手机号");
        mUserPassWrapper.setHint("密码");

        mBtnLogin = findView(R.id.login_btn);
        mTvRegister = findView(R.id.login_tv_register);
        mTvForgetPass = findView(R.id.login_tv_forgetPass);

        mIvAbout = findView(R.id.login_iv_about);

    }

    @Override
    public void initListener() {
        setOnClick(mBtnLogin);
        setOnClick(mTvRegister);
        setOnClick(mTvForgetPass);
        setOnClick(mIvAbout);
    }

    @Override
    public void initData() {
        bmobUser=BmobUser.getCurrentUser();
        if (bmobUser!=null){
            AppManager.getAppManager().finishActivity();
            //TODO 判断是否含有家庭，如果有家庭则跳转到主界面
            nextActivity(GuideFamilyActivity.class);
        }
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                dialog.showDialog();
                userNameString = mEdtUserName.getText().toString();
                userPassString = mEdtUserPass.getText().toString();

                if (!TextUtils.isEmpty(userNameString)&&!TextUtils.isEmpty(userPassString)){
                    BmobUser.loginByAccount(userNameString, userPassString, new LogInListener<User>() {
                        @Override
                        public void done(User user, BmobException e) {
                            if (user!=null){
                                //TODO 判断是否含有家庭，如果有家庭则跳转到主界面
                                dialog.dismissDialog();
                                nextActivity(GuideFamilyActivity.class);
                                //nextActivity(MainActivity.class);
                                AppManager.getAppManager().finishActivity();
                            }else {
                                dialog.dismissDialog();
                                ToastUtils.showToast(LoginActivity.this,e.getMessage());
                            }
                        }
                    });
                }else {
                    dialog.dismissDialog();
                    ToastUtils.showToast(LoginActivity.this,"请正确填写登陆信息");
                }
                break;
            case R.id.login_tv_register:
                bundle = new Bundle();
                bundle.putInt("sign",0);
                nextActivity(RegisterOrResetActivity.class,bundle);
                break;
            case R.id.login_tv_forgetPass:
                bundle = new Bundle();
                bundle.putInt("sign",1);
                nextActivity(RegisterOrResetActivity.class,bundle);
                break;
            case R.id.login_iv_about:
                nextActivity(AboutActivity.class);
                break;
        }
    }
}
