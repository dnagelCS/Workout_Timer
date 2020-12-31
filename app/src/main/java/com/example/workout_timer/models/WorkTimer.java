package com.example.workout_timer.models;

import android.os.CountDownTimer;
import android.widget.TextView;

import static java.lang.String.format;

public class WorkTimer extends CountDownTimer {
    public final static long DEFAULT_WORK_MILS = 60000;
    public final static long COUNTDOWN_TIMER_INTERVAL_MILS = 1000;
    public TextView mTv_work;

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

    public WorkTimer(long millisInFuture, TextView tV_work) {
        super(millisInFuture, COUNTDOWN_TIMER_INTERVAL_MILS);
        mTv_work = tV_work;
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

    private void displayRestTimerQuantity(long millisUntilFinished) {
        int seconds = (int) millisUntilFinished / 1000;
        int displayMinutes = seconds / 60;
        int displaySeconds = seconds % 60;

        if (displaySeconds < 10) {
            mTv_work.setText(format("%d : 0%d", displayMinutes, displaySeconds));
        } else {
            mTv_work.setText(format("%d : %d", displayMinutes, displaySeconds));
        }
    }
}
