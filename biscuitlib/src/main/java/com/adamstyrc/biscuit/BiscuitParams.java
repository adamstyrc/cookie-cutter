package com.adamstyrc.biscuit;

/**
 * Created by adamstyrc on 04/04/16.
 */
public class BiscuitParams {

    private Circle circle;
    private float maxZoom = 4;

    public Circle getCircle() {
        return circle;
    }

    public float getMaxZoom() {
        return maxZoom;
    }

    public void setMaxZoom(float maxZoom) {
        this.maxZoom = maxZoom;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }
}
