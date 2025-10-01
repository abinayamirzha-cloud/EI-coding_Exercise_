**Use Case**

The Strategy Pattern is used when multiple algorithms can solve the same problem, and the choice of algorithm must be dynamic. In this example, a travel app can calculate routes using fastest, shortest, or eco-friendly strategies. Users can switch strategies at runtime without changing the underlying code. It separates the algorithm logic from the context, making the system flexible and maintainable.

**Key Roles in This Example**

**Strategy (RouteStrategy)** – Defines the common interface for all route calculation algorithms (buildRoute).

**Concrete Strategies (FastestRoute, ShortestRoute, EcoFriendlyRoute)** – Implement specific algorithms for route calculation according to the chosen strategy.

**Context (TravelContext)** – Maintains a reference to a RouteStrategy and delegates route calculation to the selected strategy.

**Client (MainStrategyDemo)** – Configures the context with different strategies dynamically and triggers the behavior.

**Strategy Setter (setRouteStrategy)** – Provides a way to change the strategy at runtime, enabling flexible behavior without modifying the context.
