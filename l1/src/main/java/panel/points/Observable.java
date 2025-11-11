package panel.points;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Observable {
    private final List<Observer> observers;
    private final Random random;
    private double minX, maxX, minY, maxY;

    public Observable(double minX, double maxX, double minY, double maxY) {
        this.observers = new ArrayList<>();
        this.random = new Random();
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
    }

    public void addObserver(Observer observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers(double x, double y) {
        for (Observer observer : observers) {
            observer.update(x, y);
        }
    }

    public void generatePoint() {
        double x = minX + (maxX - minX) * random.nextDouble();
        double y = minY + (maxY - minY) * random.nextDouble();
        notifyObservers(x, y);
    }

    public void setRange(double minX, double maxX, double minY, double maxY) {
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
    }
}
