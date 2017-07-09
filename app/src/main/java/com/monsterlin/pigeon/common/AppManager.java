package com.monsterlin.pigeon.common;

import android.app.Activity;
import android.content.Context;

import java.util.Stack;

/**
 * @author : danry
 * @email : cdanry@163.com
 * @github : https://github.com/Danry-sky
 * @time : 2017/7/9
 * @desc : crashHandler设置为单例模式
 * @version : 1.0
 */

public class AppManager {

    private Stack<Activity> activityStack = new Stack<>();

    public static AppManager appManager = null;

    private AppManager() {

    }

    public static AppManager getAppManager() {
        if (appManager == null) {
            appManager = new AppManager();
        }
        return appManager;
    }

    public void addActivity(Activity activity) {
        activityStack.add(activity);
    }

    public void removeActivity(Activity activity) {
        for (int i = activityStack.size() - 1; i >= 0; i--) {
            Activity activity1 = activityStack.get(i);
            if (activity1.getClass().equals(activity.getClass())) {
                activity1.finish();
                activityStack.remove(activity1);
                break;
            }
        }
    }

    public void removeCurrent() {
        Activity lastElement = activityStack.lastElement();
        lastElement.finish();
        activityStack.remove(lastElement);
    }

    public void removeAll() {
        for (int i = activityStack.size() - 1; i >= 0; i--) {
            Activity activity1 = activityStack.get(i);
            activity1.finish();
            activityStack.remove(activity1);
        }
    }

    public int getSize() {
        return activityStack.size();
    }

}
