package com.kannan.glazy;

import android.graphics.Color;

import java.io.Serializable;

import static com.kannan.glazy.views.GlazyImageView.DEF_AUTO_TINT_MODE;
import static com.kannan.glazy.views.GlazyImageView.DEF_CUT_COUNT;
import static com.kannan.glazy.views.GlazyImageView.DEF_CUT_HEIGHT;
import static com.kannan.glazy.views.GlazyImageView.DEF_IMAGE_CUT_TYPE;
import static com.kannan.glazy.views.GlazyImageView.DEF_IMAGE_RES;
import static com.kannan.glazy.views.GlazyImageView.DEF_LINE_SPACING_DP;
import static com.kannan.glazy.views.GlazyImageView.DEF_SUB_TITLE_TEXT;
import static com.kannan.glazy.views.GlazyImageView.DEF_SUB_TITLE_TEXT_COLOR;
import static com.kannan.glazy.views.GlazyImageView.DEF_SUB_TITLE_TEXT_SIZE_DP;
import static com.kannan.glazy.views.GlazyImageView.DEF_TEXT_MARGIN_DP;
import static com.kannan.glazy.views.GlazyImageView.DEF_TINT_ALPHA;
import static com.kannan.glazy.views.GlazyImageView.DEF_TINT_COLOR;
import static com.kannan.glazy.views.GlazyImageView.DEF_TITLE_TEXT;
import static com.kannan.glazy.views.GlazyImageView.DEF_TITLE_TEXT_COLOR;
import static com.kannan.glazy.views.GlazyImageView.DEF_TITLE_TEXT_SIZE_DP;
import static com.kannan.glazy.views.GlazyImageView.ImageCutType;

public class GlazyCard implements Serializable{

    private int mImageRes;

    private String mDescription;

    private String mTitle;
    private int mTitleColor;
    private int mTitleSizeDP;

    private String mSubTitle;
    private int mSubTitleColor;
    private int mSubTitleSizeDP;

    private int mTextMarginDP;
    private int mLineSpacingDP;

    private boolean mAutoTint;
    private int mTintColor;
    private int mTintAlpha;

    private ImageCutType mImageCutType;
    private int mImageCutCount;
    private int mImageCutHeightDP;

    private int mBackgroundColor;

    public GlazyCard() {

        mImageRes = DEF_IMAGE_RES;
        mDescription = "";

        mTitle = DEF_TITLE_TEXT;
        mTitleColor = DEF_TITLE_TEXT_COLOR;
        mTitleSizeDP = DEF_TITLE_TEXT_SIZE_DP;

        mSubTitle = DEF_SUB_TITLE_TEXT;
        mSubTitleColor = DEF_SUB_TITLE_TEXT_COLOR;
        mSubTitleSizeDP = DEF_SUB_TITLE_TEXT_SIZE_DP;

        mTextMarginDP = DEF_TEXT_MARGIN_DP;
        mLineSpacingDP = DEF_LINE_SPACING_DP;

        mAutoTint = DEF_AUTO_TINT_MODE;
        mTintColor = DEF_TINT_COLOR;
        mTintAlpha = DEF_TINT_ALPHA;

        mImageCutType = DEF_IMAGE_CUT_TYPE;
        mImageCutCount = DEF_CUT_COUNT;
        mImageCutHeightDP = DEF_CUT_HEIGHT;

        mBackgroundColor = Color.parseColor("#FFF1F1F1");
    }

    public GlazyCard(String title, String description, int imageRes) {
        this();
        mTitle = title;
        mDescription = description;
        mImageRes = imageRes;
    }

    /*
     * Setters:
     */

    public GlazyCard withImageRes(int imgRes) {
        mImageRes = imgRes;
        return this;
    }

    public GlazyCard withDescription(String desc) {
        mDescription = desc;
        return this;
    }

    public GlazyCard withTitle(String title) {
        mTitle = title;
        return this;
    }

    public GlazyCard withTitleColor(int color) {
        mTitleColor = color;
        return this;
    }

    public GlazyCard withTitleSizeDP(int size)
    {
        mTitleSizeDP = size;
        return this;
    }

    public GlazyCard withSubTitle(String subTitle) {
        mSubTitle = subTitle;
        return this;
    }

    public GlazyCard withSubTitleColor(int color) {
        mSubTitleColor = color;
        return this;
    }

    public GlazyCard withSubTitleSizeDP(int size) {
        mSubTitleSizeDP = size;
        return this;
    }

    public GlazyCard withTextMarginDP(int margin) {
        mTextMarginDP = margin;
        return this;
    }

    public GlazyCard withLineSpacingDP(int spacing) {
        mLineSpacingDP = spacing;
        return this;
    }

    public GlazyCard withAutoTint () {
        mAutoTint = true;
        return this;
    }

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

    public GlazyCard withImageCutCount(int count) {
        mImageCutCount = count;
        return this;
    }

    public GlazyCard withImageCutHeightDP(int height) {
        mImageCutHeightDP = height;
        return this;
    }

    public GlazyCard withBackgroundColor(int bgColor) {
        mBackgroundColor = bgColor;
        return this;
    }

    /*
     * Getters:
     */

    public int getImageRes() {
        return mImageRes;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getTitle() {
        return mTitle;
    }

    public int getTitleColor() {
        return mTitleColor;
    }

    public int getTitleSizeDP() {
        return mTitleSizeDP;
    }

    public String getSubTitle() {
        return mSubTitle;
    }

    public int getSubTitleColor() {
        return mSubTitleColor;
    }

    public int getSubTitleSizeDP() {
        return mSubTitleSizeDP;
    }

    public int getTextmatginDP() {
        return mTextMarginDP;
    }

    public int getLineSpacingDP() {
        return mLineSpacingDP;
    }

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

    public int getImageCutCount() {
        return mImageCutCount;
    }

    public int getImageCutHeightDP() {
        return mImageCutHeightDP;
    }

    public int getBackgroundColor() {
        return mBackgroundColor;
    }
}
