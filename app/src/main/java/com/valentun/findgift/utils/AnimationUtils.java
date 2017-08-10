package com.valentun.findgift.utils;

import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;

public class AnimationUtils {
    public static ValueAnimator slideAnimator(int start, int end, final View view, int duration) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = val;
                view.setLayoutParams(layoutParams);
            }
        });
        animator.setDuration(duration);
        return animator;
    }
}
