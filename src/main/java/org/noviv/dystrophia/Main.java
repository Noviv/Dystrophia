package org.noviv.dystrophia;

import org.noviv.dystcore.DystEngine;

public class Main {

    public static void main(String[] args) {
        DystEngine engine = new DystEngine();
        engine.addObject(new Squar());
        engine.run();
    }
}
