package com.kannan.trial.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by kannan on 24/12/16.
 */
public class Converter {

    public static int dpToPx(int dp, Context ctx) {
        DisplayMetrics dMat = ctx.getResources().getDisplayMetrics();
        return Math.round(dp * (dMat.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static int pxToDp(int px, Context ctx) {
        return dpToPx(px, ctx);
    }
}
