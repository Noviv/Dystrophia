package org.noviv.dystrophia;

import java.nio.DoubleBuffer;
import java.nio.IntBuffer;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.noviv.dystcore.graphics.DystObject;
import org.noviv.dystcore.graphics.buffers.MeshBufferObject;

public class Squar extends DystObject {

    private MeshBufferObject mbo;

    public Squar(Vector3f pos, Vector3f col) {
        position = pos;
        color = col;

        mbo = new MeshBufferObject();
    }

    @Override
    public void init() {
        double[] vertices = {
            // front
            -1.0, -1.0, 1.0,
            1.0, -1.0, 1.0,
            1.0, 1.0, 1.0,
            -1.0, 1.0, 1.0,
            // back
            -1.0, -1.0, -1.0,
            1.0, -1.0, -1.0,
            1.0, 1.0, -1.0,
            -1.0, 1.0, -1.0
        };

        int[] indices = {
            // front
            0, 1, 2,
            2, 3, 0,
            // top
            1, 5, 6,
            6, 2, 1,
            // back
            7, 6, 5,
            5, 4, 7,
            // bottom
            4, 0, 3,
            3, 7, 4,
            // left
            4, 5, 1,
            1, 0, 4,
            // right
            3, 2, 6,
            6, 7, 3
        };

        double[] colors = {
            1.0, 0.0, 0.0, 1.0,
            1.0, 0.0, 0.0, 1.0,
            1.0, 0.0, 0.0, 1.0,
            1.0, 0.0, 0.0, 1.0,
            1.0, 0.0, 0.0, 1.0,
            1.0, 0.0, 0.0, 1.0,
            1.0, 0.0, 0.0, 1.0,
            1.0, 0.0, 0.0, 1.0
        };

        for (int i = 0; i < vertices.length / 3; i++) {
            vertices[i * 3 + 0] += position.x;
            vertices[i * 3 + 1] += position.y;
            vertices[i * 3 + 2] += position.z;
        }

        for (int i = 0; i < colors.length / 4; i++) {
            colors[i * 4 + 0] = color.x;
            colors[i * 4 + 1] = color.y;
            colors[i * 4 + 2] = color.z;
        }

        mbo.genVtx(vertices);
        mbo.genIdx(indices);
        mbo.genCol(colors);
        mbo.init();
    }

    @Override
    public void update(double dt) {
    }

    @Override
    public void render() {
        mbo.render();
    }

    @Override
    public void terminate() {
    }
}
