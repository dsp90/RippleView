package com.example.msa.customrippledrawable;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Created by msa on 05/12/2017.
 */

public class RippleLayout extends RelativeLayout {

    Paint paint;

    private int rippleRadius;
    private float defaultScaleValue = 6.0f;
    private int rippleColor;
    private long rippleDuration = 3000;
    private int rippleDelay;
    private int rippleCount = 6;
    private ArrayList<Animator> animators;
    private ArrayList<RippleView> rippleViews = new ArrayList<>();
    private AnimatorSet animatorSet;

    public RippleLayout(Context context) {
        super(context);
        initRippleViewParams();
    }

    public void initRippleViewParams(){
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.FILL);

        rippleDelay = (int) (rippleDuration / rippleCount);
        rippleRadius = (int) getResources().getDimension(R.dimen.radius_64_dp);

        animators = new ArrayList<>();
        animatorSet = new AnimatorSet();

        for (int i = 0; i < rippleCount; i++){

            RippleView rippleView = new RippleView(getContext());
            LayoutParams layoutParams = new LayoutParams(2*rippleRadius, 2*rippleRadius);
            layoutParams.addRule(CENTER_IN_PARENT, TRUE);
            addView(rippleView, layoutParams);

            ObjectAnimator animX = ObjectAnimator.ofFloat(rippleView, "scaleX", 1.0f, defaultScaleValue);
            animX.setRepeatCount(ValueAnimator.INFINITE);
            animX.setRepeatMode(ValueAnimator.RESTART);
            animX.setDuration(rippleDuration);
            animX.setStartDelay(i * rippleDelay);
            animators.add(animX);

            ObjectAnimator animY = ObjectAnimator.ofFloat(rippleView, "scaleY", 1.0f, defaultScaleValue);
            animY.setRepeatCount(ValueAnimator.INFINITE);
            animY.setRepeatMode(ValueAnimator.RESTART);
            animY.setDuration(rippleDuration);
            animY.setStartDelay(i * rippleDelay);
            animators.add(animY);

            ObjectAnimator animAlpha = ObjectAnimator.ofFloat(rippleView, "Alpha", 1.0f, 0.0f);
            animAlpha.setRepeatCount(ValueAnimator.INFINITE);
            animAlpha.setRepeatMode(ValueAnimator.RESTART);
            animAlpha.setDuration(rippleDuration);
            animAlpha.setStartDelay(i * rippleDelay);
            animators.add(animAlpha);
        }

        animatorSet.playTogether(animators);
    }

    public void startAnimation(){
        for (RippleView rippleView : rippleViews){
            rippleView.setVisibility(VISIBLE);
        }

        animatorSet.start();
    }

    class RippleView extends View {

        public RippleView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawCircle(rippleRadius, rippleRadius, rippleRadius, paint);
        }
    }
}