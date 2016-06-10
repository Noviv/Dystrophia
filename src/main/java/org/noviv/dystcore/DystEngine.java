package org.noviv.dystcore;

import org.noviv.dystcore.graphics.DystObject;
import java.util.ArrayList;
import org.joml.Matrix4f;
import org.lwjgl.Version;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.noviv.dystcore.accessories.system.Mouse;
import org.noviv.dystcore.graphics.shaders.Shader;
import org.noviv.dystcore.exceptions.DystException;
import org.noviv.dystcore.accessories.system.Screen;
import org.noviv.dystcore.accessories.system.Keyboard;
import org.noviv.dystcore.accessories.utilities.DystTimer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.GLUtil;
import static org.lwjgl.system.MemoryUtil.NULL;

public class DystEngine {

    private final Thread engineThread;

    private final ArrayList<DystObject> objects;
    private final DystTimer gameTimer;

    private Shader shader;
    private Matrix4f view;
    private Matrix4f projection;

    private long handle;
    private boolean running;

    public DystEngine() {
        objects = new ArrayList<>();
        gameTimer = new DystTimer();

        engineThread = new Thread(() -> {
            init();
            renderLoop();
        });
        engineThread.setName("Dystrophia Thread");
    }

    public void run() {
        System.out.println("LWJGL Version " + Version.getVersion());
        engineThread.start();
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
        GLCapabilities caps = GL.createCapabilities();

        //post init
        GLUtil.setupDebugMessageCallback(System.err);

        glClearColor(0.9f, 0.9f, 0.9f, 1.0f);

        Keyboard.init(handle);
        Screen.init(handle, width, height);
        Mouse.init(handle);
        objects.forEach((object) -> object.init());

        shader = new Shader("default");
        view = new Matrix4f().lookAt(0, 0, 5, 0, 0, 0, 0, 1, 0);
        projection = new Matrix4f().perspective((float) Math.toRadians(75.0), (float) Screen.getAspectRatio(), 0.1f, 100);

        glfwSetWindowPos(handle, Screen.getCenterX(width), Screen.getCenterY(height));

        resize();

        //finalize
        running = true;
    }

    float rotx;
    float roty;

    private void update() {
        if (Keyboard.isKeyActive(GLFW_KEY_ESCAPE)) {
            glfwSetWindowShouldClose(handle, GLFW_TRUE);
        }

        rotx += 0.002 * Mouse.getDY();
        roty += 0.002 * Mouse.getDX();

        projection.identity().perspective((float) Math.toRadians(75.0), (float) Screen.getAspectRatio(), 0.1f, 100);
        projection.rotateX(rotx);
        projection.rotateY(roty);
        objects.forEach((object) -> object.update(gameTimer.getDT()));
    }

    private void renderLoop() {
        while (running) {
            if (glfwWindowShouldClose(handle) == GLFW_TRUE) {
                running = false;
            }

            //update
            glfwPollEvents();
            update();

            //pre render
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            if (Screen.isResized()) {
                resize();
                Screen.clear();
            }

            shader.enable();
            shader.setUniform("view", view);
            shader.setUniform("projection", projection);

            //render
            objects.forEach((object) -> {
                object.render();
            });

            //post render
            shader.disable();

            glfwSwapBuffers(handle);
        }

        terminate();
    }

    private void terminate() {
        running = false;

        objects.forEach((object) -> object.terminate());

        try {
            engineThread.interrupt();
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
