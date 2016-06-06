package org.noviv.dystcore.graphics.buffers;

import java.nio.DoubleBuffer;
import org.lwjgl.BufferUtils;
import org.noviv.dystcore.exceptions.DystException;

import static org.lwjgl.opengl.GL15.*;

public class VertexBufferObject {

    private int id;

    public VertexBufferObject() {
        id = -1;
    }

    public void gen(double[] values) {
        if (id != -1) {
            throw new DystException("VBO already generated");
        }

        DoubleBuffer buffer = BufferUtils.createDoubleBuffer(values.length);
        buffer.put(values);
        buffer.flip();

        id = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, id);
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public void enable() {
        glBindBuffer(GL_ARRAY_BUFFER, id);
    }

    public void disable() {
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }
}
