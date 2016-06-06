package org.noviv.dystcore.accessories.system;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;

import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;

public class Screen {

    private static final GLFWVidMode primaryVidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

    private static boolean resized;
    private static int width;
    private static int height;

    private static int fbWidth;
    private static int fbHeight;

    public static void init(long handle, int iWidth, int iHeight) {
        resized = false;
        width = iWidth;
        height = iHeight;

        glfwSetWindowSizeCallback(handle, new GLFWWindowSizeCallback() {

            @Override
            public void invoke(long window, int w, int h) {
                resized = true;
                width = w;
                height = h;
            }
        });

        glfwSetFramebufferSizeCallback(handle, new GLFWFramebufferSizeCallback() {

            @Override
            public void invoke(long window, int width, int height) {
                fbWidth = width;
                fbHeight = height;
            }
        });
    }

    public static boolean isResized() {
        return resized;
    }

    public static void clear() {
        resized = false;
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

    public static double getAspectRatio() {
        return (double) getWidth() / getHeight();
    }

    public static int getFBWidth() {
        return fbWidth;
    }

    public static int getFBHeight() {
        return fbHeight;
    }

    public static int getPrimaryMonitorWidth() {
        return primaryVidMode.width();
    }

    public static int getPrimaryMonitorHeight() {
        return primaryVidMode.height();
    }

    public static int getCenterX(int width) {
        return (getPrimaryMonitorWidth() - width) / 2;
    }

    public static int getCenterY(int height) {
        return (getPrimaryMonitorHeight() - height) / 2;
    }

    private Screen() {
    }
}
