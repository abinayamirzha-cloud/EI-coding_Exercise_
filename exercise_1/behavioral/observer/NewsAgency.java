import java.util.*;

// Concrete Subject
public class NewsAgency implements Subject {
    private final List<Observer> subscribers = new ArrayList<>();
    private final String agencyName;

    public NewsAgency(String agencyName) {
        this.agencyName = agencyName;
    }

    @Override
    public void registerObserver(Observer o) {
        subscribers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        subscribers.remove(o);
    }

    @Override
    public void notifyObservers(String news) {
        for (Observer o : subscribers) {
            o.update(news);
        }
    }

    public void publishNews(String news) {
        System.out.println("\n" + agencyName + " publishes: " + news);
        notifyObservers(news);
    }
}
