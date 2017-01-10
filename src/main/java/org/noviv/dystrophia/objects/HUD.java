package org.noviv.dystrophia.objects;

import org.noviv.dystcore.graphics.DystObject;

import org.noviv.dystrophia.objects.subobjects.DystRect;

public class HUD extends DystObject {
    
    private DystRect hud;

    @Override
    public void init() {
        hud = new DystRect();
    }

    @Override
    public void update(double dt) {
        hud.update(dt);
    }

    @Override
    public void render() {
        hud.render();
    }

    @Override
    public void terminate() {
        hud.terminate();
    }
}
