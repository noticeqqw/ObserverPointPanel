package panel.points;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class PointsPanel extends JPanel implements Observer {
    private final List<Point2D.Double> points;
    private final int maxPoints;
    private double minX, maxX, minY, maxY;
    private static final int POINT_SIZE = 6;
    private static final Color POINT_COLOR = new Color(0, 120, 215);
    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private static final int PADDING = 20;

    public PointsPanel(int width, int height, double minX, double maxX,
                       double minY, double maxY, int maxPoints) {
        this.points = new ArrayList<>();
        this.maxPoints = maxPoints;
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;

        setPreferredSize(new Dimension(width, height));
        setBackground(BACKGROUND_COLOR);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    }

    public void setVal(double x, double y) {
        synchronized (points) {
            points.add(new Point2D.Double(x, y));

            if (points.size() > maxPoints) {
                points.remove(0);
            }
        }

        repaint();
    }

    public void setRange(double minX, double maxX, double minY, double maxY) {
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
        repaint();
    }

    public List<Point2D.Double> getPoints() {
        synchronized (points) {
            return Collections.unmodifiableList(new ArrayList<>(points));
        }
    }

    @Override
    public void update(double x, double y) {
        setVal(x, y);
    }

    private int toScreenX(double x) {
        int width = getWidth() - 2 * PADDING;
        return PADDING + (int) ((x - minX) / (maxX - minX) * width);
    }

    private int toScreenY(double y) {
        int height = getHeight() - 2 * PADDING;
        return getHeight() - PADDING - (int) ((y - minY) / (maxY - minY) * height);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.LIGHT_GRAY);
        g2d.drawRect(PADDING, PADDING,
                    getWidth() - 2 * PADDING,
                    getHeight() - 2 * PADDING);

        g2d.setColor(POINT_COLOR);
        synchronized (points) {
            for (Point2D.Double point : points) {
                int screenX = toScreenX(point.x);
                int screenY = toScreenY(point.y);

                g2d.fillOval(screenX - POINT_SIZE / 2,
                           screenY - POINT_SIZE / 2,
                           POINT_SIZE, POINT_SIZE);
            }
        }

        g2d.setColor(Color.DARK_GRAY);
        g2d.setFont(new Font("Monospaced", Font.PLAIN, 10));
        g2d.drawString(String.format(Locale.US, "Диапазон: X[%.1f, %.1f] Y[%.1f, %.1f]",
                                    minX, maxX, minY, maxY), 5, 15);
        g2d.drawString(String.format(Locale.US, "Точки: %d/%d", points.size(), maxPoints),
                      5, getHeight() - 5);
    }
}
