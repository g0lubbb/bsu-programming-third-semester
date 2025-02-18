import java.util.ArrayList; 
import java.util.List; 
 
public class KeySubject implements Subject { 
    private final List<Observer> observers = new ArrayList<>(); 
 
    @Override 
    public void attach(Observer o) { 
        observers.add(o); 
    } 
 
    @Override 
    public void detach(Observer o) { 
        observers.remove(o); 
    } 
 
    @Override 
    public void notifyObservers(String key) { 
        for (Observer o : observers) { 
            o.update(key); 
        } 
    } 
}
