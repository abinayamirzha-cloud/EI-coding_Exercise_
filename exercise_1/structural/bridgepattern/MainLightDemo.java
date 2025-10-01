public class MainLightDemo {
    public static void main(String[] args) {
        Light deskLight = new DeskLight();
        Switch simpleSwitch = new Switch(deskLight);

        simpleSwitch.pressOn();   // Desk Light is ON
        simpleSwitch.pressOff();  // Desk Light is OFF
    }
}
