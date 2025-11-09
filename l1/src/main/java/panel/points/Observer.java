package panel.points;

/**
 * Интерфейс наблюдателя для получения уведомлений о новых точках
 */
public interface Observer {
    /**
     * Метод вызывается при появлении новой точки
     * @param x координата X
     * @param y координата Y
     */
    void update(double x, double y);
}
