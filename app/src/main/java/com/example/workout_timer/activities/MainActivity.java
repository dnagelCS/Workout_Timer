package com.example.workout_timer.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.workout_timer.R;

import static com.example.workout_timer.lib.Utils.showInfoDialog;
import static com.example.workout_timer.models.WorkTimer.DEFAULT_WORK_MILS;
import static com.example.workout_timer.models.RestTimer.DEFAULT_REST_MILS;
import static java.lang.String.format;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final int DEFAULT_ROUNDS = 10;
    private long workTime;
    private long restTime;
    private TextView mTv_setWork;
    private TextView mTv_setRest;
    private TextView mTv_setNumRounds;
    private int rounds;

    private SharedPreferences sharedPreferences;

    private boolean mPrefBeepSound, mPrefTickSound;
    private String mKeyBeepSound, mKeyTickSound;
    private MenuItem beepSound, tickSound;

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(mKeyBeepSound, mPrefBeepSound);
        outState.putBoolean(mKeyTickSound, mPrefTickSound);
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveToSharedPref();
    }

    private void saveToSharedPref() {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.clear();
        editor.putBoolean(mKeyBeepSound, mPrefBeepSound);
        editor.putBoolean(mKeyTickSound, mPrefTickSound);
        editor.apply();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolbar();
        setUpFAB();
        setFieldsToResValues();
        setUpDisplay();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setUpFAB() {
        ExtendedFloatingActionButton fab = findViewById(R.id.startButton);
        fab.setOnClickListener(view -> {
            //go to the TimerActivity now
            Intent intent = new Intent(getApplicationContext(), TimerActivity.class);
            intent.putExtra("ROUNDS", rounds);
            intent.putExtra("REST_TIME", restTime);
            intent.putExtra("WORK_TIME", workTime);
            startActivity(intent);
        });
    }

    private void setFieldsToResValues() {
        mKeyBeepSound = getString(R.string.beep_sound);
        mKeyTickSound = getString(R.string.tick_sound);
        beepSound = findViewById(R.id.beep_sound);
        tickSound = findViewById(R.id.tick_sound);
    }

    /**
     * display that allows user to enter input
     */
    private void setUpDisplay() {
        //mWorkTimer = new WorkTimer(DEFAULT_WORK_MILS, COUNTDOWN_TIMER_INTERVAL_MILS);
        workTime = DEFAULT_WORK_MILS;
        restTime = DEFAULT_REST_MILS;
        rounds = DEFAULT_ROUNDS;

        mTv_setWork = findViewById(R.id.workQuantity);
        mTv_setRest = findViewById(R.id.restQuantity);
        mTv_setNumRounds = findViewById(R.id.setsQuantity);

        displayWorkTimerQuantity();
        displayRestTimerQuantity();
        displayRounds();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.beep_sound) {
            toggleMenuItems();
            mPrefBeepSound = item.isChecked();
            return true;
        } else if (id == R.id.tick_sound) {
            toggleMenuItems();
            mPrefTickSound = item.isChecked();
            return true;
        } else if (id == R.id.action_about) {
            showAbout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void toggleMenuItems() {
        beepSound.setChecked(!beepSound.isChecked());
        tickSound.setChecked(!tickSound.isChecked());
    }

    private void showAbout() {
        showInfoDialog(this, R.string.app_name, R.string.about_message);
    }

    public void decrementRounds(View view) {
        rounds--;
        displayRounds();
    }

    public void incrementRounds(View view) {
        rounds++;
        displayRounds();
    }

    public void decrementWork(View view) {
        workTime -= 1000;
        displayWorkTimerQuantity();
    }

    public void incrementWork(View view) {
        workTime += 1000;
        displayWorkTimerQuantity();
    }

    public void decrementRest(View view) {
        restTime -= 1000;
        displayRestTimerQuantity();
    }

    public void incrementRest(View view) {
        restTime += 1000;
        displayRestTimerQuantity();
    }

    @SuppressLint("DefaultLocale")
    private void displayRounds() {
        mTv_setNumRounds.setText(format("%d", rounds));
    }

    @SuppressLint("DefaultLocale")
    private void displayRestTimerQuantity() {
        int seconds = (int) restTime / 1000;
        int displayMinutes = seconds / 60;
        int displaySeconds = seconds % 60;

        if (displaySeconds < 10) {
            mTv_setRest.setText(format("%d : 0%d", displayMinutes, displaySeconds));
        } else {
            mTv_setRest.setText(format("%d : %d", displayMinutes, displaySeconds));
        }
    }

    @SuppressLint("DefaultLocale")
    private void displayWorkTimerQuantity() {
        int seconds = (int) workTime / 1000;
        int displayMinutes = seconds / 60;
        int displaySeconds = seconds % 60;

        if (displaySeconds < 10) {
            mTv_setWork.setText(format("%d : 0%d", displayMinutes, displaySeconds));
        } else {
            mTv_setWork.setText(format("%d : %d", displayMinutes, displaySeconds));
        }
    }
}