package com.kannan.trial;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;


import java.lang.ref.WeakReference;

public class FragmentViewPager extends ViewPager {

    private boolean mIsPagerVisible;

    private FragmentVisibilityNotifier mFragmentVisibilityNotifier;

    public FragmentViewPager(Context context) {
        super(context);
        init();
    }

    public FragmentViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mIsPagerVisible = false;
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        if (adapter instanceof CustomFragmentPagerAdapter) {
            attachFragmentVisibilityNotifier();
        } else {
            detachFragmentVisibilityNotifier();
        }
        super.setAdapter(adapter);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    private void attachFragmentVisibilityNotifier() {
        if (mFragmentVisibilityNotifier == null) {
            mFragmentVisibilityNotifier = new FragmentVisibilityNotifier();
            addOnPageChangeListener(mFragmentVisibilityNotifier);
        }
    }

    private void detachFragmentVisibilityNotifier() {
        if (mFragmentVisibilityNotifier != null) {
            removeOnPageChangeListener(mFragmentVisibilityNotifier);
            mFragmentVisibilityNotifier = null;
        }
    }

    public void setVisibility(boolean visible) {
        if (visible) {
            mIsPagerVisible = true;
            if (mFragmentVisibilityNotifier != null) {
                mFragmentVisibilityNotifier.notifyCurrentFragment(true);
            }
        } else {
            mIsPagerVisible = false;
            if (mFragmentVisibilityNotifier != null) {
                mFragmentVisibilityNotifier.notifyCurrentFragment(false);
            }
        }
    }

    class FragmentVisibilityNotifier extends ViewPager.SimpleOnPageChangeListener {

        CurrentFragmentHolder mFragmentHolder;

        FragmentVisibilityNotifier() {
            mFragmentHolder = new CurrentFragmentHolder();
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            if (mFragmentHolder.getPosition() == position   ||
                    positionOffset != 0) {
                return;
            }

            // mIsPagerVisible check
            Fragment outGoingFragment = mFragmentHolder.getFragment();
            if (outGoingFragment != null) {
                notifyFragmentInvisible(outGoingFragment);
            }

            Fragment inCommingFragment = getFragment(position);
            mFragmentHolder.setPosition(position);
            if (inCommingFragment != null) {
                mFragmentHolder.setFragment(inCommingFragment);
                if (inCommingFragment.isAdded()) {
                    notifyFragmentVisible(inCommingFragment);
                }
            }
        }

        public void notifyCurrentFragment(boolean fragmentVisible) {

            if (mFragmentHolder.getFragment() == null) {
                Fragment currentFragment = getFragment(mFragmentHolder.getPosition());
                mFragmentHolder.setFragment(currentFragment);
            }

            Fragment targetFragment = mFragmentHolder.getFragment();
            if (targetFragment != null) {
                if (fragmentVisible && !mFragmentHolder.isVisible()) {
                    notifyFragmentVisible(targetFragment);
                    mFragmentHolder.setVisible(true);
                }
                if (!fragmentVisible && mFragmentHolder.isVisible()) {
                    notifyFragmentInvisible(targetFragment);
                    mFragmentHolder.setVisible(false);
                }
            }
        }

        private void notifyFragmentVisible(Fragment targetFragment) {
            try {
                ((FragmentVisibilityListener) targetFragment).onFragmentVisible();
            } catch (ClassCastException ignored) { }
        }

        private void notifyFragmentInvisible(Fragment targetFragment) {
            try {
                ((FragmentVisibilityListener) targetFragment).onFragmentInvisible();
            } catch (ClassCastException ignored) { }
        }

        private Fragment getFragment(int position) {
            return ((CustomFragmentPagerAdapter) getAdapter())
                    .getFragment(position);
        }

    }

    class CurrentFragmentHolder {
        private int mPosition;
        private boolean mIsVisible;
        private WeakReference<Fragment> mFragmentRef;

        public CurrentFragmentHolder() {
            mPosition = -1;
            mIsVisible = false;
        }

        public int getPosition() {
            return mPosition;
        }

        public void setPosition(int position) {
            mPosition = position;
        }

        public boolean isVisible() {
            return mIsVisible;
        }

        public void setVisible(boolean isVisible) {
            mIsVisible = isVisible;
        }

        public Fragment getFragment() {
            return mFragmentRef != null ? mFragmentRef.get() : null ;
        }

        public void setFragment(Fragment fragment) {
            mFragmentRef = new WeakReference<>(fragment);
        }
    }
}
