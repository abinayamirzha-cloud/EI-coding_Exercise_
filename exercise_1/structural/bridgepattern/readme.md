**usecase**


A desk lamp can be controlled using a simple switch, and later the same lamp can also be operated with a fancy switch without changing its code.
Similarly, a ceiling light can work with both types of switches.
This shows how switches (abstraction) and lights (implementation) stay independent but connect through the bridge.
Key Roles in Bridge Pattern

**Implementor (Light)**

 Defines the interface for the functionality (turnOn, turnOff).

 Abstracts the operations so the controller (switch) does not depend on specific implementations.

**Concrete Implementor (DeskLight, CeilingLight)**
Provides the actual implementation of the interface.

Executes the behavior defined by the Implementor.

**Abstraction (Switch)**

Maintains a reference to the Implementor.

Provides high-level control methods (pressOn, pressOff) for the client.

**Refined Abstraction (FancySwitch)**

Extends the abstraction with additional functionality.

Still works with any Implementor without changing the light code.

**Client (MainLightDemo)**

Uses the abstraction to operate the Implementor.

Interacts with the system without knowing the internal details of the light.

