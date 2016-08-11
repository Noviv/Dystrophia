package org.noviv.dystcore.graphics;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.stb.*;
import org.noviv.dystcore.exceptions.DystException;
import org.noviv.dystcore.accessories.utilities.DystIO;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBTruetype.*;
import static org.lwjgl.system.MemoryUtil.memAllocFloat;

public final class DystText extends DystObject {

    private static final int BITMAP_W = 512;
    private static final int BITMAP_H = 512;

    private String text;
    private float x;
    private float y;

    private final STBTTAlignedQuad q = STBTTAlignedQuad.malloc();
    private final FloatBuffer xb = memAllocFloat(1);
    private final FloatBuffer yb = memAllocFloat(1);

    private STBTTPackedchar.Buffer chardata;
    private int id;

    private boolean oversampling;
    private boolean integer_align;

    public DystText(String t, int xx, int yy) {
        text = t;
        x = (float) xx;
        y = (float) yy;

        oversampling = false;
        integer_align = false;
    }

    @Override
    public void init() {
        id = glGenTextures();
        chardata = STBTTPackedchar.mallocBuffer(128);

        try {
            STBTTPackContext pc = STBTTPackContext.malloc();
            ByteBuffer ttf = DystIO.ioResourceToByteBuffer("fonts\\FiraSans.ttf", 160 * 1024);

            ByteBuffer bitmap = BufferUtils.createByteBuffer(BITMAP_W * BITMAP_H);

            stbtt_PackBegin(pc, bitmap, BITMAP_W, BITMAP_H, 0, 1, null);
            chardata.limit(32 + 95);
            chardata.position(32);
            stbtt_PackSetOversampling(pc, oversampling ? 3 : 1, 1);
            stbtt_PackFontRange(pc, ttf, 0, 24.f, 32, chardata);
            chardata.clear();
            stbtt_PackEnd(pc);

            glBindTexture(GL_TEXTURE_2D, id);
            glTexImage2D(GL_TEXTURE_2D, 0, GL_ALPHA, BITMAP_W, BITMAP_H, 0, GL_ALPHA, GL_UNSIGNED_BYTE, bitmap);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
            glBindTexture(GL_TEXTURE_2D, 0);
        } catch (Exception e) {
            throw new DystException(e.getMessage());
        }
    }

    @Override
    public void update(double dt) {
    }

    @Override
    public void render() {
        glDisable(GL_TEXTURE_2D);
        glDisable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        xb.put(0, x);
        yb.put(0, y);

        chardata.position(0);

        glBindTexture(GL_TEXTURE_2D, id);

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

        glDisable(GL_BLEND);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_TEXTURE_2D);
    }

    @Override
    public void terminate() {
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
