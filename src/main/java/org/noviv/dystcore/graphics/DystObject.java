package org.noviv.dystcore.graphics;

import org.joml.Vector3f;

public abstract class DystObject {

    protected Vector3f position;
    protected Vector3f color;

    public abstract void init();

    public abstract void update(double dt);

    public abstract void render();

    public abstract void terminate();
}
