package managers;

import java.util.ArrayList;
import java.util.Arrays;

import Exceptions.InvalidEnemyException;
import Exceptions.NoMoreWavesException;
import events.Level;
import events.Wave;
import scenes.Playing;

public class WaveManager {

    private final Playing playing;
    private final ArrayList<Wave> waves = new ArrayList<>();
    private final int enemySpawnTickLimit = 60;
    private int enemySpawnTick = enemySpawnTickLimit;
    private int enemyIndex, waveIndex;
    private final int waveTickLimit = 60 * 5;
    private int waveTick = 0;
    private boolean waveStartTimer, waveTickTimerOver;

    public WaveManager(Playing playing) {
        this.playing = playing;
        createWaves(this.playing.getLevelManager().getCurrentLevel());
    }

    public void update() {
        if (enemySpawnTick < enemySpawnTickLimit)
            enemySpawnTick++;

        if (waveStartTimer) {
            waveTick++;
            if (waveTick >= waveTickLimit) {
                waveTickTimerOver = true;
            }
        }

    }

    public void increaseWaveIndex() throws NoMoreWavesException {
        if(waveIndex + 1 >= waves.size()) {
            throw new NoMoreWavesException("There are no more waves in this level.");
        }
        waveIndex++;
        waveTick = 0;
        waveTickTimerOver = false;
        waveStartTimer = false;
    }

    public boolean isWaveTimerOver() {
        return waveTickTimerOver;
    }

    public void startWaveTimer() {
        waveStartTimer = true;
    }

    public int getNextEnemy() throws InvalidEnemyException {
        if(enemyIndex >= waves.get(waveIndex).getEnemies().size()) {
            throw new InvalidEnemyException("No more enemies in this wave.");
        }
        enemySpawnTick = 0;
        return waves.get(waveIndex).getEnemies().get(enemyIndex++);
    }

    private void createWaves(Level currentLevel) {
        switch(currentLevel.getName()) {
            case "new level" -> {  // Ghost level
                waves.add(new Wave(new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 4))));
                waves.add(new Wave(new ArrayList<>(Arrays.asList(3, 3, 3, 3, 0, 5))));
                waves.add(new Wave(new ArrayList<>(Arrays.asList(0, 0, 3, 3, 3, 4, 11, 14))));
            }

            case "new level2" -> {  // Orc level
                waves.add(new Wave(new ArrayList<>(Arrays.asList(1, 1, 1, 1, 1, 6))));
                waves.add(new Wave(new ArrayList<>(Arrays.asList(1, 1, 8, 8, 7))));
                waves.add(new Wave(new ArrayList<>(Arrays.asList(8, 9, 11, 14))));
                waves.add(new Wave(new ArrayList<>(Arrays.asList(1, 1, 1, 1, 10))));
            }

            case "new level3" -> {  // Demon level
                waves.add(new Wave(new ArrayList<>(Arrays.asList(2, 2, 2, 2, 2, 5))));
                waves.add(new Wave(new ArrayList<>(Arrays.asList(12, 12, 2, 7))));
                waves.add(new Wave(new ArrayList<>(Arrays.asList(2, 2, 2, 2, 12, 4, 11, 14))));
                waves.add(new Wave(new ArrayList<>(Arrays.asList(2, 12, 12, 13))));
            }
        }
    }


    public float getTimeLeft() {
        float ticksLeft = waveTickLimit - waveTick;
        return ticksLeft / 60.0f;
    }

    public void reset() {
        waves.clear();
        createWaves(this.playing.getLevelManager().getCurrentLevel());
        enemyIndex = 0;
        waveIndex = 0;
        waveStartTimer = false;
        waveTickTimerOver = false;
        waveTick = 0;
        enemySpawnTick = enemySpawnTickLimit;
    }
    public ArrayList<Wave> getWaves() {
        return waves;
    }

    public boolean isTimeForNewEnemy() {
        return enemySpawnTick >= enemySpawnTickLimit;
    }

    public boolean isThereMoreEnemiesInWave() {
        return enemyIndex < waves.get(waveIndex).getEnemies().size();
    }

    public boolean isThereMoreWaves() {
        return waveIndex + 1 < waves.size();
    }

    public void resetEnemyIndex() {
        enemyIndex = 0;
    }

    public int getWaveIndex() {
        return waveIndex;
    }

    public void setEnemyIndex(int enemyIndex) {
        this.enemyIndex = enemyIndex;
    }

    public boolean isWaveTimerStarted(){
        return waveStartTimer;
    }

    public void setCurrentWaveIndex(int waveIndex) {
        this.waveIndex = waveIndex;
    }

    public int getEnemyIndex() {
        return enemyIndex;
    }
}