package friday;

import java.io.IOException;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.shape.Circle;

/**
 * Represents a dialog bubble (text + avatar) shown in the chat UI.
 */
public class DialogBox extends HBox {

    @FXML
    private Label dialog;

    @FXML
    private ImageView displayPicture;

    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(DialogBox.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        dialog.setText(text);
        displayPicture.setImage(img);
        double radius = displayPicture.getFitWidth() / 2;
        Circle clip = new Circle(radius, radius, radius);
        displayPicture.setClip(clip);


        // sizing only
        this.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(dialog, Priority.ALWAYS);
        dialog.setMaxWidth(Double.MAX_VALUE);
    }

    private void flip() {
        var tmp = javafx.collections.FXCollections.observableArrayList(this.getChildren());
        java.util.Collections.reverse(tmp);
        this.getChildren().setAll(tmp);
        this.setAlignment(Pos.TOP_LEFT);
    }

    /**
     * Creates a DialogBox representing a user message.
     * The dialog is aligned to the right.
     *
     * @param text The user input text.
     * @param img  The user's profile image.
     * @return A DialogBox configured for a user message.
     */
    public static DialogBox getUserDialog(String text, Image img) {
        DialogBox db = new DialogBox(text, img);
        db.dialog.getStyleClass().add("user-bubble");
        db.setAlignment(Pos.TOP_RIGHT);
        return db;
    }

    /**
     * Creates a DialogBox representing a bot response.
     * The dialog is aligned to the left.
     *
     * @param text The bot response text.
     * @param img  The bot's profile image.
     * @return A DialogBox configured for a bot message.
     */
    public static DialogBox getFridayDialog(String text, Image img) {
        DialogBox db = new DialogBox(text, img);
        db.dialog.getStyleClass().add("bot-bubble");
        db.flip();
        return db;
    }

    /**
     * Binds the maximum width of the dialog text to a proportion of the
     * container width to ensure consistent layout and wrapping.
     *
     * @param containerWidth The width property of the dialog container.
     */
    public void bindMaxWidth(ReadOnlyDoubleProperty containerWidth) {
        dialog.maxWidthProperty().bind(containerWidth.multiply(0.6));
    }
}
