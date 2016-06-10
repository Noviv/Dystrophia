package org.noviv.dystcore.accessories.utilities;

public class DystTimer {

    private final double startTime;

    private double lastTime;

    private double timeTrigger;
    private double totalTrigger;

    public DystTimer() {
        startTime = getTime();
        lastTime = getTime();
        timeTrigger = -1;
    }

    public double getTime() {
        return (System.nanoTime() / 1_000_000_000.0) - startTime;
    }

    public double getDT() {
        return getTime() - lastTime;
    }

    public void cycle() {
        lastTime = getTime();
    }

    public void setTimeTrigger(double trigger) {
        timeTrigger = trigger;
        totalTrigger = timeTrigger + getTime();
    }

    public boolean isTriggered() {
        if (timeTrigger != -1 && getTime() > totalTrigger) {
            totalTrigger = timeTrigger + getTime();
            return true;
        } else {
            return false;
        }
    }
}
