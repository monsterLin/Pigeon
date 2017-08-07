package com.monsterlin.pigeon.ui.welcome;

import android.view.View;

import com.monsterlin.pigeon.MainActivity;
import com.monsterlin.pigeon.R;
import com.monsterlin.pigeon.base.BaseActivity;
import com.monsterlin.pigeon.bean.User;
import com.monsterlin.pigeon.common.AppManager;
import com.monsterlin.pigeon.constant.AppConfig;
import com.monsterlin.pigeon.constant.FamilyConfig;
import com.monsterlin.pigeon.ui.family.GuideFamilyActivity;
import com.monsterlin.pigeon.ui.user.LoginActivity;
import com.monsterlin.pigeon.utils.SPUtils;

import cn.bmob.v3.BmobUser;

/**
 * @author : monsterLin
 * @version : 1.0
 * @email : monster941025@gmail.com
 * @github : https://github.com/monsterLin
 * @time : 2017/8/7
 * @desc : 启动页面
 */
public class WelcomeActivtiy extends BaseActivity {

    private BmobUser bmobUser;
    private boolean isFirst; //是否第一次进入程序
    private boolean isExist; //是否存在家庭


    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initListener() {


    }

    @Override
    public void initData() {


        new Thread() {
            public void run() {
                try {
                    sleep(1500);
                    isFirst = SPUtils.getBoolean(AppConfig.SPFIST, true);

                    if (isFirst) {
                        AppManager.getAppManager().finishActivity();
                        nextActivity(GuidePageActivity.class); //app引导页

                    } else {
                        bmobUser = BmobUser.getCurrentUser(User.class);
                        if (bmobUser != null) {

                            isExist = SPUtils.getBoolean(FamilyConfig.SPEXIST, false);

                            if (isExist) {
                                AppManager.getAppManager().finishActivity();
                                nextActivity(MainActivity.class); //app主界面
                            } else {
                                AppManager.getAppManager().finishActivity();
                                nextActivity(GuideFamilyActivity.class);  //家庭引导页
                            }

                        } else {
                            AppManager.getAppManager().finishActivity();
                            nextActivity(LoginActivity.class); //app登陆页面
                        }

                    }


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    @Override
    public void processClick(View v) {

    }
}
