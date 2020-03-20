package com.indptechnologies.happybirthdayphotoframes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by DDR INFO SYSTEM on 19-04-2017.
 */

public class ColorPanelView extends View {

    /**
     * The width in pixels of the border
     * surrounding the color panel.
     */
    private final static float	BORDER_WIDTH_PX = 1;

    private static float mDensity = 1f;

    private int 		mBorderColor = 0xff6E6E6E;
    private int 		mColor = 0xff000000;

    private Paint mBorderPaint;
    private Paint mColorPaint;

    private RectF mDrawingRect;
    private RectF mColorRect;

    private AlphaPatternDrawable mAlphaPattern;


    public ColorPanelView(Context context){
        this(context, null);
    }

    public ColorPanelView(Context context, AttributeSet attrs){
        this(context, attrs, 0);
    }

    public ColorPanelView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        init();
    }

    private void init(){
        mBorderPaint = new Paint();
        mColorPaint = new Paint();
        mDensity = getContext().getResources().getDisplayMetrics().density;
    }


    @Override
    protected void onDraw(Canvas canvas) {

        final RectF rect = mColorRect;

        if(BORDER_WIDTH_PX > 0){
            mBorderPaint.setColor(mBorderColor);
            canvas.drawRect(mDrawingRect, mBorderPaint);
        }
        try{

            if(mAlphaPattern != null){

                mAlphaPattern.draw(canvas);

                Log.v("Alpha Pattern", "in not nullll");

            }else{
                Log.v("Alpha Pattern", "in  nullll......");

            }
        }catch(Exception e){
            Log.v("Alpha Pattern", " exception");

        }
        mColorPaint.setColor(mColor);

        canvas.drawRect(rect, mColorPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width = View.MeasureSpec.getSize(widthMeasureSpec);
        int height = View.MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mDrawingRect = new RectF();
        mDrawingRect.left =  getPaddingLeft();
        mDrawingRect.right  = w - getPaddingRight();
        mDrawingRect.top = getPaddingTop();
        mDrawingRect.bottom = h - getPaddingBottom();

        setUpColorRect();

    }

    private void setUpColorRect(){
        final RectF dRect = mDrawingRect;

        float left = dRect.left + BORDER_WIDTH_PX;
        float top = dRect.top + BORDER_WIDTH_PX;
        float bottom = dRect.bottom - BORDER_WIDTH_PX;
        float right = dRect.right - BORDER_WIDTH_PX;

        mColorRect = new RectF(left,top, right, bottom);

        mAlphaPattern = new AlphaPatternDrawable((int)(5 * mDensity));

        mAlphaPattern.setBounds(Math.round(mColorRect.left),
                Math.round(mColorRect.top),
                Math.round(mColorRect.right),
                Math.round(mColorRect.bottom));

    }

    /**
     * Set the color that should be shown by this view.
     * @param color
     */
    public void setColor(int color){
        mColor = color;
        invalidate();
    }

    /**
     * Get the color currently show by this view.
     * @return
     */
    public int getColor(){
        return mColor;
    }

    /**
     * Set the color of the border surrounding the panel.
     * @param color
     */
    public void setBorderColor(int color){
        mBorderColor = color;
        invalidate();
    }

    /**
     * Get the color of the border surrounding the panel.
     */
    public int getBorderColor(){
        return mBorderColor;
    }

}
