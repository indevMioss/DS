package com.interdev.dstrike;

/**
 * Created by Evg256 on 15.01.2015.
 */
public interface PriceList {
    public static final short base_upgrade_lvl [] = {
            100,
            200,
            300
    };

    public static final short land_atk_lvl [] = {
            100,
            200,
            300
    };

    public static final short land_def_lvl [] = {
            100,
            200,
            300
    };

    public static final short air_atk_lvl [] = {
            100,
            200,
            300
    };

    public static final short air_def_lvl [] = {
            100,
            200,
            300
    };

    public static final short gas_upgrade_lvl [] = {
            100,
            200,
            300
    };

    public boolean purchaseAccept(PriceList p) {

    }



}