package helperMethods;

public class Constants {

    // Clasa pentru tipurile de turete
    public static class Towers {
        public static final int G_TOWER = 0;
        public static final int R_TOWER = 1;
        public static final int LAUNCHER = 2;

        public static int GetTowerCost(int tower_type) {
            return switch (tower_type) {
                case G_TOWER -> 30;
                case R_TOWER -> 40;
                case LAUNCHER -> 50;
                default -> 0;
            };
        }

        // Metode pentru lucrul cu tipurile de turete
        public static String GetName(int tower_type) {
            return switch (tower_type) {
                case G_TOWER -> "Green Tower";
                case R_TOWER -> "Red Tower";
                case LAUNCHER -> "Launcher";
                default -> "";
            };
        }

        public static int GetStartDmg(int tower_type) {
            return switch (tower_type) {
                case G_TOWER -> 20;
                case R_TOWER -> 10;
                case LAUNCHER -> 40;
                default -> 0;
            };
        }

        public static float GetDefaultRange(int tower_type) {
            return switch (tower_type) {
                case G_TOWER -> 100;
                case R_TOWER -> 120;
                case LAUNCHER -> 80;
                default -> 0;
            };
        }

        public static float GetDefaultCooldown(int tower_type) {
            return switch (tower_type) {
                case G_TOWER -> 50;
                case R_TOWER -> 60;
                case LAUNCHER -> 100;
                default -> 0;
            };
        }
    }

    // Clasa pentru tipurile de proiectile
    public static class Projectiles {
        public static final int BULLET = 0;
        public static final int SPECIAL_BULLET = 1;
        public static final int ROCKET = 2;

        // Metoda pentru viteza proiectilelor
        public static float GetSpeed(int type) {
            return switch (type) {
                case BULLET -> 7f;
                case SPECIAL_BULLET -> 5f;
                case ROCKET -> 8f;
                default -> 0f;
            };
        }
    }

    // Clasa pentru directii
    public static class Direction {
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }

    // Clasa pentru tipurile de dale
    public static class Tiles {
        public static final int SAND_TILE = 0;
        public static final int GRASS_TILE = 1;
        public static final int ROAD_TILE = 2;
        public static final int ROCK_TILE = 3;
    }

    // Clasa pentru tipurile de monstrii
    public static class Enemies {
        public static final int SMALL_GHOST = 0;
        public static final int SMALL_ORC = 1;
        public static final int SMALL_DEMON = 2;
        public static final int BIG_GHOST = 3;
        public static final int B_MONSTER = 4;
        public static final int G_MONSTER = 5;
        public static final int W_TREE = 6;
        public static final int B_TREE = 7;
        public static final int MASK_ORC = 8;
        public static final int BIG_ORC = 9;
        public static final int BOSS_ORC = 10;
        public static final int WIZARD = 11;
        public static final int FAST_DEMON = 12;
        public static final int BOSS_DEMON = 13;
        public static final int ANGEL = 14;

        public static int GetReward(int enemyType) {
            return switch (enemyType) {
                case SMALL_GHOST -> 3;
                case SMALL_ORC -> 5;
                case SMALL_DEMON -> 7;
                case BIG_GHOST -> 15;
                case B_MONSTER, G_MONSTER -> 4;
                case B_TREE, W_TREE -> 6;
                case MASK_ORC -> 20;
                case BIG_ORC -> 30;
                case BOSS_ORC -> 70;
                case WIZARD -> 25;
                case FAST_DEMON -> 40;
                case BOSS_DEMON -> 100;
                case ANGEL -> -10;
                default -> 0;
            };
        }

        public static int GetScore(int enemyType) {
            return switch (enemyType) {
                case SMALL_GHOST -> 1;
                case SMALL_ORC -> 2;
                case SMALL_DEMON -> 3;
                case BIG_GHOST -> 5;
                case B_MONSTER, G_MONSTER, B_TREE, W_TREE -> 4;
                case MASK_ORC -> 10;
                case BIG_ORC -> 15;
                case BOSS_ORC -> 80;
                case WIZARD -> 20;
                case FAST_DEMON -> 40;
                case BOSS_DEMON -> 200;
                case ANGEL -> -5;
                default -> 0;
            };
        }

        // Metode pentru lucrul cu tipurile de monstrii
        public static float GetSpeed(int enemyType) {
            return switch (enemyType) {
                case SMALL_GHOST, BIG_GHOST -> 0.4f;
                case SMALL_ORC -> 0.5f;
                case SMALL_DEMON -> 0.55f;
                case B_MONSTER, G_MONSTER, B_TREE, W_TREE -> 0.35f;
                case MASK_ORC -> 0.65f;
                case BIG_ORC -> 0.3f;
                case BOSS_ORC -> 0.25f;
                case WIZARD -> 0.8f;
                case FAST_DEMON -> 0.7f;
                case BOSS_DEMON -> 0.20f;
                case ANGEL -> 1f;
                default -> 0;
            };
        }

        public static int GetStartHealth(int enemyType) {
            return switch (enemyType) {
                case SMALL_GHOST -> 20;
                case SMALL_ORC -> 30;
                case SMALL_DEMON -> 40;
                case BIG_GHOST -> 100;
                case B_MONSTER, G_MONSTER, B_TREE, W_TREE, ANGEL -> 50;
                case MASK_ORC -> 200;
                case BIG_ORC -> 300;
                case BOSS_ORC -> 750;
                case WIZARD -> 120;
                case FAST_DEMON -> 225;
                case BOSS_DEMON -> 1000;
                default -> 0;
            };
        }
    }
}