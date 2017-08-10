package com.valentun.findgift;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.valentun.findgift.utils.AnimationUtils;

public class Test extends AppCompatActivity {

    private boolean isActive = true;
    private View slider;
    private ValueAnimator expand, collapse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        slider = findViewById(R.id.test_slide);

        expand = AnimationUtils.slideAnimator(0, 200, slider, 500);
        collapse = AnimationUtils.slideAnimator(200, 0, slider, 500);
    }

    public void click(View view) {
        if (isActive) collapse.start();
        else expand.start();
        isActive = !isActive;
    }
}
