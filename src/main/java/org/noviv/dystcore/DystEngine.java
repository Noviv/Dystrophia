package org.noviv.dystcore;

import org.noviv.dystcore.graphics.DystObject;
import java.util.ArrayList;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL;
import org.noviv.dystcore.graphics.shaders.Shader;
import org.noviv.dystcore.exceptions.DystException;
import org.noviv.dystcore.accessories.system.Screen;
import org.noviv.dystcore.accessories.system.Keyboard;
import org.noviv.dystcore.accessories.utilities.DystTimer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;
import org.noviv.dystcore.accessories.system.Mouse;

public class DystEngine {

    private final Thread updateThread;
    private final Thread renderThread;

    private final ArrayList<DystObject> objects;

    private Shader shader;
    private Matrix4f model;
    private Matrix4f view;
    private Matrix4f projection;

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

        if (glfwInit() != GLFW_TRUE) {
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
        Keyboard.init(handle);
        Screen.init(handle, width, height);
        Mouse.init(handle);
        objects.forEach((object) -> object.init());

        shader = new Shader("default");
        model = new Matrix4f().identity();
        view = new Matrix4f().lookAt(0, 0, 5, 0, 0, 0, 0, 1, 0);
        projection = new Matrix4f().perspective((float) Math.toRadians(75.0), (float) Screen.getAspectRatio(), 0.1f, 100);

        glfwSetWindowPos(handle, Screen.getCenterX(width), Screen.getCenterY(height));
        glfwSetInputMode(handle, GLFW_CURSOR, GLFW_CURSOR_DISABLED);

        resize();

        //finalize
        running = true;
    }

    private void updateLoop() {
        DystTimer timer = new DystTimer();

        while (running) {
            if (Keyboard.isKeyActive(GLFW_KEY_ESCAPE)) {
                glfwSetWindowShouldClose(handle, GLFW_TRUE);
            }
            if (Keyboard.isKeyActive(GLFW_KEY_W)) {
                view.translate(0, 0, 0.000001f);
            }
            if (Keyboard.isKeyActive(GLFW_KEY_S)) {
                view.translate(0, 0, -0.000001f);
            }
            if (Keyboard.isKeyActive(GLFW_KEY_A)) {
                view.translate(0.000001f, 0, 0);
            }
            if (Keyboard.isKeyActive(GLFW_KEY_D)) {
                view.translate(-0.000001f, 0, 0);
            }
            if (Keyboard.isKeyActive(GLFW_KEY_SPACE)) {
                view.translate(0, -0.000001f, 0);
            }
            if (Keyboard.isKeyActive(GLFW_KEY_LEFT_CONTROL)) {
                view.translate(0, 0.000001f, 0);
            }

            projection.rotateY((float) Math.toRadians(0.1 * Mouse.getDX()));
            projection.rotateX((float) Math.toRadians(0.1 * Mouse.getDY()));

            objects.forEach((object) -> object.update(timer.getDT()));
        }
    }

    private void renderLoop() {
        while (running) {
            if (glfwWindowShouldClose(handle) == GLFW_TRUE) {
                running = false;
            }
            //pre render
            glfwPollEvents();
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            if (Screen.isResized()) {
                resize();
                Screen.clear();
            }

            shader.enable();
            shader.setUniform("model", model);
            shader.setUniform("view", view);
            shader.setUniform("projection", projection);

            //render
            objects.forEach((object) -> object.render());

            //post render
            shader.disable();

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

        glfwDestroyWindow(handle);
        glfwTerminate();
    }

    private void resize() {
        glViewport(0, 0, Screen.getWidth(), Screen.getHeight());
    }
}
