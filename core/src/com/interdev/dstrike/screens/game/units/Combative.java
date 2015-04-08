package com.interdev.dstrike.screens.game.units;

public interface Combative {
    float getX();
    float getY();
    int getID();
    int getBulletType();
    boolean isAlive();
    float getAttackInterval();

}
