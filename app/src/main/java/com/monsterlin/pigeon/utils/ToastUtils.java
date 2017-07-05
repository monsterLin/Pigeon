package com.monsterlin.pigeon.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * @author : monsterLin
 * @email ： monster941025@gmail.com
 * @time : 2017/7/5
 * @desc : Toast弹出层
 * @version: 1.0
 */

public class ToastUtils {

    private static Toast mToast;
    private static int DURATION = Toast.LENGTH_SHORT;

    public static void showToast(Context context, CharSequence text) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, DURATION);
        } else {
            mToast.setText(text);
            mToast.setDuration(DURATION);
        }
        mToast.show();
    }

}
