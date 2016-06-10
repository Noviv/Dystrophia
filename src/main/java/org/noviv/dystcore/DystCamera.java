package org.noviv.dystcore;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.noviv.dystcore.accessories.system.Keyboard;
import org.noviv.dystcore.accessories.system.Mouse;
import org.noviv.dystcore.accessories.system.Screen;

import static org.lwjgl.glfw.GLFW.*;

public class DystCamera {

    private final Vector3f position;
    private final Vector3f rotation;

    private final Matrix4f projection;

    public DystCamera() {
        position = new Vector3f();
        rotation = new Vector3f();

        projection = new Matrix4f();
    }

    public void calc() {
        rotation.x += 0.3 * Mouse.getDY();
        rotation.y += 0.3 * Mouse.getDX();

        if (Keyboard.isKeyActive(GLFW_KEY_W)) {
            position.x += Math.sin(rotation.x) * 0.01;
            position.z += Math.cos(rotation.y) * 0.01;
        }
        if (Keyboard.isKeyActive(GLFW_KEY_S)) {
            position.x -= Math.sin(rotation.x) * 0.01;
            position.z -= Math.cos(rotation.y) * 0.01;
        }
        projection.identity().perspective((float) Math.toRadians(75.0), (float) Screen.getAspectRatio(), 0.1f, 100);
        projection.rotateX((float) Math.toRadians(rotation.x));
        projection.rotateY((float) Math.toRadians(rotation.y));

        projection.translate(position);
    }

    public Matrix4f getProjection() {
        return projection;
    }
}
