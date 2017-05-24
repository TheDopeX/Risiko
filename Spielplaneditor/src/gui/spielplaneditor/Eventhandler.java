package gui.spielplaneditor;

import gui.spielplaneditor.Shapes.Hexagon;
import gui.spielplaneditor.Shapes.Rectangle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * @author Kevin Gerspacher, David Bartberger
 * @version 0.2
 */
public class Eventhandler {

    private Pane group;

    public Eventhandler(Pane group) {
        this.group = group;
    }

    public Toolstate state;

    private ShapeDrawState shapeState = new ShapeDrawState();
    private Rectangle rect;
    private Hexagon hex;

    public EventHandler<MouseEvent> drawEvent() {
        return new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                if (event.getEventType() == MouseEvent.MOUSE_PRESSED && state != null) {
                    state.setPane(group);
                    state.onLeftClick(event);
                } else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED && state != null) {
                    state.onLeftDown(event);
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

    public EventHandler<ActionEvent> changeState() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                MenuItem mItem = (MenuItem) event.getSource();
                String side = mItem.getText();

                if ("◻".equalsIgnoreCase(side)) {
                    rect = new Rectangle();
                    state = shapeState;
                    shapeState.setShape(rect);

                } else if ("◯".equalsIgnoreCase(side)) {
                    /*state = shapeState;
                    shapeState.setShape(null);
                    state.setPane(group);*/

                } else if ("⬡".equalsIgnoreCase(side)) {
                    hex = new Hexagon();
                    state = shapeState;
                    shapeState.setShape(hex);
                }
            }
        };
    }

}
