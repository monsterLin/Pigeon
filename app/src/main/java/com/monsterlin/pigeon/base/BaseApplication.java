package com.monsterlin.pigeon.base;

import android.app.Application;

/**
 *  @autor : Hensen_
 *  @desc : Application的基类
 *  @url : http://blog.csdn.net/qq_30379689/article/details/58034750
 */

public abstract class BaseApplication extends Application {

    public abstract void initConfigs();

    @Override
    public void onCreate() {
        super.onCreate();
        initConfigs();
    }

}
