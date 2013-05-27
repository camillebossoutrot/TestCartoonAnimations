package com.bnotions.test_cartoon_animations;

import android.animation.*;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.RelativeLayout;

/**
 * Created by camillebossoutrot on 13-05-22.
 */
public class ThirdActivity extends Activity implements View.OnClickListener {

    public static final int NO_ID = 2038;
    public static final int EXP_ID = 2039;
    public static final int SIN_ID = 2040;

    private Button button;
    private RelativeLayout top_layout;
    private boolean isUp = true;
    private int curve;
    private long duration = 1000;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
        top_layout = (RelativeLayout) findViewById(R.id.top_layout);
        curve = EXP_ID;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SubMenu courbes = menu.addSubMenu("Formula");
        courbes.add(0, NO_ID, 0, "NO");
        courbes.add(0, EXP_ID, 1, "Exp");
        courbes.add(0, SIN_ID, 2, "Sin");

        SubMenu duration = menu.addSubMenu("Duration");
        duration.add(1, 500, 0, "1/2s");
        duration.add(1, 1000, 0, "1s");
        duration.add(1, 3000, 1, "3s");
        duration.add(1, 5000, 2, "5s");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case EXP_ID:
                curve = EXP_ID;
                break;
            case SIN_ID:
                curve = SIN_ID;
                break;
            case NO_ID:
                curve = NO_ID;
                break;
            case 500:
                duration = 500;
                break;
            case 1000:
                duration = 1000;
                break;
            case 3000:
                duration = 3000;
                break;
            case 5000:
                duration = 5000;
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(final View view) {

        if (isUp) {
            runDownAnim();
            isUp = false;
        } else {
            runUpAnim();
            isUp = true;
        }


    }

    private void runDownAnim() {

        ValueAnimator animator_left = ValueAnimator.ofObject(new TypeEvaluator<Integer>() {
            @Override
            public Integer evaluate(float x, Integer start_value, Integer end_value) {

                int delta = (end_value - start_value);
                double d = x;
                if (curve == EXP_ID) {
                    d = (Math.exp(-x) - 1.) / (Math.exp(-1) - 1.);
                } else if (curve == SIN_ID) {
                    d = Math.sin(x * 2. * Math.PI) / 4. + x;
                }
                return (int) (d * delta);
            }
        }, button.getLeft(), (top_layout.getWidth() - button.getWidth() - button.getLeft()));
        animator_left.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                button.setTranslationX((Integer) animation.getAnimatedValue());

            }
        });
        animator_left.setDuration(duration);
        //if (courbe == CIRCLE_ID)  animator_left.setRepeatCount(ValueAnimator.INFINITE);

        ValueAnimator animator_top = ValueAnimator.ofObject(new TypeEvaluator<Integer>() {
            @Override
            public Integer evaluate(float x, Integer start_value, Integer end_value) {

                int delta = (end_value - start_value);
                double d = x;
                if (curve == EXP_ID) {
                    d = (Math.exp(x) - 1.) / (Math.exp(1) - 1.);
                }
                return (int) (d * delta);
            }
        }, button.getTop(), (top_layout.getHeight() - button.getHeight() - button.getTop()));
        animator_top.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                button.setTranslationY((Integer) animation.getAnimatedValue());

            }
        });
        animator_top.setDuration(duration);
        //if (courbe == CIRCLE_ID) animator_top.setRepeatCount(ValueAnimator.INFINITE);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(animator_left, animator_top);
        set.start();
    }

    private void runUpAnim() {

        ValueAnimator animator_left = ValueAnimator.ofObject(new TypeEvaluator<Integer>() {
            @Override
            public Integer evaluate(float x, Integer start_value, Integer end_value) {

                int delta = (end_value - start_value);
                double d = x;
                if (curve == EXP_ID) {
                    d = (Math.exp(-x) - 1.) / (Math.exp(-1) - 1.);
                } else if (curve == SIN_ID) {
                    d = Math.sin(-x * 2. * Math.PI) / 4. + x;
                }
                return end_value - (int) (d * delta);
            }
        },0, (top_layout.getWidth() - button.getWidth() - 2*button.getLeft()));
        animator_left.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                button.setTranslationX((Integer) animation.getAnimatedValue());

            }
        });
        animator_left.setDuration(duration);

        ValueAnimator animator_top = ValueAnimator.ofObject(new TypeEvaluator<Integer>() {
            @Override
            public Integer evaluate(float x, Integer start_value, Integer end_value) {

                int delta = (end_value - start_value);
                double d = x;
                if (curve == EXP_ID){
                   d = (Math.exp(x) - 1.) / (Math.exp(1) - 1.);
                } 
                return end_value - (int) (d * delta);

            }
        }, 0, (top_layout.getHeight() - button.getHeight() - 2*button.getTop()));
        animator_top.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                button.setTranslationY((Integer) animation.getAnimatedValue());

            }
        });
        animator_top.setDuration(duration);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(animator_left, animator_top);
        set.start();
    }
}