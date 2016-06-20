package org.noviv.dystrophia;

import org.joml.Vector3f;
import org.noviv.dystcore.DystEngine;
import org.noviv.dystcore.accessories.utilities.DystTimer;

public class Main {

    public static void main(String[] args) {
        DystEngine engine = new DystEngine();

        Axis axis = new Axis();
        engine.addObject(axis);

        Vector3f color1 = new Vector3f(1, 0, 0);
        Vector3f color2 = new Vector3f(0, 1, 0);

        int i = 0;
        for (int x = -5; x <= 5; x++) {
            for (int z = -5; z <= 5; z++) {
                Squar square = new Squar(false);
                square.setColor(i % 2 == 0 ? color1 : color2);
                square.move(new Vector3f(x, -1, z));
                engine.addObject(square);
                i++;
            }
        }

        Squar square = new Squar(true);
        square.move(new Vector3f(0, 0, 2));
        engine.addObject(square);

        engine.run();

        DystTimer timer = new DystTimer();
        timer.setTimeTrigger(0.01);
        while (true) {
            if (timer.isTriggered()) {
                square.rotateX(0.5f);
            }
        }
    }
}
