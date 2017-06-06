package com.kannan.glazy;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.LinearGradient;
import android.graphics.Path;
import android.graphics.Shader;
import android.util.TypedValue;

public class Utils {

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public static int dpToPx(Context ctx, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, ctx.getResources().getDisplayMetrics());
    }

    public static Path getWavePath(
            float width, float height, float amplitude, float cyclesPerSocond, float phaseShift) {
        if(amplitude > height * 2) {
            amplitude = height / 12;
        }

        int unitPixels = 15;
        float heightDiff = height - amplitude;

        Path path = new Path();
        path.moveTo(0, 0);
        path.lineTo(0, heightDiff);
        float x, y;
        for (int i = 0; i < width + unitPixels; i += unitPixels) {
            x = i;
            y = amplitude * (float) Math.sin(
                    Math.toRadians(2 * Math.PI * cyclesPerSocond * (i + unitPixels)) +
                            Math.toRadians(phaseShift)
            );
            path.lineTo(x, heightDiff + y);
        }
        path.lineTo(width, 0);
        path.close();
        return path;
    }

    public static Path getLinePath(
            float width, float height, float cutHeight, boolean negativeSlope) {

        if (cutHeight >= height) {
            cutHeight = height / 6;
        }


//      _____________________
//      | (0,0)       (1,1) |
//      |                   |
//      | (3,3)       (2,2) |
//      ---------------------


        float[] x = new float[4];
        float[] y = new float[4];

        x[0] = 0;
        y[0] = 0;
        x[1] = width;
        y[1] = 0;
        if (negativeSlope) {
            x[2] = width;
            y[2] = height;
            x[3] = 0;
            y[3] = (height - cutHeight);
        } else {
            x[2] = width;
            y[2] = (height - cutHeight);
            x[3] = 0;
            y[3] = height;
        }

        Path path = new Path();
        path.moveTo(x[0], y[0]);
        path.lineTo(x[1], y[1]);
        path.lineTo(x[2], y[2]);
        path.lineTo(x[3], y[3]);
        path.close();

        return path;
    }

    public static Shader getLinearGradient(float width, float height, int startColor, int endColor) {
        float[] x = new float[2];
        float[] y = new float[2];

        x[0] = y[0] = x[1] = 0;
        y[1] = height;

        return new LinearGradient(x[0], y[0], x[1], y[1], startColor, endColor, Shader.TileMode.CLAMP);
    }
}
