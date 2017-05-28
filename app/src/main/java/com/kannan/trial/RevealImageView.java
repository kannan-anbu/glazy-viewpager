package com.kannan.trial;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.util.Log;
public class RevealImageView extends ImageView {

    int mImageRes;
    Bitmap mBitmapImage;
    Bitmap mScaledBitmapImage;
    BitmapShader mBitmapShader;
    Paint paint;

    private RectF mCoverRect;
    private RectF mSavedRect;
    private Paint mCoverPaint;

    float width;
    float height;


    boolean pending = false;

    public RevealImageView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public RevealImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public RevealImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {

        setWillNotDraw(false);

        mCoverPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCoverPaint.setStyle(Paint.Style.FILL);
        mCoverPaint.setColor(Color.WHITE);

//        mCoverRect = new RectF();
//        Toast.makeText(getContext(),"drawing... \n " + ((BitmapDrawable) getBackground()).getBitmap().getHeight()+ "\n" +getWidth(), Toast.LENGTH_SHORT).show();
//        invalidate();




////        if (attrs != null){
////            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RevealImageView, defStyleAttr, 0);
////            mImageRes = array.getInt(R.styleable.RevealImageView_src, R.drawable.pic1);
////            array.recycle();
////        }
//
//
////        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        Drawable drawable = getDrawable();
//        mBitmapImage = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
////        mBitmapImage = BitmapFactory.decodeResource(getResources(),R.drawable.pic1);
////        mScaledBitmapImage = Bitmap.createScaledBitmap(mBitmapImage, )
////        mBitmapShader =
////        paint.setShader()
//        Canvas c = new Canvas(mBitmapImage);
//        drawable.setBounds(0, 0, c.getWidth(), c.getHeight());
////        drawable.draw(c);
//        setScaleType(ScaleType.CENTER_CROP);
//        invalidate();

    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
//        if (!pending) {
        if (mCoverRect == null) {
            mCoverRect = new RectF();
            mCoverRect.set(0, 0, width, height);
        }
//            mCoverRect.set(0, 0, width, height);
//        if (mSavedRect == null){
//            mSavedRect = new RectF();
//            mSavedRect.set(
//                    mCoverRect.left,
//                    mCoverRect.top,
//                    mCoverRect.right,
//                    mCoverRect.bottom
//            );
//        }
//        }

//        update(1f);
    }
    boolean drawn = false;
    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        canvas.drawRect(mCoverRect, mCoverPaint);
        drawn = true;
//        canvas.
//        canvas.drawLine(0, 0, getWidth(), getHeight(), mCoverPaint);
    }



    public void update(float scaleFactor) {
        String str = mCoverRect.toString();
        str += mCoverRect.toShortString();
        mCoverRect.set(0, height * scaleFactor, width, height);
        str += mCoverRect.toString();
//        Toast.makeText(getContext(), str + "update"+scaleFactor, Toast.LENGTH_LONG).show();
        Log.i("app",  "update"+scaleFactor);
        drawn = false;
//        while (!drawn)
            postInvalidate();
//        requestLayout();

//        requestLayout();
    }
}
