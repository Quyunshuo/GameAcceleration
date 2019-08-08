package com.quyunshuo.gameacceleration;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.image_01)
    ImageView mImage_01;
    @BindView(R.id.image_02)
    ImageView mImage_02;
    @BindView(R.id.image_03)
    ImageView mImage_03;
    @BindView(R.id.image_04)
    ImageView mImage_04;
    @BindView(R.id.image_05)
    ImageView mImage_05;
    @BindView(R.id.image_06)
    ImageView mImage_06;
    @BindView(R.id.image_07)
    ImageView mImage_07;
    @BindView(R.id.image_08)
    ImageView mImage_08;
    @BindView(R.id.image_09)
    ImageView mImage_09;
    @BindView(R.id.image_10)
    ImageView mImage_10;
    @BindView(R.id.image_11)
    ImageView mImage_11;
    @BindView(R.id.image_12)
    ImageView mImage_12;
    @BindView(R.id.image_bullet1)
    ImageView mImage_bullet1;
    @BindView(R.id.image_bullet2)
    ImageView mImage_bullet2;
    @BindView(R.id.image_fighter)
    ImageView mImage_fighter;
    @BindView(R.id.speed_tv)
    TextView mSpeed_tv;
    @BindView(R.id.image_flash)
    ImageView mImage_flash;
    //爆炸特效
    private ExplosionField mExplosionField;

    //    private float startX;
//    private float startY;
//    private float endX;
//    private float endY;
//    private float endX1;
//    private float endY1;
//    private float widthScreen;
//    private float heightScreen;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //判断  当启动activity的时候才执行
        if (hasFocus) {
            fighterAnimation();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {
        mImage_fighter = findViewById(R.id.image_fighter);
        mImage_flash = findViewById(R.id.image_flash);
        Glide.with(this)
                .asGif()
                .load(R.mipmap.rocket)
                .into(mImage_fighter);
    }


    private void initData() {
        //初始化ButterKnife
        ButterKnife.bind(this);
        //获取爆炸特效实例
        mExplosionField = ExplosionField.attach2Window(this);
    }

    /**
     * 子弹攻击图标 图标消失
     */
    private void bulletAnimation() {
        //子弹透明度变化动画
        ObjectAnimator bullet1Alpha = ObjectAnimator.ofFloat(mImage_bullet1, "alpha", 0.0f, 1.0f);
        bullet1Alpha.setDuration(1000);
        ObjectAnimator bullet2Alpha = ObjectAnimator.ofFloat(mImage_bullet2, "alpha", 0.0f, 1.0f);
        bullet2Alpha.setDuration(1000);
        ObjectAnimator bullet1Y = ObjectAnimator.ofFloat(mImage_bullet1, "translationY", 0, -750);
        //设置插值器 效果：开始时结束时缓慢，中间加速
        bullet1Y.setInterpolator(new AccelerateDecelerateInterpolator());
        ObjectAnimator bullet2Y = ObjectAnimator.ofFloat(mImage_bullet2, "translationY", 0, -750);
        //设置插值器 效果：开始时结束时缓慢，中间加速
        bullet2Y.setInterpolator(new AccelerateDecelerateInterpolator());
        AnimatorSet bulletAnimatorSet = new AnimatorSet();
        bulletAnimatorSet.playTogether(bullet1Alpha, bullet2Alpha, bullet1Y, bullet2Y);
        bulletAnimatorSet.setDuration(2000);
        //动画监听(结束时)
        bulletAnimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //隐藏子弹
                mImage_bullet1.setVisibility(View.GONE);
                mImage_bullet2.setVisibility(View.GONE);
                //调用图标爆炸特效
                startExplosionField(mImage_07, mImage_08, mImage_09, mImage_10, mImage_11, mImage_12);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startExplosionField(mImage_01, mImage_02, mImage_03, mImage_04, mImage_05, mImage_06);
                    }
                }, 2300);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fighterEndAnimation();
                    }
                }, 4600);
            }
        });
        bulletAnimatorSet.start();
    }

    /**
     * 爆炸特效
     *
     * @param view1
     * @param view2
     * @param view3
     * @param view4
     * @param view5
     * @param view6
     */
    private void startExplosionField(View view1, View view2, View view3, View view4, View view5, View view6) {
        mExplosionField.explode(view2);
        mExplosionField.explode(view3);
        mExplosionField.explode(view4);
        mExplosionField.explode(view5);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mExplosionField.explode(view1);
                mExplosionField.explode(view6);
            }
        }, 1200);
    }

    /**
     * 飞机进场动画
     */
    private void fighterAnimation() {
        ObjectAnimator fighterStart = ObjectAnimator.ofFloat(mImage_fighter, "translationY", 0, -600);
        fighterStart.setDuration(2000);
        fighterStart.setInterpolator(new AccelerateDecelerateInterpolator());
        fighterStart.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mImage_bullet1.setVisibility(View.VISIBLE);
                mImage_bullet2.setVisibility(View.VISIBLE);
                bulletAnimation();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        fighterStart.start();
    }

    private void fighterEndAnimation() {
        mImage_flash.setVisibility(View.VISIBLE);
        ObjectAnimator fighterEndFlash = ObjectAnimator.ofFloat(mImage_flash, "translationY", 0, 3000);
        fighterEndFlash.setDuration(1000);
        ObjectAnimator fighterEndFighter = ObjectAnimator.ofFloat(mImage_fighter, "translationY", -600, -2300);
        fighterEndFighter.setDuration(2000);
        fighterEndFighter.setInterpolator(new AccelerateInterpolator());
        AnimatorSet endAnimationSet = new AnimatorSet();
        endAnimationSet.playTogether(fighterEndFlash, fighterEndFighter);
        endAnimationSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mImage_flash.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        endAnimationSet.start();
    }


}
