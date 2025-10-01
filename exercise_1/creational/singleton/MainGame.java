public class MainSingletonDemo {
    public static void main(String[] args) {
        // First reference
        GameSettings s1 = GameSettings.getInstance();
        s1.showSettings();

        // Change settings
        s1.setDifficulty("Hard");
        s1.setMaxPlayers(8);

        // Second reference
        GameSettings s2 = GameSettings.getInstance();
        System.out.println("\nFrom second reference:");
        s2.showSettings();

        // Check both are same object
        System.out.println("\nAre both same instance? " + (s1 == s2));
    }
}
