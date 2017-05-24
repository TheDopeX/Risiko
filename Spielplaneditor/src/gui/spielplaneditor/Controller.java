package gui.spielplaneditor;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Kevin Gerspacher
 * @version 0.2
 */

public class Controller extends Application {

    /*
     * Controller creates View object,
     * updates and creates the UI
     */
    @Override
    public void start(Stage primaryStage) {
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
