package org.noviv.dystcore.graphics.data;

import java.nio.DoubleBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Mesh {

    private final int vaoID;
    private final int vboID;
    private final int iboID;

    private final int drawCount;

    public Mesh(double[] vertices, int[] indices) {
        drawCount = indices.length;

        IntBuffer idxBuffer = BufferUtils.createIntBuffer(indices.length);
        idxBuffer.put(indices);
        idxBuffer.flip();

        DoubleBuffer vtxBuffer = BufferUtils.createDoubleBuffer(vertices.length);
        vtxBuffer.put(vertices);
        vtxBuffer.flip();

        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, vtxBuffer, GL_STATIC_DRAW);
        glVertexAttribPointer(0, 3, GL_DOUBLE, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        iboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, idxBuffer, GL_STATIC_DRAW);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

        glBindVertexArray(0);
    }

    public void render() {
        glBindVertexArray(vaoID);
        glEnableVertexAttribArray(0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iboID);

        glDrawElements(GL_TRIANGLES, drawCount, GL_UNSIGNED_INT, 0);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);
    }

    public void terminate() {
        glDisableVertexAttribArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glDeleteBuffers(vboID);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glDeleteBuffers(iboID);

        glBindVertexArray(0);
        glDeleteVertexArrays(vaoID);
    }
}
