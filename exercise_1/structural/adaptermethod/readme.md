**Use Case**


The Adapter Pattern is used when you want to make an existing class compatible with a new interface without modifying its code. In this example, an old Micro-USB charger can be used by devices expecting a USB-C charger. It allows legacy hardware to work with modern systems. The client code can now use the new interface without worrying about the underlying implementation.


**Key Roles in This Example**

**Target (UsbCCharger)** – Defines the interface expected by the client (chargeWithUsbC()), representing the new USB-C charging standard.

**Adaptee (MicroUsbCharger)** – The existing interface/class that needs adapting (chargeWithMicroUsb()), representing the old Micro-USB charger.

**Adapter (ChargerAdapter)** – Implements the target interface (UsbCCharger) and internally calls the adaptee’s methods, bridging the gap between old and new interfaces.

**Client (MainAdapterDemo)** – The class that uses the target interface (UsbCCharger) and is unaware of the adaptee, demonstrating seamless usage.

