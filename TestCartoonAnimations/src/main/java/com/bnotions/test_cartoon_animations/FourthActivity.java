package com.bnotions.test_cartoon_animations;

import android.animation.AnimatorSet;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Random;

/**
 * Created by camillebossoutrot on 13-05-27.
 */
public class FourthActivity extends Activity implements View.OnClickListener {

    private RelativeLayout top_layout;
    private Button add_button;
    private Point size;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);

        top_layout = (RelativeLayout) findViewById(R.id.top_layout);
        add_button = (Button) findViewById(R.id.add_button);
        add_button.setOnClickListener(this);
        Display display = getWindowManager().getDefaultDisplay();
        size = new Point();
        display.getSize(size);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.add_button:
                Random rand = new Random();
                addNewItem(rand.nextInt(size.x/2 - 200) + 100, rand.nextInt(rand.nextInt(size.y/2 - 200)) + 100, rand.nextInt(2000) + 2000);
                break;
        }

    }

    private void addNewItem(int radius_vert, int radius_horiz, int animation_duration) {

        final ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.menu_item);
        Random rand = new Random();
        int r = rand.nextInt(255);
        int g = rand.nextInt(255);
        int b = rand.nextInt(255);
        int color = Color.argb(255, r, g, b);
        imageView.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        imageView.setLayoutParams(params);
        top_layout.addView(imageView, 1);

        ValueAnimator animator_left = ValueAnimator.ofObject(new TypeEvaluator<Integer>() {
            @Override
            public Integer evaluate(float x, Integer start_value, Integer end_value) {

                int delta = (end_value - start_value);
                double d = Math.cos(x * 2 * Math.PI);
                return (int) (d * delta);
            }
        }, 0, radius_vert);
        animator_left.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                imageView.setTranslationX((Integer) animation.getAnimatedValue());

            }
        });
        animator_left.setDuration(animation_duration);
        animator_left.setRepeatCount(ValueAnimator.INFINITE);
        animator_left.setInterpolator(null);

        ValueAnimator animator_top = ValueAnimator.ofObject(new TypeEvaluator<Integer>() {
            @Override
            public Integer evaluate(float x, Integer start_value, Integer end_value) {

                int delta = (end_value - start_value);
                double d = Math.sin(x * 2 * Math.PI);
                return (int) (d * delta);
            }
        }, 0, radius_horiz);
        animator_top.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                imageView.setTranslationY((Integer) animation.getAnimatedValue());

            }
        });
        animator_top.setDuration(animation_duration);
        animator_top.setRepeatCount(ValueAnimator.INFINITE);
        animator_top.setInterpolator(null);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(animator_left, animator_top);
        set.start();
    }

}