package org.noviv.dystcore;

import org.noviv.dystcore.graphics.DystObject;
import java.util.ArrayList;
import org.lwjgl.Version;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLUtil;
import org.lwjgl.opengl.GLCapabilities;
import org.noviv.dystcore.accessories.system.Mouse;
import org.noviv.dystcore.graphics.shaders.Shader;
import org.noviv.dystcore.exceptions.DystException;
import org.noviv.dystcore.accessories.system.Screen;
import org.noviv.dystcore.accessories.system.Keyboard;
import org.noviv.dystcore.accessories.utilities.DystTimer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_SRGB;
import static org.lwjgl.system.MemoryUtil.NULL;

public class DystEngine {

    private final Thread engineThread;

    private final ArrayList<DystObject> objects;
    private final DystPlayer camera;
    private final DystTimer gameTimer;

    private Shader shader;

    private long handle;
    private boolean running;

    public DystEngine() {
        objects = new ArrayList<>();
        camera = new DystPlayer();
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
        glfwSetWindowPos(handle, Screen.getCenterX(width), Screen.getCenterY(height));
        glfwSetInputMode(handle, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
        glfwSwapInterval(1);
        GLCapabilities caps = GL.createCapabilities();

        //post init
        GLUtil.setupDebugMessageCallback(System.err);
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_DEPTH_TEST);
        glDepthFunc(GL_LESS);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        if (caps.OpenGL30 || caps.GL_ARB_framebuffer_sRGB || caps.GL_EXT_framebuffer_sRGB) {
            glEnable(GL_FRAMEBUFFER_SRGB);
        }

        glClearColor(0.9f, 0.9f, 0.9f, 1.0f);

        Keyboard.init(handle);
        Screen.init(handle, width, height);
        Mouse.init(handle);
        objects.forEach((object) -> object.init());

        shader = new Shader("default");

        resize();

        //finalize
        running = true;
    }

    private void update() {
        if (Keyboard.isKeyActive(GLFW_KEY_ESCAPE)) {
            glfwSetWindowShouldClose(handle, GLFW_TRUE);
        }

        camera.update(gameTimer.getDT());

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
            shader.setUniform("projection", camera.getProjection());

            //render
            objects.forEach((object) -> {
                object.render();
            });

//            post render
            shader.disable();
            glfwSwapBuffers(handle);
            gameTimer.cycle();
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
