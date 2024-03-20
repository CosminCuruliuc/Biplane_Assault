package main;

// Clasa GameStates definește diferitele stări ale jocului și metodele pentru gestionarea acestora.
public enum GameStates {

    // Starile
    PLAYING,
    MENU,
    EDIT,
    GAME_OVER,
    LEVEL_COMPLETE,
    GAME_COMPLETE;

    // Starea initiala este Meniul
    public static GameStates gameStates = MENU;

    // Setează starea curentă a jocului
    public static void SetGameState(GameStates state) {
        gameStates = state;
    }
}
