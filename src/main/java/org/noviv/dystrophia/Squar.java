package org.noviv.dystrophia;

import org.noviv.dystcore.graphics.DystObject;
import org.noviv.dystcore.graphics.buffers.VertexBufferObject;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

public class Squar extends DystObject {

    private VertexBufferObject vbo;

    @Override
    public void init() {
        double[] vertices = {
            -1.0, -1.0, 0.0,
            1.0, -1.0, 0.0,
            0.0, 1.0, 0.0
        };
        vbo = new VertexBufferObject();
        vbo.gen(vertices);
    }

    @Override
    public void update(double dt) {
    }

    @Override
    public void render() {
        glEnableVertexAttribArray(0);
        vbo.enable();

        glVertexAttribPointer(0, 3, GL_DOUBLE, false, 0, 0);
        glDrawArrays(GL_TRIANGLES, 0, 3);

        vbo.disable();
        glDisableVertexAttribArray(0);
    }
}
