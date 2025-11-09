package panel.points;

import javax.swing.*;
import java.awt.*;

/**
 * Главный класс приложения.
 * Демонстрирует работу PointsPanel с паттерном Observer/Observable.
 */
public class Main {
    public static void main(String[] args) {
        // Запускаем GUI в потоке событий Swing
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    private static void createAndShowGUI() {
        // Создаем главное окно
        JFrame frame = new JFrame("📊 Панель Точек");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Создаем панель для отображения точек
        // Параметры: ширина, высота, minX, maxX, minY, maxY, количество точек N
        PointsPanel pointsPanel = new PointsPanel(600, 400, 0.0, 100.0, 0.0, 100.0, 10);

        // Создаем генератор данных (Observable)
        Observable generator = new Observable(0.0, 100.0, 0.0, 100.0);

        // Подписываем панель на генератор
        generator.addObserver(pointsPanel);

        // Добавляем панель на окно
        frame.add(pointsPanel, BorderLayout.CENTER);

        // Создаем панель управления
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        // Кнопка для генерации одной точки
        JButton generateButton = new JButton("🎲 Сгенерировать точку");
        generateButton.addActionListener(e -> generator.generatePoint());
        controlPanel.add(generateButton);

        // Кнопка для изменения диапазона
        JButton changeRangeButton = new JButton("🔽 Изменить диапазон (0-50)");
        changeRangeButton.addActionListener(e -> {
            generator.setRange(0.0, 50.0, 0.0, 50.0);
            pointsPanel.setRange(0.0, 50.0, 0.0, 50.0);
        });
        controlPanel.add(changeRangeButton);

        // Кнопка для восстановления диапазона
        JButton resetRangeButton = new JButton("🔄 Сбросить диапазон (0-100)");
        resetRangeButton.addActionListener(e -> {
            generator.setRange(0.0, 100.0, 0.0, 100.0);
            pointsPanel.setRange(0.0, 100.0, 0.0, 100.0);
        });
        controlPanel.add(resetRangeButton);

        // Кнопка для добавления точки напрямую через setVal
        JButton addDirectButton = new JButton("➕ Добавить точку (50, 50)");
        addDirectButton.addActionListener(e -> pointsPanel.setVal(50.0, 50.0));
        controlPanel.add(addDirectButton);

        frame.add(controlPanel, BorderLayout.SOUTH);

        // Создаем таймер для автоматической генерации точек
        Timer timer = new Timer(1000, e -> generator.generatePoint());

        // Кнопка для запуска/остановки автоматической генерации
        JToggleButton autoGenerateButton = new JToggleButton("▶️ Запустить автогенерацию");
        autoGenerateButton.addActionListener(e -> {
            if (autoGenerateButton.isSelected()) {
                timer.start();
                autoGenerateButton.setText("⏸️ Остановить автогенерацию");
            } else {
                timer.stop();
                autoGenerateButton.setText("▶️ Запустить автогенерацию");
            }
        });
        controlPanel.add(autoGenerateButton);

        // Настраиваем и показываем окно
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        System.out.println("Панель точек запущена!✅");
    }
}