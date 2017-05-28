package com.kannan.trial;

import android.support.annotation.IntegerRes;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kannan.trial.utils.Converter;

class SlidePagerTransformer implements ViewPager.PageTransformer {

    static enum TransformType {
        FLOW,
        DEPTH,
        ZOOM,
        SLIDE_OVER
    }

    private final TransformType mTransformType;

    private static final float MIN_SCALE_DEPTH      = 0.75f;
    private static final float MIN_SCALE_ZOOM       = 0.85f;
    private static final float MIN_ALPHA_ZOOM       = 0.5f;
    private static final float SCALE_FACTOR_SLIDE   = 0.85f;
    private static final float MIN_ALPHA_SLIDE      = 0.35f;

    public SlidePagerTransformer(TransformType transformType) {
        this.mTransformType = transformType;
    }

    @Override
    public void transformPage(View page, float position1) {
        final float alpha, scale, translationX;
        float position = position1 - 0.13157895f;// - 0.15084746f;

        if(Math.abs(position) <= 1.0) {
//            TextView tv = (TextView) page.findViewById(R.id.fragment_cardview_title);
//            tv.setText("" + position);


            RevealImageView iv = (RevealImageView) page.findViewById(R.id.fragment_cardview_image);
            String t = ((TextView) page.findViewById(R.id.fragment_cardview_title)).getText().toString();
            float f = 1.0f - Math.abs(position);
            Log.i("app", t +  f );
            iv.update(f);
//            page.invalidate();
        }
        switch (mTransformType) {
            case FLOW:
                page.setRotationY(position * -30f);
                return;
            case SLIDE_OVER:
                if(position > -1 && position < 0) {
                    scale = Math.abs(Math.abs(position) - 1) * (1.0f - SCALE_FACTOR_SLIDE)
                            + SCALE_FACTOR_SLIDE;
                    alpha = Math.max(MIN_ALPHA_SLIDE, 1 - Math.abs(position));
                    int pageWidth = page.getWidth();
                    float translateValue = position * -pageWidth;
                    translationX = (translateValue > -pageWidth) ? translateValue : 0;
                } else {
                    alpha = scale = 1;
                    translationX = 0;
                }
                break;
            case DEPTH:
                if(position > 0 && position < 1) {
                    alpha = 1 - position;
                    scale = MIN_SCALE_DEPTH + (1 - MIN_SCALE_DEPTH) * (1 - Math.abs(position));
                    translationX = page.getWidth() * -position;
                } else {
                    alpha = scale = 1;
                    translationX = 0;
                }
                break;
            case ZOOM:
                page.setRotationY(position * -10f);
                scale = (1 - 0.1f * (1 - Math.abs(position)));
                alpha = 1;
                translationX = 0;
//                if (position >= -1 && position <= 1) {
//                    scale = Math.max(MIN_SCALE_ZOOM, 1 - Math.abs(position));
//                    alpha = MIN_SCALE_ZOOM +
//                            (scale - MIN_SCALE_ZOOM) / (1 - MIN_SCALE_ZOOM) * (1 - MIN_ALPHA_ZOOM);
//                    float vMargin = page.getHeight() * (1 - scale) / 2;
//                    float hMargin = page.getWidth() * (1 - scale) / 2;
//                    if (position < 0) {
//                        translationX = (hMargin - vMargin/2);
//                    } else {
//                        translationX = (-hMargin + vMargin/2);
//                    }
//                } else {
//                    alpha = MIN_ALPHA_ZOOM;
//                    scale = MIN_SCALE_ZOOM;
//                    translationX = 0;
//                }
                break;
            default:
                return;
        }

        page.setAlpha(alpha);
//        page.setTranslationX(translationX);
        page.setScaleX(scale);
        page.setScaleY(scale);
    }
}