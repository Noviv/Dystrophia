package org.noviv.dystcore.accessories;

import java.util.HashMap;
import java.util.function.Consumer;
import org.lwjgl.glfw.GLFWKeyCallback;

import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class Keyboard {

    private static HashMap<Integer, Consumer> map;

    private static GLFWKeyCallback keyCallback;

    public static void init(long handle) {
        map = new HashMap<>();

        glfwSetKeyCallback(handle, keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if (action == GLFW_RELEASE) {
                    return;
                }
                Consumer c = map.get(key);
                if (c != null) {
                    c.accept(c);
                }
            }
        });
    }

    public static void appendConsumer(int key, Consumer cons) {
        map.put(key, cons);
    }

    public static void terminate() {
        if (keyCallback != null) {
            keyCallback.free();
        }
    }
}
