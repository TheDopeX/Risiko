package gui.spielplaneditor.Shapes;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;

/**
 * @author Kevin Gerspacher
 * @version 0.1
 */
public class Rectangle extends Shape {
    private double xStart, yStart;
    private double xEnd, yEnd;
    private Color color;
    private int line;
    private Polyline shape;

    /*
     * @param color
     * @param line
     */
    @Override
    public void setproperties(Color color, int line) {
        this.color = color;
        this.line = line;
    }

    /*
 * @param xVal
 * @param yVal
 */
    @Override
    public void setStart(double xVal, double yVal) {
        this.xStart = xVal;
        this.yStart = yVal;
    }

    /*
 * @param xVal
 * @param yVal
 */
    @Override
    public void setEnd(double xVal, double yVal) {
        this.xEnd = xVal;
        this.yEnd = yVal;
    }

    /*
    *
    */
    @Override
    public void draw() {
        shape = new Polyline(xStart, yStart,
                xStart + xEnd, yStart,
                xStart + xEnd, yStart + yEnd,
                xStart, yStart + yEnd,
                xStart, yStart);

        shape.setFill(Color.RED);
        shape.setStroke(Color.BLACK);
        shape.setStrokeWidth(5.0);
    }

    /*
     * @return Returns polyline from shape
     */
    public Polyline getShape() {
        return shape;
    }
}

