package com.totlc.Actors.traps;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Rectangle;
import com.totlc.Directionality;

public class ExitPortal extends ATrap {

    private static float width = 64;
    private static float height = 128;
    private Directionality modifiedDirection;

    public ExitPortal(AssetManager assetManager, float x, float y) {
        this(assetManager, new Rectangle(x, y, width, height), 0);
    }

    public ExitPortal(AssetManager assetManager, Rectangle r, long delay) {
        super(assetManager, r, delay);
    }

    public ExitPortal(AssetManager assetManager, float x, float y, Directionality d) {
        this(assetManager, x, y);
        this.modifiedDirection = d;
    }

    @Override
    public void activate() {
        getTargetActor().moveAbs(getX(), getY());
    }
}
