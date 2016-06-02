package org.noviv.dystcore.engine;

import java.util.ArrayList;
import org.noviv.dystcore.logging.DystLogger;
import org.noviv.dystcore.accessories.Screen;
import org.noviv.dystcore.accessories.Keyboard;
import org.noviv.dystcore.accessories.Mouse;
import org.noviv.dystcore.logging.DystFPSLogger;
import org.noviv.dystcore.graphics.DystObject;
import org.lwjgl.opengl.GL;
import org.noviv.dystcore.Settings;
import org.lwjgl.opengl.GLUtil;
import org.lwjgl.opengl.GLCapabilities;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_SRGB;
import static org.lwjgl.system.MemoryUtil.NULL;

public class DystEngine {

    private final Thread engineThread;

    private final ArrayList<DystObject> objects;
    private final Settings settings;

    private boolean running;
    private double lastTime;
    private double dt;

    private int width;
    private int height;

    private long windowHandle;

    public DystEngine(Settings s) {
        objects = new ArrayList<>();
        width = s.width();
        height = s.height();
        settings = s;

        engineThread = new Thread(() -> {
            run0();
        });
        engineThread.setName("Dystrophia");
    }

    private void init() {
        glfwSetErrorCallback(DystLogger.createErrorCallback());

        if (!glfwInit()) {
            DystLogger.log(DystLogger.LEVEL_ERROR, "Could not init GLFW");
            terminate(1);
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        windowHandle = glfwCreateWindow(width, height, "Dystrophia", NULL, NULL);
        if (windowHandle == NULL) {
            DystLogger.log(DystLogger.LEVEL_ERROR, "Could not create window");
            terminate(1);
        }

        Screen.init(windowHandle);
        Mouse.init(windowHandle);
        Keyboard.init(windowHandle);
        DystFPSLogger.init();

        glfwMakeContextCurrent(windowHandle);

        if (settings.vsync()) {
            glfwSwapInterval(1);
        } else {
            glfwSwapInterval(0);
        }

        GLCapabilities caps = GL.createCapabilities();
        GLUtil.setupDebugMessageCallback(System.out);

        if (settings.polygonLines()) {
            glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        } else {
            glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
        }
        glEnable(GL_DEPTH_TEST);
        glDepthFunc(GL_LESS);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_TEXTURE_2D);
        if (caps.OpenGL30 || caps.GL_ARB_framebuffer_sRGB || caps.GL_EXT_framebuffer_sRGB) {
            glEnable(GL_FRAMEBUFFER_SRGB);
        }

        objects.forEach((object) -> object.init());

        glfwShowWindow(windowHandle);

        lastTime = DystFPSLogger.getTime();
        running = true;
    }

    public void run() {
        engineThread.start();
    }

    private void run0() {
        init();

        while (running) {
            if (glfwWindowShouldClose(windowHandle)) {
                terminate(0);
            }
            //init render
            glfwPollEvents();
            dt = DystFPSLogger.getTime() - lastTime;
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            //render
            DystFPSLogger.trigger();
            objects.forEach((object) -> {
                object.update(dt);
                object.render();
            });

            //post render
            glfwSwapBuffers(windowHandle);
            lastTime = DystFPSLogger.getTime();
            Screen.clear();
        }
    }

    public void addObject(DystObject obj) {
        objects.add(obj);
    }

    private void terminate(int status) {
        running = false;

        objects.forEach((object) -> object.terminate());
        Screen.terminate();
        Mouse.terminate();
        Keyboard.terminate();
        DystLogger.terminate();
        DystFPSLogger.terminate();

        glfwDestroyWindow(windowHandle);
        glfwTerminate();
        System.exit(status);
    }
}
