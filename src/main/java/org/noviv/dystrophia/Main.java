package org.noviv.dystrophia;

import org.noviv.dystcore.engine.DystEngine;
import org.noviv.dystcore.Settings;
import org.noviv.dystcore.graphics.text.DystText;

public class Main {

    public static void main(String[] args) throws Exception {
        DystEngine engine = new DystEngine(Settings.DEFAULT);

        DystText text = new DystText("hello rachel", 400, 400);
        engine.addObject(new Squar());
//        engine.addObject(text);
        engine.run();
    }
}
