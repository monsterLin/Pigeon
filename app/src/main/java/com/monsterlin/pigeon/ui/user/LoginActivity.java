package com.monsterlin.pigeon.ui.user;

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
import com.monsterlin.pigeon.bean.Family;
import com.monsterlin.pigeon.bean.User;
import com.monsterlin.pigeon.common.AppManager;
import com.monsterlin.pigeon.ui.app.AboutActivity;
import com.monsterlin.pigeon.ui.family.GuideFamilyActivity;
import com.monsterlin.pigeon.utils.SPUtils;
import com.monsterlin.pigeon.utils.ToastUtils;

import java.util.Arrays;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
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

    private BmobUser bmobUser;
    private Bundle bundle;

    private boolean isCreateFamily = false;  //是否创建家庭
    private boolean isJoinFamily = false ; //是否加入家庭

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
        bmobUser = BmobUser.getCurrentUser();
        if (bmobUser != null) {
            //登陆成功后，判断当前用户是否创建过家庭或者加入过家庭
            //TODO 目前判断的是当前用户是否为创建者
            isCreateFamily = SPUtils.getBoolean("isCreateFamily", false);
            isJoinFamily = SPUtils.getBoolean("isJoinFamily",false);

            if (isCreateFamily||isJoinFamily) {
                AppManager.getAppManager().finishActivity();
                nextActivity(MainActivity.class);
            } else {
                AppManager.getAppManager().finishActivity();
                nextActivity(GuideFamilyActivity.class);
            }

        }
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                dialog.showDialog();
                userNameString = mEdtUserName.getText().toString();
                userPassString = mEdtUserPass.getText().toString();

                if (!TextUtils.isEmpty(userNameString) && !TextUtils.isEmpty(userPassString)) {

                    BmobUser.loginByAccount(userNameString, userPassString, new LogInListener<User>() {
                        @Override
                        public void done(User user, BmobException e) {
                            if (user != null) {
                                //TODO 登陆逻辑存在Bug
                                BmobQuery<Family> queryFamily = new BmobQuery<>();
                                queryFamily.addWhereEqualTo("familyCreator", user);
                                queryFamily.addWhereContainedIn("familyList", Arrays.asList(user.getObjectId()));
                                queryFamily.findObjects(new FindListener<Family>() {
                                    @Override
                                    public void done(List<Family> list, BmobException e) {
                                        if (e==null){
                                            if (list.size()!=0) {
                                                dialog.dismissDialog();
                                                AppManager.getAppManager().finishActivity();
                                                SPUtils.putBoolean("isJoinFamily",true);
                                                nextActivity(MainActivity.class);
                                            } else {
                                                dialog.dismissDialog();
                                                AppManager.getAppManager().finishActivity();
                                                nextActivity(GuideFamilyActivity.class);

                                            }
                                        }
                                    }
                                });

                            } else {
                                dialog.dismissDialog();
                                ToastUtils.showToast(LoginActivity.this, e.getMessage());
                            }
                        }
                    });
                } else {
                    dialog.dismissDialog();
                    ToastUtils.showToast(LoginActivity.this, "请正确填写登陆信息");
                }
                break;
            case R.id.login_tv_register:
                bundle = new Bundle();
                bundle.putInt("sign", 0);
                nextActivity(RegisterOrResetActivity.class, bundle);
                break;
            case R.id.login_tv_forgetPass:
                bundle = new Bundle();
                bundle.putInt("sign", 1);
                nextActivity(RegisterOrResetActivity.class, bundle);
                break;
            case R.id.login_iv_about:
                nextActivity(AboutActivity.class);
                break;
        }
    }
}
