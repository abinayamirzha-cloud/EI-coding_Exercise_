// Concrete Strategy 1
public class FastestRoute implements RouteStrategy {
    @Override
    public void buildRoute(String start, String end) {
        System.out.println("Calculating fastest route from " + start + " to " + end);
    }
}
