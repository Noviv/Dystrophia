package org.noviv.dystcore.graphics.text;

import java.awt.FontMetrics;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBTTAlignedQuad;
import org.lwjgl.stb.STBTTPackedchar;
import org.noviv.dystcore.graphics.DystObject;
import org.lwjgl.stb.STBTTPackContext;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBTruetype.*;
import static org.lwjgl.system.MemoryUtil.memAllocFloat;
import static org.lwjgl.system.MemoryUtil.memFree;
import static org.noviv.dystcore.accessories.DystIO.ioReadMetrics;
import static org.noviv.dystcore.accessories.DystIO.ioResourceToByteBuffer;

public final class DystText extends DystObject {

    private static final String TTF_FONT = "fonts\\FiraSans.ttf";

    private static final int BITMAP_W = 512;
    private static final int BITMAP_H = 512;

    private final STBTTAlignedQuad q = STBTTAlignedQuad.malloc();
    private final FloatBuffer xb = memAllocFloat(1);
    private final FloatBuffer yb = memAllocFloat(1);

    private STBTTPackedchar.Buffer chardata;
    private FontMetrics metrics;
    private int texID;

    private float x_rot;
    private float y_rot;

    private final String text;
    private float x;
    private float y;
    private double rotation;
    private int fontSize;
    private boolean oversampling;
    private boolean integer_align;

    private boolean needsRecalc;

    public DystText(String t, float xx, float yy) {
        this(t, xx, yy, 0);
    }

    public DystText(String t, float xx, float yy, double rot) {
        this(t, xx, yy, rot, 24);
    }

    public DystText(String t, float xx, float yy, double rot, int size) {
        text = t;
        x = xx;
        y = yy;
        rotation = rot;
        fontSize = size;
        oversampling = true;
        integer_align = false;

        needsRecalc = false;

        setFontSize(fontSize);
    }

    public float getX() {
        return x;
    }

    public void setX(float xx) {
        x = xx;
    }

    public float getY() {
        return y;
    }

    public void setY(float yy) {
        y = yy;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rot) {
        rotation = rot;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int size) {
        fontSize = size;
        needsRecalc = true;
    }

    public String getText() {
        return text;
    }

    @Override
    public void init() {
        texID = glGenTextures();
    }

    @Override
    public void update(double dt) {
    }

    @Override
    public void render() {
        if (needsRecalc) {
            needsRecalc = false;
            calc();
        }

        print(x, y, rotation, text);
    }

    @Override
    public void terminate() {
        memFree(xb);
        memFree(yb);

        q.free();
    }

    private void calc() {
        ByteBuffer bitmap = BufferUtils.createByteBuffer(BITMAP_W * BITMAP_H);
        chardata = STBTTPackedchar.malloc(128);

        STBTTPackContext pc = STBTTPackContext.malloc();
        ByteBuffer ttf = ioResourceToByteBuffer(TTF_FONT, 160 * 1024);

        stbtt_PackBegin(pc, bitmap, BITMAP_W, BITMAP_H, 0, 1, null);
        chardata.limit(32 + 95);
        chardata.position(32);
        if (oversampling) {
            stbtt_PackSetOversampling(pc, 1, 1);
        } else {
            stbtt_PackSetOversampling(pc, 3, 1);
        }
        stbtt_PackFontRange(pc, ttf, 0, fontSize, 32, chardata);
        chardata.clear();
        stbtt_PackEnd(pc);

        metrics = ioReadMetrics(TTF_FONT);
        x_rot = x + metrics.stringWidth(text) * text.length();
        y_rot = y + metrics.getHeight() / 2.0f;

        glBindTexture(GL_TEXTURE_2D, texID);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_ALPHA, BITMAP_W, BITMAP_H, 0, GL_ALPHA, GL_UNSIGNED_BYTE, bitmap);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    private void print(float x, float y, double rotation, String text) {
        glTranslatef(x_rot, y_rot, 0);
        glRotatef((float) rotation, 0, 0, 1);
        glTranslatef(-x_rot, -y_rot, 0);

        xb.put(0, x);
        yb.put(0, y);

        chardata.position(0);

        glBindTexture(GL_TEXTURE_2D, texID);
        glBegin(GL_QUADS);
        for (int i = 0; i < text.length(); i++) {
            stbtt_GetPackedQuad(chardata, BITMAP_W, BITMAP_H, text.charAt(i), xb, yb, q, integer_align ? 1 : 0);
            drawBoxTC(
                    q.x0(), q.y0(), q.x1(), q.y1(),
                    q.s0(), q.t0(), q.s1(), q.t1()
            );
        }
        glEnd();
        glBindTexture(GL_TEXTURE_2D, 0);

        glRotatef((float) -rotation, 0, 0, 1);
    }

    private static void drawBoxTC(float x0, float y0, float x1, float y1, float s0, float t0, float s1, float t1) {
        glTexCoord2f(s0, t0);
        glVertex2f(x0, y0);
        glTexCoord2f(s1, t0);
        glVertex2f(x1, y0);
        glTexCoord2f(s1, t1);
        glVertex2f(x1, y1);
        glTexCoord2f(s0, t1);
        glVertex2f(x0, y1);
    }
}
