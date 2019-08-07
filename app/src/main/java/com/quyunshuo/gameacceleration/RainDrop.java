package com.quyunshuo.gameacceleration;

import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

public class RainDrop {

    private Paint mPaint = new Paint();
    private int mStartX = 10;
    private int mStartY = 0;

    private int mk=0;
    private int mRainSizeW=8;
    private int mSpeed=3;
    private int mDirectX=1;

    private int mViewW;
    private int mViewH;
    private Random mRandom;

    private int mRandRainSizeW;
    private int mRandRainSpeed;
    private int mRainColor;
    private int mRainSize;

    public RainDrop(TypedArray array){
        mRandRainSizeW=array.getInteger(R.styleable.RainView_RainView_drop_length,5);
        mRandRainSpeed=array.getInteger(R.styleable.RainView_RainView_drop_speed,5);
        mRainColor=array.getColor(R.styleable.RainView_RainView_drop_color, Color.BLACK);
        mRainSize=array.getInteger(R.styleable.RainView_RainView_drop_length,3);

        mPaint.setColor(mRainColor);
        mPaint.setStrokeWidth(mRainSize);
    }

    void init(int w,int h){
        mViewW=w;
        mViewH=h;
    }
    void initPos(){
        mRandom = new Random();
        mStartX=mRandom.nextInt(mViewW);
        mStartY=mRandom.nextInt(mViewH);

        mRainSizeW = 5+mRandom.nextInt(mRandRainSizeW);
        mk=3+mRandom.nextInt(5);
        mSpeed=1+mRandom.nextInt(mRandRainSpeed);
        if ((mSpeed+mk)%2==0){
            mDirectX=1;
        }else {
            mDirectX=1;
        }
    }
    void rain(Canvas canvas){
        canvas.drawLine(mStartX,mStartY,mStartX+mRainSizeW,mStartY+mRainSizeW*mk,mPaint);
        mStartY += mSpeed*mDirectX;
        mStartY +=mSpeed*mk;

        if (mStartX>mViewW||mStartY>mViewH){
            initPos();
        }
    }
}
