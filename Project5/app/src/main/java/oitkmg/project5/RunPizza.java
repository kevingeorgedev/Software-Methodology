package oitkmg.project4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Runs the pizza store.
 * @author Kevin George, Omar Talaat
 */
public class RunPizza extends Application {
    /**
     * Starts the main view.
     * @param stage Stage of the main view.
     * @throws IOException if view is not found.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(RunPizza.class.getResource("MainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        stage.setTitle("Pizza");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Launches the main view.
     * @param args String Arguments
     */
    public static void main(String[] args) {
        launch();
    }
}