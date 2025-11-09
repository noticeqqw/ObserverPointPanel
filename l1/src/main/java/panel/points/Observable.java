package panel.points;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Генератор данных, реализующий паттерн Observable.
 * Хранит список наблюдателей и уведомляет их о новых точках.
 */
public class Observable {
    private final List<Observer> observers;
    private final Random random;
    private double minX, maxX, minY, maxY;

    /**
     * Конструктор генератора
     * @param minX минимальное значение X для генерации
     * @param maxX максимальное значение X для генерации
     * @param minY минимальное значение Y для генерации
     * @param maxY максимальное значение Y для генерации
     */
    public Observable(double minX, double maxX, double minY, double maxY) {
        this.observers = new ArrayList<>();
        this.random = new Random();
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
    }

    /**
     * Добавить наблюдателя
     * @param observer наблюдатель
     */
    public void addObserver(Observer observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }

    /**
     * Удалить наблюдателя
     * @param observer наблюдатель
     */
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    /**
     * Уведомить всех наблюдателей о новой точке
     * @param x координата X
     * @param y координата Y
     */
    public void notifyObservers(double x, double y) {
        for (Observer observer : observers) {
            observer.update(x, y);
        }
    }

    /**
     * Сгенерировать случайную точку в заданном диапазоне
     * и уведомить всех наблюдателей
     */
    public void generatePoint() {
        double x = minX + (maxX - minX) * random.nextDouble();
        double y = minY + (maxY - minY) * random.nextDouble();
        notifyObservers(x, y);
    }

    /**
     * Установить новый диапазон для генерации
     */
    public void setRange(double minX, double maxX, double minY, double maxY) {
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
    }
}
