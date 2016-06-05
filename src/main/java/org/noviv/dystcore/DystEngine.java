package org.noviv.dystcore;

import java.util.ArrayList;
import org.lwjgl.opengl.GL;
import org.noviv.dystcore.accessories.DystTimer;
import org.noviv.dystcore.objects.DystObject;
import org.noviv.dystcore.accessories.Keyboard;
import org.noviv.dystcore.exceptions.DystException;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;
import org.noviv.dystcore.accessories.Screen;
import org.noviv.dystcore.accessories.SystemAccessories;

public class DystEngine {

    private final Thread updateThread;
    private final Thread renderThread;

    private final ArrayList<DystObject> objects;

    private long handle;
    private boolean running;

    public DystEngine() {
        objects = new ArrayList<>();

        updateThread = new Thread(() -> {
            updateLoop();
        });
        updateThread.setName("Dystrophia Update Thread");

        renderThread = new Thread(() -> {
            init();
            updateThread.start();
            renderLoop();
        });
        renderThread.setName("Dystrophia Render Thread");
    }

    public void run() {
        renderThread.start();
    }

    public void addObject(DystObject object) {
        if (object == null) {
            throw new DystException("Added a null object");
        }
        objects.add(object);
    }

    private void init() {
        int width = 1280;
        int height = 720;

        if (!glfwInit()) {
            throw new DystException("Could not init GLFW");
        }

        //init
        handle = glfwCreateWindow(width, height, "Title", NULL, NULL);
        if (handle == NULL) {
            throw new DystException("Could not create window");
        }

        glfwMakeContextCurrent(handle);
        GL.createCapabilities();

        //post init
        resize();

        glfwSetWindowPos(handle, Screen.getCenterX(width), Screen.getCenterY(height));

        //finalize
        SystemAccessories.init(handle, width, height);
        objects.forEach((object) -> object.init());
        running = true;
    }

    private void updateLoop() {
        DystTimer timer = new DystTimer();

        while (running) {
            objects.forEach((object) -> object.update(timer.getDT()));
        }
    }

    private void renderLoop() {
        while (running) {
            if (glfwWindowShouldClose(handle)) {
                running = false;
            }
            //pre render
            glfwPollEvents();
            glClear(GL_COLOR_BUFFER_BIT);

            if (Screen.isResized()) {
                resize();
                Screen.clear();
            }

            //render
            objects.forEach((object) -> object.render());

            //post render
            glfwSwapBuffers(handle);
        }

        terminate();
    }

    private void terminate() {
        running = false;

        try {
            renderThread.interrupt();
            updateThread.interrupt();
        } catch (Exception e) {
            throw new DystException(e);
        }

        SystemAccessories.terminate();

        glfwDestroyWindow(handle);
        glfwTerminate();
    }

    private void resize() {
        glViewport(0, 0, Screen.getWidth(), Screen.getHeight());
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        if (Screen.getAspectRatio() >= 1.0) {
            glOrtho(-Screen.getAspectRatio(), Screen.getAspectRatio(), -1, 1, 0, 1);
        } else {
            glOrtho(1.0 / -Screen.getAspectRatio(), 1.0 / Screen.getAspectRatio(), -1, 1, 0, 1);
        }
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
    }
}
