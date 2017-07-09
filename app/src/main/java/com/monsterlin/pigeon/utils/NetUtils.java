package com.monsterlin.pigeon.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * @author : monsterLin
 * @email : monster941025@gmail.com
 * @github : https://github.com/monsterLin
 * @time : 2017/7/5
 * @desc : 网络检测
 * @version: 1.0
 */

public class NetUtils {

    public static int NO_NETWORK = 0;
    public static int NETWORK_WIFI = 1;
    public static int NETWORK_MOBILE = 2;
    private MaterialDialog mMaterialDialog;

    public static int checkNetwork(Context context) {
        if (context == null) {
            return NETWORK_WIFI;
        }
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifi != null && wifi.isConnected()) {
            return NETWORK_WIFI;
        }
        NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobile != null && mobile.isConnected()) {
            return NETWORK_MOBILE;
        }
        return NO_NETWORK;
    }


    /**
     * 显示提示框
     *
     * @param context
     */

    public void showDialog(final Context context) {
        mMaterialDialog = new MaterialDialog(context)

                .setMessage("please check your network")
                .setPositiveButton("setting", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = null;
                        try {
                            @SuppressWarnings("deprecation")
                            String sdkVersion = android.os.Build.VERSION.SDK;
                            if (Integer.valueOf(sdkVersion) > 10) {
                                intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                            } else {
                                intent = new Intent();
                                ComponentName comp = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
                                intent.setComponent(comp);
                                intent.setAction("android.intent.action.VIEW");
                            }
                            context.startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        mMaterialDialog.dismiss();

                    }
                })
                .setNegativeButton("cancel", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMaterialDialog.dismiss();
                    }
                });
        mMaterialDialog.show();
    }
}
