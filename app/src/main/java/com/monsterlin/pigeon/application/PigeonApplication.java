package com.monsterlin.pigeon.application;

import com.monsterlin.pigeon.base.BaseApplication;
import com.monsterlin.pigeon.constant.BmobConfig;

import cn.bmob.v3.Bmob;

/**
 * @author : monsterLin
 * @version : 1.0
 * @email : monster941025@gmail.com
 * @github : https://github.com/monsterLin
 * @time : 2017/7/10
 * @desc : 程序的application
 */
public class PigeonApplication extends BaseApplication{

    @Override
    public void initConfigs() {
        Bmob.initialize(this, BmobConfig.APPID);
    }
}
