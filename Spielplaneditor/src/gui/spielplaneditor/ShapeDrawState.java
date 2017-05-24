package gui.spielplaneditor;

import gui.spielplaneditor.Shapes.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polyline;

/**
 * @author Kevin Gerspacher
 * @version 0.2
 */
public class ShapeDrawState implements Toolstate {
    private double xStart, yStart;
    private double xDiverence, yDiverence;
    private double xEnd, yEnd;

    private Pane group;

    public Shape shape;


    public void setPane(Pane pane) {
        this.group = pane;
    }


    public void onLeftClick(MouseEvent event) {
        xStart = event.getX();
        yStart = event.getY();
        xEnd = xStart;
        yEnd = yStart;

        shape.setStart(xStart, yStart);
        shape.setEnd(0, 0);
        shape.draw();
        Polyline pol = shape.getShape();
        group.getChildren().add(pol);

    }

    public void onRightClick(MouseEvent event) {

    }

    public void onLeftDown(MouseEvent event) {
        group.getChildren().remove(group.getChildren().size() - 1);
        xDiverence = event.getX() - xStart;
        yDiverence = event.getY() - yStart;
        shape.setStart(xStart, yStart);
        shape.setEnd(xDiverence, yDiverence);
        shape.draw();
        Polyline pol = shape.getShape();
        group.getChildren().add(pol);

    }

    public void onRightDown(MouseEvent event) {

    }

    public void onLeftUp(MouseEvent event) {

    }

    public void onRightUp(MouseEvent event) {

    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }
}
