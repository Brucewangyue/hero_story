package org.tinygame.herostory.entity;

public class MoveState {
    private float toPosX;
    private float toPosY;
    private float fromPosX;
    private float fromPosY;
    private long startTime;

    public float getToPosX() {
        return toPosX;
    }

    public void setToPosX(float toPosX) {
        this.toPosX = toPosX;
    }

    public float getToPosY() {
        return toPosY;
    }

    public void setToPosY(float toPosY) {
        this.toPosY = toPosY;
    }

    public float getFromPosX() {
        return fromPosX;
    }

    public void setFromPosX(float fromPosX) {
        this.fromPosX = fromPosX;
    }

    public float getFromPosY() {
        return fromPosY;
    }

    public void setFromPosY(float fromPosY) {
        this.fromPosY = fromPosY;
    }

    public long getStartTime() {
        return startTime;
    }

    @Override
    public String toString() {
        return "MoveState{" +
                "toPosX=" + toPosX +
                ", toPosY=" + toPosY +
                ", fromPosX=" + fromPosX +
                ", fromPosY=" + fromPosY +
                ", startTime=" + startTime +
                '}';
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
}
