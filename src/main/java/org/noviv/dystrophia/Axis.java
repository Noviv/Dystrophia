package org.noviv.dystrophia;

import org.noviv.dystcore.graphics.DystObject;

import static org.lwjgl.opengl.GL11.*;

public class Axis extends DystObject {

    @Override
    public void init() {
    }

    @Override
    public void update(double dt) {
    }

    @Override
    public void render() {
        glColor3f(1.0f, 0.0f, 0.0f);
        glBegin(GL_LINES);
        glVertex3f(-10, 0, -.01f);
        glVertex3f(10, 0, -.01f);
        glVertex3f(-10, 0, 0);
        glVertex3f(10, 0, 0);
        glVertex3f(-10, 0, 0.01f);
        glVertex3f(10, 0, 0.01f);

        glVertex3f(-.01f, -10, 0);
        glVertex3f(-.01f, 10, 0);
        glVertex3f(0, -10, 0);
        glVertex3f(0, 10, 0);
        glVertex3f(0.01f, -10, 0);
        glVertex3f(0.01f, 10, 0);

        glVertex3f(-.01f, 0, -10);
        glVertex3f(-.01f, 0, 10);
        glVertex3f(0, 0, -10);
        glVertex3f(0, 0, 10);
        glVertex3f(0.01f, 0, -10);
        glVertex3f(0.01f, 0, 10);
        glEnd();
    }

    @Override
    public void terminate() {
    }
}