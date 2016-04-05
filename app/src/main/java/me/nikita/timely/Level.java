package me.nikita.timely;

/**
 * Created by nikita on 4/5/16.
 */
public class Level {

    private int startingNumber;
    private int stepsDisplayed;
    private int step;

    public Level(int startingNumber, int step, int stepsDisplayed) {
        this.startingNumber = startingNumber;
        this.step = step;
        this.stepsDisplayed = stepsDisplayed;
    }

    public int getStartingNumber() {
        return startingNumber;
    }

    public int getStep() {
        return step;
    }

    public int getStepsDisplayed() {
        return stepsDisplayed;
    }

}
