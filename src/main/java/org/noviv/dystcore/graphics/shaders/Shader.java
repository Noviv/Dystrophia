package org.noviv.dystcore.graphics.shaders;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.noviv.dystcore.logging.DystLogger;
import org.noviv.dystcore.logging.DystFPSLogger;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_NO_ERROR;
import static org.lwjgl.opengl.GL11.glGetError;
import static org.lwjgl.opengl.GL20.*;
import org.noviv.dystcore.accessories.DystIO;

public class Shader {

    private final int id;

    private final int vtxID;
    private final int frgID;

    private final int timerID;

    public Shader(String name) {
        vtxID = loadShader("shaders\\" + name + ".vert", GL_VERTEX_SHADER);
        frgID = loadShader("shaders\\" + name + ".frag", GL_FRAGMENT_SHADER);

        id = glCreateProgram();
        glAttachShader(id, vtxID);
        glAttachShader(id, frgID);

        glLinkProgram(id);
        if (glGetProgrami(id, GL_LINK_STATUS) == GL_FALSE) {
            DystLogger.log(DystLogger.LEVEL_ERROR, "Link error", glGetProgramInfoLog(id, glGetProgrami(id, GL_INFO_LOG_LENGTH)));
        }
        glValidateProgram(id);
        if (glGetProgrami(id, GL_VALIDATE_STATUS) == GL_FALSE) {
            DystLogger.log(DystLogger.LEVEL_ERROR, "Validation error", glGetProgramInfoLog(id, glGetProgrami(id, GL_INFO_LOG_LENGTH)));
        }

        int ret = glGetError();
        if (ret != GL_NO_ERROR) {
            DystLogger.log(DystLogger.LEVEL_ERROR, "Shader compilation error", "" + ret);
        }

        timerID = glGetUniformLocation(id, "timer");
    }

    public void enable() {
        glUseProgram(id);
        glUniform1f(timerID, (float) DystFPSLogger.getTime());
    }

    public void disable() {
        glUseProgram(0);
    }

    public void terminate() {
        glUseProgram(0);
        glDetachShader(id, vtxID);
        glDetachShader(id, frgID);

        glDeleteShader(vtxID);
        glDeleteShader(frgID);
        glDeleteProgram(id);
    }

    private int loadShader(String fileName, int type) {
        String src = "";
        int id = 0;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(DystIO.ioResourceToStream(fileName)))) {
            String line;

            while ((line = br.readLine()) != null) {
                src += line + "\n";
            }
            br.close();
        } catch (Exception e) {
            DystLogger.log(DystLogger.LEVEL_ERROR, e.getMessage());
        }

        id = glCreateShader(type);
        glShaderSource(id, src);
        glCompileShader(id);

        return id;
    }
}
