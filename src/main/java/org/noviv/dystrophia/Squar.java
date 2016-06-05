package org.noviv.dystrophia;

import java.nio.DoubleBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.noviv.dystcore.graphics.DystObject;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

public class Squar extends DystObject {

    private int vboID;

    @Override
    public void init() {
        double[] vertices = {
            -1.0, -1.0, 0.0,
            1.0, -1.0, 0.0,
            0.0, 1.0, 0.0
        };

        DoubleBuffer vtxBuffer = BufferUtils.createDoubleBuffer(vertices.length);
        vtxBuffer.put(vertices);
        vtxBuffer.flip();

        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, vtxBuffer, GL_STATIC_DRAW);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    @Override
    public void update(double dt) {
    }

    @Override
    public void render() {
        glEnableVertexAttribArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glVertexAttribPointer(0, 3, GL_DOUBLE, false, 0, 0);

        glDrawArrays(GL11.GL_TRIANGLES, 0, 3);
        glDisableVertexAttribArray(0);
    }
}
