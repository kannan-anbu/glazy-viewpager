package com.kannan.glazysample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.kannan.glazy.GlazyCard;
import com.kannan.glazy.Utils;
import com.kannan.glazy.pager.GlazyFragmentPagerAdapter;
import com.kannan.glazy.transformers.*;
import com.kannan.glazy.views.GlazyImageView.ImageCutType;

public class PagerActivity extends AppCompatActivity {

    private ViewPager mPager;
    private GlazyFragmentPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);
        setTitle("GlazyViewPager");

        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new GlazyFragmentPagerAdapter(getSupportFragmentManager(), getApplicationContext());
        int imageResId = getApplicationContext().getResources().getIdentifier("matt_le_blanc", "drawable", getPackageName());

        for (int i = 0; i < 5; i++) {
            GlazyCard card = new GlazyCard("Title text",
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do" +
                            " eiusmod tempor incididunt ut labore et dolore magna aliqua." +
                            " Ut enim ad minim veniam, quis nostrud exercitation ullamco" +
                            " laboris nisi ut aliquip ex ea commodo consequat. Duis aute" +
                            " irure dolor in reprehenderit in voluptate velit esse cillum " +
                            "dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat" +
                            " non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
                    imageResId);
            card.withImageCutType(ImageCutType.LINE)
                    .withImageCutCount(10)
                    .withImageCutAngle(10);
            mPagerAdapter.addCardItem(card);
        }
        mPager.setAdapter(mPagerAdapter);
        mPager.setOffscreenPageLimit(2);
        mPager.setClipToPadding(false);
        mPager.setPageMargin(Utils.dpToPx(15, this));

        mPager.setPageTransformer(true, new GlazyPagerTransformer(GlazyPagerTransformer.TransformType.ZOOM));
    }

}
