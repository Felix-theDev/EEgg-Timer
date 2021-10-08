package com.company.eggtimer;
/** A timer app that makes a sound when the timer is completed
 * @author Felix Ogbonnaya
 * @since 2020-10-13
 */
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    CountDownTimer countDown;
    SeekBar seekBar;
    TextView textView;
    Button startButton;
    Button stopButton;

    int minutes;
    int seconds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mediaPlayer = MediaPlayer.create(this, R.raw.horn);
        setContentView(R.layout.activity_main);
        seekBar = findViewById(R.id.seekBar);
        textView = findViewById(R.id.countDown);
        startButton = findViewById(R.id.start);
        stopButton = findViewById(R.id.stop);

        seekBar.setProgress(30);
        getTimeRemaining(seekBar.getProgress());
        updateTime();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                getTimeRemaining(i);
                updateTime();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }


    public void startTimer(View view){
        if(minutes != 0 || seconds != 0) {

            seekBar.setEnabled(false);
            countDown = new CountDownTimer(seekBar.getProgress() * 1000 + 100, 1000) {
                public void onTick(long timeRemaining) {
                    startButton.setVisibility(View.INVISIBLE);
                    stopButton.setVisibility(View.VISIBLE);

                    getTimeRemaining((int) timeRemaining / 1000);
                    updateTime();
                }

                public void onFinish() {
                    mediaPlayer.start();
                    seekBar.setProgress(30);
                    seekBar.setEnabled(true);
                    stopButton.setVisibility(View.INVISIBLE);
                    startButton.setVisibility(View.VISIBLE);

                }
            }.start();
        }
    }

    public void stopTimer(View view){
        countDown.cancel();
        seekBar.setProgress(30);
        seconds= 30;
        updateTime();
        stopButton.setVisibility(View.INVISIBLE);
        startButton.setVisibility(View.VISIBLE);
        seekBar.setEnabled(true);
    }

    public void getTimeRemaining(int time){
        minutes = time / 60;
        seconds = time % 60;

    }

    public void updateTime(){
        textView.setText(String.format("%d:%02d", minutes, seconds));
    }
}
