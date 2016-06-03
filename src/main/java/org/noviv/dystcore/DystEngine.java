package org.noviv.dystcore;

import org.lwjgl.opengl.GL;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;

import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.GLFWKeyCallback;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class DystEngine {

    private long handle;
    private boolean running;

    private int width;
    private int height;
    private boolean resized;

    public DystEngine() {
        width = 640;
        height = 480;
        resized = false;

        init();
    }

    private void init() {
        glfwInit();
        handle = glfwCreateWindow(width, height, "Title", NULL, NULL);
        glfwMakeContextCurrent(handle);
        GL.createCapabilities();

        glfwSetFramebufferSizeCallback(handle, new GLFWFramebufferSizeCallback() {
            @Override
            public void invoke(long window, int w, int h) {
                width = w;
                height = h;
                resized = true;
            }
        });

        glfwSetKeyCallback(handle, new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
            }
        });

        glClearColor(0.2f, 0.3f, 0.3f, 1.0f);

        running = true;
    }

    public void run() {
        while (running) {
            if (glfwWindowShouldClose(handle)) {
                running = false;
            }
            prerender();
            render();
            postrender();
        }

        terminate();
    }

    private void prerender() {
        if (resized) {
            resized = false;
            glViewport(0, 0, width, height);
        }

        glfwPollEvents();
        glClear(GL_COLOR_BUFFER_BIT);
    }

    private void render() {
    }

    private void postrender() {
        glfwSwapBuffers(handle);
    }

    private void terminate() {
        glfwTerminate();
    }
}
