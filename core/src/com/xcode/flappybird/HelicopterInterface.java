package com.xcode.flappybird;

public interface HelicopterInterface {

    public abstract void drawBackground();
    public abstract void gameStateOne();
    public abstract void tubeLogic();
    public abstract void animateBird();
    public abstract void startRestartLogic();
    public abstract void justTouched();
    public abstract void keepBirdAlive();
    public abstract void collisionDetection();
    public abstract void restartGame();
    public abstract void restartOnTouch();
    public abstract void playMusic();


}
