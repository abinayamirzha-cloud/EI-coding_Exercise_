**Use Case**

The Observer Pattern is used when a one-to-many dependency exists and multiple objects need to be updated automatically when the subject changes. In this example, a news agency can notify all registered subscribers whenever a new article is published. Subscribers can join or leave dynamically, and updates happen without changing the subject’s core logic. This ensures decoupled, maintainable, and real-time notifications.

**Key Roles in This Example**

**Subject (NewsAgency)** – Maintains a list of observers (subscribers) and notifies them of state changes (new articles).

**Observer (Observer interface)** – Declares the update method that all concrete observers must implement to receive notifications.

**Concrete Observer (Subscriber)** – Implements the Observer interface and defines how updates are handled when the subject publishes news.

**Concrete Subject Behavior (publishNews)** – Encapsulates the logic for state change and automatically triggers notifications to all registered observers.

**Client (MainObserverDemo)** – Registers/unregisters observers and interacts with the subject, demonstrating the dynamic one-to-many relationship.
