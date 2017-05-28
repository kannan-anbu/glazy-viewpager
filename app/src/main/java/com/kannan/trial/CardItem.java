package com.kannan.trial;

public class CardItem {
    private String mTitle;
    private String mDescription;
    private int mImageRes;

    public CardItem(String title, String description, int imageRes) {
        mTitle = title;
        mDescription = description;
        mImageRes = imageRes;
    }

    public int getImageRes() {
        return mImageRes;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getTitle() {
        return mTitle;
    }
}
