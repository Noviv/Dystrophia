package org.noviv.dystcore.accessories.system;

public class SystemAccessories {

    public static void init(long handle, int width, int height) {
        Keyboard.init(handle);
        Screen.init(handle, width, height);
        Mouse.init(handle);
    }

    public static void terminate() {
        Keyboard.terminate();
        Screen.terminate();
        Mouse.terminate();
    }

    private SystemAccessories() {
    }
}
