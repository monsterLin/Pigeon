package com.monsterlin.pigeon.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.monsterlin.pigeon.R;

/**
 * @author : monsterLin
 * @version : 1.0
 * @email : monster941025@gmail.com
 * @github : https://github.com/monsterLin
 * @time : 2017/7/11
 * @desc : 加载进度框
 */
public class LoadingDialog {

    private AlertDialog alertDialog;
    private Context mContext;
    public LoadingDialog(Context context) {
        this.mContext = context;
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_alert, null);
        alertDialog = new AlertDialog.Builder(context)
                .setView(view)
                .create();
        alertDialog.setCancelable(false); //这句代码设置这个对话框不能被用户按[返回键]而取消掉
    }
    public void showDialog() {
        alertDialog.show();
    }
    public void dismissDialog() {
        alertDialog.dismiss();
    }
}
