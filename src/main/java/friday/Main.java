package friday;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX entry point for Friday GUI.
 */
public class Main extends Application {

    private final Friday friday = new Friday();

    /**
     * Starts the JavaFX application by loading the main window,
     * setting up the scene, and displaying the stage.
     *
     * @param stage The primary stage for this application.
     * @throws Exception If the FXML file cannot be loaded.
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(Main.class.getResource("/css/main.css").toExternalForm());
        scene.getStylesheets().add(Main.class.getResource("/css/dialog.css").toExternalForm());

        MainWindow controller = fxmlLoader.getController();
        controller.setFriday(friday);

        stage.setScene(scene);
        stage.setTitle("Friday");
        stage.setMinWidth(600);
        stage.setMinHeight(500);
        stage.setMaximized(true);
        stage.show();
    }
}
