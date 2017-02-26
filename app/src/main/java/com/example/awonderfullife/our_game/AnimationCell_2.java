package com.example.awonderfullife.our_game;

import com.example.awonderfullife.our_game.Cell;

/**
 * Created by 程涌潇 on 2016/5/28.
 */
public class AnimationCell_2 extends Cell {
    private int animationType;
    private long timeElapsed;
    private long animationTime;
    private long delayTime;
    public int[] extras;

    public AnimationCell_2(int x, int y, int animationType, long length, long delay, int[] extras) {
        super(x, y);
        this.animationType = animationType;
        animationTime = length;
        delayTime = delay;
        this.extras = extras;
    }

    public int getAnimationType() {
        return animationType;
    }

    public void tick(long timeElapsed) {
        this.timeElapsed = this.timeElapsed + timeElapsed;
    }

    public boolean animationDone() {
        return animationTime + delayTime < timeElapsed;
    }

    public double getPercentageDone() {
        return Math.max(0, 1.0 * (timeElapsed - delayTime) / animationTime);
    }

    public boolean isActive() {
        return (timeElapsed >= delayTime);
    }
}
