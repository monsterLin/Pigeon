package com.monsterlin.pigeon.common;

import android.content.Context;
import android.os.Build;
import android.os.Looper;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;


/**
 * @author : danry
 * @version : 1.0
 * @email : cdanry@163.com
 * @github : https://github.com/Danry-sky
 * @time : 2017/7/9
 * @desc : crashHandler设置为单例模式
 */


public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static CrashHandler crashHandler = null;

    private CrashHandler() {

    }

    //TODO 这个地方会产生内存泄漏
    public static CrashHandler getInstance() {

        if (crashHandler == null) {
            crashHandler = new CrashHandler();
        }

        return crashHandler;

    }

    private Context mContext;

    private Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler;

    public void init(Context context) {
        //将CrashHandler作为系统的默认异常处理器
        this.mContext = context;
        defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 把信息提示汉化，记录日志信息，反馈给后台
     *
     * @param t 进程
     * @param e 异常
     */

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        if (isHandler(e)) {
            handlerException(t, e);
        } else {
            //系统默认处理
            defaultUncaughtExceptionHandler.uncaughtException(t, null);
        }

    }


    /**
     * 判断是否需要自己处理
     *
     * @param e 异常
     * @return bollean
     */
    private boolean isHandler(Throwable e) {
        return e != null;
    }

    /**
     * 自定义异常处理
     *
     * @param t 进程
     * @param e 异常
     */
    private void handlerException(Thread t, Throwable e) {
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, "抱歉，系统出现未知异常，即将退出...",
                        Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }.start();

        collectionException(e);
        try {
            Thread.sleep(2000);
            AppManager.getAppManager().finishAllActivity();
            Process.killProcess(Process.myPid());
            System.exit(0);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * 收集奔溃异常信息
     *
     * @param e 异常
     */
    private void collectionException(Throwable e) {
        final String deviceInfo = Build.DEVICE + Build.VERSION.SDK_INT + Build.PRODUCT;
        final String errorInfo = e.getMessage();
        new Thread() {
            @Override
            public void run() {
                Log.e("系统异常信息：", "deviceInfo------" + deviceInfo + ":errorInfo" + errorInfo);
            }
        }.start();

    }

}
