package com.example.chronometershowcase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText stopTimeEditText, secondsStart;
    Chronometer chronometer;
    Button ms10Button, ms100Button, ms1000Button, applyButton;
    CheckBox countBackwardsCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setViews();
        ms10Button.setOnClickListener(onTimeChangeClick);
        ms100Button.setOnClickListener(onTimeChangeClick);
        ms1000Button.setOnClickListener(onTimeChangeClick);

        applyButton.setOnClickListener(onApplyButtonClick);

        chronometer.setOnClickListener(onChronometerClick);
    }

    View.OnClickListener onTimeChangeClick = v -> {
            switch (v.getId()) {
                case R.id.ms10Button:
                    chronometer.setDelayMillis(10);
                    break;
                case R.id.ms100Button:
                    chronometer.setDelayMillis(100);
                    break;
                case R.id.ms1000Button:
                    chronometer.setDelayMillis(1000);
                    break;
            }
        };

        //  restarts the chronometer
    View.OnClickListener onChronometerClick = v -> {
        chronometer.stop();
        chronometer.init();
        applyButton.setText("start");
    };

    View.OnClickListener onApplyButtonClick = v -> {
        if(chronometer.isStarted()) {
            chronometer.stop();
            applyButton.setText("start");
        }
        else {
                //  sets the chronometer to have a previous value
            chronometer.setBaseWithCurrentTime(chronometer.getTimeElapsed() * (-1));
            if(stopTimeEditText.getText().length() != 0) {
                    //  stops the chronometer after set time elapses
                int stopAfterTime = Integer.parseInt(stopTimeEditText.getText().toString());
                chronometer.observeIfTimeFinished(stopAfterTime).observe(this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        chronometer.stop();
                        applyButton.setText("start");
                            //  removes time in which the chronometer stops
                        chronometer.removeTimeStop();
                    }
                });
            }
            Boolean backwards = countBackwardsCheckBox.isChecked();
            if(backwards) {
                if(secondsStart.getText().length() != 0) {
                    int startFrom = Integer.parseInt(secondsStart.getText().toString());
                    chronometer.startBackwards(startFrom);
                }
                else {
                        //  sets the chronometer to have a previous value
                    long modifier = chronometer.getTimeElapsed();
                    chronometer.startBackwards();
                    chronometer.setBaseWithCurrentTime(modifier);
                }
            }
            else
                chronometer.setBackwards(false);

                //  starts the chronometer with the settings from above
            chronometer.start();
            applyButton.setText("stop");
        }
    };


    private void setViews() {
        stopTimeEditText = findViewById(R.id.stopTimeEditText);
        secondsStart = findViewById(R.id.secondsStart);
        chronometer = findViewById(R.id.chronometer);
        ms10Button = findViewById(R.id.ms10Button);
        ms100Button = findViewById(R.id.ms100Button);
        ms1000Button = findViewById(R.id.ms1000Button);
        applyButton = findViewById(R.id.applyButton);
        countBackwardsCheckBox = findViewById(R.id.countBackwardsCheckBox);
    }
}






















