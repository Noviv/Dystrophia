package org.noviv.dystrophia;

import org.noviv.dystcore.graphics.DystObject;

import static org.lwjgl.opengl.GL11.*;

public class HUD extends DystObject {

    @Override
    public void init() {
    }

    @Override
    public void update(double dt) {
    }

    @Override
    public void render() {
        glColor3f(0, 1, 0);
        glBegin(GL_QUADS);
        glVertex2f(0.0f, 0.0f);
        glVertex2f(0.5f, 0.0f);
        glVertex2f(0.5f, 0.5f);
        glVertex2f(0.0f, 0.5f);
        glEnd();
    }

    @Override
    public void terminate() {
    }
}
