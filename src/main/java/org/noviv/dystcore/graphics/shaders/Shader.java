package org.noviv.dystcore.graphics.shaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.FloatBuffer;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.noviv.dystcore.exceptions.DystException;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;
import static org.noviv.dystcore.accessories.utilities.DystIO.ioResourceToFile;

public class Shader {

    private final int programID;
    private final int vertID;
    private final int fragID;

    public Shader(String name) {
        vertID = loadShader("shaders/" + name + ".vert", GL_VERTEX_SHADER);
        fragID = loadShader("shaders/" + name + ".frag", GL_FRAGMENT_SHADER);

        programID = glCreateProgram();
        glAttachShader(programID, vertID);
        glAttachShader(programID, fragID);

        glLinkProgram(programID);
        if (glGetProgrami(programID, GL_LINK_STATUS) == GL_FALSE) {
            throw new DystException("Could not link shader");
        }

        glValidateProgram(programID);
        if (glGetProgrami(programID, GL_VALIDATE_STATUS) == GL_FALSE) {
            throw new DystException("Could not validate shader");
        }
    }

    private int loadShader(String path, int type) {
        int id;

        String src = "";
        try (BufferedReader br = new BufferedReader(new FileReader(ioResourceToFile(path)))) {
            String line;
            while ((line = br.readLine()) != null) {
                src += line + "\n";
            }
        } catch (Exception e) {
            throw new DystException(e);
        }

        id = glCreateShader(type);
        glShaderSource(id, src);
        glCompileShader(id);

        if (glGetShaderi(id, GL_COMPILE_STATUS) == GL_FALSE) {
            throw new DystException("Failed to compile shader " + path + ": " + glGetShaderInfoLog(id));
        }

        return id;
    }

    public void enable() {
        glUseProgram(programID);
    }

    public void setUniform(String name, Matrix4f value) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
        value.get(buffer);
        buffer.rewind();

        glUniformMatrix4fv(getLocation(name), false, buffer);
    }

    public void disable() {
        glUseProgram(0);
    }

    public void terminate() {
        glDetachShader(programID, vertID);
        glDetachShader(programID, fragID);

        glDeleteShader(vertID);
        glDeleteShader(fragID);

        glDeleteProgram(programID);
    }

    private int getLocation(String name) {
        return glGetUniformLocation(programID, name);
    }
}
