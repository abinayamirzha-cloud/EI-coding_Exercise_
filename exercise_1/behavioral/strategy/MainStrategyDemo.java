public class MainStrategyDemo {
    public static void main(String[] args) {
        TravelContext travel = new TravelContext();

        // User chooses strategy dynamically
        travel.setRouteStrategy(new FastestRoute());
        travel.buildRoute("New York", "Boston");

        travel.setRouteStrategy(new ShortestRoute());
        travel.buildRoute("New York", "Boston");

        travel.setRouteStrategy(new EcoFriendlyRoute());
        travel.buildRoute("New York", "Boston");
    }
}
