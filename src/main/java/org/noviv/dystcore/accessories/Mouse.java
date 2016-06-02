package org.noviv.dystcore.accessories;

import org.joml.Vector2d;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWCursorEnterCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

import static org.lwjgl.glfw.GLFW.*;

public class Mouse {

    private static Vector2d currentPos;

    private static boolean inWindow;
    private static boolean leftMouseButton;
    private static boolean rightMouseButton;

    private static GLFWCursorPosCallback cursorPosCallback;
    private static GLFWCursorEnterCallback cursorEnterCallback;
    private static GLFWMouseButtonCallback mouseButtonCallback;

    public static void init(long handle) {
        currentPos = new Vector2d();

        glfwSetCursorPosCallback(handle, cursorPosCallback = new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
                currentPos.x = xpos;
                currentPos.y = ypos;
            }
        });
        glfwSetCursorEnterCallback(handle, cursorEnterCallback = new GLFWCursorEnterCallback() {
            @Override
            public void invoke(long window, boolean entered) {
                inWindow = entered;
            }
        });
        glfwSetMouseButtonCallback(handle, mouseButtonCallback = new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                leftMouseButton = button == GLFW_MOUSE_BUTTON_1 && action == GLFW_PRESS;
                rightMouseButton = button == GLFW_MOUSE_BUTTON_2 && action == GLFW_PRESS;
            }
        });
    }

    public static double getX() {
        return currentPos.x;
    }

    public static double getY() {
        return currentPos.y;
    }

    public static boolean inWindow() {
        return inWindow;
    }

    public static boolean isMouseLeftButton() {
        return leftMouseButton;
    }

    public static boolean isMouseRightButton() {
        return rightMouseButton;
    }

    public static void terminate() {
        if (cursorPosCallback != null) {
            cursorPosCallback.free();
        }
        if (cursorEnterCallback != null) {
            cursorEnterCallback.free();
        }
        if (mouseButtonCallback != null) {
            mouseButtonCallback.free();
        }
    }

    private Mouse() {
    }
}
