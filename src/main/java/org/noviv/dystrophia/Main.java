package org.noviv.dystrophia;

import org.joml.Vector3f;
import org.noviv.dystcore.DystEngine;

public class Main {

    public static void main(String[] args) {
        DystEngine engine = new DystEngine();

        Axis axis = new Axis();
        engine.addObject(axis);

        Squar square = new Squar();
        square.setColor(new Vector3f(0.5f, 0.1f, 0.9f));
        square.move(new Vector3f(0.5f, 0.5f, 0.5f));
        engine.addObject(square);
        
        HUD hud = new HUD();
        engine.addObject(hud);

        engine.run();
    }
}
