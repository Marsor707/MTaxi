package com.github.marsor.mtaxi.common.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */

public class ToastUtil {

    public static void show(Context context, String string) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
    }
}
