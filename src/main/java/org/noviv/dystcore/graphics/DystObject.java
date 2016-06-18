package org.noviv.dystcore.graphics;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

public abstract class DystObject {

    protected Vector3f position = new Vector3f();
    protected Vector3f rotation = new Vector3f();
    protected Vector3f color = new Vector3f();

    protected Matrix4f model = new Matrix4f();

    public abstract void init();

    public abstract void update(double dt);

    public final void render() {
        GL11.glTranslatef(-position.x, -position.y, -position.z);
        _render();
        GL11.glTranslatef(position.x, position.y, position.z);
    }

    protected abstract void _render();

    public abstract void terminate();

    public final Matrix4f getModel() {
        return model;
    }

    public final Vector3f getColor() {
        return color;
    }

    public final void rotateX(float degrees) {
        model.rotateX((float) Math.toRadians(degrees));
        rotation.add(degrees, 0, 0);
    }

    public final void rotateY(float degrees) {
        model.rotateY((float) Math.toRadians(degrees));
        rotation.add(0, degrees, 0);
    }

    public final void rotateZ(float degrees) {
        model.rotateZ((float) Math.toRadians(degrees));
        rotation.add(0, 0, degrees);
    }

    public final void move(Vector3f offset) {
        position.add(offset);
        model.translation(position);
    }

    public final void setColor(Vector3f col) {
        color = col;
    }
}
