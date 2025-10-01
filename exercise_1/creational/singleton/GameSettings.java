// Singleton class to manage game settings
public class GameSettings {
    // Single private static instance
    private static GameSettings instance;

    // Game settings variables
    private String difficulty;
    private int maxPlayers;

    // Private constructor to prevent external instantiation
    private GameSettings() {
        // default settings
        this.difficulty = "Medium";
        this.maxPlayers = 4;
    }

    // Public method to get the single instance (lazy initialization)
    public static synchronized GameSettings getInstance() {
        if (instance == null) {
            instance = new GameSettings();
        }
        return instance;
    }

    // Getters and setters
    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

    public int getMaxPlayers() { return maxPlayers; }
    public void setMaxPlayers(int maxPlayers) { this.maxPlayers = maxPlayers; }

    // Display settings
    public void showSettings() {
        System.out.println("Game Difficulty: " + difficulty);
        System.out.println("Max Players: " + maxPlayers);
    }
}
