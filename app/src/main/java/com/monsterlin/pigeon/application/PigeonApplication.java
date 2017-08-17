package com.monsterlin.pigeon.application;

import android.content.pm.PackageManager;

import com.monsterlin.pigeon.base.BaseApplication;
import com.monsterlin.pigeon.constant.AliHotFixConfig;
import com.monsterlin.pigeon.constant.BmobConfig;
import com.monsterlin.pigeon.utils.ToastUtils;
import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;

import cn.bmob.v3.Bmob;

/**
 * @author : monsterLin
 * @version : 1.0
 * @email : monster941025@gmail.com
 * @github : https://github.com/monsterLin
 * @time : 2017/7/10
 * @desc : 程序的application
 */
public class PigeonApplication extends BaseApplication {

    @Override
    public void initConfigs() {
        initSophix();
        Bmob.initialize(this, BmobConfig.APPID);
    }

    private void initSophix() {
        String appVersion;

        try {
            appVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            appVersion = "1.0.0";
            e.printStackTrace();
        }

        // initialize最好放在attachBaseContext最前面
        SophixManager.getInstance().setContext(this)
                .setAppVersion(appVersion)
                .setAesKey(null)
                .setSecretMetaData(AliHotFixConfig.AppId,AliHotFixConfig.AppSecret,AliHotFixConfig.RSA)
                .setEnableDebug(true)
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
                        // 补丁加载回调通知
                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                            // 表明补丁加载成功
                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            // 表明新补丁生效需要重启. 开发者可提示用户或者强制重启;
                            // 建议: 用户可以监听进入后台事件, 然后调用killProcessSafely自杀，以此加快应用补丁，详见1.3.2.3
                            SophixManager.getInstance().killProcessSafely();
                        } else {
                            // 其它错误信息, 查看PatchStatus类说明
                            ToastUtils.showToast(getApplicationContext(),"HotFixError："+PatchStatus.INFO_LOAD_NOPATCH);
                        }
                    }
                }).initialize();
        // queryAndLoadNewPatch不可放在attachBaseContext 中，否则无网络权限，建议放在后面任意时刻，如onCreate中
        SophixManager.getInstance().queryAndLoadNewPatch();
    }


}
