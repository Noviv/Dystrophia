package org.noviv.dystcore;

public class Settings {

    public static final Settings DEFAULT = new Settings();

    private boolean vsync;
    private boolean polygonLines;

    private int width;
    private int height;

    public Settings() {
        vsync = true;
        polygonLines = false;

        width = 640;
        height = 480;
    }

    //vsync
    public Settings vsync(boolean v) {
        vsync = v;
        return this;
    }

    public boolean vsync() {
        return vsync;
    }

    //polygonLines
    public Settings polygonLines(boolean p) {
        polygonLines = p;
        return this;
    }

    public boolean polygonLines() {
        return polygonLines;
    }

    //width
    public Settings width(int w) {
        width = w;
        return this;
    }

    public int width() {
        return width;
    }

    //height
    public Settings height(int h) {
        height = h;
        return this;
    }

    public int height() {
        return height;
    }
}
