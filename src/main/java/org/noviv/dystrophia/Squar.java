package org.noviv.dystrophia;

import org.joml.Vector3f;
import org.noviv.dystcore.graphics.DystObject;
import org.noviv.dystcore.graphics.buffers.MeshBuffer;

public class Squar extends DystObject {

    private final MeshBuffer mbo;

    public Squar() {
        mbo = new MeshBuffer();
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

        for (int i = 0; i < vertices.length / 3; i++) {
            vertices[i * 3 + 0] += position.x;
            vertices[i * 3 + 1] += position.y;
            vertices[i * 3 + 2] += position.z;
        }

        mbo.genVtx(vertices);
        mbo.genIdx(indices);
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
