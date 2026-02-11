package friday;

import java.io.IOException;
import java.util.Collections;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.collections.FXCollections;
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
    private static final double MAX_WIDTH_RATIO = 0.6;

    @FXML
    private Label dialog;

    @FXML
    private ImageView displayPicture;

    private DialogBox(String text, Image img) {
        loadFxml();

        dialog.setText(text);
        displayPicture.setImage(img);
        makeAvatarCircular();

        // sizing only
        this.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(dialog, Priority.ALWAYS);
        dialog.setMaxWidth(Double.MAX_VALUE);
    }

    private void loadFxml() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(DialogBox.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void makeAvatarCircular() {
        double radius = displayPicture.getFitWidth() / 2;
        Circle clip = new Circle(radius, radius, radius);
        displayPicture.setClip(clip);
    }

    private void flip() {
        var children = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(children);
        this.getChildren().setAll(children);
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
        db.applyUserStyle();
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
        db.applyBotStyle();

        return db;
    }

    private void applyUserStyle() {
        dialog.getStyleClass().add("user-bubble");
        setAlignment(Pos.TOP_RIGHT);
    }

    private void applyBotStyle() {
        dialog.getStyleClass().add("bot-bubble");
        flip();
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
