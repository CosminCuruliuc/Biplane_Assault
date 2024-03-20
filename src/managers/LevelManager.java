package managers;

import Exceptions.InvalidLevelException;
import events.Level;
import helperMethods.LoadSave;
import objects.PathPoint;
import java.util.ArrayList;

import static main.GameStates.*;

public class LevelManager {
    private final ArrayList<Level> levels;
    private int currentLevelIndex;

    public LevelManager() {
        levels = new ArrayList<>();
        try {
            loadLevels();
        } catch (InvalidLevelException e) {
            throw new RuntimeException(e);
        }
        currentLevelIndex = 0;
    }

    private void loadLevels() throws InvalidLevelException {
        // Adauga numele fisierelor de nivel aici
        String[] levelNames = {"new level", "new level2", "new level3"};

        for (String name : levelNames) {
            int[][] map = LoadSave.GetLevelData(name);
            if(map == null) {
                throw new InvalidLevelException("Level data for " + name + " could not be loaded.");
            }
            ArrayList<PathPoint> pathPoints = LoadSave.GetLevelPathPoints(name);
            if(pathPoints == null) {
                throw new InvalidLevelException("Path points for " + name + " could not be loaded.");
            }
            Level level = new Level(name, map, pathPoints);
            levels.add(level);
        }
    }

    public void nextLevel() {
        if (currentLevelIndex < levels.size() - 1) {
            currentLevelIndex++;
            SetGameState(LEVEL_COMPLETE);
        }
    }

    public boolean hasMoreLevels() {
        return currentLevelIndex < levels.size() - 1;
    }

    public void gameEnd() {
        SetGameState(GAME_COMPLETE);
    }

    public void resetLevel() {
        currentLevelIndex = 0;
    }

    public void setCurrentLevelIndex(int currentLevelIndex) {
        this.currentLevelIndex = currentLevelIndex;
    }

    public Level getCurrentLevel() {
        return levels.get(currentLevelIndex);
    }

    public int getCurrentLevelIndex() {
        return currentLevelIndex;
    }
}
