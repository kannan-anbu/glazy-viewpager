package com.kannan.glazy.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.media.ThumbnailUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.kannan.glazy.Utils;


public class GlazyImageView extends View {

    private Context mContext;

    private int mImageRes = -1;
    private Bitmap mImageBitmap;
    private Paint mBitmapPaint;
    private Shader mGradientShader;
    private Paint mGradientPaint;
    private Paint mCutPaint;
    private Path[] mPaths;

    private float mHeight;
    private float mWidth;

    private ImageCutType mCutType;
    private int mCutangle;
    private int mCutCount;
    private int mCutHeight;
    private int mCutPhaseShift;
    private float mCloseFactor;

    private RectF mBitmapScaleRect;

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

//        if(attrs != null){
//            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.GlazyImageView);
//            try {
//                if (array.hasValue(R.styleable.GlazyImageView_src))
//                    mImageRes = array.getResourceId(R.styleable.GlazyImageView_src, -1);
//                if (array.hasValue(R.styleable.GlazyImageView_coverHeight))
//                    mCoverHeight = array.getDimensionPixelSize(R.styleable.GlazyImageView_coverHeight, 100);
//                if (array.hasValue(R.styleable.GlazyImageView_slopeHeight))
//                    mSlopeHeight = array.getDimensionPixelSize(R.styleable.GlazyImageView_slopeHeight, 100);
//                if (array.hasValue(R.styleable.GlazyImageView_tintColor))
//                    mCoverTint = array.getColor(R.styleable.GlazyImageView_tintColor, Color.YELLOW);
//            } finally {
//                array.recycle();
//            }
//        }

        setLayerType(LAYER_TYPE_SOFTWARE, null);

        mBitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBitmapPaint.setStyle(Paint.Style.FILL);

        mGradientPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mGradientPaint.setStyle(Paint.Style.FILL);

        mCutPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCutPaint.setStyle(Paint.Style.FILL);

        mBitmapScaleRect = new RectF();
        mPaths = new Path[1];

        mCutCount = 3;
        mCloseFactor = 0f;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

        if (mImageRes != -1) {
            prepareBitmap();
            update(mCloseFactor);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawPath(mCoverPath, mCoverPaint);
        if (mImageRes != -1) {
//             mCoverPaint.setShader(new BitmapShader(mImageBitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
//
//            Path path = Utils.getPath(mWidth, mHeight - 25, mCutType, mCutangle - 6, mCloseFactor);
////            canvas.drawPath(path, mCutPaint);
//            path = Utils.getPath(mWidth, mHeight, mCutType, mCutangle - 1.5f, mCloseFactor);
//            canvas.drawPath(path, mCutPaint);
//            path = Utils.getPath(mWidth, mHeight, mCutType, mCutangle - 3f, mCloseFactor);
//            canvas.drawPath(path, mCutPaint);

//            canvas.drawBitmap(mImageBitmap, 0, 0, mBitmapPaint);
//
//            mPath.
            canvas.clipRect(mBitmapScaleRect);
            try {
                canvas.clipPath(mPaths[0]);
            } catch (Exception e) {}
            canvas.drawBitmap(mImageBitmap, null, mBitmapScaleRect, mBitmapPaint);


//            path = Utils.getPath(mWidth, mHeight, mCutType, mCutangle + 1.5f, mCloseFactor);
//            canvas.clipPath(path);
//            canvas.drawPaint(mGradientPaint);
////            canvas.drawPath(mPath, mGradientPaint);
////            canvas.drawPath(path, mCutPaint);
//            path = Utils.getPath(mWidth, mHeight, mCutType, mCutangle + 3f, mCloseFactor);
//            canvas.clipPath(path);
//            canvas.drawPaint(mGradientPaint);
////            canvas.drawPath(mPath, mGradientPaint);
////            canvas.drawPath(path, mCutPaint);

            try {
                canvas.drawPath(mPaths[0], mGradientPaint);
            } catch (Exception e) {}



//        canvas.drawPath(mCoverPath, mCoverPaint);
        }
    }


    public void update(float factor) {
        if (mCloseFactor != factor) {
            mCloseFactor = factor;
            mPaths = new Path[mCutCount];
            Toast.makeText(mContext, "" + mPaths.length + mCutCount, Toast.LENGTH_SHORT).show();
            float angleIncrement = mCutangle / (float) mCutCount;
            float cutHeightIncrement = mCutHeight / (float) mCutCount;
            for (int i = 0; i < mCutCount; i += 1) {
                mPaths[i] = Utils.getPath(
                        mWidth, mHeight, mCutType, mCutangle - angleIncrement * i,
                        mCutHeight - cutHeightIncrement * i, mCutPhaseShift, mCloseFactor);
            }
//            Toast.makeText(mContext, "" + mPaths.length, Toast.LENGTH_SHORT).show();
//            mPath = Utils.getPath(mWidth, mHeight, mCutType, mCutangle, mCutHeight, mCutPhaseShift, mCloseFactor);
            postInvalidate();
        }
    }

    private void prepareBitmap() {
        if (mImageRes != -1) {
//            Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), mImageRes);
            mImageBitmap = decodeSampledBitmapFromResource(mContext.getResources(), mImageRes, mWidth, mHeight);
            if (mImageBitmap == null ) {
                Toast.makeText(getContext(), "null bitmap", Toast.LENGTH_SHORT).show();
            }
            Log.i("app", "res " + mImageBitmap.getWidth() + " " + mImageBitmap.getHeight());


            float ratioChange = 1;
            if (mWidth != mImageBitmap.getWidth()) {
                ratioChange = mWidth / mImageBitmap.getWidth();
            }
            if (ratioChange * mImageBitmap.getHeight() < mHeight) {
                ratioChange = mHeight / mImageBitmap.getHeight();
            }
            float requiredHeight = mImageBitmap.getHeight() * ratioChange;
            float requiredWidth = mImageBitmap.getWidth() * ratioChange;
            int y = (int) ((requiredHeight / 2) - (mHeight / 2));
            int x = (int) ((requiredWidth / 2) - (mWidth / 2));
            if (x > 0) x = -x;
            if (y > 0) y = -y;

            mBitmapScaleRect.set(x, y, x + requiredWidth, y + requiredHeight);
            Log.i("app", requiredWidth + " " + requiredHeight + "\n" + mBitmapScaleRect.toString());


            mGradientShader = Utils.getLinearGradient(
                    mWidth, mHeight, Color.parseColor("#00000000"), Color.parseColor("#9a000000"));
            mGradientPaint.setShader(mGradientShader);

            mCutPaint.setColor(Color.parseColor("#55000000"));
//
//            bitmap = squareCropBitmap(bitmap);
//            Log.i("app", "crop " + bitmap.getWidth() + " " + bitmap.getHeight());
//
//            int scaleDimension = Math.max(mWidth, mHeight);
//            bitmap = Bitmap.createScaledBitmap(bitmap, scaleDimension, scaleDimension, false);
//            Log.i("app", "scale " + bitmap.getWidth() + " " + bitmap.getHeight());
//
//            int x, y;
//
//            if (mWidth < bitmap.getWidth()) {
//                x = (bitmap.getWidth() - mWidth) / 2;
//                y = 0;
//            } else {
//                x = 0;
//                y = (bitmap.getHeight() - mHeight) / 2;
//            }
//            mImageBitmap = Bitmap.createBitmap(bitmap, x, y, mWidth, mHeight);
//            Log.i("app", "img " + mImageBitmap.getWidth() + " " + mImageBitmap.getHeight() + " "
//            + mWidth + " " + mHeight);
        }
    }

    public static Bitmap decodeSampledBitmapFromResource(
            Resources res, int resId, float reqWidth, float reqHeight) {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        options.inJustDecodeBounds = false;
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

    public void setImageRes(int imgRes) {
        mImageRes = imgRes;
//        requestLayout();
    }

    public void setCutType(ImageCutType cutType) {
        mCutType = cutType;
    }

    public void setCutAngle(int angle) {
        angle = Math.abs(angle) % 180;
        if ((angle >= 0 && angle <= 45) || (angle >= 135 && angle <= 180)) {
            mCutangle = angle;
        } else {
            mCutangle = 15;
        }
    }

    public void setCutCount(int count) {
        mCutCount = Math.abs(count) % 4;
    }

    public void setCutHeight(int height) {
        mCutHeight = height;
    }

    public void setCutPhaseShift(int phaseShift) {
        mCutPhaseShift = phaseShift;
    }

}
