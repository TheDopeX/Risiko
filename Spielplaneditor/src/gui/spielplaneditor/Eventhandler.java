package gui.spielplaneditor;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 * Created by Kevin Gerspacher on 16.05.17.
 */
public class Eventhandler {

    private int stage;
    private double xStart, yStart;
    private double xDiverence,yDiverence;
    private double xEnd, yEnd;
    private boolean setStart;
    private GraphicsContext graphicsContext;
    private Group group;

    public Eventhandler(GraphicsContext graphicsContext, Group group) {
        this.graphicsContext = graphicsContext;
        this.group = group;
    }

    public EventHandler<MouseEvent> drawEvent() {
        return new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                switch (stage) {
                    case 0:
                        break;

                    case 1:
                        if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
                            graphicsContext.beginPath();
                            graphicsContext.moveTo(event.getX(), event.getY());
                            graphicsContext.stroke();
                        } else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                            graphicsContext.lineTo(event.getX(), event.getY());
                            graphicsContext.stroke();
                        } else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {

                        }
                        break;

                    case 2:
                        if (event.getEventType() == MouseEvent.MOUSE_CLICKED && setStart == false) {
                            graphicsContext.beginPath();
                            graphicsContext.moveTo(event.getX(), event.getY());
                            graphicsContext.stroke();
                            graphicsContext.setLineWidth(2);// hier wird die Liniendicke ersetzt mit View verbinden
                            setStart = true;
                        } else if (event.getEventType() == MouseEvent.MOUSE_CLICKED && setStart == true) {
                            graphicsContext.lineTo(event.getX(), event.getY());
                            graphicsContext.stroke();
                            setStart = true;
                        } else if (event.getEventType() == MouseEvent.MOUSE_MOVED && setStart == true) {
                            //graphicsContext.clearRect(x, y, w, h);
                            //graphicsContext.lineTo(event.getX(), event.getY());
                            graphicsContext.stroke();
                        }
                        break;

                    case 3:
                        if (event.getEventType() == MouseEvent.MOUSE_PRESSED ) {
                            xStart = event.getX();
                            yStart = event.getY();
                            xEnd = xStart;
                            yEnd = yStart;

                            Rectangle rect1 = new Rectangle(xStart, yStart, 1, 1);

                            rect1.setFill(Color.BLUE); // übergabe von colorpicker
                            group.getChildren().add(rect1);

                        }
                        else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED){
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
                            rect1.setFill(Color.BLUE);//übergabe von colorpicker
                            group.getChildren().add(rect1);

                        }
                        break;

                    case 4:
                        if (event.getEventType() == MouseEvent.MOUSE_PRESSED ) {
                            xStart = event.getX();
                            yStart = event.getY();

                            Circle circle = new Circle(xStart, yStart, 2);
                            circle.setFill(Color.BLUE);// übergabe von colorpicker
                            group.getChildren().add(circle);
                        }
                        else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                            group.getChildren().remove(group.getChildren().size() - 1);
                            double a =xStart - event.getX();
                            double b = yStart - event.getY();
                            xDiverence = Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
                            Circle circle = new Circle(xStart, yStart, xDiverence);
                            circle.setFill(Color.BLUE);// übergabe von colorpicker
                            group.getChildren().add(circle);
                        }

                        break;

                    case 5:
                        break;

                    default:
                        break;
                }

            }

        };
    }

    public EventHandler<ActionEvent> changeTabPlacement() {
        return new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                MenuItem mItem = (MenuItem) event.getSource();
                String side = mItem.getText();
                if ("new".equalsIgnoreCase(side)) {
                    System.out.println("New");
                } else if ("open".equalsIgnoreCase(side)) {
                    System.out.println("Open");
                } else if ("---".equalsIgnoreCase(side)) {
                    System.out.println("-----");
                } else if ("exit".equalsIgnoreCase(side)) {
                    System.exit(0);
                }
            }
        };
    }

    public void updateStage(int stage) {
        this.stage = stage;
    }
}
