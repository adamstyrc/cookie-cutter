package com.adamstyrc.cookiecutter;

/**
 * Created by adamstyrc on 01/04/16.
 */
public class Circle {

    private int cx;
    private int  cy;
    private int radius;

    public Circle(int cx, int cy, int radius) {
        this.cx = cx;
        this.cy = cy;
        this.radius = radius;
    }

    public int getCx() {
        return cx;
    }

    public int getCy() {
        return cy;
    }

    public int getRadius() {
        return radius;
    }

    public int getDiameter() {
        return radius * 2;
    }

    public int getLeftBound() {
        return cx - radius;
    }

    public int getRightBound() {
        return cx + radius;
    }

    public int getTopBound() {
        return cy - radius;
    }

    public int getBottomBound() {
        return cy + radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
