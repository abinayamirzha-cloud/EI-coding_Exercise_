public class MainObserverDemo {
    public static void main(String[] args) {
        NewsAgency agency = new NewsAgency("Global News");

        Subscriber alice = new Subscriber("Alice");
        Subscriber bob = new Subscriber("Bob");
        Subscriber charlie = new Subscriber("Charlie");

        agency.registerObserver(alice);
        agency.registerObserver(bob);
        agency.registerObserver(charlie);

        agency.publishNews("Java 21 Released!");
        agency.publishNews("New AI Breakthrough Announced");

        agency.removeObserver(bob);
        agency.publishNews("SpaceX Launch Successful");
    }
}
