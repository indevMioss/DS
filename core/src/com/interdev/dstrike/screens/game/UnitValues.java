package com.interdev.dstrike.screens.game;


public class UnitValues {
    public static final int unitTypesAmount = 8;

    private static UnitVal unit0 = new Unit0Values();
    private static UnitVal unit1 = new Unit0Values();
    private static UnitVal unit2 = new Unit0Values();
    private static UnitVal unit3 = new Unit0Values();
    private static UnitVal unit4 = new Unit0Values();
    private static UnitVal unit5 = new Unit0Values();
    private static UnitVal unit6 = new Unit0Values();
    private static UnitVal unit7 = new Unit0Values();

    public static UnitVal getByType(int type) {
        switch (type) {
            case 0:
                return unit0;
            case 1:
                return unit1;
            case 2:
                return unit2;
            case 3:
                return unit3;
            case 4:
                return unit4;
            case 5:
                return unit5;
            case 6:
                return unit6;
            case 7:
                return unit7;
        }
        return unit0;
    }

    public static class UnitVal {
        public short price;
        public short lives;
        public short damage;
        public short atk_range; // 0 - ближний бой
        public float atk_speed; // раз в X ms
        public short walk_speed; // виртуальных px/s
        public short texture_width;

        public String texturePath;
        public String iconTexturePath;

    }

    private static class Unit0Values extends UnitVal {
        private Unit0Values() {
            price = 120;
            lives = 60;
            damage = 5;
            atk_range = 0; // 0 - ближний бой
            atk_speed = 300; // раз в X ms
            walk_speed = 20; // виртуальных px/s
            texture_width = 80;

            texturePath = "unit0.png";
            iconTexturePath = "icon";
        }

    }
}
