package com.kannan.trial.fragment;

import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kannan.trial.CardItem;
import com.kannan.trial.FragmentVisibilityListener;
import com.kannan.trial.R;
import com.kannan.trial.RevealImageView;

public class CardFragment extends Fragment implements FragmentVisibilityListener{

    private String mTitle;
    private String mDescription;
    private int mImageResID;

    private ViewHolder mViewHolder;


    public static CardFragment newInstance(String title, String description, int imageResID) {
        CardFragment cardFragment = new CardFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("desc", description);
        args.putInt("imageResID", imageResID);
        cardFragment.setArguments(args);

        return cardFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTitle = getArguments().getString("title", "Title");
        mDescription = getArguments().getString("desc", "Description");
        mImageResID = getArguments().getInt("imageResID");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewHolder = new ViewHolder();
        View v =  inflater.inflate(R.layout.fragment_card, container, false);
        mViewHolder.titleTextView = (TextView) v.findViewById(R.id.fragment_cardview_title);
        mViewHolder.titleTextView.setText(mTitle);
        mViewHolder.descTextView = (TextView) v.findViewById(R.id.fragment_cardview_description);
        mViewHolder.descTextView.setText(mDescription);
        mViewHolder.imageView = (RevealImageView) v.findViewById(R.id.fragment_cardview_image);
        mViewHolder.imageView.setImageResource(R.drawable.pic1);
        return v;
    }

    @Override
    public void onFragmentVisible() {
//        getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                Log.i("app", "Visible " + mTitle);
//                mViewHolder.imageView.update(1f);
//            }
//        });
        Log.i("app", "Visible " + mTitle);
        mViewHolder.imageView.update(1f);
//        mViewHolder.imageView.invalidate();
    }

    @Override
    public void onFragmentInvisible() {
        mViewHolder.imageView.update(1f);
//        mViewHolder.imageView.invalidate();
//        Toast.makeText(getContext(), "Invisible " + mTitle, Toast.LENGTH_SHORT).show();

    }

    private class ViewHolder {
        RevealImageView imageView;
        TextView        titleTextView;
        TextView        descTextView;
    }
}
