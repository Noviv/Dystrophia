package org.noviv.dystcore.graphics.text;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import org.joml.Vector2d;
import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBTTBakedChar;
import org.lwjgl.stb.STBTTAlignedQuad;
import org.noviv.dystcore.graphics.DystObject;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBTruetype.*;
import static org.lwjgl.system.MemoryUtil.memAllocFloat;
import static org.noviv.dystcore.accessories.utilities.DystIO.ioResourceToByteBuffer;

public class DystText extends DystObject {

    private static final int BITMAP_W = 512;
    private static final int BITMAP_H = 512;

    private String text;
    private Vector2d position;

    private int id;
    private STBTTBakedChar.Buffer chardata;

    public DystText(String tex, int x, int y) {
        text = tex;
        position = new Vector2d(x, y);
    }

    @Override
    public void init() {
        chardata = STBTTBakedChar.mallocBuffer(96);

        ByteBuffer ttf = ioResourceToByteBuffer("fonts\\FiraSans.ttf", 160 * 1024);
        ByteBuffer bitmap = BufferUtils.createByteBuffer(BITMAP_W * BITMAP_H);
        stbtt_BakeFontBitmap(ttf, 24, bitmap, BITMAP_W, BITMAP_H, 32, chardata);
        
        id = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, id);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_ALPHA, BITMAP_W, BITMAP_H, 0, GL_ALPHA, GL_UNSIGNED_BYTE, bitmap);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    @Override
    public void update(double dt) {
    }

    @Override
    public void render() {
        FloatBuffer xb = memAllocFloat(1);
        FloatBuffer yb = memAllocFloat(1);

        STBTTAlignedQuad q = STBTTAlignedQuad.malloc();

        glBegin(GL_QUADS);
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == '\n') {
                yb.put(0, yb.get(0) + 24);
                xb.put(0, 0.0f);
                continue;
            } else if (c < 32 || 128 <= c) {
                continue;
            }

            stbtt_GetBakedQuad(chardata, BITMAP_W, BITMAP_H, c - 32, xb, yb, q, 1);

            glTexCoord2f(q.s0(), q.t0());
            glVertex2f(q.x0(), q.y0());

            glTexCoord2f(q.s1(), q.t0());
            glVertex2f(q.x1(), q.y0());

            glTexCoord2f(q.s1(), q.t1());
            glVertex2f(q.x1(), q.y1());

            glTexCoord2f(q.s0(), q.t1());
            glVertex2f(q.x0(), q.y1());
        }
        glEnd();
    }

    @Override
    public void terminate() {
    }
}
