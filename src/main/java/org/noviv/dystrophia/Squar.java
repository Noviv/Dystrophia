package org.noviv.dystrophia;

import org.noviv.dystcore.graphics.data.Mesh;
import org.noviv.dystcore.graphics.shaders.Shader;
import org.noviv.dystcore.graphics.DystObject;

public class Squar extends DystObject {

    private Mesh mesh;
    private Shader shader;

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

        mesh = new Mesh(vertices, indices);
        shader = new Shader("default");
    }

    @Override
    public void update(double dt) {
    }

    @Override
    public void render() {
        shader.enable();
        mesh.render();
        shader.disable();
    }

    @Override
    public void terminate() {
        mesh.terminate();
        shader.terminate();
    }
}
