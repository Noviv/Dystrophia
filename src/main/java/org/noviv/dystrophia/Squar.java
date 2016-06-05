package org.noviv.dystrophia;

import org.noviv.dystcore.objects.DystObject;

import static org.lwjgl.opengl.GL11.*;

public class Squar extends DystObject {

    float[] vertices = {
        -0.9f, 0.9f, 0,
        0.9f, 0.9f, 0,
        0.9f, -0.9f, 0,
        -0.9f, -0.9f, 0
    };

    @Override
    public void init() {
    }

    @Override
    public void update(double dt) {
    }

    @Override
    public void render() {
        glEnableClientState(GL_VERTEX_ARRAY);

        glVertexPointer(3, GL_FLOAT, 0, vertices);
        glDrawArrays(GL_QUADS, 0, 4);

        glDisableClientState(GL_VERTEX_ARRAY);
    }
}
