package com.kannan.glazy;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.LinearGradient;
import android.graphics.Path;
import android.graphics.Shader;
import android.util.TypedValue;

public class Utils {

//    public static int[] getScreenSize(Context context) {
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        int height = displayMetrics.heightPixels;
//        int width = displayMetrics.widthPixels;
//    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public static int dpToPx(Context ctx, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, ctx.getResources().getDisplayMetrics());
    }

//    public static Path getWavePath1(float width, float height, float amplitude, float shift, float divide) {
//        Path path = new Path();
//        float quadrant = height - amplitude;
//        float x, y;
//        path.moveTo(0, 0);
//        path.lineTo(0, quadrant);
//        for (int i = 0; i < width + 10; i = i + 10) {
//            x = (float) i;
//            y = quadrant + amplitude * (float) Math.sin(((i + 10) * Math.PI / 180) / divide + shift);
//            path.lineTo(x, y);
//        }
//        path.lineTo(width, 0);
//        path.close();
//        return path;
//    }

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

//    private static Path getArcPath(
//            float width, float height, float amplitude, float phaseShift)

//    public static Path getPath(
//            float width, float height, ImageCutType cutType,
//            float angle, float cutHeight, float phaseShift,
//            float factor) {
//        Path path = new Path();
//        switch (cutType) {
//            case LINE:
//                path = getLinePath(width, height, angle, factor);
//                break;
//            case ARC:
//                path = getWavePath(width, height * factor, cutHeight * factor / 2, 0.05f, phaseShift);
//                break;
//            case WAVE:
//                path = getWavePath(width, height * factor, cutHeight * factor / 2, 0.1f, phaseShift);
//        }
//
//        return path;
//    }


//    public static Path getPath1(
//            float width, float height, ImageCutType cutType, float angle, float factor) {
//        float[] x = new float[4];
//        float[] y = new float[4];
//
////      _____________________
////      | (0,0)       (1,1) |
////      |                   |
////      | (3,3)       (2,2) |
////      ---------------------
//
//
//        Path path = new Path();
//        path.moveTo(0, 0);
//        path.lineTo(width, 0);
//        path.lineTo(width, height);
//        path.lineTo(0, height);
//        path.close();
//
//
//        float heightDiff = Math.abs(
//                ((float) Math.tan(Math.toRadians(angle))) * width
//        );
//        if (heightDiff >= height) {
//            heightDiff = height / 5;
//        }
//
//
//        switch (cutType) {
//
//            case LINE :
//                x[0] = 0;
//                y[0] = 0;
//                x[1] = width;
//                y[1] = 0;
//                if (angle < 90) {
//                    x[2] = width;
//                    y[2] = (height - heightDiff) * factor;
//                    x[3] = 0;
//                    y[3] = height * factor;
//                } else {
//                    x[2] = width;
//                    y[2] = height * factor;
//                    x[3] = 0;
//                    y[3] = (height - heightDiff) * factor;
//                }
//
//                path.reset();
//                path.moveTo(x[0], y[0]);
//                path.lineTo(x[1], y[1]);
//                path.lineTo(x[2], y[2]);
//                path.lineTo(x[3], y[3]);
//                path.close();
//                break;
//
//            case ARC :
////                x[0] = 0;
////                y[0] = 0;
////                x[1] = width;
////                y[1] = 0;
////                x[2] = width;
////                y[2] = (height - heightDiff) * factor;
////                x[3] = 0;
////                y[3] = (height - heightDiff) * factor;
////
////                path.reset();
////                path.moveTo(x[0], y[0]);
////                path.lineTo(x[1], y[1]);
////                path.lineTo(x[2], y[2]);
////                path.cubicTo(width / 3, height * factor,(width / 3) * 2, height * factor, x[3], y[3]);
////                path.close();
//                path = getWavePath(width, height*factor, heightDiff*factor/2, 0f, 3.4f);
//                break;
//
//            case WAVE :
////                path = getWavePath(width, height*factor, heightDiff*factor/2, -0.3f, 2);
////                path = getWave(width, height * factor, heightDiff * factor / 2, 0.1f, -20);
//                break;
//        }
//
//        return path;
////        return getWavePath(width, height*factor, heightDiff*factor/2, -0.3f, 2);
//    }


    public static Shader getLinearGradient(float width, float height, int startColor, int endColor) {
        float[] x = new float[2];
        float[] y = new float[2];

        x[0] = y[0] = x[1] = 0;
        y[1] = height;

        return new LinearGradient(x[0], y[0], x[1], y[1], startColor, endColor, Shader.TileMode.CLAMP);
    }
}
