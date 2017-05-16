package gui.spielplaneditor;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;

/**
 * Created by Kevin Gerspacher on 16.05.17.
 */
public class Eventhandler {

    private int stage;
    private boolean setStart;
    private GraphicsContext graphicsContext;

    public Eventhandler(GraphicsContext graphicsContext)
    {
        this.graphicsContext = graphicsContext;
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
                            System.out.println(graphicsContext);
                            graphicsContext.beginPath();
                            graphicsContext.moveTo(event.getX(), event.getY());
                            graphicsContext.stroke();
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

    public void updateStage(int stage)
    {
        this.stage = stage;
    }
}
