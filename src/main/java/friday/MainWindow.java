package friday;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

/**
 * Controller for the main GUI window of the Friday application.
 * Handles user input and displays dialog boxes for user commands
 * and bot responses.
 */
public class MainWindow {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox dialogContainer;

    @FXML
    private TextField userInput;

    @FXML
    private Button sendButton;

    private Friday friday;

    private final Image userImage =
            new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private final Image dukeImage =
            new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));

    /**
     * Initializes the main window by configuring layout behaviour,
     * enabling automatic scrolling, and preparing the dialog container.
     */
    @FXML
    public void initialize() {
        scrollPane.setFitToWidth(true);
        dialogContainer.heightProperty().addListener((obs) -> scrollPane.setVvalue(1.0));

        // bind VBox width to ScrollPane viewport width
        scrollPane.viewportBoundsProperty().addListener((obs, oldVal, newVal) -> {
            dialogContainer.setPrefWidth(newVal.getWidth());
        });
    }

    /**
     * Injects the Friday logic instance into the controller and
     * displays the initial welcome message.
     *
     * @param friday The Friday instance that processes user commands.
     */
    public void setFriday(Friday friday) {
        this.friday = friday;

        String welcome = friday.getWelcomeMessage();
        if (welcome != null && !welcome.isBlank()) {
            DialogBox welcomeBox = DialogBox.getFridayDialog(welcome, dukeImage);
            welcomeBox.bindMaxWidth(dialogContainer.widthProperty());
            dialogContainer.getChildren().add(welcomeBox);
        }
    }

    @FXML
    private void handleUserInput() {
        String input = userInput.getText().trim();
        if (input.isEmpty()) {
            return;
        }

        //user bubble
        DialogBox userBox = DialogBox.getUserDialog(input, userImage);
        userBox.bindMaxWidth(dialogContainer.widthProperty());
        dialogContainer.getChildren().add(userBox);

        // bot bubble
        String response = friday.getResponse(input);
        if (!response.isBlank()) {
            DialogBox botBox = DialogBox.getFridayDialog(response, dukeImage);
            botBox.bindMaxWidth(dialogContainer.widthProperty());
            dialogContainer.getChildren().add(botBox);
        }

        userInput.clear();

        if (friday.isExit()) {
            javafx.application.Platform.exit();
        }
    }
}
