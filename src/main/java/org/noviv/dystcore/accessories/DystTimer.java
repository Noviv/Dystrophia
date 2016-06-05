package org.noviv.dystcore.accessories;

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
        double dt = getTime() - lastTime;
        lastTime = getTime();
        return dt;
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
