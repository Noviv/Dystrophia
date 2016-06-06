package org.noviv.dystcore.accessories.system;

import org.lwjgl.glfw.GLFWKeyCallback;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_LAST;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_REPEAT;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;

public class Keyboard {

    private static int[] actions;

    private static GLFWKeyCallback keyCallback;

    public static void init(long handle) {
        actions = new int[GLFW_KEY_LAST + 1];

        glfwSetKeyCallback(handle, keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                actions[key] = action;
            }
        });
    }

    public static boolean isKeyPressed(int key) {
        return actions[key] == GLFW_PRESS;
    }

    public static boolean isKeyHeld(int key) {
        return actions[key] == GLFW_REPEAT;
    }

    public static void terminate() {
    }

    private Keyboard() {
    }
}
