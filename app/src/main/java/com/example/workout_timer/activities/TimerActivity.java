package com.example.workout_timer.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import com.example.workout_timer.R;
import com.example.workout_timer.models.RestTimer;
import com.example.workout_timer.models.WorkTimer;
import static java.lang.String.format;

public class TimerActivity extends AppCompatActivity {
    private RestTimer mRestTimer;
    private WorkTimer mWorkTimer;
    private long workTime;
    private long restTime;
    private int rounds;
    private TextView mTv_work;
    private TextView mTv_rest;
    private TextView mTv_rounds;

    public final static long DEFAULT_WORK_MILS = 60000;
    public static final long DEFAULT_REST_MILS = 10000;
    public final static long COUNTDOWN_TIMER_INTERVAL_MILS = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        getIncomingData();
        setUpView();
        playWorkTimer();

    }

    public void continueTimer(View view) {
    }

    public void pauseTimer(View view) {
    }

    private void setUpView() {
        mTv_work = findViewById(R.id.trainingWorkQuantity);
        mTv_rest = findViewById(R.id.trainingRestQuantity);
        mTv_rounds = findViewById(R.id.trainingSetsQuantity);
        displayRounds();
        displayWorkTimerQuantity();
    }

    private void getIncomingData() {
        Intent intent = getIntent();
        rounds = intent.getIntExtra("ROUNDS", 10);
        restTime = intent.getLongExtra("REST_TIME", 10_000);
        workTime = intent.getLongExtra("WORK_TIME", 60_000);
    }

    @SuppressLint("DefaultLocale")
    private void displayWorkTimerQuantity() {
        int seconds = (int) workTime / 1000;
        int displayMinutes = seconds / 60;
        int displaySeconds = seconds % 60;

        String leadingZero = displaySeconds > 9 ? "" : "0";
        mTv_work.setText(format("%d : %S%d", displayMinutes, leadingZero, displaySeconds));
    }

    @SuppressLint("DefaultLocale")
    private void displayRestTimerQuantity() {
        int seconds = (int) restTime / 1000;
        int displayMinutes = seconds / 60;
        int displaySeconds = seconds % 60;

        String leadingZero = displaySeconds > 9 ? "" : "0";
        mTv_rest.setText(format("%d : %s%d", displayMinutes, leadingZero, displaySeconds));
    }

    @SuppressLint("DefaultLocale")
    private void displayRounds() {
        mTv_rounds.setText(format("%d", rounds));
    }

    private void playWorkTimer() {
        new Thread(){
            public void run(){
                mWorkTimer = new WorkTimer(workTime);
            }
        };

    }

    class WorkTimer extends CountDownTimer {

        /**
         * @param millisInFuture The number of millis in the future from the call
         *                       to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                       is called.
         * @variable COUNTDOWN_TIMER_INTERVAL_MILS The interval along the way to receive
         * {@link #onTick(long)} callbacks.
         */
        public WorkTimer(long millisInFuture) {
            super(millisInFuture, COUNTDOWN_TIMER_INTERVAL_MILS);
        }

        /**
         * Callback fired on regular interval.
         *
         * @param millisUntilFinished The amount of time until finished.
         */
        @Override
        public void onTick(long millisUntilFinished) {
            displayWorkTimerQuantity();
            System.out.println("I'm at WorkTimer.onTick");
        }

        /**
         * Callback fired when the time is up.
         */
        @Override
        public void onFinish() {
            //play sounds
            playRestTimer();
            rounds--;

        }
    }

    private void playRestTimer() {
        if (rounds > 0) {
            mRestTimer = new RestTimer(restTime);
        }
        //else { play the final sounds }
    }

    class RestTimer extends CountDownTimer {

        /**
         * @param millisInFuture The number of millis in the future from the call
         *                       to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                       is called.
         * @variable COUNTDOWN_TIMER_INTERVAL_MILS The interval along the way to receive
         * {@link #onTick(long)} callbacks.
         */
        public RestTimer(long millisInFuture) {
            super(millisInFuture, COUNTDOWN_TIMER_INTERVAL_MILS);
        }

        /**
         * Callback fired on regular interval.
         *
         * @param millisUntilFinished The amount of time until finished.
         */
        @Override
        public void onTick(long millisUntilFinished) {
            displayRestTimerQuantity();
        }

        /**
         * Callback fired when the time is up.
         */
        @Override
        public void onFinish() {
            //play sounds
            playWorkTimer();

        }
    }
}
