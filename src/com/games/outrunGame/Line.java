package com.games.outrunGame;

public class Line {
    float x, y, z;
    float Xb, Yb, W;
    float scale;

    public Line() {
        x = 0f;
        y = 0f;
        z = 0f;
    }

    public Line(float x, float y, float z, float x1, float y1, float w, float scale) {
        this.x = x;
        this.y = y;
        this.z = z;
        Xb = x1;
        Yb = y1;
        W = w;
        this.scale = scale;
    }

    void project(int camX, int camY, int camZ, float camD, int width, int height, int roadW){
        scale = camD/(z-camZ);
        Xb = (1 + scale*(x - camX)) * width/2;
        Yb = (1 - scale*(y - camY)) * height/2;
        W = scale * roadW  * width/2;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getW() {
        return W;
    }

    public void setW(float w) {
        W = w;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public float getXb() {
        return Xb;
    }

    public void setXb(float xb) {
        Xb = xb;
    }

    public float getYb() {
        return Yb;
    }

    public void setYb(float yb) {
        Yb = yb;
    }
}
