package com.kannan.glazy;

import android.content.Context;
import android.graphics.Path;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import com.kannan.glazy.views.GlazyImageView.ImageCutType;

public class Utils {

    public static int getPixelForDp(Context context, int displayPixels) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, displayPixels, context.getResources().getDisplayMetrics());
    }

    public static int dpToPx(int dp, Context ctx) {
        DisplayMetrics dMat = ctx.getResources().getDisplayMetrics();
        return Math.round(dp * (dMat.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    static Path getWavePath(float width, float height, float amplitude, float shift, float divide) {
        Path path = new Path();
        float quadrant = height - amplitude;
        float x, y;
        path.moveTo(0, 0);
        path.lineTo(0, quadrant);
        for (int i = 0; i < width + 10; i = i + 10) {
            x = (float) i;
            y = quadrant + amplitude * (float) Math.sin(((i + 10) * Math.PI / 180) / divide + shift);
            path.lineTo(x, y);
        }
        path.lineTo(width, 0);
        path.close();
        return path;
    }

    public static Path getPath(
            float width, float height, ImageCutType cutType, int angle, float factor) {
        float[] x = new float[4];
        float[] y = new float[4];

//      _____________________
//      | (0,0)       (1,1) |
//      |                   |
//      | (3,3)       (2,2) |
//      ---------------------

        switch (cutType) {
            case LINE :
                x[0] = 0;
                y[0] = 0;
                x[1] = width;
                y[1] = 0;
                float heightDiff = Math.abs(
                        ((float) Math.tan(Math.toRadians(angle))) * width
                );
                if (heightDiff >= height) {
                    heightDiff = height / 5;
                }
                if (angle < 90) {
                    x[2] = width;
                    y[2] = (height - heightDiff) * factor;
                    x[3] = 0;
                    y[3] = height * factor;
                } else {
                    x[2] = width;
                    y[2] = height * factor;
                    x[3] = 0;
                    y[3] = (height - heightDiff) * factor;
                }
                break;
            case ARC :
                break;
            case WAVE :
                break;
        }
        Path path = new Path();
        path.moveTo(x[0], y[0]);
        path.lineTo(x[1], y[1]);
        path.lineTo(x[2], y[2]);
        path.lineTo(x[3], y[3]);
        path.close();

        return path;
    }
}
