// Adapter makes Micro-USB work like USB-C
public class ChargerAdapter implements UsbCCharger {
    private MicroUsbCharger microUsbCharger;

    public ChargerAdapter(MicroUsbCharger microUsbCharger) {
        this.microUsbCharger = microUsbCharger;
    }

    @Override
    public void chargeWithUsbC() {
        // internally use Micro-USB
        microUsbCharger.chargeWithMicroUsb();
    }
}
