package com.bnotions.test_cartoon_animations;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TimePicker;

/**
 * Created by camillebossoutrot on 13-05-17.
 */
public class SecondActivity extends Activity implements View.OnClickListener, TimePicker.OnTimeChangedListener {

    private Button button;
    private ImageView hour_hand;
    private ImageView minute_hand;
    private TimePicker time_picker;

    private int tmp_hour;
    private int current_hour;
    private int old_hour;

    private int tmp_min;
    private int current_min;
    private int old_min;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
        hour_hand = (ImageView) findViewById(R.id.hour_hand);
        minute_hand = (ImageView) findViewById(R.id.minute_hand);
        time_picker = (TimePicker) findViewById(R.id.time_picker);
        time_picker.setOnTimeChangedListener(this);
        time_picker.setIs24HourView(false);
        time_picker.setCurrentHour(0);
        time_picker.setCurrentMinute(0);
        old_hour = 0;
    }

    @Override
    public void onClick(View view) {

        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                old_hour = current_hour;
                current_hour = tmp_hour;
                RotateAnimation anim = new RotateAnimation(360/12 * old_hour, 360/12 * (current_hour + ((old_hour > current_hour)?12:0)), hour_hand.getWidth()/2, hour_hand.getHeight()-30);
                anim.setDuration(1000);
                anim.setFillAfter(true);
                hour_hand.startAnimation(anim);
            }
        });

        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                old_min = current_min;
                current_min = tmp_min;
                RotateAnimation anim = new RotateAnimation(360/60 * old_min, 360/60 * (current_min + ((old_min > current_min)?60:0)), minute_hand.getWidth()/2, minute_hand.getHeight()-30);
                anim.setDuration(1000);
                anim.setFillAfter(true);
                minute_hand.startAnimation(anim);
            }
        });
    }

    @Override
    public void onTimeChanged(TimePicker timePicker, int i, int i2) {
        if (i > 12) i-=12;
        tmp_hour = i;
        tmp_min = i2;
        Log.d("new hour", "hour:"+i + "mn:"+i2);
    }
}