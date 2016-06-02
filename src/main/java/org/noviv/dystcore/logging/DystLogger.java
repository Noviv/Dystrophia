package org.noviv.dystcore.logging;

import java.util.Date;
import java.util.Map;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.system.APIUtil;

public class DystLogger {

    public static final LogLevel LEVEL_MESSAGE = new LogLevel(0, "MESSAGE");
    public static final LogLevel LEVEL_GAME = new LogLevel(1, "GAME");
    public static final LogLevel LEVEL_ERROR = new LogLevel(2, "ERROR");

    private static class LogLevel {

        public final int call;
        public final String name;

        public LogLevel(int c, String n) {
            call = c;
            name = n;
        }
    }

    private static final Map<Integer, String> ERROR_CODES = APIUtil.apiClassTokens((field, value) -> 0x10000 < value && value < 0x20000, null, GLFW.class);
    private static GLFWErrorCallback errorCallback;

    public static void log(String... msgs) {
        log(LEVEL_MESSAGE, msgs);
    }

    public static void log(LogLevel level, String... msgs) {
        System.out.println("-----[" + level.name + " @ " + new Date(System.currentTimeMillis()).toString() + "]-----");
        for (String msg : msgs) {
            System.out.println(msg);
        }
    }

    public static GLFWErrorCallback createErrorCallback() {
        if (errorCallback == null) {
            return errorCallback = new GLFWErrorCallback() {
                @Override
                public void invoke(int error, long description) {
                    log(LEVEL_ERROR, "LWJGL error: " + ERROR_CODES.getOrDefault(error, "UNKNOWN"), "Description : " + org.lwjgl.glfw.GLFWErrorCallback.getDescription(description));
                }
            };
        } else {
            return errorCallback;
        }
    }

    public static void terminate() {
        if (errorCallback != null) {
            errorCallback.free();
        }
    }
}
