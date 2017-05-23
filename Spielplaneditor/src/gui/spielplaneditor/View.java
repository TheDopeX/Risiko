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
 * Created by Kevin Gerspacher und David Bartberger on 16.05.17.
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
    Button buttonText;
    Button buttonAutoBorder;
    Button buttonSelectShape;
    Button buttonBackground;
    Button buttonReset;
    Button buttonUndo;
    Button buttonRedo;

    ToolBar toolBar_Left = new ToolBar();
    ToolBar toolBar_Right = new ToolBar(); // Not included yet!
    ToolBar toolBar_Bottom = new ToolBar();

    ColorPicker colorPickerFill = new ColorPicker();
    ColorPicker colorPickerBorder = new ColorPicker();
    MenuButton menuButton = new MenuButton("Formen");



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

        Eventhandler eventHandler = new Eventhandler(holder);

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
        buttonText = new Button( "T"); //Text
        buttonAutoBorder = new Button( "AutoBorder"); //AutoBorder
        buttonSelectShape = new Button( "SelectShape"); //SelectShape
        buttonBackground = new Button("Background"); //Bachground Hinzufügen
        buttonUndo = new Button("↺"); // UNDO
        buttonRedo = new Button("↻"); // REDO


        EventHandler<ActionEvent> state = eventHandler.changeState();

        MenuItem line = new MenuItem("╲");
        line.setOnAction(state);
        menuButton.getItems().add(line);

        MenuItem rectangle = new MenuItem("◻");
        rectangle.setOnAction(state);
        menuButton.getItems().add(rectangle);

        MenuItem circle = new MenuItem("◯");
        circle.setOnAction(state);
        menuButton.getItems().add(circle);

        MenuItem hex = new MenuItem("⬡");
        hex.setOnAction(state);
        menuButton.getItems().add(hex);

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
        toolBar_Left.getItems().addAll(buttonMouse, new Separator(), menuButton, new Separator(), buttonText, new Separator(),
                buttonBackground, new Separator(), buttonSelectShape,new Separator(), buttonAutoBorder, bottSpacer, buttonReset);
        toolBar_Left.setStyle("-fx-background-color: #C1C1C1;");
        /*
         * Right toolbar
		 */
        toolBar_Right.setOrientation(Orientation.VERTICAL);
        toolBar_Right.getItems().addAll(colorPickerFill, new Separator(), colorPickerBorder, infoLabel, new Separator());
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


