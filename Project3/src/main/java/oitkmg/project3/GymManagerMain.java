package oitkmg.project3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Creates the GUI with specified settings. Makes the scene and
 * displays the stage.
 * @author Kevin George, Omar Talaat
 */
public class GymManagerMain extends Application {

    /**
     * Default constructor for GymManagerMain.
     */
    public GymManagerMain(){}

    /**
     * Starts the GUI by making the scene and stage.
     * @param stage Stage to be used.
     * @throws IOException thrown if fxml file can not be found.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GymManagerMain.class
                .getResource("GymManagerView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        stage.setTitle("Project 3 - Gym Manager");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Launches the GUI.
     * @param args String array arguments.
     */
    public static void main(String[] args) {
        launch();
    }
}