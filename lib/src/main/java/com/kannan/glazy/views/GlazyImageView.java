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

    private String TAG = "glazy"; //GlazyImageView.class.getSimpleName();

    private final int           DEF_IMAGE_RES               = -1;
    private final String        DEF_TITLE_TEXT              = "";
    private final int           DEF_TITLE_TEXT_COLOR        = Color.WHITE;
    private final int           DEF_TITLE_TEXT_SIZE_DP      = 20;
    private final String        DEF_SUB_TITLE_TEXT          = "";
    private final int           DEF_SUB_TITLE_TEXT_COLOR    = Color.GRAY;
    private final int           DEF_SUB_TITLE_TEXT_SIZE_DP  = 10;
    private final int           DEF_TEXT_MARGIN_DP          = 15;
    private final int           DEF_TINT_COLOR              = Color.BLACK;
    private final int           DEF_TINT_ALPHA              = 150;
    private final ImageCutType  DEF_IMAGE_CUT_TYPE          = ImageCutType.WAVE;
    private final int           DEF_CUT_COUNT               = 3;

    private Context mContext;

    private float mHeight;
    private float mWidth;

    private int mImageRes = -1;
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

    private ArrayList<Path> mPathsFull;
    private ArrayList<Path> mPathsScaled;
    private Matrix mScaleMatrix;

    private boolean mAutoTint;
    private int mTintColor;
    private int mTintAlpha;
    private Paint mTintPaint;

    private ImageCutType mCutType;
    private int mCutAngle;
    private int mCutCount;
    private int mCutHeight;
    private int mCutPhaseShift;

    private float mOpenFactor;

    public enum ImageCutType {
        LINE(0),
        ARC(1),
        WAVE(2);

        int mType;
        ImageCutType(int type) {
            mType = type;
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


        mTextMargin = Utils.dpToPx(DEF_TEXT_MARGIN_DP, mContext);
        mTitleTextColor = DEF_TITLE_TEXT_COLOR;
        mTitleText = DEF_TITLE_TEXT;
        mTitleTextSize = Utils.dpToPx(DEF_TITLE_TEXT_SIZE_DP, mContext);
        mSubTitleTextColor = DEF_SUB_TITLE_TEXT_COLOR;
        mSubTitleText = DEF_SUB_TITLE_TEXT;
        mSubTitleTextSize = Utils.dpToPx(DEF_SUB_TITLE_TEXT_SIZE_DP, mContext);

        mAutoTint = false;
        mTintColor = DEF_TINT_COLOR;
        mTintAlpha = DEF_TINT_ALPHA;

        mCutCount = DEF_CUT_COUNT;

        mCutAngle = 0;
        mCutHeight = 0;
        mOpenFactor = 0f;

        if(attrs != null){
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.GlazyImageView);
            try {
                if (array.hasValue(R.styleable.GlazyImageView_src))
                    mImageRes = array.getResourceId(R.styleable.GlazyImageView_src, DEF_IMAGE_RES);
                if (array.hasValue(R.styleable.GlazyImageView_cutType))
                     mCutType = array.getInteger(R.styleable.GlazyImageView_cutType, DEF_IMAGE_CUT_TYPE);
                if (array.hasValue(R.styleable.GlazyImageView_cutHeight))
                     mCutHeight = array.getDimensionPixelSize(R.styleable.GlazyImageView_cutHeight, mCutHeight);
                if (array.hasValue(R.styleable.GlazyImageView_cutCount))
                     mCutCount = array.getInteger(R.styleable.GlazyImageView_cutCount, DEF_CUT_COUNT);
                if (array.hasValue(R.styleable.GlazyImageView_autoTint))
                     mAutoTint = array.getBoolean(R.styleable.GlazyImageView_autoTint, false);
                if (array.hasValue(R.styleable.GlazyImageView_tintColor))
                     mTintColor= array.getColor(R.styleable.GlazyImageView_tintColor, DEF_TINT_COLOR);
                if (array.hasValue(R.styleable.GlazyImageView_tintAlpha))
                     mTintAlpha = array.getInteger(R.styleable.GlazyImageView_tintAlpha, DEF_TINT_ALPHA);
                if (array.hasValue(R.styleable.GlazyImageView_titleText))
                     mTitleText = array.getString(R.styleable.GlazyImageView_titleText);
                if (array.hasValue(R.styleable.GlazyImageView_titleTextColor))
                     mTitleTextColor = array.getColor(R.styleable.GlazyImageView_titleTextColor, DEF_TITLE_TEXT_COLOR);
                if (array.hasValue(R.styleable.GlazyImageView_titleTextSize))
                     mTitleTextSize= array.getDimensionPixelSize(R.styleable.GlazyImageView_titleTextSize, Utils.dpToPx(DEF_TITLE_TEXT_SIZE_DP, mContext);
                if (array.hasValue(R.styleable.GlazyImageView_subTitleText))
                    mSubTitleText = array.getString(R.styleable.GlazyImageView_subTitleText);
                if (array.hasValue(R.styleable.GlazyImageView_subTitleTextColor))
                    mSubTitleTextColor = array.getColor(R.styleable.GlazyImageView_subTitleTextColor, DEF_SUB_TITLE_TEXT_COLOR);
                if (array.hasValue(R.styleable.GlazyImageView_subTitleTextSize))
                    mSubTitleTextSize= array.getDimensionPixelSize(R.styleable.GlazyImageView_subTitleTextSize, Utils.dpToPx(DEF_SUB_TITLE_TEXT_SIZE_DP, mContext);
                if (array.hasValue(R.styleable.GlazyImageView_textMargin))
                     mTextMargin = array.getDimensionPixelSize(R.styleable.GlazyImageView_textMargin, Utils.dpToPx(DEF_TEXT_MARGIN_DP, mContext);
                if (array.hasValue(R.styleable.GlazyImageView_lineSpacing))
                     mLineSpacing = array.getDimensionPixelSize(R.styleable.GlazyImageView_lineSpacing, );
                if (array.hasValue(R.styleable.GlazyImageView_openFactor, ))
                     mOpenFactor = array.getInteger(R.styleable.GlazyImageView_);
            } finally {
                array.recycle();
            }
        }


        mBitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBitmapPaint.setStyle(Paint.Style.FILL);

        mGradientPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mGradientPaint.setStyle(Paint.Style.FILL);

        mTintPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTintPaint.setStyle(Paint.Style.FILL);

        mTitleTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTitleTextPaint.setTextSize(mTitleTextSize);
        mTitleTextPaint.setTextAlign(Paint.Align.LEFT);
        mTitleTextPaint.setTypeface(Typeface.create("Helvetica", Typeface.BOLD));
        mTitleTextPaint.setStyle(Paint.Style.FILL);
        mTitleTextPaint.setColor(mTitleTextColor);

        mSubTitleTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mSubTitleTextPaint.setTextSize(mSubTitleTextSize);
        mSubTitleTextPaint.setTextAlign(Paint.Align.LEFT);
        mSubTitleTextPaint.setTypeface(Typeface.create("Helvetica", Typeface.BOLD));
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
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

        Log.i(TAG, "onMeasure");
        prepareDrawingElements();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.i(TAG, "layout");
//        update(mOpenFactor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mImageRes != -1 && mImageBitmap != null) {
            mTintPaint.setAlpha(mTintAlpha);
            for (int i = 1; i < mPathsScaled.size(); i++) {
                canvas.drawPath(mPathsScaled.get(i), mTintPaint);
            }
            if (mPathsScaled.size() > 0) {
                canvas.clipPath(mPathsScaled.get(0));
                canvas.drawBitmap(mImageBitmap, null, mBitmapScaleRect, mBitmapPaint);
//                canvas.clipRect(mBitmapScaleRect);
                canvas.drawPath(mPathsScaled.get(0), mGradientPaint);
                mTintPaint.setAlpha((int) (255 * (1-mOpenFactor)));
                canvas.drawPath(mPathsScaled.get(0), mTintPaint);
//                canvas.drawPaint(mGradientPaint);
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
//        if (Math.abs(mOpenFactor - factor) < 0.001) {
//            return;
//        }
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
                (mCutHeight * mOpenFactor + mSubTitleTextSize));

        mTitleTextX = mTextMargin;
        mTitleTextY = mSubTitleTextY - 2 * mSubTitleTextSize;
//        mTitleTextY = (int) (bound.height() - ((bound.height() / 5) * (1 )));

        postInvalidate();
    }

    private void prepareDrawingElements() {
        if (mWidth != 0 && mHeight != 0) {
            prepareBitmap();
            createPaths();
            prepareTints();
            prepareText();
        }
    }

    private void createPaths() {
        Log.i(TAG, "createPaths" + mWidth + " " + mHeight + " " + mOpenFactor);
        mPathsFull.clear();
        float angleIncrement = mCutAngle / ((float) 1.5 * mCutCount);
        float cutHeightIncrement = mCutHeight / ((float) 1.5 * mCutCount);

        switch (mCutType) {
            case LINE:
                for (int i = 0; i < mCutCount; i += 1) {
                    mPathsFull.add(
                            Utils.getLinePath(
                                    mWidth,
                                    mHeight,
                                    mCutAngle - angleIncrement * i
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
                                    mCutPhaseShift
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
                                    mCutPhaseShift
                            )
                    );
                }
                break;
            default:
                Log.e(TAG, "Unknown ImageCutType enum : " + mCutType + "\n"
                + "switching to default value : " + "");
        }
//            RectF bound = new RectF();
//            mPathsFull.get(0).computeBounds(bound, true);
//            Log.i("app", bound.toString());

    }

    private void prepareTints() {
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
        if (mTitleText != null && !mTitleText.trim().equals("")) {
            float availableSpace = (mWidth - 2 * mTextMargin) * 0.75f;
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

    private void prepareBitmap() {
        Log.i(TAG, "prepare bitmap");
        if (mImageRes != -1) {
            try {
                mImageBitmap = decodeSampledBitmapFromResource(mContext.getResources(), mImageRes, mWidth, mHeight);
            } catch(OutOfMemoryError e) {
                mImageBitmap = null;
                Log.e(TAG, "Image too large to load: \n" + e.getMessage());
            } catch(Exception e) {
                Log.e(TAG, "Could not load bitmap image : \n" + e.getMessage());
            }

//            prepare scaleRect for drawing bitmap
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
                        Log.i(TAG, "Could not pick color from bitmap, using default tint : " + "");
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
                Log.i(TAG, "Could not pick color from bitmap, using default tint : " + "");
            }
        }
    }

    public void setImageRes(int imgRes) {
        mImageRes = imgRes;
//        postInvalidate();
    }

    public void setCutType(ImageCutType cutType) {
        mCutType = cutType;
    }

    public void setCutAngle(int angle) {
        angle = Math.abs(angle) % 180;
        if ((angle >= 0 && angle <= 45) || (angle >= 135 && angle <= 180)) {
            mCutAngle = angle;
        } else {
            mCutAngle = 15;
        }
    }

    public void setCutCount(int count) {
        count = Math.abs(count);
        if (count <= 0) count = 0;
        if (count > 4)  count = 4;
        mCutCount = count;
    }

    public void setCutHeight(int height) {
        mCutHeight = height;
    }

    public void setCutPhaseShift(int phaseShift) {
        mCutPhaseShift = phaseShift;
    }

    public void setTintColor(int tintColor) {
        mTintColor = tintColor;
        mAutoTint = false;
    }

    public void setTitleText(String title) {
        mTitleText = title;
    }

    public void setTitleTextColor(int color) {
        mTitleTextColor = color;
    }

    public void setTitleTextSize(int size) {
        mTitleTextSize = size;
    }

    public void setSubTitleText(String title) {
        mSubTitleText = title;
    }

    public void setSubTitleTextColor(int color) {
        mSubTitleTextColor = color;
    }

    public void setSubTitleTextSize(int size) {
        mSubTitleTextSize = size;
    }

}
