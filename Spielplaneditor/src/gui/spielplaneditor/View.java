package gui.spielplaneditor;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Screen;

/**
 * Created by Kevin Gerspacher on 16.05.17.
 * Version 0.1
 */

public class View {

    /*
     * GraphicContext for adding graphics to canvas
     */
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
    Button buttonRec;
    Button buttonCircle;
    Button buttonHex;
    Button buttonReset;
    Button buttonUndo;
    Button buttonRedo;
    ToolBar toolBar_Left = new ToolBar();
    ToolBar toolBar_Right = new ToolBar(); // Not included yet!
    ToolBar toolBar_Bottom = new ToolBar();

    MenuBar menuBar;

    Canvas canvas;

    CommandController cc = new CommandController();

    Pane holder;

    //Eventhandler eventHandler;

    public Scene create() {
        Group root = new Group();
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        Scene scene = new Scene(root, primaryScreenBounds.getWidth(), primaryScreenBounds.getHeight(), Color.WHITE);
        BorderPane borderPane = new BorderPane();
        holder = new Pane();

        holder.setStyle("-fx-background-color: white");

        canvas = new Canvas(scene.getWidth(), scene.getHeight());

        graphicsContext = canvas.getGraphicsContext2D();

        Eventhandler eventHandler = new Eventhandler(graphicsContext, holder);

        borderPane.prefHeightProperty().bind(scene.heightProperty());
        borderPane.prefWidthProperty().bind(scene.widthProperty());

        /*
         * MENUBAR
         */
        menuBar = new MenuBar();
        EventHandler<ActionEvent> action = eventHandler.changeTabPlacement();

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


        infoLabel = new Label("Pick a draw tool from the list.");
        mainLabel = new Label("Menu\nPlaceholder");
        helpLabel = new Label("");

        canvas.setOnMouseClicked(eventHandler.drawEvent());
        canvas.setOnMouseDragged(eventHandler.drawEvent());
        canvas.setOnMouseEntered(eventHandler.drawEvent());
        canvas.setOnMouseExited(eventHandler.drawEvent());
        canvas.setOnMouseMoved(eventHandler.drawEvent());
        canvas.setOnMousePressed(eventHandler.drawEvent());
        canvas.setOnMouseReleased(eventHandler.drawEvent());

		/*
         * Button definition
		 */
        buttonReset = new Button("✗"); // RESET
        buttonMouse = new Button("☝"); // MOUSE
        buttonPen = new Button("✎"); // PENCIL
        buttonLine = new Button("╲"); // LINE
        buttonRec = new Button("◻"); //Rectangle
        buttonCircle = new Button("◯"); //Circle
        buttonHex = new Button("⬡"); //HEXAGON
        buttonUndo = new Button("↺"); // UNDO
        buttonRedo = new Button("↻"); // REDO

        buttonMouse.setOnAction(e -> {
            eventHandler.updateStage(0);
            helpLabel.setText("");
        });
        buttonPen.setOnAction(e -> {
            eventHandler.updateStage(1);
            helpLabel.setText("Press LEFT-CLICK to draw");
        });
        buttonLine.setOnAction(e -> {
            eventHandler.updateStage(2);
            helpLabel.setText("Press LEFT-CLICK to place a startpoint");
        });
        buttonRec.setOnAction(e -> {
            eventHandler.updateStage(3);
            helpLabel.setText("Press LEFT-CLICK to place a startpoint");
        });
        buttonCircle.setOnAction(e -> {
            eventHandler.updateStage(4);
            helpLabel.setText("Press LEFT-CLICK to place a startpoint");
        });
        buttonHex.setOnAction(e -> {
            eventHandler.updateStage(5);
            helpLabel.setText("Press LEFT-CLICK to place a startpoint");
        });

        buttonUndo.setOnAction(e -> updateHolder(cc.undo(holder)));
        buttonRedo.setOnAction(e -> updateHolder(cc.redo(holder)));

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
        toolBar_Left.getItems().addAll(buttonMouse, new Separator(), buttonPen, new Separator(), buttonLine, buttonRec,
                buttonCircle, buttonHex, bottSpacer, buttonReset);
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

        holder.getChildren().add(canvas);
        borderPane.setCenter(holder);
        borderPane.setLeft(toolBar_Left);
        borderPane.setBottom(toolBar_Bottom);
        borderPane.setRight(toolBar_Right);

        root.getChildren().add(borderPane);

        return scene;
    }

    public void update(Scene scene) {
        canvas.setHeight(scene.getHeight() - (menuBar.getHeight() + toolBar_Bottom.getHeight()));
        canvas.setWidth(scene.getWidth() - (toolBar_Left.getWidth() + toolBar_Right.getWidth()));
        canvas.maxHeight(scene.getHeight() - (menuBar.getHeight() + toolBar_Bottom.getHeight()));
        canvas.maxWidth(scene.getWidth() - (menuBar.getWidth() + toolBar_Bottom.getWidth()));
        holder.maxHeight(scene.getHeight() - (menuBar.getHeight() + toolBar_Bottom.getHeight()));
        holder.maxWidth(scene.getWidth() - (menuBar.getWidth() + toolBar_Bottom.getWidth()));
        initDraw(graphicsContext);
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

    public void updateHolder(Pane holderNew)
    {
        System.out.println(holder.getChildren().size());
        this.holder = holderNew;
        System.out.println(holder.getChildren().size());
    }
}


