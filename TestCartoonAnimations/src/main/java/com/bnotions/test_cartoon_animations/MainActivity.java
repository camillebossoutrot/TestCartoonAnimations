package com.bnotions.test_cartoon_animations;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;

public class MainActivity extends Activity implements View.OnTouchListener {

    private static final float MAX_SLIDE = 300.f;
    public static final float TARGET = 350.f;
    private ImageView image_view;
    private float start_x;
    private float start_y;
    private boolean target_reached = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image_view = (ImageView) findViewById(R.id.image_view);
        image_view.setOnTouchListener(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        float new_x = motionEvent.getRawX();
        float new_y = motionEvent.getRawY();
        float delta_y = Math.abs(start_y - new_y);
        float delta_x = Math.abs(start_x - new_x);

        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                image_view.setTranslationY(0.f);
                start_x = motionEvent.getRawX();
                start_y = motionEvent.getRawY();
                target_reached = false;
                return true;
            case MotionEvent.ACTION_MOVE:
                if (delta_y > MAX_SLIDE ) {
                    if (!target_reached) {
                        target_reached = true;
                        image_view.animate().setStartDelay(0).setInterpolator(new OvershootInterpolator(4.f)).translationY(TARGET * ((start_y < new_y) ? 1 : -1)).scaleX(1.f).scaleY(1.f);
                    }
                } else {
                    target_reached = false;
                    float movement = (float) ((-Math.exp(-delta_y / MAX_SLIDE) + 1.f) * MAX_SLIDE);
                    movement *= (start_y < new_y) ? 1 : -1;
                    image_view.setTranslationY(movement);
                    image_view.setScaleY(1 + delta_y / 900.f);
                    image_view.setScaleX(1 - delta_y / 2000.f);
                }
                return true;
            case MotionEvent.ACTION_UP:

                image_view.animate().setInterpolator(new OvershootInterpolator(4.f)).translationY(0).scaleX(1.f).scaleY(1.f);

                return true;
        }
        return false;
    }
}
