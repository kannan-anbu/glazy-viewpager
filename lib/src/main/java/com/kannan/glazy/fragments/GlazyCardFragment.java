package com.kannan.glazy.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.kannan.glazy.GlazyCard;
import com.kannan.glazy.R;
import com.kannan.glazy.views.GlazyImageView;

public class GlazyCardFragment extends Fragment {

    private GlazyCard card;

    public static GlazyCardFragment newInstance(GlazyCard card) {
        GlazyCardFragment glazyCardFragment = new GlazyCardFragment();
        Bundle args = new Bundle();
        args.putSerializable("glazy_card", card);
        glazyCardFragment.setArguments(args);

        return glazyCardFragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        card = (GlazyCard) getArguments().getSerializable("glazy_card");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.layout_page_2, container, false);
        TextView title = (TextView) v.findViewById(R.id.title_text);
        title.setText(card.getTitle());

        TextView description = (TextView) v.findViewById(R.id.description_text);
        description.setText(card.getDescription());

        GlazyImageView imgView = (GlazyImageView) v.findViewById(R.id.glazy_image_view);
        imgView.setImageRes(card.getImageRes());
        imgView.setBackgroundColor(card.getBackgroundColor());
        imgView.setCutType(card.getImageCutType());
        imgView.setCutAngle(card.getImageCutAngle());
        imgView.setCutCount(card.getImageCutCount());
        imgView.setCutHeight(card.getImageCutHeight());
        imgView.setCutPhaseShift(card.getImageCutPhaseShift());
//        v.setBackgroundColor(card.getBackgroundColor());

        ScrollView textContainer = (ScrollView) v.findViewById(R.id.text_container);
        textContainer.setBackgroundColor(card.getBackgroundColor());
//        Log.i("app", "scroll view hight" + Utils.getPixelForDp(getContext(), card.getCoverHeight_dp()));
//        textContainer.setLayoutParams(
//                new RelativeLayout.LayoutParams(
//                        RelativeLayout.LayoutParams.MATCH_PARENT,
//                        Utils.getPixelForDp(getContext(), card.getCoverHeight_dp())
//                )
//        );

        return v;
    }

}
