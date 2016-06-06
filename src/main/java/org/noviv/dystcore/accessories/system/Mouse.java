package org.noviv.dystcore.accessories.system;

import java.nio.DoubleBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWCursorEnterCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

import static org.lwjgl.glfw.GLFW.*;

public class Mouse {

    private static double x;
    private static double y;
    private static double prevX;
    private static double prevY;

    private static boolean inWindow;
    private static boolean leftMouseButton;
    private static boolean rightMouseButton;

    public static void init(long handle) {
        DoubleBuffer xb = BufferUtils.createDoubleBuffer(1);
        DoubleBuffer yb = BufferUtils.createDoubleBuffer(1);
        
        glfwGetCursorPos(handle, xb, yb);
        
        prevX = x = xb.get();
        prevY = y = yb.get();
        
        glfwSetCursorPosCallback(handle, new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
                x = xpos;
                y = ypos;
            }
        });
        glfwSetCursorEnterCallback(handle, new GLFWCursorEnterCallback() {
            @Override
            public void invoke(long window, int entered) {
                inWindow = entered == GLFW_TRUE;
            }
        });
        glfwSetMouseButtonCallback(handle, new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                leftMouseButton = button == GLFW_MOUSE_BUTTON_1 && action == GLFW_PRESS;
                rightMouseButton = button == GLFW_MOUSE_BUTTON_2 && action == GLFW_PRESS;
            }
        });
    }

    public static double getX() {
        return x;
    }

    public static double getY() {
        return y;
    }

    public static double getDX() {
        double dx = x - prevX;
        prevX = x;
        return dx;
    }

    public static double getDY() {
        double dy = y - prevY;
        prevY = y;
        return dy;
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

    private Mouse() {
    }
}
