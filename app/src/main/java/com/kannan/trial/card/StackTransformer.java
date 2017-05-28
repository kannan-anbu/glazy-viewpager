package com.kannan.trial.card;

/**
 * Created by kannan on 26/5/17.
 */

import android.support.v4.view.ViewPager;
import android.view.View;

public class StackTransformer implements ViewPager.PageTransformer {
    @Override
    public void transformPage(View page, float position) {
        page.setTranslationX(page.getWidth() * -position);
        float y;
        if (position < 0)
            y = position * page.getHeight();
        else
            y = 0f;
        page.setTranslationY(y);
//        position < 0 ? position * page.getHeight() : 0f
    }
}
