package com.interdev.dstrike.screens.game;

import com.interdev.dstrike.networking.PackedUnit;

import java.util.HashMap;

public class Player {

    public HashMap<Integer, Unit> myPersonalFieldUnitsHashMap;

    public HashMap<Integer, Unit> myUnitsHashMap;
    public HashMap<Integer, Unit> enemyUnitsHashMap;

    public Player() {
        myPersonalFieldUnitsHashMap = new HashMap<Integer, Unit>();

        myUnitsHashMap = new HashMap<Integer, Unit>();
        enemyUnitsHashMap = new HashMap<Integer, Unit>();
    }

    public void onPacketWaveSpawned() {

    }

    public void onPacketBattlefieldUpdate(PackedUnit[] player1PackedUnits, PackedUnit[] player2PackedUnits) {

    }

    public void onPacketAnswerUnitPurchase(int id) {

    }

    public void onPacketAnswerUnitSell(boolean answer) {
    }

    public void onPacketAnswerUpgrade(boolean answer) {

    }
}
