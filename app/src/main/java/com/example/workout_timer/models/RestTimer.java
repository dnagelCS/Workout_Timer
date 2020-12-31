package com.example.workout_timer.models;

import android.annotation.SuppressLint;
import android.os.CountDownTimer;
import android.widget.TextView;

import static com.example.workout_timer.models.WorkTimer.COUNTDOWN_TIMER_INTERVAL_MILS;
import static java.lang.String.format;

public class RestTimer extends CountDownTimer {
    public static final long DEFAULT_REST_MILS = 10000;
    private TextView mTv_rest;
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

    public RestTimer(long restTime, TextView tV_rest) {
        super(restTime, COUNTDOWN_TIMER_INTERVAL_MILS);
        mTv_rest = tV_rest;
    }

    /**
     * Callback fired on regular interval.
     *
     * @param millisUntilFinished The amount of time until finished.
     */
    @Override
    public void onTick(long millisUntilFinished) {
        displayRestTimerQuantity(millisUntilFinished);
    }

    /**
     * Callback fired when the time is up.
     */
    @Override
    public void onFinish() {

    }

    @SuppressLint("DefaultLocale")
    private void displayRestTimerQuantity(long millisUntilFinished) {
        int seconds = (int) millisUntilFinished / 1000;
        int displayMinutes = seconds / 60;
        int displaySeconds = seconds % 60;

        if (displaySeconds < 10) {
            mTv_rest.setText(format("%d : 0%d", displayMinutes, displaySeconds));
        } else {
            mTv_rest.setText(format("%d : %d", displayMinutes, displaySeconds));
        }
    }
}
