package org.noviv.dystrophia.objects.subobjects;

import org.noviv.dystcore.graphics.DystObject;

import static org.lwjgl.opengl.GL11.*;

public class DystRect extends DystObject {

    private float centerX;
    private float centerY;
    private float width;
    private float height;

    public DystRect() {
        this(0.05f, 0.05f);
    }

    public DystRect(float w, float h) {
        this(w, h, -0.9f, 0.9f);
    }

    public DystRect(float w, float h, float cX, float cY) {
        centerX = cX;
        centerY = cY;
        width = w;
        height = h;
    }

    @Override
    public void init() {
    }

    @Override
    public void update(double dt) {
    }

    @Override
    public void render() {
        glColor3f(1.f, 0, 0);

        glBegin(GL_QUADS);
        glVertex2f(centerX + width / 2, centerY + height / 2);
        glVertex2f(centerX - width / 2, centerY + height / 2);
        glVertex2f(centerX - width / 2, centerY - height / 2);
        glVertex2f(centerX + width / 2, centerY - height / 2);
        glEnd();
    }

    @Override
    public void terminate() {
    }
}
