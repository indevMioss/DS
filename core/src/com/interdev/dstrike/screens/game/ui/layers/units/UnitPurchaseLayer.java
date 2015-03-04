package com.interdev.dstrike.screens.game.ui.layers.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.esotericsoftware.minlog.Log;
import com.interdev.dstrike.Main;
import com.interdev.dstrike.screens.Utils.GDXUtilily;
import com.interdev.dstrike.screens.game.GameScreen;
import com.interdev.dstrike.screens.game.PlayerValues;
import com.interdev.dstrike.screens.game.ui.UI;
import com.interdev.dstrike.screens.game.ui.layers.UILayer;

public class UnitPurchaseLayer implements UILayer {

    private Stage stage;
    private InputListener inputListener;

    private float personalFieldWidth = GameScreen.personalFieldWidth;
    private float personalFieldHeight = GameScreen.personalFieldHeight;
    private float getPersonalFieldBorder = personalFieldWidth * PlayerValues.PERSONAL_FIELD_BORDER;

    public Image purchaseButton;
    private short selectedUnitType = 0;

    public boolean placingUnit = false;
    public boolean unitPlaceIsOK = false;

    private Vector3 touchUnprojectedVector;

    private SpriteBatch pickedUnitBatch = new SpriteBatch();
    private UnitImages unitImages;
    private Image pickedUnitImage;

    private SelectionSystem selectionSystem;


    public UnitPurchaseLayer(final UI ui) {
        this.stage = ui.stage;

        selectionSystem = new SelectionSystem(ui);

        unitImages = new UnitImages();

        purchaseButton = new Image(new Texture(Gdx.files.internal("ui/unit_purchase_button.png")));
        GDXUtilily.scale(purchaseButton, ui.layersScale);
        GDXUtilily.setPosCentr(purchaseButton, ui.bg.getWidth()/2,  ui.bg.getHeight()*0.3f);
        purchaseButton.setColor(1f,1f,1f,0f);
        purchaseButton.setVisible(false);
        stage.addActor(purchaseButton);


        inputListener = new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (Main.gameScreenReference.player.canBuyUnit(selectedUnitType)) {
                    placingUnit = true;
                    pickedUnitImage = unitImages.getUnitImage(selectedUnitType);
                    pickedUnitImage.setScale(1 / Main.gameScreenReference.camera.zoom);
                    pickedUnitImage.setPosition(-pickedUnitImage.getWidth(), -pickedUnitImage.getHeight());
                    pickedUnitImage.setVisible(true);
                }
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (placingUnit) {
                    if (unitPlaceIsOK) {
                        Main.gameScreenReference.player.requestUnit(touchUnprojectedVector.x, touchUnprojectedVector.y, selectedUnitType);
                    }
                    pickedUnitImage.setVisible(false);
                }

                placingUnit = false;
                unitPlaceIsOK = false;

            }

            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                if (placingUnit) {
                    float actualX = ((x) * purchaseButton.getScaleX() + purchaseButton.getX());
                    float actualY = ((y) * purchaseButton.getScaleY() + purchaseButton.getY());
                    pickedUnitImage.setPosition(actualX - pickedUnitImage.getWidth() * pickedUnitImage.getScaleX() / 2, actualY - pickedUnitImage.getHeight() * pickedUnitImage.getScaleY() / 2);
                    if (actualY > UI.BG_SAFE_HEIGHT) {
                        touchUnprojectedVector = Main.gameScreenReference.camera.unproject(new Vector3(actualX, Main.gameScreenReference.virutalHeight - actualY, 0));

                        if (touchUnprojectedVector.x > getPersonalFieldBorder && touchUnprojectedVector.x < personalFieldWidth - getPersonalFieldBorder &&
                                touchUnprojectedVector.y > getPersonalFieldBorder && touchUnprojectedVector.y < personalFieldHeight - getPersonalFieldBorder) {
                            unitPlaceIsOK = true;
                            pickedUnitImage.setColor(1, 1, 1, 1f);

                            Log.info("unitPlaceIsOK = true;");
                        } else {
                            unitPlaceIsOK = false;
                            pickedUnitImage.setColor(1, 1, 1, 0.5f);
                        }
                    } else {
                        unitPlaceIsOK = false;
                        pickedUnitImage.setColor(1, 1, 1, 0.5f);
                    }

                }
            }
        };

    }

    public void draw(float deltaTime) {
        if (pickedUnitImage != null && pickedUnitImage.isVisible()) {
            pickedUnitBatch.begin();
            pickedUnitImage.draw(pickedUnitBatch, 1f);
            pickedUnitBatch.end();
        }
    }

    @Override
    public void setVisible(boolean visible) {
        purchaseButton.setVisible(visible);
        selectionSystem.setVisible(visible);

        if (visible) {
            purchaseButton.addListener(inputListener);
        } else {
            purchaseButton.clearListeners();
        }

    }


}
