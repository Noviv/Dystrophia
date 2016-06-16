package org.noviv.dystrophia;

import org.joml.Vector3f;
import org.noviv.dystcore.DystEngine;
import org.noviv.dystcore.accessories.utilities.DystTimer;

public class Main {

    public static void main(String[] args) {
        DystEngine engine = new DystEngine();
        Vector3f color1 = new Vector3f(1, 1, 1);
        Vector3f color2 = new Vector3f(0, 0, 0);

        int i = 0;
        for (int x = -5; x <= 5; x++) {
            for (int z = -5; z <= 5; z++) {
                Squar square = new Squar(i % 2 == 0 ? color1 : color2);
                engine.addObject(square);
                square.move(new Vector3f(x, -1, z));
                i++;
            }
        }

        DystTimer timer = new DystTimer();
        timer.setTimeTrigger(0.001);

        Squar square = new Squar(color2);
        square.move(new Vector3f(0, 1, 0));
        engine.addObject(square);
        engine.run();

        while (engine.isRunning()) {
            if (timer.isTriggered()) {
                square.rotateX(.1f);
                square.move(new Vector3f(0, (float) (0.005 * Math.cos(10 * timer.getTime())), 0));
            }
        }
    }
}
