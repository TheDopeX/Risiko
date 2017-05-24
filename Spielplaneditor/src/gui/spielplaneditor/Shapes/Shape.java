package gui.spielplaneditor.Shapes;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;

/**
 * @author Kevin Gerspacher
 * @version 0.1
 */
public abstract class Shape {
    public void setproperties(Color color, int line) {

    }

    public void setStart(double xVal, double yVal) {

    }

    public void setEnd(double xVal, double yVal) {

    }

    public void draw() {

    }

    public abstract Polyline getShape();

}
