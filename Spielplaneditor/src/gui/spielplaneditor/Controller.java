package gui.spielplaneditor;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Kevin Gerspacher on 16.05.17.
 * Version 0.1
 */
public class Controller extends Application{

    /*
     * Controller creates View object,
     * updates and creates the UI
     */
    @Override
    public void start(Stage primaryStage)
    {
        primaryStage.setTitle("Spielplaneditor");

        View view = new View();

        Scene scene = view.create();

        primaryStage.setScene(scene);
        primaryStage.show();

        view.update(scene);
    }

    /*
     * Main method gets called on start up
     */
    public static void main(String[] args) {
        launch(args);
    }
}
