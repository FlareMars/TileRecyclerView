package com.utils.javenruan.tilerecyclerview;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by javenruan on 2016/8/11
 */
public class Utils {

    public static int dpToPx(final Context context, final float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) ((dp * scale) + 0.5f);
    }

    public static int getScreenWidth(final Context context) {
        if (context == null) {
            return 0;
        }
        return getDisplayMetrics(context).widthPixels;
    }

    public static DisplayMetrics getDisplayMetrics(final Context context) {
        final WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        final DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        return metrics;
    }
}
