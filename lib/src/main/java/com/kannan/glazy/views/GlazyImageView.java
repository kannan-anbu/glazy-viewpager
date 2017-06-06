package com.kannan.glazy.views;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.media.ThumbnailUtils;
import android.support.v7.graphics.Palette;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.kannan.glazy.R;
import com.kannan.glazy.Utils;

import java.util.ArrayList;


public class GlazyImageView extends View {

    private String TAG = GlazyImageView.class.getSimpleName();

    public static final int           DEF_IMAGE_RES               = -1;
    public static final String        DEF_TITLE_TEXT              = "";
    public static final int           DEF_TITLE_TEXT_COLOR        = Color.WHITE;
    public static final int           DEF_TITLE_TEXT_SIZE_DP      = 22;
    public static final String        DEF_SUB_TITLE_TEXT          = "";
    public static final int           DEF_SUB_TITLE_TEXT_COLOR    = Color.GRAY;
    public static final int           DEF_SUB_TITLE_TEXT_SIZE_DP  = 15;
    public static final int           DEF_TEXT_MARGIN_DP          = 15;
    public static final int           DEF_LINE_SPACING_DP         = DEF_SUB_TITLE_TEXT_SIZE_DP / 2;
    public static final boolean       DEF_AUTO_TINT_MODE          = false;
    public static final int           DEF_TINT_COLOR              = Color.BLACK;
    public static final int           DEF_TINT_ALPHA              = 125;
    public static final ImageCutType  DEF_IMAGE_CUT_TYPE          = ImageCutType.WAVE;
    public static final int           DEF_CUT_COUNT               = 3;
    public static final int           DEF_CUT_HEIGHT              = 0;
    public static final float         DEF_OPEN_FACTOR             = 1.0f;

    private Context mContext;

    private float mHeight;
    private float mWidth;

    private int mImageRes;
    private Bitmap mImageBitmap;
    private Paint mBitmapPaint;
    private RectF mBitmapScaleRectOriginal;
    private RectF mBitmapScaleRect;

    private Shader mGradientShader;
    private Paint mGradientPaint;

    private String mTitleText;
    private int mTitleTextColor;
    private int mTitleTextSize;
    private TextPaint mTitleTextPaint;
    private int mTitleTextX;
    private int mTitleTextY;

    private String mSubTitleText;
    private int mSubTitleTextColor;
    private int mSubTitleTextSize;
    private TextPaint mSubTitleTextPaint;
    private int mSubTitleTextX;
    private int mSubTitleTextY;
    private int mTextMargin;
    private int mLineSpacing;

    private ArrayList<Path> mPathsFull;
    private ArrayList<Path> mPathsScaled;
    private Matrix mScaleMatrix;

    private boolean mAutoTint;
    private int mTintColor;
    private int mTintAlpha;
    private Paint mTintPaint;

    private ImageCutType mCutType;
    private int mCutCount;
    private int mCutHeight;

    private float mOpenFactor;

    public enum ImageCutType {
        LINE_POSITIVE(0),
        LINE_NEGATIVE(1),
        ARC(2),
        WAVE(3);

        final int mType;

        ImageCutType(int type) {
            mType = type;
        }
        static ImageCutType fromId(int type) {
            for (ImageCutType cutType : values()) {
                if (cutType.mType == type) {
                    return cutType;
                }
            }
            return ImageCutType.WAVE;
        }
    }

    public GlazyImageView(Context context) {
        super(context);
        init(context, null);
    }

    public GlazyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public GlazyImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        mContext = context;

        setLayerType(LAYER_TYPE_NONE, null);

        mTextMargin = Utils.dpToPx(mContext, DEF_TEXT_MARGIN_DP);
        mTitleTextColor = DEF_TITLE_TEXT_COLOR;
        mTitleText = DEF_TITLE_TEXT;
        mTitleTextSize = Utils.dpToPx(mContext, DEF_TITLE_TEXT_SIZE_DP);
        mSubTitleTextColor = DEF_SUB_TITLE_TEXT_COLOR;
        mSubTitleText = DEF_SUB_TITLE_TEXT;
        mSubTitleTextSize = Utils.dpToPx(mContext, DEF_SUB_TITLE_TEXT_SIZE_DP);
        mLineSpacing = Utils.dpToPx(mContext, DEF_LINE_SPACING_DP);

        mAutoTint = false;
        mTintColor = DEF_TINT_COLOR;
        mTintAlpha = DEF_TINT_ALPHA;

        mCutCount = DEF_CUT_COUNT;
        mCutHeight = DEF_CUT_HEIGHT;
        mOpenFactor = 0f;

        if(attrs != null){
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.GlazyImageView);
            try {
                if (array.hasValue(R.styleable.GlazyImageView_src))
                    mImageRes = array.getResourceId(
                            R.styleable.GlazyImageView_src,
                            DEF_IMAGE_RES
                    );
                if (array.hasValue(R.styleable.GlazyImageView_cutType))
                    mCutType = ImageCutType.fromId(
                            array.getInteger(
                                    R.styleable.GlazyImageView_cutType,
                                    DEF_IMAGE_CUT_TYPE.mType
                            )
                    );
                if (array.hasValue(R.styleable.GlazyImageView_cutHeight))
                    mCutHeight = array.getDimensionPixelSize(
                            R.styleable.GlazyImageView_cutHeight,
                            mCutHeight
                    );
                if (array.hasValue(R.styleable.GlazyImageView_cutCount))
                    mCutCount = array.getInteger(
                            R.styleable.GlazyImageView_cutCount,
                            DEF_CUT_COUNT
                    );
                if (array.hasValue(R.styleable.GlazyImageView_autoTint))
                    mAutoTint = array.getBoolean(
                            R.styleable.GlazyImageView_autoTint,
                            false
                    );
                if (array.hasValue(R.styleable.GlazyImageView_tintColor))
                    mTintColor= array.getColor(
                            R.styleable.GlazyImageView_tintColor,
                            DEF_TINT_COLOR
                    );
                if (array.hasValue(R.styleable.GlazyImageView_tintAlpha))
                    mTintAlpha = array.getInteger(
                            R.styleable.GlazyImageView_tintAlpha,
                            DEF_TINT_ALPHA
                    );
                if (array.hasValue(R.styleable.GlazyImageView_titleText))
                    mTitleText = array.getText(
                            R.styleable.GlazyImageView_titleText
                    ).toString();
                if (array.hasValue(R.styleable.GlazyImageView_titleTextColor))
                    mTitleTextColor = array.getColor(
                            R.styleable.GlazyImageView_titleTextColor,
                            DEF_TITLE_TEXT_COLOR
                    );
                if (array.hasValue(R.styleable.GlazyImageView_titleTextSize))
                    mTitleTextSize= array.getDimensionPixelSize(
                            R.styleable.GlazyImageView_titleTextSize,
                            mTitleTextSize
                    );
                if (array.hasValue(R.styleable.GlazyImageView_subTitleText))
                    mSubTitleText = array.getText(
                            R.styleable.GlazyImageView_subTitleText
                    ).toString();
                if (array.hasValue(R.styleable.GlazyImageView_subTitleTextColor))
                    mSubTitleTextColor = array.getColor(
                            R.styleable.GlazyImageView_subTitleTextColor,
                            DEF_SUB_TITLE_TEXT_COLOR
                    );
                if (array.hasValue(R.styleable.GlazyImageView_subTitleTextSize))
                    mSubTitleTextSize= array.getDimensionPixelSize(
                            R.styleable.GlazyImageView_subTitleTextSize,
                            mSubTitleTextSize
                    );
                if (array.hasValue(R.styleable.GlazyImageView_textMargin))
                    mTextMargin = array.getDimensionPixelSize(
                            R.styleable.GlazyImageView_textMargin,
                            mTextMargin
                    );
                if (array.hasValue(R.styleable.GlazyImageView_lineSpacing))
                    mLineSpacing = array.getDimensionPixelSize(
                            R.styleable.GlazyImageView_lineSpacing,
                            mLineSpacing
                    );
                if (array.hasValue(R.styleable.GlazyImageView_openFactor))
                    mOpenFactor = array.getFloat(
                            R.styleable.GlazyImageView_openFactor,
                            DEF_OPEN_FACTOR
                    );
            } finally {
                array.recycle();
            }
        }


        mBitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBitmapPaint.setFilterBitmap(true);
        mBitmapPaint.setStyle(Paint.Style.FILL);

        mGradientPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mGradientPaint.setStyle(Paint.Style.FILL);

        mTintPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTintPaint.setStyle(Paint.Style.FILL);

        mTitleTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTitleTextPaint.setTextSize(mTitleTextSize);
        mTitleTextPaint.setTextAlign(Paint.Align.LEFT);
        mTitleTextPaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));
        mTitleTextPaint.setStyle(Paint.Style.FILL);
        mTitleTextPaint.setColor(mTitleTextColor);

        mSubTitleTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mSubTitleTextPaint.setTextSize(mSubTitleTextSize);
        mSubTitleTextPaint.setTextAlign(Paint.Align.LEFT);
        mSubTitleTextPaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
        mSubTitleTextPaint.setStyle(Paint.Style.FILL);
        mSubTitleTextPaint.setColor(mSubTitleTextColor);

        mBitmapScaleRectOriginal = new RectF();
        mPathsFull = new ArrayList<>();
        mPathsScaled = new ArrayList<>();
        mScaleMatrix = new Matrix();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        Log.i(TAG, "onMeasure");
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        prepareDrawingElements();
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        Log.i(TAG, "onDraw");
        super.onDraw(canvas);
        if (mImageRes != -1 && mImageBitmap != null) {
            mTintPaint.setAlpha(mTintAlpha);
            for (int i = 1; i < mPathsScaled.size(); i++) {
                canvas.drawPath(mPathsScaled.get(i), mTintPaint);
            }
            if (mPathsScaled.size() > 0) {
                canvas.clipPath(mPathsScaled.get(0));
                canvas.drawBitmap(mImageBitmap, null, mBitmapScaleRect, mBitmapPaint);
                canvas.drawPaint(mGradientPaint);
                mTintPaint.setAlpha((int) (255 * (1-mOpenFactor)));
                canvas.drawPath(mPathsScaled.get(0), mTintPaint);
            }
            float f = mOpenFactor - 0.5f;
            if (f > 0) {
                mTitleTextPaint.setAlpha((int) (255 * (f * 2)));
                mSubTitleTextPaint.setAlpha((int) (255 * (f * 2)));
                canvas.drawText(mTitleText, mTitleTextX, mTitleTextY, mTitleTextPaint);
                canvas.drawText(mSubTitleText, mSubTitleTextX, mSubTitleTextY, mSubTitleTextPaint);
            }
        }
    }

    public void update(float factor) {
//        Log.i(TAG, "update");
        mOpenFactor = factor;
        mScaleMatrix.setScale(1f, factor);
        mPathsScaled.clear();
        for (int i = 0; i < mPathsFull.size(); i++) {
            Path path = new Path();
            mPathsFull.get(i).transform(mScaleMatrix, path);
            mPathsScaled.add(i, path);
        }

        mBitmapScaleRect = new RectF(mBitmapScaleRectOriginal);
        mBitmapScaleRect.offsetTo(
                mBitmapScaleRectOriginal.left,
                mBitmapScaleRectOriginal.top - (mBitmapScaleRectOriginal.height() / 2 * (1 - mOpenFactor))
        );

        RectF bound = new RectF();
        mPathsScaled.get(0).computeBounds(bound, true);

        mSubTitleTextX = mTextMargin;
        mSubTitleTextY = (int) (bound.height() -
                (mCutHeight * 0.7));
        mTitleTextX = mTextMargin;
        mTitleTextY = (mSubTitleTextY - (mSubTitleTextSize + mLineSpacing));

        invalidate();
    }

    private void prepareDrawingElements() {
        if (mWidth != 0 && mHeight != 0) {
            prepareBitmap();
            preparePaths();
            prepareTints();
            prepareText();
        }
    }

    private void preparePaths() {
//        Log.i(TAG, "preparePaths" + mWidth + " " + mHeight + " " + mOpenFactor);
        if (mWidth != 0 && mHeight != 0) {
            mPathsFull.clear();
            float cutHeightIncrement = mCutHeight / ((float) 1.5 * mCutCount);

            switch (mCutType) {
                case LINE_POSITIVE:
                case LINE_NEGATIVE:
                    for (int i = 0; i < mCutCount; i += 1) {
                        mPathsFull.add(
                                Utils.getLinePath(
                                        mWidth,
                                        mHeight,
                                        mCutHeight - cutHeightIncrement * i,
                                        mCutType.mType != 0
                                )
                        );
                    }
                    break;
                case ARC:
                    for (int i = 0; i < mCutCount; i += 1) {
                        mPathsFull.add(
                                Utils.getWavePath(
                                        mWidth,
                                        mHeight,
                                        mCutHeight - cutHeightIncrement * i,
                                        0.05f,
                                        0
                                )
                        );
                    }
                    break;
                case WAVE:
                    for (int i = 0; i < mCutCount; i += 1) {
                        mPathsFull.add(
                                Utils.getWavePath(
                                        mWidth,
                                        mHeight,
                                        (mCutHeight - cutHeightIncrement * i) / 2,
                                        0.1f,
                                        0
                                )
                        );
                    }
                    break;
                default:
                    Log.e(TAG, "Unknown ImageCutType enum : " + mCutType + "\n"
                            + "switching to default value : " + DEF_IMAGE_CUT_TYPE);
                    mCutType = DEF_IMAGE_CUT_TYPE;
                    preparePaths();
            }
        }
    }

    private void prepareTints() {
//        Log.i(TAG, "prepareTint");
        if (mAutoTint) {
            pickColorFromBitmapAsync();
        }
        mGradientShader = Utils.getLinearGradient(
                mWidth, mHeight, Color.parseColor("#00000000"), mTintColor);
        mGradientPaint.setShader(mGradientShader);
        mGradientPaint.setAlpha(255);
        mTintPaint.setColor(mTintColor);
        mTintPaint.setAlpha(255);
    }

    private void prepareText() {
        if (mWidth != 0 && mHeight != 0) {
//            Log.i(TAG, "prepareText" + mTitleText + mSubTitleText);
            if (mTitleText != null && !mTitleText.trim().equals("")) {
                float availableSpace = (mWidth - 2 * mTextMargin) * 0.9f;
                mTitleText = TextUtils.ellipsize(
                        mTitleText,
                        mTitleTextPaint,
                        availableSpace,
                        TextUtils.TruncateAt.END
                ).toString();
            }
            if (mSubTitleText != null && !mSubTitleText.trim().equals("")) {
                float availableSpace = (mWidth - 2 * mTextMargin) * 0.5f;
                mSubTitleText = TextUtils.ellipsize(
                        mSubTitleText,
                        mSubTitleTextPaint,
                        availableSpace,
                        TextUtils.TruncateAt.END
                ).toString();
            }
        }
    }

    private void prepareBitmap() {
//        Log.i(TAG, "prepare bitmap");
        if (mImageRes != -1 && mWidth != 0 && mHeight != 0) {
            try {
                mImageBitmap = decodeSampledBitmapFromResource(mContext.getResources(), mImageRes, mWidth, mHeight);
            } catch(OutOfMemoryError e) {
                mImageBitmap = null;
                Log.e(TAG, "Image too large to load: \n" + e.getMessage());
                return;
            } catch(Exception e) {
                mImageBitmap = null;
                Log.e(TAG, "Could not load bitmap image : \n" + e.getMessage());
                return;
            }

            /*
             * prepare scaleRect for drawing bitmap
             */
            float scaleRatio = 1;
            if (mWidth != mImageBitmap.getWidth()) {
                scaleRatio = mWidth / mImageBitmap.getWidth();
            }
            if (scaleRatio * mImageBitmap.getHeight() < mHeight) {
                scaleRatio = mHeight / mImageBitmap.getHeight();
            }
            float requiredHeight = mImageBitmap.getHeight() * scaleRatio;
            float requiredWidth = mImageBitmap.getWidth() * scaleRatio;
            int y = (int) ((requiredHeight / 2) - (mHeight / 2));
            int x = (int) ((requiredWidth / 2) - (mWidth / 2));
            if (x > 0) x = -x;
            if (y > 0) y = -y;

            mBitmapScaleRectOriginal.set(x, y, x + requiredWidth, y + requiredHeight);
        }
    }

    public static Bitmap decodeSampledBitmapFromResource(
            Resources res, int resId, float reqWidth, float reqHeight) {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, float reqWidth, float reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    private Bitmap squareCropBitmap(Bitmap bitmap) {
        int sideDimension = Math.min(bitmap.getWidth(), bitmap.getHeight());
        return ThumbnailUtils.extractThumbnail(
                bitmap, sideDimension, sideDimension, ThumbnailUtils.OPTIONS_RECYCLE_INPUT
        );
    }

    private void pickColorFromBitmapAsync() {
        if (mImageBitmap != null) {
            Palette.from(mImageBitmap).generate(new Palette.PaletteAsyncListener() {
                @Override
                public void onGenerated(Palette palette) {
                    int defaultColor = 0x000000;
                    if (palette.getDominantColor(defaultColor) != 0) {
                        mTintColor = Math.abs(palette.getDominantColor(defaultColor));
                    } else if (palette.getDarkVibrantColor(defaultColor) != 0) {
                        mTintColor = Math.abs(palette.getDarkVibrantColor(defaultColor));
                    } else if (palette.getDarkMutedColor(defaultColor) != 0) {
                        mTintColor = Math.abs(palette.getDarkMutedColor(defaultColor));
                    } else if (palette.getMutedColor(defaultColor) != 0) {
                        mTintColor = Math.abs(palette.getMutedColor(defaultColor));
                    } else {
                        Log.i(TAG, "Could not pick color from bitmap, using default tint : "
                                + DEF_TINT_COLOR
                        );
                    }
                }
            });
        }
    }

    private void pickColorFromBitmap() {
        if (mImageBitmap != null) {
            Palette palette = Palette.from(mImageBitmap).generate();

            int defaultColor = 0x000000;
            if (palette.getDominantColor(defaultColor) != 0) {
                mTintColor = Math.abs(palette.getDominantColor(defaultColor));
            } else if (palette.getDarkVibrantColor(defaultColor) != 0) {
                mTintColor = Math.abs(palette.getDarkVibrantColor(defaultColor));
            } else if (palette.getDarkMutedColor(defaultColor) != 0) {
                mTintColor = Math.abs(palette.getDarkMutedColor(defaultColor));
            } else if (palette.getMutedColor(defaultColor) != 0) {
                mTintColor = Math.abs(palette.getMutedColor(defaultColor));
            } else {
                Log.i(TAG, "Could not pick color from bitmap, using default tint : "
                        + DEF_TINT_COLOR
                );
            }
        }
    }


    /*
     * Setters:
     */

    public void setImageRes(int imgRes) {
        mImageRes = imgRes;
        prepareBitmap();
        prepareTints();
        invalidate();
    }

    public void setTitleText(String title) {
        if (mTitleText.equals(title))
            return;

        mTitleText = title;
        prepareText();
        invalidate();
    }

    public void setTitleTextColor(int color) {
        if (mTitleTextColor == color)
            return;

        mTitleTextColor = color;
        mTitleTextPaint.setColor(mTitleTextColor);
        invalidate();
    }

    public void setTitleTextSize(int size) {
        if (mTitleTextSize == size)
            return;

        mTitleTextSize = size;
        mTitleTextPaint.setTextSize(mTitleTextSize);
        invalidate();
    }

    public void setSubTitleText(String title) {
        if (mSubTitleText.equals(title))
            return;

        mSubTitleText = title;
        prepareText();
        invalidate();
    }

    public void setSubTitleTextColor(int color) {
        if (mSubTitleTextColor == color)
            return;

        mSubTitleTextColor = color;
        mSubTitleTextPaint.setColor(mSubTitleTextColor);
        invalidate();
    }

    public void setSubTitleTextSize(int size) {
        if (mSubTitleTextSize == size)
            return;

        mSubTitleTextSize = size;
        mSubTitleTextPaint.setTextSize(mSubTitleTextSize);
        invalidate();
    }

    public void setTextMargin(int textMargin) {
        if (mTextMargin == textMargin)
            return;

        mTextMargin = textMargin;
        update(mOpenFactor);
    }

    public void setLineSpacing(int spacing) {
        if(mLineSpacing == spacing)
            return;

        mLineSpacing = spacing;
        update(mOpenFactor);
    }

    public void setAutoTint(boolean flag) {
        if (mAutoTint == flag)
            return;

        mAutoTint = flag;
        prepareTints();
        invalidate();
    }

    public void setTintColor(int tintColor) {
        if (mTintColor == tintColor)
            return;

        mTintColor = tintColor;
        mAutoTint = false;
        prepareTints();
        invalidate();
    }

    public void setTintAlpha(int alpha) {
        if (mTintAlpha == alpha)
            return;

        mTintAlpha = alpha;
        prepareTints();
        invalidate();
    }

    public void setCutType(ImageCutType cutType) {
        if (mCutType == cutType)
            return;

        mCutType = cutType;
        preparePaths();
        invalidate();
    }

    public void setCutCount(int count) {
        if (mCutCount == count)
            return;

        count = Math.abs(count);
        if (count <= 0) count = 0;
        if (count > 4)  count = 4;
        mCutCount = count;

        preparePaths();
        invalidate();
    }

    public void setCutHeight(int height) {
        if (mCutHeight == height)
            return;

        mCutHeight = height;
        preparePaths();
        invalidate();
    }

    /*
     * Getters:
     */


    public int getImageRes() {
        return mImageRes;
    }

    public Bitmap getImageBitmap() {
        return mImageBitmap;
    }

    public String getTitleText() {
        return mTitleText;
    }

    public int getTitleTextColor() {
        return mTitleTextColor;
    }

    public int getTitleTextSize() {
        return mTitleTextSize;
    }

    public String getSubTitleText() {
        return mSubTitleText;
    }

    public int getSubTitleTextColor() {
        return mSubTitleTextColor;
    }

    public int getSubTitleTextSize() {
        return mSubTitleTextSize;
    }

    public int getTextMargin() {
        return mTextMargin;
    }

    public int getLineSpacing() {
        return mLineSpacing;
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

    public Paint getTintPaint() {
        return mTintPaint;
    }

    public ImageCutType getCutType() {
        return mCutType;
    }

    public int getCutCount() {
        return mCutCount;
    }

    public int getCutHeight() {
        return mCutHeight;
    }

    public float getOpenFactor() {
        return mOpenFactor;
    }

}
