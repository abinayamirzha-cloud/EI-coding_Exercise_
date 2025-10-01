**Use Case**

The Factory Method Pattern is used when a class wants to delegate the instantiation of objects to subclasses or separate factories. In this example, the client can request different types of notifications without knowing their concrete classes. It provides flexibility to add new notification types easily. The client remains independent of specific notification implementations, ensuring scalable and maintainable code.

**Key Roles in This Example**

**Product (Notification)** – The common interface (Notification) that defines the operations that all concrete products must implement (notifyUser).

**Concrete Products (EmailNotification, SMSNotification, PushNotification)** – Specific implementations of the product interface, representing different types of notifications.

**Creator (NotificationFactory**) – Declares the factory method (createNotification) that returns objects of type Notification. It encapsulates the object creation logic.

**Client (Main.java)** – Uses the factory method to obtain notification objects without knowing the concrete classes, promoting loose coupling.
