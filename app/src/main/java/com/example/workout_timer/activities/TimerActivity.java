package com.example.workout_timer.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import android.os.CountDownTimer;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.workout_timer.R;

import static java.lang.String.format;

public class TimerActivity extends AppCompatActivity {
    private RestTimer mRestTimer;
    private WorkTimer mWorkTimer;

    private long workTime;
    private long restTime;
    private long currentWorkTime;
    private long currentRestTime;
    private int rounds;

    //used to know which timer to cancel when necessary.
    //used in continueTimer(), pauseTimer(), endTimer() methods
    private boolean isWorkTimerRunning;
    private boolean isRestTimerRunning;

    //used to know which sound to play in playSounds() method
    private boolean mPrefBeepSound, mPrefTickSound;

    private ConstraintLayout constraintLayout;
    private TextView mTv_work;
    private TextView mTv_rest;
    private TextView mTv_rounds;
    private TextView mTv_paused;
    private TextView mTv_motivation;
    private Button pauseBtn;
    private Button continueBtn;

    public final static long DEFAULT_WORK_MILS = 60_000;
    public static final long DEFAULT_REST_MILS = 10_000;
    public final static long COUNTDOWN_TIMER_INTERVAL_MILS = 1_000;

    private MediaPlayer mpBeep;
    private MediaPlayer mpTick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        setupActionBar();
        getIncomingData();
        setUpView();
        setUpSounds();
        playWorkTimer();

    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void getIncomingData() {
        Intent intent = getIntent();
        rounds = intent.getIntExtra("ROUNDS", 10);
        currentRestTime = restTime = intent.getLongExtra("REST_TIME", 10_000);
        currentWorkTime = workTime = intent.getLongExtra("WORK_TIME", 60_000);
        mPrefBeepSound = intent.getBooleanExtra("PREF_BEEP", true);
        mPrefTickSound = intent.getBooleanExtra("PREF_TICK", false);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    public void continueTimer() {
        if(isWorkTimerRunning){
            mWorkTimer = new WorkTimer(currentWorkTime);
            mWorkTimer.start();
            setWorkView();
        } else if(isRestTimerRunning){
            mRestTimer = new RestTimer(currentRestTime);
            mRestTimer.start();
            setRestView();
        }

        //display pauseBtn again
        pauseBtn.setVisibility(View.VISIBLE);
    }

    public void pauseTimer() {
        if(isWorkTimerRunning){
            mWorkTimer.cancel();
        }else if (isRestTimerRunning){
            mRestTimer.cancel();
        }
        setUpPauseView();
    }

    public void endTimer() {
        if(isWorkTimerRunning){
            mWorkTimer.cancel();
        } else if(isRestTimerRunning){
            mRestTimer.cancel();
        }

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);

    }

    private void setUpView() {
        //set up res fields
        mTv_work = findViewById(R.id.trainingWorkQuantity);
        mTv_rest = findViewById(R.id.trainingRestQuantity);
        mTv_rounds = findViewById(R.id.trainingSetsQuantity);

        mTv_motivation = findViewById(R.id.trainingMotivationalText);
        mTv_paused = findViewById(R.id.trainingPausedText);

        pauseBtn = findViewById(R.id.pauseBtn);
        continueBtn = findViewById(R.id.continueBtn);

        constraintLayout = findViewById(R.id.timerActivity);

        displayRounds();
        displayWorkTimerQuantity();
        setWorkView();
    }

    private void setUpPauseView() {
        pauseBtn.setVisibility(View.GONE);
        mTv_motivation.setVisibility(View.GONE);

        mTv_paused.setVisibility(View.VISIBLE);
        continueBtn.setVisibility(View.VISIBLE);
    }

    @SuppressLint("ResourceAsColor")
    private void setWorkView() {
        //change background color
        constraintLayout.setBackgroundColor(R.color.workColor);

        //set as visible
        mTv_motivation.setVisibility(View.VISIBLE);
        mTv_work.setVisibility(View.VISIBLE);

        //set as invisible
        mTv_paused.setVisibility(View.GONE);
        continueBtn.setVisibility(View.GONE);
        mTv_rest.setVisibility(View.GONE);
    }

    private void setUpSounds() {
        mpBeep = MediaPlayer.create(getApplicationContext(), R.raw.beep);
        mpTick = MediaPlayer.create(getApplicationContext(), R.raw.tick);
    }

    @SuppressLint("DefaultLocale")
    private void displayWorkTimerQuantity() {
        int seconds = (int) currentWorkTime / 1000;
        int displayMinutes = seconds / 60;
        int displaySeconds = seconds % 60;

        String leadingZero = displaySeconds > 9 ? "" : "0";
        mTv_work.setText(format("%d : %S%d", displayMinutes, leadingZero, displaySeconds));
    }

    @SuppressLint("DefaultLocale")
    private void displayRestTimerQuantity() {
        int seconds = (int) currentRestTime / 1000;
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
        currentWorkTime = workTime;
        mWorkTimer = new WorkTimer(workTime);
        mWorkTimer.start();
        isWorkTimerRunning = true;
    }

    @SuppressLint("ResourceAsColor")
    private void setRestView() {
        //background color
        constraintLayout.setBackgroundColor(R.color.restColor);

        //set as visible
        mTv_rest.setVisibility(View.VISIBLE);

        //set as invisible
        mTv_paused.setVisibility(View.GONE);
        continueBtn.setVisibility(View.GONE);
        mTv_motivation.setVisibility(View.GONE);
        mTv_work.setVisibility(View.GONE);

    }

    private void playRestTimer() {
        if (rounds > 0) {
            currentRestTime = restTime;
            mRestTimer = new RestTimer(restTime);
            mRestTimer.start();
            isRestTimerRunning = true;
        }
        //else { play the final sounds }
    }

    private void playSounds(){
        if(mPrefBeepSound){
            mpBeep.start();
        } else if(mPrefTickSound){
            mpTick.start();
        }
    }

    /**
     * inner class WorkTimer  and RestTimer are timer Models (re. MVC paradigm) it was easier to have
     * them as an inner classes.
     */
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
            currentWorkTime = millisUntilFinished;
            displayWorkTimerQuantity();
        }

        /**
         * Callback fired when the time is up.
         */
        @SuppressLint("ResourceAsColor")
        @Override
        public void onFinish() {
            isWorkTimerRunning = false;
            playSounds();
            rounds--;
            displayRounds();
            playRestTimer();
            setRestView();
            this.cancel();
        }
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
            currentRestTime = millisUntilFinished;
            displayRestTimerQuantity();
        }

        /**
         * Callback fired when the time is up.
         */
        @Override
        public void onFinish() {
            playSounds();
            isRestTimerRunning = false;
            playWorkTimer();
            setWorkView();
            this.cancel();
        }
    }
}