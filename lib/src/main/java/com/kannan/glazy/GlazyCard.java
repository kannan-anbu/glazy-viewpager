package com.kannan.glazy;

import android.graphics.Color;

import com.kannan.glazy.views.GlazyImageView.ImageCutType;

import java.io.Serializable;

public class GlazyCard implements Serializable{

    private String mTitle;
    private String mSubTitle;
    private String mDescription;
    private int mImageRes;

    private ImageCutType mImageCutType;
//    private int mImageCutAngle;
    private int mImageCutCount;
    private int mImageCutHeight;
    private int mImageCutPhaseShift;

    private boolean mAutoTint;
    private int mTintColor;
    private int mTintAlpha;
    private int mBackgroundColor;

    private boolean mHasImageSource;

    public GlazyCard() {
        mTitle = "TITLE";
        mSubTitle = "SUB TITLE";
        mDescription = "jfhkjuh fgfuhkjh fuyhkhk ksfhghkfhgkfjh ";
        mImageCutType = ImageCutType.LINE_POSITIVE;
//        mImageCutAngle = 20;
        mImageCutCount = 2;
        mImageCutHeight = 0;
        mImageCutPhaseShift = 0;
        mAutoTint = true;
        mBackgroundColor = Color.parseColor("#fff1f1f1");
        mHasImageSource = false;
    }

    public GlazyCard(String title, String description, int imageRes) {
        this();
        mTitle = title;
        mDescription = description;
        mImageRes = imageRes;
    }

    public GlazyCard withTitle(String title) {
        mTitle = title;
        return this;
    }

    public GlazyCard withSubTitle(String subTitle) {
        mSubTitle = subTitle;
        return this;
    }

    public GlazyCard withDescription(String desc) {
        mDescription = desc;
        return this;
    }

    public GlazyCard withImageRes(int imgRes) {
        mImageRes = imgRes;
        mHasImageSource = true;
        return this;
    }

//    public GlazyCard withCoverHeight_px (int coverHeight_px) {
//        mCoverHeight_px = coverHeight_px;
//        return this;
//    }

//    public GlazyCard withCoverHeight_dp (int coverHeight_dp) {
//        mCoverHeight_dp = coverHeight_dp;
//        return this;
//    }

//    public GlazyCard withSlopeHeight_px (int slopeHeight_px) {
//        mSlopeHeight_px = slopeHeight_px;
//        return this;
//    }

//    public GlazyCard withSlopeHeight_dp (int slopeHeight_dp) {
//        mSlopeHeight_dp = slopeHeight_dp;
//        return this;
//    }

//    public GlazyCard withSlopeDirection (SlopeDirection direction) {
//        mSlopeDirection = direction;
//        return this;
//    }

//    public GlazyCard withAutoTint () {
//        mAutoTint = true;
//        return this;
//    }
//
//    public GlazyCard withoutAutoTint () {
//        mAutoTint = false;
//        return this;
//    }

    public GlazyCard withTintColor (int color) {
        mTintColor = color;
        mAutoTint = false;
        return this;
    }

    public GlazyCard withTintAlpha (int alpha) {
        mTintAlpha = alpha;
        return this;
    }

    public GlazyCard withImageCutType(ImageCutType cutType) {
        mImageCutType = cutType;
        return this;
    }

//    public GlazyCard withImageCutAngle(int angle) {
//        mImageCutAngle = angle;
//        return this;
//    }

    public GlazyCard withImageCutCount(int count) {
        mImageCutCount = count;
        return this;
    }

    public GlazyCard withImageCutHeight(int height) {
        mImageCutHeight = height;
        return this;
    }

    public GlazyCard withImageCutPhaseShift(int phaseShift) {
        mImageCutPhaseShift = phaseShift;
        return this;
    }

    public GlazyCard withBackgroundColor(int bgColor) {
        mBackgroundColor = bgColor;
        return this;
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

    public String getSubTitle() {
        return mSubTitle;
    }

//    public int getCoverHeight_dp() {
//        return mCoverHeight_dp;
//    }
//
//    public int getSlopeHeight_dp() {
//        return mSlopeHeight_dp;
//    }

//    public SlopeDirection getSlopeDirection() {
//        return mSlopeDirection;
//    }

    public boolean isAutoTint() {
        return mAutoTint;
    }

    public int getTintColor() {
        return mTintColor;
    }

    public int getTintAlpha() {
        return mTintAlpha;
    }

    public ImageCutType getImageCutType() {
        return mImageCutType;
    }

//    public int getImageCutAngle() {
//        return mImageCutAngle;
//    }

    public int getImageCutCount() {
        return mImageCutCount;
    }

    public int getImageCutHeight() {
        return mImageCutHeight;
    }

    public int getImageCutPhaseShift() {
        return mImageCutPhaseShift;
    }

    public boolean hasImageSource() {
        return mHasImageSource;
    }

    public int getBackgroundColor() {
        return mBackgroundColor;
    }
}
