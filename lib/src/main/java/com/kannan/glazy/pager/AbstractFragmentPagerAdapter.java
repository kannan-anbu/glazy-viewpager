package com.kannan.glazy.pager;

/**
 * Created by kannan on 20/1/17.
 */

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public abstract class AbstractFragmentPagerAdapter extends PagerAdapter {

    private final FragmentManager mFragmentManager;
    private FragmentTransaction   mCurTransaction     = null;
    private Fragment              mCurrentPrimaryItem = null;

    public AbstractFragmentPagerAdapter(FragmentManager fm) {
        mFragmentManager = fm;
    }

    @Override
    public void startUpdate(ViewGroup container) { }

    public Fragment getFragment(int position) {
        String name = makeFragmentName(this, position);
        Fragment fragment = mFragmentManager.findFragmentByTag(name);
        return fragment;
    }

    public abstract Fragment instantiateFragment(int position);

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (mCurTransaction == null) {
            mCurTransaction = mFragmentManager.beginTransaction();
        }

        Fragment fragment = getFragment(position);
        if (fragment != null) {
            mCurTransaction.attach(fragment);
        } else {
            fragment = instantiateFragment(position);
            mCurTransaction.add(container.getId(), fragment,
                    makeFragmentName(this, position));
        }
        if (fragment != mCurrentPrimaryItem) {
            fragment.setMenuVisibility(false);
            fragment.setUserVisibleHint(false);
        }

        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (mCurTransaction == null) {
            mCurTransaction = mFragmentManager.beginTransaction();
        }
        mCurTransaction.detach((Fragment)object);
    }

   @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        Fragment fragment = (Fragment)object;
        if (fragment != mCurrentPrimaryItem) {
            if (mCurrentPrimaryItem != null) {
                mCurrentPrimaryItem.setMenuVisibility(false);
                mCurrentPrimaryItem.setUserVisibleHint(false);
            }
            if (fragment != null) {
                fragment.setMenuVisibility(true);
                fragment.setUserVisibleHint(true);
            }
            mCurrentPrimaryItem = fragment;
        }
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        if (mCurTransaction != null) {
            mCurTransaction.commitAllowingStateLoss();
            mCurTransaction = null;
            mFragmentManager.executePendingTransactions();
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return ((Fragment)object).getView() == view;
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) { }

    private static String makeFragmentName(AbstractFragmentPagerAdapter adapter, long id) {
        return "android:super-switcher:" + adapter.hashCode() + ":" + id;
    }
}