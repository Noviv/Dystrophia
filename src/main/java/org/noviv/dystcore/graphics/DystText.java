package org.noviv.dystcore.graphics;

import java.nio.FloatBuffer;
import org.lwjgl.stb.STBTTAlignedQuad;
import static org.lwjgl.system.MemoryUtil.memAllocFloat;

public final class DystText extends DystObject {
    
    private static final int BITMAP_W = 512;
    private static final int BITMAP_H = 512;
    
    private static final float[] scale = {
        24.f,
        14.f
    };
    
    private static final int[] sf = {
        0, 1, 2,
        0, 1, 2
    };
    
    private final STBTTAlignedQuad q = STBTTAlignedQuad.malloc();
    private final FloatBuffer xb = memAllocFloat(1);
    private final FloatBuffer yb = memAllocFloat(1);

    @Override
    public void init() {
    }

    @Override
    public void update(double dt) {
    }

    @Override
    public void render() {
    }

    @Override
    public void terminate() {
    }
}
