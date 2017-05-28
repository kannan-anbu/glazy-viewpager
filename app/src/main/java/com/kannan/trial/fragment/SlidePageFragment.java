package com.kannan.trial.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kannan.trial.R;

/**
 * Created by kannan on 24/12/16.
 */
public class SlidePageFragment extends Fragment {
    private String mTitle;

    public static SlidePageFragment newInstance(String title) {
        SlidePageFragment spf = new SlidePageFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        spf.setArguments(args);
        return spf;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitle = getArguments().getString("title");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup vg = (ViewGroup) inflater.inflate(R.layout.fragment_slide_page, container, false);
        ((TextView) vg.findViewById(R.id.fragment_slidepage_tv)).setText(mTitle);
        return vg;
    }
}
