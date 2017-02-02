package com.totlc.status;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.totlc.Actors.TotlcObject;

public abstract class AStatus extends Actor{

    private AssetManager assetManager;
    private TotlcObject followMe;

    public AStatus(AssetManager manager, TotlcObject target){
        setAssetManager(manager);
        setFollowMe(target);
    }

    @Override
    public void act(float deltaTime){
        setX((float) getFollowMe().getHitBoxCenter().getX());
        setY((float) getFollowMe().getHitBoxCenter().getY());
    }

    public abstract void draw(Batch batch, float alpha);

    /**
     * Called by actors when iterating through own status array.
     */
    public abstract void proc();

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public void setAssetManager(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public TotlcObject getFollowMe() {
        return followMe;
    }

    public void setFollowMe(TotlcObject followMe) {
        this.followMe = followMe;
    }
}
