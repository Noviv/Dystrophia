package org.noviv.dystrophia;

import org.joml.Vector3f;
import org.noviv.dystcore.DystEngine;

public class Main {

    public static void main(String[] args) {
        DystEngine engine = new DystEngine();

        Axis axis = new Axis();
        engine.addObject(axis);

        Vector3f color1 = new Vector3f(1, 0, 0);
        Vector3f color2 = new Vector3f(0, 1, 0);

        int i = 0;
        for (int x = -10; x <= 10; x += 2) {
            for (int z = -10; z <= 10; z += 2) {
                Squar square = new Squar();
                square.setColor(i % 2 == 0 ? color1 : color2);
                square.move(new Vector3f(x, -1, z));
                engine.addObject(square);
                i++;
            }
        }

        engine.run();
    }
}
