package com.interdev.dstrike.screens.game.ui.layers.upgrades;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.interdev.dstrike.screens.game.ui.UI;
import com.interdev.dstrike.screens.game.ui.layers.UILayer;

public class UpgradesLayer implements UILayer {

    private Stage stage;
    private UI ui;

    public UpgradesLayer(final UI ui) {
        this.ui = ui;
        this.stage = ui.stage;

    }

    @Override
    public void setVisible(boolean vis) {

    }
}
