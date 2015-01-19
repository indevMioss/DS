package com.interdev.dstrike.networking;

import com.interdev.dstrike.Player;

/**
 * Created by Evg256 on 14.01.2015.
 */
public enum PriceList {
    BASE_UPGRADE_LVL1(100),
    BASE_UPGRADE_LVL2(200),
    BASE_UPGRADE_LVL3(300),

    LAND_ATK_LVL1(100),
    LAND_ATK_LVL2(200),
    LAND_ATK_LVL3(300),

    LAND_DEF_LVL1(100),
    LAND_DEF_LVL2(200),
    LAND_DEF_LVL3(300),

    AIR_ATK_LVL1(100),
    AIR_ATK_LVL2(200),
    AIR_ATK_LVL3(300),

    AIR_DEF_LVL1(100),
    AIR_DEF_LVL2(200),
    AIR_DEF_LVL3(300),

    GAS_UPGRADE_LVL1(100),
    GAS_UPGRADE_LVL2(200),
    GAS_UPGRADE_LVL3(300);


    private short value;

    private PriceList(int value) {
        this.value = ((short) value);
    }

    public short getValue() {
        return value;
    }

    public boolean purchaseAccept(Player p) {
        return p.getMinerals() > this.value;
    }

}
