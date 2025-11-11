package panel.points;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    private static void createAndShowGUI() {
        JPanel inputPanel = new JPanel(new GridLayout(7, 2, 5, 5));

        JTextField widthField = new JTextField("600");
        JTextField heightField = new JTextField("400");
        JTextField minXField = new JTextField("0.0");
        JTextField maxXField = new JTextField("100.0");
        JTextField minYField = new JTextField("0.0");
        JTextField maxYField = new JTextField("100.0");
        JTextField maxPointsField = new JTextField("10");

        inputPanel.add(new JLabel("Ширина панели (пикселей):"));
        inputPanel.add(widthField);
        inputPanel.add(new JLabel("Высота панели (пикселей):"));
        inputPanel.add(heightField);
        inputPanel.add(new JLabel("Минимум X:"));
        inputPanel.add(minXField);
        inputPanel.add(new JLabel("Максимум X:"));
        inputPanel.add(maxXField);
        inputPanel.add(new JLabel("Минимум Y:"));
        inputPanel.add(minYField);
        inputPanel.add(new JLabel("Максимум Y:"));
        inputPanel.add(maxYField);
        inputPanel.add(new JLabel("Количество точек N:"));
        inputPanel.add(maxPointsField);

        int result = JOptionPane.showConfirmDialog(null, inputPanel,
                "Введите параметры панели", JOptionPane.OK_CANCEL_OPTION);

        if (result != JOptionPane.OK_OPTION) {
            System.out.println("Создание панели отменено");
            return;
        }

        int width, height, maxPoints;
        double minX, maxX, minY, maxY;

        try {
            width = Integer.parseInt(widthField.getText().trim());
            height = Integer.parseInt(heightField.getText().trim());
            minX = Double.parseDouble(minXField.getText().trim());
            maxX = Double.parseDouble(maxXField.getText().trim());
            minY = Double.parseDouble(minYField.getText().trim());
            maxY = Double.parseDouble(maxYField.getText().trim());
            maxPoints = Integer.parseInt(maxPointsField.getText().trim());

            if (width <= 0 || height <= 0 || maxPoints <= 0) {
                JOptionPane.showMessageDialog(null,
                        "Ширина, высота и количество точек должны быть положительными числами!",
                        "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (minX >= maxX || minY >= maxY) {
                JOptionPane.showMessageDialog(null,
                        "Минимальные значения должны быть меньше максимальных!",
                        "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
                return;
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                    "Неверный формат числа! Проверьте введенные данные.",
                    "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JFrame frame = new JFrame("📊 Панель Точек");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        PointsPanel pointsPanel = new PointsPanel(width, height, minX, maxX, minY, maxY, maxPoints);

        Observable generator = new Observable(minX, maxX, minY, maxY);

        generator.addObserver(pointsPanel);

        frame.add(pointsPanel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        JButton generateButton = new JButton("Сгенерировать точку");
        generateButton.addActionListener(e -> generator.generatePoint());
        controlPanel.add(generateButton);

        Timer timer = new Timer(1000, e -> generator.generatePoint());

        JToggleButton autoGenerateButton = new JToggleButton("Запустить автогенерацию");
        autoGenerateButton.addActionListener(e -> {
            if (autoGenerateButton.isSelected()) {
                timer.start();
                autoGenerateButton.setText("Остановить автогенерацию");
            } else {
                timer.stop();
                autoGenerateButton.setText("Запустить автогенерацию");
            }
        });
        controlPanel.add(autoGenerateButton);

        frame.add(controlPanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        System.out.println("Панель точек запущена!✅");
    }
}