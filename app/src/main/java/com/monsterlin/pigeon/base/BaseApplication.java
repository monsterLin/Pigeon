package com.monsterlin.pigeon.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.monsterlin.pigeon.common.CrashHandler;

/**
 * @autor : Hensen_
 * @desc : Application的基类
 * @url : http://blog.csdn.net/qq_30379689/article/details/58034750
 */

public abstract class BaseApplication extends Application {

    public abstract void initConfigs();

    public static Context context = null;

    public static Handler handler = null;

    public static Thread mainThread = null;

    public static int mainThreadId = 0;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();

        handler = new Handler();

        mainThread = Thread.currentThread();

        //自定义异常捕获
        CrashHandler.getInstance().init(this);

        initConfigs();
    }

}
