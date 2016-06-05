package org.noviv.dystcore.objects;

public abstract class DystObject {
    
    public abstract void init();

    public abstract void update(double dt);

    public abstract void render();
}
