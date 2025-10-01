public class MainAdapterDemo {
    public static void main(String[] args) {
        MicroUsbCharger oldCharger = new MicroUsbCharger();
        UsbCCharger adapter = new ChargerAdapter(oldCharger);

        // Now phone thinks it's charging with USB-C
        adapter.chargeWithUsbC();
    }
}
