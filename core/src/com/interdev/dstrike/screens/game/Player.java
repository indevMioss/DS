package com.interdev.dstrike.screens.game;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.esotericsoftware.minlog.Log;
import com.interdev.dstrike.Main;
import com.interdev.dstrike.networking.PackedCell;
import com.interdev.dstrike.networking.PackedUnit;
import com.interdev.dstrike.networking.Packet;

import java.util.HashMap;

public class Player {
    public static int money = PlayerValues.START_MONEY;

    public static float myLives = PlayerValues.BASE_START_LIVES - 1300;
    public static float enemyLives = PlayerValues.BASE_START_LIVES - 3700;

    public HashMap<Integer, PassiveUnit> myPersonalFieldUnitsHashMap;
    public HashMap<Integer, ActiveUnit> myUnitsHashMap;
    public HashMap<Integer, ActiveUnit> enemyUnitsHashMap;

    private short tempRequestedUnitX = 0;
    private short tempRequestedUnitY = 0;
    private short tempRequestedUnitType = 1;

    private Stage stage;

    public PackedCell[][] packedCells;
    private GameScreen gameScreenRef;

    public Player(GameScreen gameScreen) {
        this.gameScreenRef = gameScreen;
        this.stage = gameScreenRef.mainStage;

        myPersonalFieldUnitsHashMap = new HashMap<Integer, PassiveUnit>();

        myUnitsHashMap = new HashMap<Integer, ActiveUnit>();
        enemyUnitsHashMap = new HashMap<Integer, ActiveUnit>();
    }


    public void requestUnit(float x, float y, short type) {
        tempRequestedUnitX = (short) x;
        tempRequestedUnitY = (short) y;
        tempRequestedUnitType = type;
        Packet.PacketRequestUnitPurchase packet = new Packet.PacketRequestUnitPurchase();
        packet.x = (short) x;
        packet.y = (short) y;
        packet.type = type;
        Main.dsClient.client.sendTCP(packet);
        Log.info("requested unit");
    }

    public void onPacketAnswerUnitPurchase(Packet.PacketAnswerUnitPurchase packet) {
        int id = packet.id;

        if (id != 0) {
            PassiveUnit unit = new PassiveUnit(tempRequestedUnitX, tempRequestedUnitY, tempRequestedUnitType);
            stage.addActor(unit);
            myPersonalFieldUnitsHashMap.put(id, unit);
            gameScreenRef.ui.unitPurchaseLayer.onUnitPurchased(tempRequestedUnitX, tempRequestedUnitY);
            Log.info("unit request answer: OK");
        } else {
            Log.info("unit request answer: ID = 0");
        }

    }

    public void onPacketWaveSpawned() {
        gameScreenRef.ui.waveTimer.onWaveSpawned();
    }


    public void onPacketBattlefieldUpdate(Packet.PacketGameUpdate packet) {
        money = packet.money;
        PackedUnit[] player1PackedUnits = packet.Player1PackedUnits;
        PackedUnit[] player2PackedUnits = packet.Player2PackedUnits;

        for (int i = 0; i < player1PackedUnits.length; i++) {

            PackedUnit packedUnit = player1PackedUnits[i];
            ActiveUnit unit;

            if (myUnitsHashMap.containsKey(packedUnit.id)) {
                unit = myUnitsHashMap.get(packedUnit.id);
            } else {
                unit = new ActiveUnit(packedUnit.x, packedUnit.y, packedUnit.type, packedUnit.id, gameScreenRef.bulletFactory);
                myUnitsHashMap.put(unit.id, unit);
                stage.addActor(unit);
            }

            unit.setTargetDestination(packedUnit.x, packedUnit.y);
            unit.lives = packedUnit.lives;

            if (unit.targetId != packedUnit.targetId && enemyUnitsHashMap.containsKey(packedUnit.targetId)) {
                unit.setTarget(enemyUnitsHashMap.get(packedUnit.targetId));
            }

            if (unit.lives <= 0) {
                Log.info(" 1 my DEAD");
                unit.setVisible(false);
                unit.dispose();
                myUnitsHashMap.remove(unit.id);
            }
        }


        for (int i = 0; i < player2PackedUnits.length; i++) {

            PackedUnit packedUnit = player2PackedUnits[i];
            ActiveUnit unit;

            if (enemyUnitsHashMap.containsKey(packedUnit.id)) {
                unit = enemyUnitsHashMap.get(packedUnit.id);
            } else {
                unit = new ActiveUnit(packedUnit.x, packedUnit.y, packedUnit.type, packedUnit.id, gameScreenRef.bulletFactory);
                enemyUnitsHashMap.put(unit.id, unit);
                stage.addActor(unit);

            }

            unit.setTargetDestination(packedUnit.x, packedUnit.y);
            unit.lives = packedUnit.lives;

            if (unit.targetId != packedUnit.targetId && myUnitsHashMap.containsKey(packedUnit.targetId)) {
                unit.setTarget(myUnitsHashMap.get(packedUnit.targetId));
            }

            if (unit.lives <= 0) {
                Log.info(" 1 his DEAD");
                unit.setVisible(false);
                unit.dispose();
                enemyUnitsHashMap.remove(unit.id);
            }
        }

    }


    public void onPacketAnswerUnitSell(boolean answer) {
    }

    public void onPacketAnswerUpgrade(boolean answer) {

    }

    public boolean canBuyUnit(short type) {

        return true;
    }

    public void onPacketCellsDebug(PackedCell[][] cells) {
        packedCells = cells;
    }

    public void onPacketRoomDestroyed() {
        System.out.println("ROOM DESTROYED");
    }
}
