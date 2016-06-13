package org.noviv.dystcore.graphics.buffers;

import java.nio.DoubleBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class MeshBufferObject {

    private DoubleBuffer vtxBuffer;
    private IntBuffer idxBuffer;
    private DoubleBuffer colBuffer;

    private int vaoID;
    private int vboID;
    private int iboID;
    private int cboID;

    private int drawCount;

    public void genVtx(double[] vertices) {
        vtxBuffer = BufferUtils.createDoubleBuffer(vertices.length);
        vtxBuffer.put(vertices);
        vtxBuffer.flip();
    }

    public void genIdx(int[] indices) {
        drawCount = indices.length;

        idxBuffer = BufferUtils.createIntBuffer(indices.length);
        idxBuffer.put(indices);
        idxBuffer.flip();
    }

    public void genCol(double[] colors) {
        colBuffer = BufferUtils.createDoubleBuffer(colors.length);
        colBuffer.put(colors);
        colBuffer.flip();
    }

    public void init() {
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, vtxBuffer, GL_STATIC_DRAW);
        glVertexAttribPointer(0, 3, GL_DOUBLE, false, 0, 0);

        iboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, idxBuffer, GL_STATIC_DRAW);

        cboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, cboID);
        glBufferData(GL_ARRAY_BUFFER, colBuffer, GL_STATIC_DRAW);
        glVertexAttribPointer(1, 4, GL_DOUBLE, false, 0, 0);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    public void render() {
        glBindVertexArray(vaoID);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iboID);

        glDrawElements(GL_TRIANGLES, drawCount, GL_UNSIGNED_INT, 0);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);
    }
}
