package com.zl.recordmanager;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.ViewFlipper;

/**
 * @author xiaolong
 * @ClassName RecordDialog
 * @Date 2019/4/28
 **/
public class RecordDialog extends DialogFragment {

    public static RecordDialog newInstance() {

        Bundle args = new Bundle();

        RecordDialog fragment = new RecordDialog();
        fragment.setArguments(args);
        return fragment;
    }

    private WaveView mWaveView;
    private ViewFlipper mViewFlipper;

    private int MAX_TIME = 30;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.record_dialog,container,false);
        mWaveView = view.findViewById(R.id.waveView);
        mViewFlipper = view.findViewById(R.id.viewFlipper);
        Chronometer mChronometer = view.findViewById(R.id.chronometer);
        mChronometer.setFormat("");
        mChronometer.setBase(0);
        mChronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            long value = -1;
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if (value == -1) {
                    value = chronometer.getBase();
                } else {
                    value++;
                }
                chronometer.setText(String.valueOf(value));
                if (value == MAX_TIME - 11) {
                    startAnimation();
                }
                if (value == MAX_TIME) {
                    dismiss();
                }
            }
        });
        mChronometer.start();
        return view;
    }

    private void startAnimation() {
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(mWaveView, "scaleX", 0.5f);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mWaveView, "scaleY", 0.5f);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(mWaveView, "translationY", 0, 120f);
        animator1.setInterpolator(new LinearInterpolator());
        animator2.setInterpolator(new LinearInterpolator());
        animator3.setInterpolator(new DecelerateInterpolator());
        animator1.setDuration(700);
        animator2.setDuration(700);
        animator3.setDuration(300);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animator1,animator2);
        animatorSet.playSequentially(animator1,animator3);
        animatorSet.start();
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                for (int i = 10; i >= 0; i--) {
                    TextView textView = new TextView(getActivity());
                    textView.setText(String.valueOf(i));
                    textView.setTextSize(40);
                    textView.setGravity(Gravity.CENTER);
                    textView.setTextColor(Color.GREEN);
                    mViewFlipper.addView(textView);
                }
                mViewFlipper.setFlipInterval(1000);
                mViewFlipper.startFlipping();
            }
        });
    }
}
