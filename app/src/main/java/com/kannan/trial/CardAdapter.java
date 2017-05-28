package com.kannan.trial;


import android.support.v7.widget.CardView;

public interface CardAdapter {

    CardView getCardViewAt(int position);

    int getCount();

}
