package com.interdev.dstrike.screens.game;

public class UnitValues {

    public static UnitVal unit1 = new Unit1Values();
    public static UnitVal unit2 = new Unit1Values();
    public static UnitVal unit3 = new Unit1Values();
    public static UnitVal unit4 = new Unit1Values();

    public static class UnitVal {
        public short lives;
        public short damage;
        public short atk_range; // 0 - ближний бой
        public float atk_speed; // раз в X ms
        public short walk_speed; // виртуальных px/s
        public short texture_width;
    }

    public static class Unit1Values extends UnitVal {
        private Unit1Values() {
            lives = 60;
            damage = 5;
            atk_range = 0; // 0 - ближний бой
            atk_speed = 300; // раз в X ms
            walk_speed = 20; // виртуальных px/s
            texture_width = 80;
        }

    }
}





