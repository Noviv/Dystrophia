package org.noviv.dystcore;

import org.joml.Matrix4f;
import org.joml.Vector3d;
import org.joml.Vector3f;
import org.noviv.dystcore.accessories.system.Keyboard;
import org.noviv.dystcore.accessories.system.Mouse;
import org.noviv.dystcore.accessories.system.Screen;

import static org.lwjgl.glfw.GLFW.*;
import org.noviv.dystcore.accessories.utilities.DystUtil;

public class DystPlayer {

    private final Vector3f position;
    private final Vector3d velocity;
    private double yaw;
    private double pitch;

    private double speed;

    private final Vector3f t_position;
    private final Matrix4f projection;

    public DystPlayer() {
        position = new Vector3f();
        velocity = new Vector3d();

        speed = 0.08;

        t_position = new Vector3f();
        projection = new Matrix4f();
        
        position.z = -2;
    }

    public void update(double dt) {
        pitch += Mouse.getDY();
        yaw += Mouse.getDX();

        pitch = DystUtil.limit(-90, pitch, 80);

        if (Keyboard.isKeyActive(GLFW_KEY_W)) {
            position.x -= speed * Math.sin(Math.toRadians(yaw));
            velocity.x = speed * Math.sin(Math.toRadians(yaw));
            position.z += speed * Math.cos(Math.toRadians(yaw));
            velocity.z = speed * Math.cos(Math.toRadians(yaw));
        }
        if (Keyboard.isKeyActive(GLFW_KEY_S)) {
            position.x += speed * Math.sin(Math.toRadians(yaw));
            velocity.x = speed * Math.sin(Math.toRadians(yaw));
            position.z -= speed * Math.cos(Math.toRadians(yaw));
            velocity.z = speed * Math.cos(Math.toRadians(yaw));
        }
        if (Keyboard.isKeyActive(GLFW_KEY_A)) {
            position.x -= speed * Math.sin(Math.toRadians(yaw - 90));
            velocity.x = speed * Math.sin(Math.toRadians(yaw - 90));
            position.z += speed * Math.cos(Math.toRadians(yaw - 90));
            velocity.z = speed * Math.cos(Math.toRadians(yaw - 90));
        }
        if (Keyboard.isKeyActive(GLFW_KEY_D)) {
            position.x -= speed * Math.sin(Math.toRadians(yaw + 90));
            velocity.x = speed * Math.sin(Math.toRadians(yaw + 90));
            position.z += speed * Math.cos(Math.toRadians(yaw + 90));
            velocity.z = speed * Math.cos(Math.toRadians(yaw + 90));
        }
        if (Keyboard.isKeyActive(GLFW_KEY_SPACE)) {
            velocity.y = 100;
        }
        if (Mouse.isMouseLeftButton()) {
            System.out.println(position.x);
        }
        if (position.y > 0) {
            velocity.y -= 1000 * 0.005;
        } else {
            position.y = 0;
        }
        position.y += velocity.y * 0.001;

        projection.identity().perspective((float) Math.toRadians(75.0), (float) Screen.getAspectRatio(), 0.1f, 100);
        projection.rotateX((float) Math.toRadians(pitch));
        projection.rotateY((float) Math.toRadians(yaw));

        t_position.set(position);
        t_position.y *= -1;
        projection.translate(t_position);
    }

    public Matrix4f getProjection() {
        return projection;
    }
}
