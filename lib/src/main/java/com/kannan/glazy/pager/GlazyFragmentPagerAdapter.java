package com.kannan.glazy.pager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.kannan.glazy.GlazyCard;
import com.kannan.glazy.fragments.GlazyCardFragment;

import java.util.ArrayList;

public class GlazyFragmentPagerAdapter extends AbstractFragmentPagerAdapter {

    private int CARDS_COUNT = 0;

    private ArrayList<GlazyCard> cards;
    Context context;

    public GlazyFragmentPagerAdapter(FragmentManager fm, Context ctx) {
        super(fm);
        context = ctx;
        cards = new ArrayList<>();
    }

    public void addCardItem(GlazyCard card) {
        cards.add(card);
        updateCount();
    }

    public void removeCardItem(int position) {
        try {
            cards.remove(position);
            updateCount();
        } catch (Exception e) { }
    }

    public void removeCardItem(GlazyCard card) {
        try {
            cards.remove(card);
            updateCount();
        } catch (Exception e) { }
    }

    private void updateCount() {
        CARDS_COUNT = cards.size();
        notifyDataSetChanged();
    }

    @Override
    public Fragment instantiateFragment(int position) {
        GlazyCard card = cards.get(position);
        return GlazyCardFragment.newInstance( card );
    }

    @Override
    public int getCount() {
        return CARDS_COUNT;
    }
}
