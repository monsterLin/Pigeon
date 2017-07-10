package com.monsterlin.pigeon.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.monsterlin.pigeon.R;

/**
 * @author : monsterLin
 * @email : monster941025@gmail.com
 * @github : https://github.com/monsterLin
 * @time : 2017/7/5
 * @desc : Toast弹出层
 * @version: 1.0
 */

public class ToastUtils {

    private static View toastRoot;
    private static TextView tv;
    private static Toast mToast;
    private static int DURATION = Toast.LENGTH_SHORT;

    public static void showToast(Context context, CharSequence text) {
        toastRoot = LayoutInflater.from(context).inflate(R.layout.custom_toast, null);
        mToast = new Toast(context);
        tv = (TextView) toastRoot.findViewById(R.id.tv_toast_tips);
        mToast.setView(toastRoot);
        tv.setText(text);
        mToast.setDuration(DURATION);
        mToast.show();
    }

}
