package com.kannan.glazy.pager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import com.kannan.glazy.transformers.GlazyPagerTransformer;

/**
 * Created by kannan on 5/6/17.
 */

public class GlazyViewPager extends ViewPager {

    Context mContext;

    public GlazyViewPager(Context context) {
        super(context);
        init(context, null);
    }

    public GlazyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public void init(Context context, AttributeSet attrs) {
        mContext = context;
        setOnPageChangeListener(
                new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {

                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {
//                        if (state != ViewPager.SCROLL_STATE_IDLE) {
//                            final int childCount = getChildCount();
//                            for (int i = 0; i < childCount; i++)
//                                getChildAt(i).setLayerType(View.LAYER_TYPE_NONE, null);
//                        }
                    }
                }
        );
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        if (adapter instanceof GlazyFragmentPagerAdapter) {
            super.setAdapter(adapter);
        }
    }

    @Override
    public void setPageTransformer(boolean reverseDrawingOrder, PageTransformer transformer) {
        if (transformer instanceof GlazyPagerTransformer) {
            ((GlazyPagerTransformer) transformer).attachedPager(this);
            ((GlazyPagerTransformer) transformer).setContext(mContext);
            super.setPageTransformer(reverseDrawingOrder, transformer, LAYER_TYPE_NONE);
        }
    }

    @Override
    public void setPageTransformer(boolean reverseDrawingOrder, PageTransformer transformer, int pageLayerType) {
        if (transformer instanceof GlazyPagerTransformer) {
            ((GlazyPagerTransformer) transformer).attachedPager(this);
            ((GlazyPagerTransformer) transformer).setContext(mContext);
            super.setPageTransformer(reverseDrawingOrder, transformer, LAYER_TYPE_NONE);
        }
    }
}
