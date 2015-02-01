package com.interdev.dstrike.screens.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.esotericsoftware.minlog.Log;
import com.interdev.dstrike.Main;
import com.interdev.dstrike.screens.game.GameScreen;
import com.interdev.dstrike.screens.game.PlayerValues;

public class UnitPurchaseSystem extends Actor {

    private float personalFieldWidth = GameScreen.personalFieldWidth;
    private float personalFieldHeight = GameScreen.personalFieldHeight;
    private float getPersonalFieldBorder = personalFieldWidth * PlayerValues.PERSONAL_FIELD_BORDER;

    public Texture texture;
    private short selectedUnitType = 1;

    public boolean placingUnit = false;
    public boolean unitPlaceIsOK = false;

    private Vector3 touchUnprojectedVector;

    private UnitImages unitImages;
    private Image selectedUnitImage;

    public UnitPurchaseSystem(final float uiBgHeight, Stage stage) {
        unitImages = new UnitImages(stage);

        texture = new Texture(Gdx.files.internal("unit_purchase_button.png"));
        setWidth(texture.getWidth());
        setHeight(texture.getHeight());
        setBounds(getX(), getY(), getWidth(), getHeight());


        this.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (Main.gameScreenReference.player.canBuyUnit(selectedUnitType)) {
                    placingUnit = true;
                    selectedUnitImage = unitImages.getUnitImage(selectedUnitType);
                    selectedUnitImage.setScale(1 / Main.gameScreenReference.camera.zoom);
                    selectedUnitImage.setPosition(-selectedUnitImage.getWidth(), -selectedUnitImage.getHeight());
                    selectedUnitImage.setVisible(true);
                }
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (placingUnit) {
                    if (unitPlaceIsOK) {
                        Main.gameScreenReference.player.requestUnit(touchUnprojectedVector.x, touchUnprojectedVector.y, selectedUnitType);
                    }
                    selectedUnitImage.setVisible(false);
                }

                placingUnit = false;
                unitPlaceIsOK = false;

            }

            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                if (placingUnit) {
                    float actualX = ((x) * getScaleX() + getX());
                    float actualY = ((y) * getScaleY() + getY());
                    selectedUnitImage.setPosition(actualX - selectedUnitImage.getWidth() * selectedUnitImage.getScaleX() / 2, actualY - selectedUnitImage.getHeight() * selectedUnitImage.getScaleY() / 2);
                    if (actualY > uiBgHeight) {
                        touchUnprojectedVector = Main.gameScreenReference.camera.unproject(new Vector3(actualX, Main.gameScreenReference.virutalHeight - actualY, 0));
                        // Log.info("x: " + actualX + ", y: " + actualY);
                        // Log.info("x_un: " + touchUnprojectedVector.x + ", y_un: " + touchUnprojectedVector.y);
                        if (touchUnprojectedVector.x > getPersonalFieldBorder && touchUnprojectedVector.x < personalFieldWidth - getPersonalFieldBorder &&
                                touchUnprojectedVector.y > getPersonalFieldBorder && touchUnprojectedVector.y < personalFieldHeight - getPersonalFieldBorder) {
                            unitPlaceIsOK = true;
                            selectedUnitImage.setColor(1, 1, 1, 1f);

                            Log.info("unitPlaceIsOK = true;");
                        } else {
                            unitPlaceIsOK = false;
                            selectedUnitImage.setColor(1, 1, 1, 0.25f);
                        }
                    } else {
                        unitPlaceIsOK = false;
                        selectedUnitImage.setColor(1, 1, 1, 0.25f);
                    }

                }
            }
        });
    }

    public void confirmPurchase(boolean confirmed) {
        if (confirmed) {

        }
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(texture, getX(), getY(), getWidth() * getScaleX(), getHeight() * getScaleY());
    }

}
