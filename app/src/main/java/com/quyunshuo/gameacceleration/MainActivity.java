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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.quyunshuo.gameacceleration.explosion.ExplosionField;
import com.quyunshuo.gameacceleration.util.StatusBarUtil;

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
    //子弹距离第二行图标的距离
    private int mBulletDistanceIcon;
    //飞机距离子弹的距离
    private int mFighterDistanceBullet;
    //飞机的高度
    private int mFighterHeight;
    //屏幕高度
    private int mParentHeight;
    //第一列图标距离第二列图标
    private int mIcon1DistanceIcon2;
    //是否进行动画
    private boolean mIsAnimate = true;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //判断  当启动activity的时候才执行
        if (hasFocus) {
                getControlPositionData();
                fighterAnimation();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.bg));
        //初始化ButterKnife
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        //利用Glide加载飞机的GIF图片
        Glide.with(this)
                .asGif()
                .load(R.mipmap.rocket)
                .into(mImage_fighter);
    }


    private void initData() {
        //获取爆炸特效实例
        mExplosionField = ExplosionField.attach2Window(this);
    }

    /**
     * 爆炸特效
     */
    private void startExplosionField(View view1, View view2, View view3,
                                     View view4, View view5, View view6) {
        mExplosionField.explode(view3);
        mExplosionField.explode(view4);
        new Handler().postDelayed(() -> {
            mExplosionField.explode(view2);
            mExplosionField.explode(view5);
            new Handler().postDelayed(() -> {
                mExplosionField.explode(view1);
                mExplosionField.explode(view6);
            }, 600);
        }, 600);
    }

    /**
     * 飞机进场动画
     */
    private void fighterAnimation() {
        ObjectAnimator fighterStart = ObjectAnimator.ofFloat(mImage_fighter,
                "translationY", 0, -mFighterDistanceBullet);
        fighterStart.setDuration(2000);
        fighterStart.setInterpolator(new AccelerateDecelerateInterpolator());
        fighterStart.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                //进场动画结束时开始子弹攻击图标的AnimatorSet
                bulletAnimation();
            }
        });
        fighterStart.start();
    }

    /**
     * 第一次子弹攻击图标 图标消失
     */
    private void bulletAnimation() {
        //子弹透明度变化动画
        ObjectAnimator bullet1Alpha = ObjectAnimator.ofFloat(mImage_bullet1,
                "alpha", 0.0f, 1.0f, 1.0f, 1.0f);
        bullet1Alpha.setDuration(1500);
        bullet1Alpha.setRepeatCount(1);
        ObjectAnimator bullet2Alpha = ObjectAnimator.ofFloat(mImage_bullet2,
                "alpha", 0.0f, 1.0f, 1.0f, 1.0f);
        bullet2Alpha.setDuration(1500);
        bullet2Alpha.setRepeatCount(1);
        ObjectAnimator bullet1Y = ObjectAnimator.ofFloat(mImage_bullet1,
                "translationY", 0, -mBulletDistanceIcon);
        bullet1Y.setDuration(1500);
        //设置插值器 效果：开始时结束时缓慢，中间加速
        bullet1Y.setInterpolator(new AccelerateInterpolator());
        ObjectAnimator bullet2Y = ObjectAnimator.ofFloat(mImage_bullet2,
                "translationY", 0, -mBulletDistanceIcon);
        bullet2Y.setDuration(1500);
        //设置插值器 效果：开始时结束时缓慢，中间加速
        bullet2Y.setInterpolator(new AccelerateInterpolator());
        bullet2Y.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                //调用图标爆炸特效
                startExplosionField(mImage_07, mImage_08, mImage_09, mImage_10, mImage_11, mImage_12);
                bulletSecondAnimation();
            }
        });
        AnimatorSet bulletAnimatorSet = new AnimatorSet();
        bulletAnimatorSet.playTogether(bullet1Alpha, bullet2Alpha, bullet1Y, bullet2Y);
        bulletAnimatorSet.setDuration(1500);
        bulletAnimatorSet.start();
    }

    /**
     * 子弹第二次攻击
     */
    private void bulletSecondAnimation() {
        ObjectAnimator bullet1YSecond = ObjectAnimator.ofFloat(mImage_bullet1, "translationY",
                0, -(mBulletDistanceIcon + mIcon1DistanceIcon2));
        bullet1YSecond.setDuration(1500);
        bullet1YSecond.setInterpolator(new AccelerateInterpolator());
        bullet1YSecond.start();
        ObjectAnimator bullet2YSecond = ObjectAnimator.ofFloat(mImage_bullet2, "translationY",
                0, -(mBulletDistanceIcon + mIcon1DistanceIcon2));
        bullet2YSecond.setDuration(1500);
        bullet2YSecond.setInterpolator(new AccelerateInterpolator());
        bullet2YSecond.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //隐藏子弹
                mImage_bullet1.setVisibility(View.GONE);
                mImage_bullet2.setVisibility(View.GONE);
                startExplosionField(mImage_01, mImage_02, mImage_03, mImage_04, mImage_05, mImage_06);
                new Handler().postDelayed(() -> fighterEndAnimation(), 2000);
            }
        });
        bullet2YSecond.start();
    }


    /**
     * 飞机退出动画
     */
    private void fighterEndAnimation() {
        mImage_flash.setVisibility(View.VISIBLE);
        ObjectAnimator fighterEndFlash = ObjectAnimator.ofFloat(mImage_flash,
                "translationY", 0, 4000);
        fighterEndFlash.setDuration(1500);
        ObjectAnimator fighterEndFighter = ObjectAnimator.ofFloat(mImage_fighter,
                "translationY", -mFighterDistanceBullet,
                -(mParentHeight + mFighterHeight));
        fighterEndFighter.setDuration(1500);
        fighterEndFighter.setInterpolator(new AccelerateInterpolator());
        AnimatorSet endAnimationSet = new AnimatorSet();
        endAnimationSet.playTogether(fighterEndFlash, fighterEndFighter);
        endAnimationSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mImage_flash.setVisibility(View.GONE);
            }
        });
        endAnimationSet.start();
    }

    /**
     * 获取控件之间的距离
     */
    private void getControlPositionData() {
        mBulletDistanceIcon = mImage_bullet1.getTop() - (mImage_07.getTop() + mImage_07.getMeasuredHeight());
        mFighterDistanceBullet = mImage_fighter.getTop() - (mImage_bullet1.getTop() - 120);
        mFighterHeight = mImage_fighter.getMeasuredHeight();
        mParentHeight = mImage_fighter.getTop();
        mIcon1DistanceIcon2 = mImage_07.getTop() - mImage_01.getTop();
    }

}
