package org.noviv.dystrophia;

import org.joml.Vector3f;
import org.noviv.dystcore.DystEngine;
import org.noviv.dystcore.graphics.DystText;

public class Main {

    public static void main(String[] args) {
        DystEngine engine = new DystEngine();

        Axis axis = new Axis();
        engine.addObject3D(axis);

        Squar square = new Squar();
        square.setColor(new Vector3f(0.5f, 0.1f, 0.9f));
        square.move(new Vector3f(0.5f, 0.5f, 0.5f));
        engine.addObject3D(square);
        
        DystText text = new DystText("hi", 0, 0);
        engine.addObject2D(text);

        engine.run();
    }
}
