package com.interdev.dstrike.screens.game.ui.layers.units;


import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.interdev.dstrike.screens.game.UnitValues;
import com.interdev.dstrike.screens.game.ui.UI;

import java.util.ArrayList;

public class SelectionSystem {
    public static final float ICONS_OFFSET = 0.15f;
    private static final float ICON_MOVEMENT_SPEED = 0.12f;

    private static final float ICON_MAX_SCALE = 1f;
    private static final float ICON_MIN_SCALE = 0.75f;


    public static int selectedIconNum;
    private UI ui;

    private ArrayList<UnitIcon> iconsList = new ArrayList<UnitIcon>();
    private Group iconsGroup = new Group();

    public SelectionSystem(UI ui) {
        this.ui = ui;

        for (int i = 0; i < UnitValues.unitTypesAmount; i++) {
            iconsList.add(new UnitIcon(i, iconsGroup, ui));
        }

        ui.stage.addActor(iconsGroup);
        setVisible(false);

        centerGroupBy(1);
    }


    public void setVisible(boolean visible) {
        iconsGroup.setVisible(visible);
        if (visible) {
            registerListeners();
        } else {
            unregisterListeners();
        }

    }

    private void centerGroupBy(int num) {
        Image icon = iconsList.get(num).icon;
        selectedIconNum = num;
        rescale();
        iconsGroup.addAction(Actions.moveTo(ui.virtualWidth / 2 - icon.getX() - icon.getWidth() / 2, iconsGroup.getY(), ICON_MOVEMENT_SPEED, Interpolation.linear));
    }

    private void rescale() {
        int i = 0;
        for (UnitIcon unitIcon: iconsList) {
            float scale = (i == selectedIconNum) ? ICON_MAX_SCALE : ICON_MIN_SCALE;
            unitIcon.setScale(scale);
            i++;
        }
    }

    private void registerListeners() {
        for (int i = 0; i < iconsList.size(); i++) {
            final int index = i;
            iconsList.get(i).addListener(new InputListener() {
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    System.out.println("DOWN");
                    return true;
                }

                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    System.out.println("UP");
                    centerGroupBy(index);
                    //iconsGroup.setPosition(icon.getX(), 0);
                    System.out.println(iconsList.get(index).getX());
                }
            });
        }
    }

    private void unregisterListeners() {
        for (int i = 0; i < iconsList.size(); i++) {
            iconsList.get(i).clearListeners();
        }
    }
}
