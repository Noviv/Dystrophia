package org.noviv.dystcore.graphics.text;

import org.joml.Vector2d;
import org.noviv.dystcore.graphics.DystObject;

public class DystText extends DystObject {

    private Vector2d position;

    public DystText(String text, int x, int y) {
        position = new Vector2d(x, y);
    }

    @Override
    public void init() {
    }

    @Override
    public void update(double dt) {
    }

    @Override
    public void render() {
    }

    @Override
    public void terminate() {
    }
}
