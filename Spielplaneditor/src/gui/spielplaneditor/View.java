package gui.spielplaneditor;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
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

    Button buttonAddContinent;
    /*
     * ToolBars
     */
    ToolBar toolBar_Left = new ToolBar();
    ToolBar toolBar_Right = new ToolBar(); // Not included yet!
    ToolBar toolBar_Bottom = new ToolBar();

    ToolBar toolBar_Shape = new ToolBar();
    /*
     * ColorPicker
     */
    ColorPicker colorPickerFill = new ColorPicker();
    ColorPicker colorPickerBorder = new ColorPicker();
    /*
     * MenuButtons
     */
    MenuButton menuShapeForm = new MenuButton("Formen");
    MenuButton menuContinents = new MenuButton("Kontinent 1");
    MenuButton menuBorderWith = new MenuButton("1.5 px");
    /*
     * Tabs
     */
    TabPane tabPane = new TabPane();
    Tab tabShape = new Tab();
    Tab tabLevel = new Tab();
    Tab tabMap = new Tab();
    /*
     * CheckBoxes
     */
    CheckBox checkCountry = new CheckBox("Land");
    CheckBox checkoverwrite = new CheckBox("überschreiben");
    /*
     * TextImput
     */
    TextField textPositionX = new TextField();
    TextField textPositionY = new TextField();
    TextField textShapeWith = new TextField();
    TextField textShapeHaight = new TextField();

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

        buttonAddContinent = new Button("+"); // Add Continent Button
        /*
         * CollorPicker definition
		 */
        colorPickerFill.setMinHeight(25);
        colorPickerBorder.setMinHeight(25);
        /*
         * Position input
		 */
        textPositionX.setMaxWidth(25);
        textPositionX.setMaxHeight(25);
        textPositionX.setMinWidth(25);
        textPositionX.setMinHeight(25);
        textPositionX.setAlignment(Pos.CENTER);

        textPositionY.setMaxWidth(25);
        textPositionY.setMaxHeight(25);
        textPositionY.setMinWidth(25);
        textPositionY.setMinHeight(25);
        textPositionY.setAlignment(Pos.CENTER);
        /*
         * Shape Hith and Haight input
		 */
        textShapeWith.setMaxWidth(25);
        textShapeWith.setMaxHeight(25);
        textShapeWith.setMinWidth(25);
        textShapeWith.setMinHeight(25);
        textShapeWith.setAlignment(Pos.CENTER);

        textShapeHaight.setMaxWidth(25);
        textShapeHaight.setMaxHeight(25);
        textShapeHaight.setMinWidth(25);
        textShapeHaight.setMinHeight(25);
        textShapeHaight.setAlignment(Pos.CENTER);
        /*
         * Shape Forms dropdownbutton input
		 */
        EventHandler<ActionEvent> state = eventHandler.changeState();

        MenuItem line = new MenuItem("╲");
        line.setOnAction(state);
        menuShapeForm.getItems().add(line);

        MenuItem rectangle = new MenuItem("◻");
        rectangle.setOnAction(state);
        menuShapeForm.getItems().add(rectangle);

        MenuItem circle = new MenuItem("◯");
        circle.setOnAction(state);
        menuShapeForm.getItems().add(circle);

        MenuItem hex = new MenuItem("⬡");
        hex.setOnAction(state);
        menuShapeForm.getItems().add(hex);
        /*
         * undo / redo button action
		 */
        buttonUndo.setOnAction(e -> updateHolder(cc.undo(holder)));
        buttonRedo.setOnAction(e -> updateHolder(cc.redo(holder)));
        /*
         * Creat TapPane
		 */
        tabPane.setTabMaxWidth(100);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        /*
         * Creat Tabs
		 */
        tabShape.setText("Shape Einstellungen");
        tabShape.setContent(toolBar_Shape);
        tabPane.getTabs().add(tabShape);

        tabLevel.setText("Ebenen");
        tabPane.getTabs().add(tabLevel);

        tabMap.setText("Karten Einstellungen");
        tabPane.getTabs().add(tabMap);

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
        toolBar_Left.getItems().addAll(buttonMouse, new Separator(), menuShapeForm, new Separator(), buttonText, new Separator(),
                buttonBackground, new Separator(), buttonSelectShape,new Separator(), buttonAutoBorder, bottSpacer, buttonReset);
        toolBar_Left.setStyle("-fx-background-color: #C1C1C1;");
        /*
         * Right toolbar
		 */
        toolBar_Right.setOrientation(Orientation.VERTICAL);
        toolBar_Right.getItems().addAll(tabPane);
        toolBar_Right.setStyle("-fx-background-color: #C1C1C1;");

		/*
         * Bottom toolbar
		 */
        toolBar_Bottom.getItems().addAll(buttonUndo, buttonRedo, rightSpacer, helpLabel);
        toolBar_Bottom.setStyle("-fx-background-color: #C1C1C1;");

        /*
         * Shape toolbar
         */
        toolBar_Shape.setOrientation(Orientation.VERTICAL);
        toolBar_Shape.getItems().addAll(checkCountry,new Separator(), menuContinents, buttonAddContinent,new Separator(),
                checkoverwrite,new Separator(), menuBorderWith, colorPickerBorder, colorPickerFill, new Separator(),
                textPositionX, textPositionY, textShapeWith, textShapeHaight, new Separator());
        toolBar_Shape.setStyle("-fx-background-color: #C1C1C1;");


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
        this.holder = holderNew;
    }
}


