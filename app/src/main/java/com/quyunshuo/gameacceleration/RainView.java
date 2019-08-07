package com.quyunshuo.gameacceleration;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;


public class RainView extends View {
    private ArrayList<RainDrop> mDropList;
    TypedArray mArray;
    private int mDropNum;
    private int mDelatTime=10;

    public RainView(Context context){
        super(context);
    }

    public RainView(Context context, @Nullable AttributeSet attrs){
        super(context,attrs);
        mArray = context.obtainStyledAttributes(attrs,R.styleable.RainView);
        mDropNum=mArray.getInteger(R.styleable.RainView_RainView_drop_number,30);

        if (mDropList==null){
            mDropList= new ArrayList<>();
            for (int i=0;i<mDropNum;i++){
                RainDrop drop=new RainDrop(mArray);
                mDropList.add(drop);
            }
        }
        mArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (RainDrop drop:mDropList){
            drop.init(getMeasuredWidth(),getMeasuredHeight());
            drop.initPos();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawRain(canvas);
    }

    private void drawRain(Canvas canvas){
        for (RainDrop drop:mDropList){
            drop.rain(canvas);
        }
        postInvalidateDelayed(mDelatTime);
    }
}
