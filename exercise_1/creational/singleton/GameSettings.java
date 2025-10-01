// Singleton class
public class GameSettings {
    // Only one instance
    private static GameSettings instance;

    // Some settings
    private String difficulty = "Medium";
    private int maxPlayers = 4;

    // Private constructor
    private GameSettings() {}

    // Get the single instance
    public static GameSettings getInstance() {
        if (instance == null) {
            instance = new GameSettings();
        }
        return instance;
    }

    // Methods
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }
    public void setMaxPlayers(int maxPlayers) { this.maxPlayers = maxPlayers; }

    public void showSettings() {
        System.out.println("Difficulty: " + difficulty);
        System.out.println("Max Players: " + maxPlayers);
    }
}
