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

    public HashMap<Integer, PassiveUnit> myPersonalFieldUnitsHashMap;

    public HashMap<Integer, ActiveUnit> myUnitsHashMap;
    public HashMap<Integer, ActiveUnit> enemyUnitsHashMap;

    private short tempRequestedUnitX = 0;
    private short tempRequestedUnitY = 0;
    private short tempRequestedUnitType = 1;

    private Stage stage;

    public PackedCell[][] packedCells;

    public Player(GameScreen gameScreen) {
        this.stage = gameScreen.mainStage;

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
            Log.info("unit request answer: OK");
        } else {
            Log.info("unit request answer: ID = 0");
        }

    }

    public void onPacketWaveSpawned() {
        Log.info("wave spawned");
    }


    public void onPacketBattlefieldUpdate(Packet.PacketBattlefieldUnitsUpdate packet) {

        PackedUnit[] player1PackedUnits = packet.Player1PackedUnits;
        PackedUnit[] player2PackedUnits = packet.Player2PackedUnits;

        if (player1PackedUnits.length > 0) {
            for (int i = 0; i < player1PackedUnits.length; i++) {

                PackedUnit packedUnit = player1PackedUnits[i];
                ActiveUnit unit;

                if (myUnitsHashMap.containsKey(packedUnit.id)) {
                    unit = myUnitsHashMap.get(packedUnit.id);
                } else {
                    unit = new ActiveUnit(packedUnit.x, packedUnit.y, packedUnit.type, packedUnit.id);
                    myUnitsHashMap.put(unit.id, unit);
                    stage.addActor(unit);
                }

                unit.setTargetDestination(packedUnit.x, packedUnit.y);
                unit.lives = packedUnit.lives;
                unit.targetId = packedUnit.targetId;

                if (unit.lives <= 0) {
                    Log.info(" 1 my DEAD");
                    unit.setVisible(false);
                    myUnitsHashMap.remove(unit.id);
                }
            }
        }

        if (player2PackedUnits.length > 0) {
            for (int i = 0; i < player2PackedUnits.length; i++) {

                PackedUnit packedUnit = player2PackedUnits[i];
                ActiveUnit unit;

                if (enemyUnitsHashMap.containsKey(packedUnit.id)) {
                    unit = enemyUnitsHashMap.get(packedUnit.id);
                } else {
                    unit = new ActiveUnit(packedUnit.x, packedUnit.y, packedUnit.type, packedUnit.id);
                    enemyUnitsHashMap.put(unit.id, unit);
                    stage.addActor(unit);

                }

                unit.setTargetDestination(packedUnit.x, packedUnit.y);
                unit.lives = packedUnit.lives;
                unit.targetId = packedUnit.targetId;

                if (unit.lives <= 0) {
                    Log.info(" 1 his DEAD");
                    unit.setVisible(false);

                    enemyUnitsHashMap.remove(unit.id);
                }
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
}
