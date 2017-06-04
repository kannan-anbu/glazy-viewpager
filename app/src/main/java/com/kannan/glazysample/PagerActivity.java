package com.kannan.glazysample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.kannan.glazy.GlazyCard;
import com.kannan.glazy.Utils;
import com.kannan.glazy.pager.GlazyFragmentPagerAdapter;
import com.kannan.glazy.transformers.GlazyPagerTransformer;
import com.kannan.glazy.views.GlazyImageView.ImageCutType;

public class PagerActivity extends AppCompatActivity {

    private ViewPager mPager;
    private GlazyFragmentPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pager);
        setTitle("GlazyViewPager");

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setOnPageChangeListener(
                new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {

                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {
                        if (state != ViewPager.SCROLL_STATE_IDLE) {
                            final int childCount = mPager.getChildCount();
                            for (int i = 0; i < childCount; i++)
                                mPager.getChildAt(i).setLayerType(View.LAYER_TYPE_NONE, null);
                        }
                    }
                }
        );
        mPagerAdapter = new GlazyFragmentPagerAdapter(getSupportFragmentManager(), getApplicationContext());
        int imageResId = getApplicationContext().getResources().getIdentifier("matt_le_blanc", "drawable", getPackageName());

        String title = "Title Text".toUpperCase();
        String desc = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do" +
                " eiusmod tempor incididunt ut labore et dolore magna aliqua." +
                " Ut enim ad minim veniam, quis nostrud exercitation ullamco" +
                " laboris nisi ut aliquip ex ea commodo consequat. Duis aute" +
                " irure dolor in reprehenderit in voluptate velit esse cillum " +
                "dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat" +
                " non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
        desc = desc.toUpperCase();
        for (int i = 0; i < 2; i ++) {
            mPagerAdapter.addCardItem(
                    new GlazyCard()
                            .withTitle(desc)
                            .withDescription(desc)
                            .withImageRes(imageResId)
                            .withImageCutType(ImageCutType.WAVE)
                            .withImageCutCount(3)
                            .withImageCutHeight(60));
            mPagerAdapter.addCardItem(
                    new GlazyCard()
                            .withTitle(title)
                            .withDescription(desc)
                            .withImageRes(imageResId)
                            .withImageCutType(ImageCutType.LINE)
                            .withImageCutCount(3)
                            .withImageCutAngle(10));
            mPagerAdapter.addCardItem(
                    new GlazyCard()
                            .withTitle(title)
                            .withDescription(desc)
                            .withImageRes(imageResId)
                            .withImageCutType(ImageCutType.ARC)
                            .withImageCutCount(3)
                            .withImageCutHeight(60));
        }

        mPager.setAdapter(mPagerAdapter);
        mPager.setOffscreenPageLimit(2);
        mPager.setClipToPadding(false);
        mPager.setPageMargin(Utils.dpToPx(15, this));

        mPager.setPageTransformer(true, new GlazyPagerTransformer(GlazyPagerTransformer.TransformType.ZOOM));
    }

}




//    compile 'com.android.support:design:25.1.0'
//            compile 'com.android.support:recyclerview-v7:25.1.0'
//            compile 'com.android.support:cardview-v7:25.1.0'
//            compile 'com.github.bumptech.glide:glide:3.6.1'
