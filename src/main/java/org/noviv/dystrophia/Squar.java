package org.noviv.dystrophia;

import org.noviv.dystcore.graphics.DystObject;
import org.noviv.dystcore.graphics.data.MeshBuffer;

public class Squar extends DystObject {

    private final MeshBuffer mbo;

    public Squar() {
        mbo = new MeshBuffer();
    }

    @Override
    public void init() {
        double[] vertices = {
            // front
            -0.5, -0.5, 0.5,
            0.5, -0.5, 0.5,
            0.5, 0.5, 0.5,
            -0.5, 0.5, 0.5,
            // back
            -0.5, -0.5, -0.5,
            0.5, -0.5, -0.5,
            0.5, 0.5, -0.5,
            -0.5, 0.5, -0.5
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
