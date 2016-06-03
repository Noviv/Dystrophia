package org.noviv.dystcore.accessories;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;

public class Keyboard {

    private static boolean[] keys;

    private static GLFWKeyCallback keyCallback;

    public static void init(long handle) {
       keys = new boolean[GLFW.GLFW_KEY_LAST + 1];

        glfwSetKeyCallback(handle, keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                keys[key] = action != GLFW_RELEASE;
            }
        });
    }

    public static void terminate() {
        if (keyCallback != null) {
            keyCallback.free();
        }
    }
}
