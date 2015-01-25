package com.interdev.dstrike;

import com.interdev.dstrike.networking.PriceList;

/**
 * Created by Evg256 on 14.01.2015.
 */
public class Player {
    private short minerals;
    private short base_health;
    private short base_upgrade_lvl;
    private short gas_upgrade_lvl;
    private short land_attack_bonus;
    private short land_def_bonus;
    private short air_attack_bonus;
    private short air_def_bonus;

    public static final short START_MINERALS = 100;
    public static final short START_HEALTH = 1000;
    public static final short START_BASE_LVL = 0;
    public static final short START_GAS_LVL = 0;
    public static final short START_LAND_ATK_BONUS = 0;
    public static final short START_LAND_DEF_BONUS = 0;
    public static final short START_AIR_ATK_BONUS = 0;
    public static final short START_AIR_DEF_BONUS = 0;

    //private ArrayList<Unit> army;

    Player() {
        minerals = START_MINERALS;
        base_health = START_HEALTH;
        base_upgrade_lvl = START_BASE_LVL;
        gas_upgrade_lvl = START_GAS_LVL;
        land_attack_bonus = START_LAND_ATK_BONUS;
        land_def_bonus = START_LAND_DEF_BONUS;
        air_attack_bonus = START_AIR_ATK_BONUS;
        air_def_bonus = START_AIR_DEF_BONUS;
    }


    public short getMinerals() {
        return minerals;
    }

    public void setMinerals(short minerals) {
        this.minerals = minerals;
    }

    public short getBase_health() {
        return base_health;
    }

    public void setBase_health(short base_health) {
        this.base_health = base_health;
    }

    public short getBase_upgrade_lvl() {
        return base_upgrade_lvl;
    }

    public void setBase_upgrade_lvl(short base_upgrade_lvl) {
        this.base_upgrade_lvl = base_upgrade_lvl;
    }

    public short getGas_upgrade_lvl() {
        return gas_upgrade_lvl;
    }

    public void setGas_upgrade_lvl(short gas_upgrade_lvl) {
        this.gas_upgrade_lvl = gas_upgrade_lvl;
    }

    public short getLand_attack_bonus() {
        return land_attack_bonus;
    }

    public void setLand_attack_bonus(short land_attack_bonus) {
        this.land_attack_bonus = land_attack_bonus;
    }

    public short getLand_def_bonus() {
        return land_def_bonus;
    }

    public void setLand_def_bonus(short land_def_bonus) {
        this.land_def_bonus = land_def_bonus;
    }

    public short getAir_attack_bonus() {
        return air_attack_bonus;
    }

    public void setAir_attack_bonus(short air_attack_bonus) {
        this.air_attack_bonus = air_attack_bonus;
    }

    public short getAir_def_bonus() {
        return air_def_bonus;
    }

    public void setAir_def_bonus(short air_def_bonus) {
        this.air_def_bonus = air_def_bonus;
    }

    public void upgradeBase() {
        switch (base_upgrade_lvl + 1) {
            case 1:
                if(PriceList.BASE_UPGRADE_LVL1.purchaseAccept(this))
                    base_upgrade_lvl += 1;
                break;
            case 2:
                if(PriceList.BASE_UPGRADE_LVL2.purchaseAccept(this))
                    base_upgrade_lvl += 1;
                break;
            case 3:
                if(PriceList.BASE_UPGRADE_LVL3.purchaseAccept(this))
                    base_upgrade_lvl += 1;
                break;
            default:
                //нот энаф минералс блеать !
        }

    }
}
