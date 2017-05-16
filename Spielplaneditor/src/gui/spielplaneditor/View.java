/*
 * @author Kevin Gerspacher
 * @date 15.05.2017
 * @version 1.0
 */

package gui.spielplaneditor;

/*
 * Imports
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class View extends Application {

    GraphicsContext graphicsContext;
    /*
     * Labels
     */
    Label infoLabel;
    Label mainLabel;
    Label helpLabel;
    /*
     * Buttons
     */
    Button buttonMouse;
    Button buttonPen;
    Button buttonLine;
    Button buttonReset;
    Button buttonUndo;
    Button buttonRedo;
    ToolBar toolBar_Left = new ToolBar();
    ToolBar toolBar_Right = new ToolBar(); // Not included yet!
    ToolBar toolBar_Bottom = new ToolBar();
    private int stage = 0;
    private boolean setStart = false;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Title");
        Group root = new Group();

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        Scene scene = new Scene(root, primaryScreenBounds.getWidth(), primaryScreenBounds.getHeight(), Color.WHITE);

        MenuBar menuBar = new MenuBar();
        EventHandler<ActionEvent> action = changeTabPlacement();

        Menu menu = new Menu("File");
        MenuItem left = new MenuItem("New");

        left.setOnAction(action);
        menu.getItems().add(left);

        MenuItem right = new MenuItem("Open");
        right.setOnAction(action);
        menu.getItems().add(right);

        MenuItem top = new MenuItem("---");
        top.setOnAction(action);
        menu.getItems().add(top);

        MenuItem bottom = new MenuItem("Exit");
        bottom.setOnAction(action);
        menu.getItems().add(bottom);

        menuBar.getMenus().add(menu);
        menuBar.setStyle("-fx-background-color: #C1C1C1;");

        BorderPane borderPane = new BorderPane();

        borderPane.prefHeightProperty().bind(scene.heightProperty());
        borderPane.prefWidthProperty().bind(scene.widthProperty());

        infoLabel = new Label("Wait mouse");
        mainLabel = new Label("Menu\nPlaceholder");
        helpLabel = new Label("");

		/*
         * Button definition
		 */
        buttonReset = new Button("✗"); // RESET
        buttonMouse = new Button("☝"); // MOUSE
        buttonPen = new Button("✎"); // PENCIL
        buttonLine = new Button("╲"); // LINE
        buttonUndo = new Button("↺"); // UNDO
        buttonRedo = new Button("↻"); // REDO

        buttonMouse.setOnAction(e -> {
            stage = 0;
            helpLabel.setText("");
        });
        buttonPen.setOnAction(e -> {
            stage = 1;
            helpLabel.setText("Press LEFT-CLICK to draw");
        });
        buttonLine.setOnAction(e -> {
            stage = 2;
            helpLabel.setText("Press LEFT-CLICK to place a startpoint");
        });

		/*
         * Panes as spacers for toolbars
		 */
        final Pane rightSpacer = new Pane();
        HBox.setHgrow(rightSpacer, Priority.SOMETIMES);

        final Pane bottSpacer = new Pane();
        VBox.setVgrow(bottSpacer, Priority.SOMETIMES);

		/*
         * Left toolbar
		 */
        toolBar_Left.setOrientation(Orientation.VERTICAL);
        toolBar_Left.getItems().addAll(buttonMouse, new Separator(), buttonPen, new Separator(), buttonLine, bottSpacer,
                buttonReset);
        toolBar_Left.setStyle("-fx-background-color: #C1C1C1;");
        /*
		 * Right toolbar
		 */
        toolBar_Right.setOrientation(Orientation.VERTICAL);
        toolBar_Right.getItems().addAll(infoLabel, new Separator());
        toolBar_Right.setStyle("-fx-background-color: #C1C1C1;");

		/*
		 * Bottom toolbar
		 */
        toolBar_Bottom.getItems().addAll(buttonUndo, buttonRedo, rightSpacer, helpLabel);
        toolBar_Bottom.setStyle("-fx-background-color: #C1C1C1;");

        borderPane.setTop(menuBar);
        StackPane holder = new StackPane();

        holder.setStyle("-fx-background-color: white");
        Canvas canvas = new Canvas(scene.getWidth(), scene.getHeight());
        graphicsContext = canvas.getGraphicsContext2D();

        canvas.setOnMouseClicked(drawEvent());
        canvas.setOnMouseDragged(drawEvent());
        canvas.setOnMouseEntered(drawEvent());
        canvas.setOnMouseExited(drawEvent());
        canvas.setOnMouseMoved(drawEvent());
        canvas.setOnMousePressed(drawEvent());
        canvas.setOnMouseReleased(drawEvent());

        holder.getChildren().add(canvas);
        borderPane.setCenter(holder);
        borderPane.setLeft(toolBar_Left);
        borderPane.setBottom(toolBar_Bottom);
        borderPane.setRight(toolBar_Right);

        root.getChildren().add(borderPane);

        primaryStage.setScene(scene);
        primaryStage.show();

        canvas.setHeight(scene.getHeight() - (menuBar.getHeight() + toolBar_Bottom.getHeight()));
        canvas.setWidth(scene.getWidth() - (toolBar_Left.getWidth() + toolBar_Right.getWidth()));
        initDraw(graphicsContext);
    }

    private EventHandler<MouseEvent> drawEvent() {
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

    private EventHandler<ActionEvent> changeTabPlacement() {
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

    private void initDraw(GraphicsContext gc) {
        double canvasWidth = gc.getCanvas().getWidth();
        double canvasHeight = gc.getCanvas().getHeight();

        gc.setFill(Color.LIGHTGRAY);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);

        gc.fill();
        gc.strokeRect(0, // x of the upper left corner
                0, // y of the upper left corner
                canvasWidth, // width of the rectangle
                canvasHeight); // height of the rectangle

        gc.setFill(Color.RED);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);

    }
}
