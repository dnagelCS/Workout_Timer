package com.example.workout_timer.models;

import android.os.CountDownTimer;

public class RestTimer extends CountDownTimer {
    public static final long DEFAULT_REST_MILS = 10000;
    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public RestTimer(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    /**
     * Callback fired on regular interval.
     *
     * @param millisUntilFinished The amount of time until finished.
     */
    @Override
    public void onTick(long millisUntilFinished) {

    }

    /**
     * Callback fired when the time is up.
     */
    @Override
    public void onFinish() {

    }
}
