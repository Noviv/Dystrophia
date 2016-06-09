package org.noviv.dystcore.accessories.system;

import org.lwjgl.glfw.GLFWKeyCallback;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_LAST;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;

public class Keyboard {

    private static boolean[] actions;
    
    private static GLFWKeyCallback kCallback;

    public static void init(long handle) {
        actions = new boolean[GLFW_KEY_LAST + 1];

        glfwSetKeyCallback(handle, kCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                actions[key] = action != GLFW_RELEASE;
            }
        });
    }

    public static boolean isKeyActive(int key) {
        return actions[key];
    }

    private Keyboard() {
    }
}
