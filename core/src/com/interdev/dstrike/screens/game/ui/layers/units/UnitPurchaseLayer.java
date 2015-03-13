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
import com.interdev.dstrike.screens.Utils.GDXUtilily;
import com.interdev.dstrike.screens.game.GameScreen;
import com.interdev.dstrike.screens.game.PlayerValues;
import com.interdev.dstrike.screens.game.ui.UI;
import com.interdev.dstrike.screens.game.ui.layers.UILayer;

public class UnitPurchaseLayer implements UILayer {

    public static final float CELL_SIZE = 64;

    private Stage stage;
    private InputListener inputListener;

    private float personalFieldWidth = GameScreen.personalFieldWidth;
    private float personalFieldHeight = GameScreen.personalFieldHeight;
    private float getPersonalFieldBorder = personalFieldWidth * PlayerValues.PERSONAL_FIELD_BORDER;

    public Image purchaseButton;
    private short selectedUnitType = 0;

    public boolean placingUnit = false;
    public boolean unitPlaceIsOK = false;

    private boolean[][] grid = new boolean[8][8];

    private Vector3 touchUnprojectedVector;
    private Vector3 touchProjectedVector;

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
        GDXUtilily.setPosCentr(purchaseButton, ui.bg.getWidth() / 2, ui.bg.getHeight() * 0.3f);
        purchaseButton.setColor(1f, 1f, 1f, 0f);
        purchaseButton.setVisible(false);
        stage.addActor(purchaseButton);


        inputListener = new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (ui.gameScreenRef.player.canBuyUnit(selectedUnitType)) {
                    placingUnit = true;
                    pickedUnitImage = unitImages.getUnitImage(selectedUnitType);
                    pickedUnitImage.setScale(1 / ui.gameScreenRef.camera.zoom);
                    pickedUnitImage.setPosition(-pickedUnitImage.getWidth(), -pickedUnitImage.getHeight());
                    pickedUnitImage.setVisible(true);
                }
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (placingUnit) {
                    if (unitPlaceIsOK) {
                        Vector3 centeredXY = getCenteredByCellsPosition(touchUnprojectedVector.x, touchUnprojectedVector.y);
                        ui.gameScreenRef.player.requestUnit(centeredXY.x, centeredXY.y, selectedUnitType);
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

                    setPickedUnitPosCenter(actualX, actualY);

                    if (actualY > UI.BG_SAFE_HEIGHT_SCALED) {
                        touchUnprojectedVector = ui.gameScreenRef.camera.unproject(new Vector3(actualX, ui.gameScreenRef.virutalHeight - actualY, 0));

                        System.out.println("tUX: " + touchUnprojectedVector.x + " tUY: " + touchUnprojectedVector.y);

                        touchProjectedVector = ui.gameScreenRef.camera.project(getCenteredByCellsPosition(touchUnprojectedVector.x, touchUnprojectedVector.y));
                        setPickedUnitPosCenter(touchProjectedVector.x, touchProjectedVector.y);

                        if (!isPlaceTaken(touchUnprojectedVector.x, touchUnprojectedVector.y) &&
                                (touchUnprojectedVector.x > getPersonalFieldBorder && touchUnprojectedVector.x < personalFieldWidth - getPersonalFieldBorder &&
                                        touchUnprojectedVector.y > getPersonalFieldBorder && touchUnprojectedVector.y < personalFieldHeight - getPersonalFieldBorder)) {
                            unitPlaceIsOK = true;
                            pickedUnitImage.setColor(1, 1, 1, 1f);
                            Log.info("unitPlaceIsOK = true;");
                        } else {
                            unitPlaceIsOK = false;
                            pickedUnitImage.setColor(1, 0.5f, 0.5f, 0.5f);
                        }
                    } else {
                        unitPlaceIsOK = false;
                        pickedUnitImage.setColor(1, 0.5f, 0.5f, 0.5f);
                    }

                }
            }
        }

        ;

    }

    private void setPickedUnitPosCenter(float x, float y) {
        pickedUnitImage.setPosition(x - pickedUnitImage.getWidth() * pickedUnitImage.getScaleX() / 2, y - pickedUnitImage.getWidth() * pickedUnitImage.getScaleX() / 2);
        //pickedUnitImage.setPosition(x,y);
    }

    private Vector3 getCenteredByCellsPosition(float x, float y) {
        return new Vector3(
                ((int) (x / (CELL_SIZE * 2))) * CELL_SIZE * 2 + CELL_SIZE,
                ((int) (y / (CELL_SIZE * 2))) * CELL_SIZE * 2 + CELL_SIZE,
                0);
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

    private boolean isPlaceTaken(float x, float y) {
        try {
            return grid[(int) (x / (CELL_SIZE * 2))][(int) (y / (CELL_SIZE * 2))];
        } catch (IndexOutOfBoundsException e) {
            return true;
        }
    }

    public void onUnitPurchased(float x, float y) {
        try {
            grid[(int) (x / (CELL_SIZE * 2))][(int) (y / (CELL_SIZE * 2))] = true;
        } catch (IndexOutOfBoundsException e) {
            System.err.println("onUnitPurchased IndexOutOfBoundsException UnitPurchaseLayer");
        }
    }
}
