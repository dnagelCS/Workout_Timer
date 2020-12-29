package com.example.workout_timer.models;

import android.os.CountDownTimer;

public class WorkTimer extends CountDownTimer {
    public final static long DEFAULT_WORK_MILS = 60000;
    public final static long COUNTDOWN_TIMER_INTERVAL_MILS = 1000;
    private long minutesLeft;
    private long secondsLeft;

    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public WorkTimer(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    /**
     * @param millisUntilFinished
     */
    @Override
    public void onTick(long millisUntilFinished) {

    }

    @Override
    public void onFinish() {

    }
}
