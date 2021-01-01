package com.example.workout_timer.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
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
    private  TextView mTv_rounds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        mTv_work = findViewById(R.id.trainingWorkQuantity);
        mTv_rest = findViewById(R.id.trainingRestQuantity);
        mTv_rounds = findViewById(R.id.trainingSetsQuantity);
        setupActionBar();
        getIncomingData();
        playTimers();

    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
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

    public void continueTimer(View view) {
    }

    public void pauseTimer(View view) {
    }

    private void getIncomingData() {
        Intent intent = getIntent();
        rounds = intent.getIntExtra("ROUNDS", 10);
        restTime = intent.getLongExtra("REST_TIME", 10_000);
        workTime = intent.getLongExtra("WORK_TIME", 60_000);
    }

    @SuppressLint("DefaultLocale")
    private void playTimers() {
        //mWorkTimer = new WorkTimer(workTime, mTv_work);
        //mRestTimer = new RestTimer(restTime, mTv_rest);

        while (rounds > 0) {
            mTv_rounds.setText(format("%d", rounds));
            new WorkTimer(workTime, mTv_work).start();
            new RestTimer(restTime, mTv_rest).start();
            rounds--;
        }
    }
}