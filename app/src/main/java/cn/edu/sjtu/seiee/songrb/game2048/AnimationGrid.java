package cn.edu.sjtu.seiee.songrb.game2048;

import java.util.ArrayList;

/**
 * Created by 程涌潇 on 2016/5/17.
 */
class AnimationGrid extends Grid {
    private ArrayList[][] field;
    private int activeAnimations = 0;
    private boolean oneMoreFrame = false;
    ArrayList<AnimationCell> globalAnimation = new ArrayList<AnimationCell>();

    AnimationGrid(int x, int y) {
        field = new ArrayList[x][y];

        for (int xx = 0; xx < x; xx++) {
            for (int yy = 0; yy < y; yy++) {
                field[xx][yy] = new ArrayList<AnimationCell>();
            }
        }
    }

    void startAnimation(int x, int y, int animationType, long length, long delay, int[] extras) {
        AnimationCell animationToAdd = new AnimationCell(x, y, animationType, length, delay, extras);
        if (x == -1 && y == -1) {
            globalAnimation.add(animationToAdd);
        } else {
            field[x][y].add(animationToAdd);
        }
        activeAnimations = activeAnimations + 1;
    }

    void tickAll(long timeElapsed) {
        ArrayList<AnimationCell> cancelledAnimations = new ArrayList<AnimationCell>();
        for (AnimationCell animation : globalAnimation) {
            animation.tick(timeElapsed);
            if (animation.animationDone()) {
                cancelledAnimations.add(animation);
                activeAnimations = activeAnimations - 1;
            }
        }

        for (ArrayList[] array : field) {
            for (ArrayList<AnimationCell> list : array) {
                for (AnimationCell animation : list) {
                    animation.tick(timeElapsed);
                    if (animation.animationDone()) {
                        cancelledAnimations.add(animation);
                        activeAnimations = activeAnimations - 1;
                    }
                }
            }
        }

        for (AnimationCell animation : cancelledAnimations) {
            cancelAnimation(animation);
        }
    }

    boolean isAnimationActive() {
        if (activeAnimations != 0) {
            oneMoreFrame = true;
            return true;
        } else if (oneMoreFrame) {
            oneMoreFrame = false;
            return true;
        } else {
            return false;
        }
    }

    ArrayList<AnimationCell> getAnimationCell(int x, int y) {
        return field[x][y];
    }

    void cancelAnimations() {
        for (ArrayList<AnimationCell>[] array : field) {
            for (ArrayList<AnimationCell> list : array) {
                list.clear();
            }
        }
        globalAnimation.clear();
        activeAnimations = 0;
    }

    private void cancelAnimation(AnimationCell animation) {
        if (animation.getX() == -1 && animation.getY() == -1) {
            globalAnimation.remove(animation);
        } else {
            field[animation.getX()][animation.getY()].remove(animation);
        }
    }
}
