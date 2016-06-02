package org.noviv.dystcore.logging;

public class DystFPSLogger {

    private static double startTime = getTime();

    private static Thread fpsThread;

    private static boolean running;

    private static int frames;
    private static double lastTime;

    public static void init() {
        running = true;

        lastTime = getTime();
        frames = 0;

        fpsThread = new Thread(() -> {
            while (running) {
                if (getTime() - lastTime >= 1.0) {
                    DystLogger.log("FPSLogger: " + frames + " FPS");
                    frames = 0;
                    lastTime = getTime();
                }
            }
        });
        fpsThread.start();
    }

    public static void trigger() {
        frames++;
    }

    public static void terminate() {
        running = false;
        try {
            fpsThread.interrupt();
        } catch (Exception e) {
            DystLogger.log(DystLogger.LEVEL_ERROR, "FPSLogger termination error", e.getMessage());
        }
    }

    public static double getTime() {
        return (System.nanoTime() / 1_000_000_000.0) - startTime;
    }

    private DystFPSLogger() {
    }
}
