package org.noviv.dystrophia;

import org.apache.commons.lang.math.RandomUtils;
import org.joml.Vector3f;
import org.noviv.dystcore.DystEngine;

public class Main {

    public static void main(String[] args) {
        DystEngine engine = new DystEngine();
        Vector3f color1 = new Vector3f(1, 1, 1);
        Vector3f color2 = new Vector3f(0, 0, 0);
        int i = 0;
        for (int x = -5; x <= 5; x++) {
            for (int y = -5; y <= 5; y++) {
                i++;
                engine.addObject(new Squar(new Vector3f(x * 2, -2, y * 2), i % 2 == 0 ? color2 : color1));
            }
        }
        
        for (int k = 0; k < 10; k++) {
            engine.addObject(new Squar(new Vector3f(gen(), gen(), gen()), color2));
        }
        engine.run();
    }
    
    public static int gen() {
        return RandomUtils.nextInt(10);
    }
}
