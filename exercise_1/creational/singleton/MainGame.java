public class MainGame {
    public static void main(String[] args) {
        GameSettings settings1 = GameSettings.getInstance();
        settings1.setVolume(80);

        GameSettings settings2 = GameSettings.getInstance();
        System.out.println("Volume: " + settings2.getVolume()); // prints 80
    }
}
