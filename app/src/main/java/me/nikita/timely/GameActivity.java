package me.nikita.timely;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {
    private ArrayList<Level> loadedLevels = new ArrayList<Level>();
    private int currentLevel, currentNumber, displayedSteps;

    private TextView timerText;

    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.setContentView(R.layout.activity_game);


        timerText = (TextView) this.findViewById(R.id.timerText);

        final View tapView = this.findViewById(R.id.tappableArea);
        tapView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timer == null) {
                    return;
                }
                timer.cancel();
                timer = null;
                if(currentNumber == 0) {
                    tapView.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));


                    startNextLevel();


                } else {
                    tapView.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
                }
            }
        });

        loadLevels();

        currentLevel = -1;
        startNextLevel();
    }

    private void loadLevels() {
        loadedLevels.add(new Level(10, 1, 3));
        loadedLevels.add(new Level(20, 2, 5));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("Cancelling timer");

        if(timer != null) {
            timer.cancel();
        }
    }

    private void startNextLevel() {
        ++currentLevel;

        if(currentLevel == loadedLevels.size()) {
            timerText.setText("Game finished!");
            return;
        }

        ((TextView) this.findViewById(R.id.levelTimer)).setText("Level "+(currentLevel + 1));
        this.findViewById(R.id.tappableArea).setBackgroundColor(Color.parseColor("#4a7fe7"));

        final Level l = loadedLevels.get(currentLevel);
        currentNumber = l.getStartingNumber();

        displayedSteps = -3;

        timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                displayedSteps++;
                if (displayedSteps <= 0) {
                    return;
                }
                currentNumber -= l.getStep();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Current number: "+currentNumber);
                        if (displayedSteps <= l.getStepsDisplayed()) {
                            timerText.setText("" + currentNumber);
                        } else {
                            timerText.setText("?");
                        }
                    }
                });
            }
        }, 0, 600);
    }
}
