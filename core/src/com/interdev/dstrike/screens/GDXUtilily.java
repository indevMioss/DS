package com.interdev.dstrike.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class GDXUtilily {

    public static void scale(Actor actor, float scale) {
        actor.setBounds(actor.getX(), actor.getY(), actor.getWidth()*scale, actor.getHeight()*scale);
    }

    public static void setPosCentr(Actor actor, float x, float y) {
        actor.setPosition(x - actor.getWidth() / 2, y - actor.getHeight() / 2);
    }


}
