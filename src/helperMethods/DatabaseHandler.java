package helperMethods;

import UI.ActionBar;
import enemies.Enemy;
import managers.EnemyManager;
import managers.LevelManager;
import managers.TowerManager;
import managers.WaveManager;
import objects.Tower;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static enemies.EnemyFactory.createEnemy;

public class DatabaseHandler {

    private static final String DB_URL = "jdbc:sqlite:game.db";

    // Method for database initialization
    public static void initializeDB() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                String sql = "CREATE TABLE IF NOT EXISTS scores " +
                        "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "playerName TEXT NOT NULL," +
                        "score INTEGER NOT NULL," +
                        "lives INTEGER NOT NULL ," +
                        "gold INTEGER NOT NULL ," +
                        "game_finished BOOLEAN NOT NULL)";
                stmt.execute(sql);

                String enemiesSql = "CREATE TABLE IF NOT EXISTS enemies " +
                        "(id INTEGER PRIMARY KEY," +
                        "enemyType INTEGER NOT NULL," +
                        "x REAL NOT NULL," +
                        "y REAL NOT NULL," +
                        "lastDir INTEGER NOT NULL," +
                        "health INTEGER NOT NULL," +
                        "isAlive BOOLEAN NOT NULL)";
                stmt.execute(enemiesSql);

                String towersSql = "CREATE TABLE IF NOT EXISTS towers " +
                        "(id INTEGER PRIMARY KEY," +
                        "x INTEGER NOT NULL," +
                        "y INTEGER NOT NULL," +
                        "towerType INTEGER NOT NULL," +
                        "tick INTEGER NOT NULL," +
                        "damage INTEGER NOT NULL," +
                        "range REAL NOT NULL," +
                        "cooldown REAL NOT NULL," +
                        "tier INTEGER NOT NULL)";
                stmt.execute(towersSql);

                String gameStateSql = "CREATE TABLE IF NOT EXISTS game_state " +
                        "(id INTEGER PRIMARY KEY," +
                        "currentLevelIndex INTEGER NOT NULL," +
                        "currentWaveIndex INTEGER NOT NULL," +
                        "currentEnemyIndex INTEGER NOT NULL," +
                        "lives INTEGER NOT NULL," +
                        "gold INTEGER NOT NULL)";
                stmt.execute(gameStateSql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method for inserting a score into the database
    public static void insertScore(String playerName, int score, int lives, int gold, boolean game_finished) {
        String sql = "INSERT INTO scores(playerName, score, lives, gold, game_finished) VALUES(?,?,?,?,?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, playerName);
            pstmt.setInt(2, score);
            pstmt.setInt(3, lives);
            pstmt.setInt(4, gold);
            pstmt.setBoolean(5, game_finished);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void saveTowers(List<Tower> towers) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            if (conn != null) {
                Statement stmt = conn.createStatement();

                // Stergerea tuturor inregistrarilor existente
                String sqlDelete = "DELETE FROM towers";
                stmt.execute(sqlDelete);

                // Inserarea noilor inregistrari
                String sqlInsert = "INSERT INTO towers(id, x, y, towerType, tick, damage, range, cooldown, tier) VALUES(?,?,?,?,?,?,?,?,?)";
                PreparedStatement pstmt = conn.prepareStatement(sqlInsert);

                for (Tower tower : towers) {
                    pstmt.setInt(1, tower.getId());
                    pstmt.setInt(2, tower.getX());
                    pstmt.setInt(3, tower.getY());
                    pstmt.setInt(4, tower.getTower_type());
                    pstmt.setInt(5, 0);  // Deoarece "tick" este o valoare temporară, este resetat la 0 la salvare
                    pstmt.setInt(6, tower.getDamage());
                    pstmt.setFloat(7, tower.getRange());
                    pstmt.setFloat(8, tower.getCooldown());
                    pstmt.setInt(9, tower.getTier());
                    pstmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void saveEnemies(List<Enemy> enemies) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            if (conn != null) {
                Statement stmt = conn.createStatement();

                // Stergerea tuturor inregistrarilor existente
                String sqlDelete = "DELETE FROM enemies";
                stmt.execute(sqlDelete);

                // Inserarea noilor inregistrari
                String sqlInsert = "INSERT INTO enemies(id, enemyType, x, y, lastDir, health, isAlive) VALUES(?,?,?,?,?,?,?)";
                PreparedStatement pstmt = conn.prepareStatement(sqlInsert);

                for (Enemy enemy : enemies) {
                    pstmt.setInt(1, enemy.getId());
                    pstmt.setInt(2, enemy.getEnemy_type());
                    pstmt.setFloat(3, enemy.getX());
                    pstmt.setFloat(4, enemy.getY());
                    pstmt.setInt(5, enemy.getLastDir());
                    pstmt.setInt(6, enemy.getHealth());
                    pstmt.setBoolean(7, enemy.isAlive());
                    pstmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void saveGameState(int currentLevelIndex, int currentWaveIndex, int currentEnemyIndex, int gold, int lives) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            if (conn != null) {
                Statement stmt = conn.createStatement();

                // Stergerea tuturor inregistrarilor existente
                String sqlDelete = "DELETE FROM game_state";
                stmt.execute(sqlDelete);

                // Inserarea noilor inregistrari
                String sqlInsert = "INSERT INTO game_state(id, currentlevelindex, currentwaveindex, currentenemyindex, gold, lives) VALUES(?,?,?,?,?,?)";
                PreparedStatement pstmt = conn.prepareStatement(sqlInsert);
                pstmt.setInt(1, 0);
                pstmt.setInt(2, currentLevelIndex);
                pstmt.setInt(3, currentWaveIndex);
                pstmt.setInt(4, currentEnemyIndex);
                pstmt.setInt(5, gold);
                pstmt.setInt(6, lives);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Enemy> loadEnemies(EnemyManager enemyManager) {
        String sql = "SELECT * FROM enemies";
        List<Enemy> enemies = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                int enemyType = rs.getInt("enemyType");
                float x = rs.getFloat("x");
                float y = rs.getFloat("y");
                int lastDir = rs.getInt("lastDir");
                int health = rs.getInt("health");
                boolean isAlive = rs.getBoolean("isAlive");

                Enemy enemy = createEnemy(enemyType, x, y, id, enemyManager);
                if (!isAlive) {
                    enemy.kill();
                }
                enemy.setLastDir(lastDir);
                enemy.setHealth(health);

                enemies.add(enemy);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return enemies;
    }

    public static List<Tower> loadTowers() {
        String sql = "SELECT * FROM towers";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)) {

            List<Tower> towers = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                int x = rs.getInt("x");
                int y = rs.getInt("y");
                int towerType = rs.getInt("towerType");
                int damage = rs.getInt("damage");
                float range = rs.getFloat("range");
                float cooldown = rs.getFloat("cooldown");
                Tower tower = new Tower(x, y, id, towerType);
                tower.setDamage(damage);
                tower.setRange(range);
                tower.setCooldown(cooldown);
                towers.add(tower);
            }
            return towers;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static int[] loadGameState() {
        String sql = "SELECT * FROM game_state ORDER BY id DESC LIMIT 1";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)) {
            if (rs.next()) {
                int currentLevelIndex = rs.getInt("currentLevelIndex");
                int currentWaveIndex = rs.getInt("currentWaveIndex");
                int currentEnemyIndex = rs.getInt("currentEnemyIndex");
                int gold = rs.getInt("gold");
                int lives = rs.getInt("lives");
                return new int[] { currentLevelIndex, currentWaveIndex, currentEnemyIndex, gold, lives };
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new int[] { 0, 0, 0, 100, 10 };
    }

    public static void loadGameFromDatabase(EnemyManager enemyManager, TowerManager towerManager, LevelManager levelManager, WaveManager waveManager, ActionBar actionBar) {
        try {
            // Load enemies
            List<Enemy> loadedEnemies = loadEnemies(enemyManager);
            enemyManager.setEnemies(loadedEnemies);

            // Load towers
            List<Tower> loadedTowers = loadTowers();
            if (loadedTowers != null) {
                towerManager.setTowers(loadedTowers);
            }

            // Load game state
            int[] loadedGameState = loadGameState();
            levelManager.setCurrentLevelIndex(loadedGameState[0]);
            waveManager.setCurrentWaveIndex(loadedGameState[1]);
            waveManager.setEnemyIndex(loadedGameState[2]);
            actionBar.setGold(loadedGameState[3]);
            actionBar.setLives(loadedGameState[4]);

        } catch (Exception e) {
            System.out.println("Failed to load game state from database.");
            e.printStackTrace();
        }
    }
}