package gui.spielplaneditor;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;

/**
 * Created by Kevin Gerspacher on 23.05.17.
 */
public class ShapeDrawState implements Toolstate
{
    private double xStart, yStart;
    private double xDiverence,yDiverence;
    private double xEnd, yEnd;

    private Pane group;

    // POSSIBLE SHAPES
    private String shape1 = "Rectangle";
    private String shape2 = "Circle";
    private String shape3 = "Hexagon";

    String shape = shape1;

    public void setPane(Pane pane) {
        this.group = pane;
    }

    public void setShape(String shape)
    {
        this.shape = shape;
    }

    public void onLeftClick(MouseEvent event)
    {
        if(shape == shape1)
        {
            xStart = event.getX();
            yStart = event.getY();
            xEnd = xStart;
            yEnd = yStart;

            Rectangle rect1 = new Rectangle(xStart, yStart, 1, 1);
            rect1.setStroke(Color.RED);
            rect1.setStrokeWidth(5.0);
            rect1.setFill(Color.BLUE); // übergabe von colorpicker
            group.getChildren().add(rect1);
        }
        else if(shape == shape2)
        {
            xStart = event.getX();
            yStart = event.getY();

            Circle circle = new Circle(xStart, yStart, 2);
            circle.setStroke(Color.RED);
            circle.setStrokeWidth(5);
            circle.setFill(Color.BLUE);// übergabe von colorpicker
            group.getChildren().add(circle);
        }
        else if(shape == shape3)
        {
            xStart = event.getX();
            yStart = event.getY();
            Polyline hexagon = new Polyline(xStart, yStart,
                    xStart+20, yStart,
                    xStart+25, yStart+15,
                    xStart+20, yStart+30,
                    xStart, yStart+30,
                    xStart-5, yStart+15,
                    xStart, yStart);
            hexagon.setFill(Color.RED);
            hexagon.setStroke(Color.BLACK);
            group.getChildren().add(hexagon);
        }
    }

    public void onRightClick(MouseEvent event)
    {

    }

    public void onLeftDown(MouseEvent event)
    {
        if(shape == shape1)
        {
            group.getChildren().remove(group.getChildren().size()-1);
            if (event.getX()>=xStart && event.getY()>=yStart){
                xDiverence = event.getX() - xStart;
                yDiverence = event.getY() - yStart;
                xEnd = xStart;
                yEnd = yStart;
            }
            else if(event.getX()<xStart && event.getY()>=yStart){
                xDiverence = -(event.getX() - xStart);
                yDiverence = event.getY() - yStart;
                xEnd= event.getX();
            }
            else if(event.getX()>=xStart && event.getY()<yStart) {
                xDiverence = event.getX() - xStart;
                yDiverence = -(event.getY() - yStart);
                yEnd=event.getY();
            }
            else{
                xDiverence = -(event.getX() - xStart);
                yDiverence = -(event.getY() - yStart);
                xEnd=event.getX();
                yEnd=event.getY();
            }
            Rectangle rect1 = new Rectangle(xEnd, yEnd, xDiverence, yDiverence);
            rect1.setStroke(Color.RED);
            rect1.setStrokeWidth(5.0);
            rect1.setFill(Color.BLUE);//übergabe von colorpicker
            group.getChildren().add(rect1);
        }
        else if(shape == shape2)
        {
            group.getChildren().remove(group.getChildren().size() - 1);
            double a =xStart - event.getX();
            double b = yStart - event.getY();
            xDiverence = Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
            Circle circle = new Circle(xStart, yStart, xDiverence);
            circle.setStroke(Color.RED);
            circle.setStrokeWidth(5);
            circle.setFill(Color.BLUE);// übergabe von colorpicker
            circle.setFill(Color.BLUE);// übergabe von colorpicker
            group.getChildren().add(circle);
        }
        else if(shape == shape3)
        {
            group.getChildren().remove(group.getChildren().size() - 1);
            xDiverence = event.getX() - xStart;
            yDiverence = event.getY() - yStart;
            Polyline hexagon = new Polyline(xStart, yStart,
                    xStart+xDiverence, yStart,
                    xStart+xDiverence*1.25, yStart+yDiverence/2,
                    xStart+xDiverence, yStart+yDiverence,
                    xStart, yStart+yDiverence,
                    xStart-xDiverence*0.25, yStart+yDiverence/2,
                    xStart, yStart);
            hexagon.setFill(Color.RED);
            hexagon.setStroke(Color.BLACK);
            hexagon.setStrokeWidth(5.0);
            group.getChildren().add(hexagon);
        }

    }

    public void onRightDown(MouseEvent event)
    {

    }

    public void onLeftUp(MouseEvent event)
    {

    }

    public void onRightUp(MouseEvent event)
    {

    }

}
