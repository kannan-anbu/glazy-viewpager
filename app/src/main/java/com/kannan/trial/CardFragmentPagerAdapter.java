package com.kannan.trial;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kannan.trial.fragment.CardFragment;

import java.util.ArrayList;

public class CardFragmentPagerAdapter extends CustomFragmentPagerAdapter {

    private int CARDS_COUNT = 0;

    private ArrayList<CardItem> cards;
    Context context;

    public CardFragmentPagerAdapter(FragmentManager fm, Context ctx) {
        super(fm);
        context = ctx;
        cards = new ArrayList<>();
    }

    public void addCardItem(CardItem card) {
        cards.add(card);
        updateCount();
    }

    public void removeCardItem(int position) {
        try {
            cards.remove(position);
            updateCount();
        } catch (Exception e) { }
    }

    public void removeCardItem(CardItem card) {
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
//        Toast.makeText(context, "get"+position, Toast.LENGTH_SHORT).show();
        CardItem card = cards.get(position);
        CardFragment cardFragment = CardFragment.newInstance(   card.getTitle(),
                                                                card.getDescription(),
                                                                card.getImageRes()      );


        return cardFragment;
//            CardFragment cardFragment = new CardFragment();
//        while(!cardFragment.is()) {}
//        View v = cardFragment.getView();
//        ((TextView) v.findViewById(R.id.fragment_cardview_title)).setText("dbckjdhkdj");

//            LinearLayout container = cardFragment.getcardContainer();
//            CardItem card = cards.get(position);
//            ((CardView) container.findViewById(R.id.fragment_cardview)).getChildCount();
//        ((TextView) cardView.findViewById(R.id.fragment_cardview_title))
//                            .setText(card.getTitle());
//            ((TextView) cardView.findViewById(R.id.fragment_cardview_description))
//                    .setText(card.getDescription());
//            ((ImageView) container.findViewById(R.id.fragment_cardview_image))
//                    .setImageResource(card.getImageRes());
//        } catch (Exception e) {
//            Toast.makeText(, e, Toast.LENGTH_LONG).show();
//        }


//        return cardFragment;
    }

    @Override
    public int getCount() {
        return CARDS_COUNT;
    }
}
