package com.quyunshuo.gameacceleration;

import android.os.Bundle;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.image_01)
    ImageView image_01;
    @BindView(R.id.image_02)
    ImageView image_02;
    @BindView(R.id.image_03)
    ImageView image_03;
    @BindView(R.id.image_04)
    ImageView image_04;
    @BindView(R.id.image_05)
    ImageView image_05;
    @BindView(R.id.image_06)
    ImageView image_06;
    @BindView(R.id.image_07)
    ImageView image_07;
    @BindView(R.id.image_08)
    ImageView image_08;
    @BindView(R.id.image_09)
    ImageView image_09;
    @BindView(R.id.image_10)
    ImageView image_10;
    @BindView(R.id.image_11)
    ImageView image_11;
    @BindView(R.id.image_12)
    ImageView image_12;
    @BindView(R.id.image_bullet1)
    ImageView image_bullet1;
    @BindView(R.id.image_bullet2)
    ImageView image_bullet2;
    @BindView(R.id.image_fighter)
    ImageView image_fighter;
    @BindView(R.id.speed_tv)
    TextView speed_tv;
    @BindView(R.id.image_flash)
    ImageView image_flash;
    private ExplosionField mExplosionField;
    private float startX;
    private float startY;
    private float endX;
    private float endY;
    private float endX1;
    private float endY1;
    private float widthScreen;
    private float heightScreen;
    TranslateAnimation animation1;
    TranslateAnimation animation2;
    TranslateAnimation animation3;
    TranslateAnimation animation4;
    TranslateAnimation animation5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {

    }
}
