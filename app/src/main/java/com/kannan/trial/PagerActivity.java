package com.kannan.trial;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.kannan.trial.fragment.SlidePageFragment;
import com.kannan.trial.utils.Converter;

public class PagerActivity extends AppCompatActivity {

    private static final int PAGE_COUNT = 4;
    private FragmentViewPager mPager;
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);

        mPager = (FragmentViewPager) findViewById(R.id.pager);
        mPagerAdapter = new CardFragmentPagerAdapter(getSupportFragmentManager(), getApplicationContext());
        int imageResId = getApplicationContext().getResources().getIdentifier("pic1.jpg", "drawable", getPackageName());
        ((CardFragmentPagerAdapter) mPagerAdapter).addCardItem(new CardItem("t0", "description", imageResId));
        ((CardFragmentPagerAdapter) mPagerAdapter).addCardItem(new CardItem("t1", "description akdjvckhdvmjghjkhj uyhkug kjgluglugjgjhfjhgjggggggggggglllll____________________________________________________________________________________jhfgjhg jjyj juyj f jgjfjg kfhgfjhgkhdfjghfjhfhgfjfjhfhgfjggfjhfhgfmbtttttkjhkjlkpgjhgkvgcjhfjhkjbghchgfkxkfchjgj cadjhcvjhc ascahvcsaj cjadhcvjahcvjac sjchvcvjhsvjhsvsbc cvdjhcgjdhv", imageResId));
        ((CardFragmentPagerAdapter) mPagerAdapter).addCardItem(new CardItem("t2", "description", imageResId));
        ((CardFragmentPagerAdapter) mPagerAdapter).addCardItem(new CardItem("t3", "description", imageResId));
        ((CardFragmentPagerAdapter) mPagerAdapter).addCardItem(new CardItem("t4", "description", imageResId));
        ((CardFragmentPagerAdapter) mPagerAdapter).addCardItem(new CardItem("t5", "description", imageResId));
        mPager.setAdapter(mPagerAdapter);
        mPager.addOnPageChangeListener(pageChangeListener);

//        mPager.setOffscreenPageLimit(2);

        mPager.setClipToPadding(false);
//        mPager.setPadding(Converter.dpToPx(40,this), 0, Converter.dpToPx(40, this), 0);
//        mPager.setPageMargin(Converter.dpToPx(15, this));

        mPager.setPageTransformer(true, new SlidePagerTransformer(SlidePagerTransformer.TransformType.ZOOM));
        //mPager.setCurrentItem(1, true);
//        mPager.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                Toast.makeText(getApplicationContext(), "tree"+mPager.getCurrentItem(), Toast.LENGTH_SHORT).show();
//
//                ((RevealImageView) mPager.getChildAt(mPager.getCurrentItem()).findViewById(R.id.fragment_cardview_image)).update(.5f);
//                mPager.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//            }
//        });
//        mPager.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
//            @Override
//            public void onViewAttachedToWindow(View view) {
//                mPager.beginFakeDrag();
//                mPager.fakeDragBy(0f);
//                mPager.endFakeDrag();
//            }
//
//            @Override
//            public void onViewDetachedFromWindow(View view) {
//
//            }
//        });

    }

    ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
//            ((RevealImageView)(mPager.getFocusedChild()).findViewById(R.id.fragment_cardview_image)).update(1);
//            Toast.makeText(PagerActivity.this, "selected:" + position, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private class SlidePagerAdapter extends FragmentStatePagerAdapter {
        public SlidePagerAdapter(FragmentManager fr){
            super(fr);
        }

        @Override
        public Fragment getItem(int position) {
            SlidePageFragment spf = SlidePageFragment.newInstance("Page : " + position);
            if (position == 1) {
                Toast.makeText(getApplicationContext(), "drag", Toast.LENGTH_SHORT).show();
                mPager.beginFakeDrag();
                mPager.fakeDragBy(10f);
                mPager.endFakeDrag();
            }
            return spf;
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public float getPageWidth(int position) {
//            if(position == 0 || position == PAGE_COUNT - 1)   return .9f;
             return 1f;
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        mPager.setVisibility(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPager.setVisibility(false);
    }
}
