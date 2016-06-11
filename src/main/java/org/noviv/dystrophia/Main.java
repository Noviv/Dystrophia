package org.noviv.dystrophia;

import org.joml.Vector3f;
import org.noviv.dystcore.DystEngine;

public class Main {

    public static void main(String[] args) {
        DystEngine engine = new DystEngine();
        for (int x = -5; x < 5; x++) {
            for (int y = -5; y < 5; y++) {
                engine.addObject(new Squar(new Vector3f(x * 2, -2, y * 2)));
            }
        }
        engine.run();
    }
}
