package org.noviv.dystrophia;

import org.noviv.dystrophia.objects.Squar;
import org.noviv.dystrophia.objects.HUD;
import org.noviv.dystrophia.objects.Axis;
import org.joml.Vector3f;
import org.noviv.dystcore.DystEngine;

public class Main {

    public static void main(String[] args) {
        DystEngine engine = new DystEngine();

        Axis axis = new Axis();
        engine.addObject3D(axis);

        Squar square = new Squar();
        square.setColor(new Vector3f(0.5f, 0.1f, 0.9f));
        square.move(new Vector3f(0.5f, 0.5f, 0.5f));
        engine.addObject3D(square);

        HUD hud = new HUD();
        engine.addObject2D(hud);

        engine.run();
    }
}
