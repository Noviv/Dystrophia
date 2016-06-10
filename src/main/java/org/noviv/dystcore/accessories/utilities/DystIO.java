package org.noviv.dystcore.accessories.utilities;

import java.awt.Font;
import java.awt.FontMetrics;
import java.io.File;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.lwjgl.BufferUtils;

import static org.lwjgl.BufferUtils.createByteBuffer;

public class DystIO {

    public static File ioResourceToFile(String resource) {
        return new File(Thread.currentThread().getContextClassLoader().getResource(resource).getFile());
    }

    public static ByteBuffer resizeBuffer(ByteBuffer buffer, int newCapacity) {
        ByteBuffer newBuffer = BufferUtils.createByteBuffer(newCapacity);
        buffer.flip();
        newBuffer.put(buffer);
        return newBuffer;
    }

    public static FontMetrics ioReadMetrics(String ttf) {
        try {
            return new java.awt.Canvas().getFontMetrics(Font.createFont(Font.TRUETYPE_FONT, ioResourceToStream(ttf)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static InputStream ioResourceToStream(String resource) {
        try {
            return Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ByteBuffer ioResourceToByteBuffer(String resource, int bufferSize) {
        ByteBuffer buffer = null;

        Path path = Paths.get(resource);
        if (Files.isReadable(path)) {
            try {
                SeekableByteChannel fc = Files.newByteChannel(path);
                buffer = BufferUtils.createByteBuffer((int) fc.size() + 1);
                while (fc.read(buffer) != -1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                InputStream source = ioResourceToStream(resource);
                ReadableByteChannel rbc = Channels.newChannel(source);
                buffer = createByteBuffer(bufferSize);

                while (true) {
                    int bytes = rbc.read(buffer);
                    if (bytes == -1) {
                        break;
                    }
                    if (buffer.remaining() == 0) {
                        buffer = resizeBuffer(buffer, buffer.capacity() * 2);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        buffer.flip();
        return buffer;
    }

    private DystIO() {
    }
}
