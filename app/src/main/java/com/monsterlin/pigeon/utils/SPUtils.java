package com.monsterlin.pigeon.utils;
import android.content.Context;
import android.content.SharedPreferences;

import com.monsterlin.pigeon.application.PigeonApplication;

/**
 * @author : monsterLin
 * @version : 1.0
 * @email : monster941025@gmail.com
 * @github : https://github.com/monsterLin
 * @time : 2017/7/13
 * @desc : SP工具类
 */
public class SPUtils {

    private static final String CONFIG = "CONFIG";

    private static SharedPreferences sp;

    public static void clear() {
        if (sp == null) {
            sp = getContext().getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
        }
        sp.edit().clear().apply();
    }



    public static Context getContext() {
        return PigeonApplication.getContext();
    }


    public static boolean getBoolean(String key,
                                     boolean defValue) {
        if (sp == null) {
            sp = getContext().getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
        }
        return sp.getBoolean(key, defValue);
    }


    public static void putBoolean(String key, boolean value) {
        if (sp == null) {
            sp = getContext().getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
        }
        sp.edit().putBoolean(key, value).commit();
    }


    public static void putString(String key, String value) {
        if (sp == null) {
            sp = getContext().getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
        }
        sp.edit().putString(key, value).commit();
    }


    public static String getString(String key, String defValue) {
        if (sp == null) {
            sp = getContext().getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
        }
        return sp.getString(key, defValue);
    }


    public static void putInt(String key, int value) {
        if (sp == null) {
            sp = getContext().getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
        }
        sp.edit().putInt(key, value).commit();
    }

    public static int getInt(String key, int defValue) {
        if (sp == null) {
            sp = getContext().getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
        }
        return sp.getInt(key, defValue);
    }

    public static void putDouble(String key, double value) {
        if (sp == null) {
            sp = getContext().getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
        }
        sp.edit().putFloat(key, (float) value).commit();
    }

    public static double getDouble(String key, double defValue) {
        if (sp == null) {
            sp = getContext().getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
        }
        return sp.getFloat(key, (float) defValue);
    }

}
