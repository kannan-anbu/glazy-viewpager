package com.kannan.trial.card;

/**
 * Created by kannan on 26/5/17.
 */

import android.support.v4.view.ViewPager;
import android.view.View;

public class ZoomOutTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.90f;

    @Override
    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();
        int pageHeight = view.getHeight();
        float alpha = 0;
        if (0 <= position && position <= 1) {
            alpha = 1 - position;
        } else if (-1 < position && position < 0) {
            float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
            float verticalMargin = pageHeight * (1 - scaleFactor) / 2;
            float horizontalMargin = pageWidth * (1 - scaleFactor) / 2;
            if (position < 0) {
                view.setTranslationX(horizontalMargin - verticalMargin / 2);
            } else {
                view.setTranslationX(-horizontalMargin + verticalMargin / 2);
            }

            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);

            alpha = position + 1;
        }

        view.setAlpha(alpha);
        view.setTranslationX(view.getWidth() * -position);
        float yPosition = position * view.getHeight();
        view.setTranslationY(yPosition);
    }

}
