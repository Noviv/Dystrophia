package org.noviv.dystrophia;

import org.joml.Vector3f;
import org.noviv.dystcore.DystEngine;
import org.noviv.dystcore.accessories.utilities.DystTimer;

public class Main {

    public static void main(String[] args) {
        DystEngine engine = new DystEngine();

        Axis axis = new Axis();
        engine.addObject(axis);

        Squar square = new Squar();
        square.setColor(new Vector3f(0.5f, 0.1f, 0.9f));
        square.move(new Vector3f(0.5f, 0.5f, 0.5f));
        engine.addObject(square);

        engine.run();

        float finalPosition = 45;

        float finalTime = 3;

        DystTimer printTimer = new DystTimer();
        printTimer.setTimeTrigger(1.0);

        DystTimer animationTimer = new DystTimer();
        animationTimer.setTimeTrigger(1.0 / 10000.0);

        while (animationTimer.getTime() <= finalTime) {
            if (printTimer.isTriggered()) {
                System.out.println(square.getRotation().x + " @ " + animationTimer.getTime());
            }
            if (animationTimer.isTriggered()) {
                square.rotateX((float) animationTimer.getDT() * finalPosition / finalTime);
            }
        }
        
        square.rotateX(finalPosition - square.getRotation().x);

        System.out.println(square.getRotation().x);
    }
}
