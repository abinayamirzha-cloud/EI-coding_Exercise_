**Use Case**

The Singleton Pattern is used when only one instance of a class should exist throughout the application. In this example, GameSettings ensures all parts of the game use the same configuration. It prevents multiple conflicting copies of settings. Any changes made via one reference are immediately visible everywhere.

**Key Roles in This Example**

**Singleton Class (GameSettings)** – Encapsulates all the data and logic, ensuring only one instance exists.

**Private Constructor** – Prevents external classes from creating new instances directly.

**Static Instance (instance)** – Holds the single shared object of the class.

**Global Access Method (getInstance)** – Provides a controlled access point for clients to retrieve the singleton instance.

**Client (MainSingletonDemo)** – Uses the singleton instance to access or modify settings, demonstrating that all references point to the same object.
