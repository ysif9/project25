package com.project25.Controllers;

import atlantafx.base.controls.CustomTextField;
import atlantafx.base.controls.Popover;
import atlantafx.base.controls.Spacer;
import com.project25.Components.Post;
import com.project25.Components.User;
import com.project25.Models.Model;
import com.project25.Models.Styles;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import org.kordamp.ikonli.feather.Feather;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class PostsPageController implements Initializable {
    public Circle profile_image;
    public TextArea status_fld;
    public VBox root;
    public Button photo_btn;
    public Button emoji_btn;
    public Button newpost_btn;
    public HBox toolbar;
    private Image currentImage;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setupToolBar();
        // Emoji Keyboard
        EmojiKeyboard emojiKeyboard = new EmojiKeyboard(status_fld);
        emojiKeyboard.setPrefWidth(300);

        // Popover for Emoji Keyboard
        Popover emojiPopover = new Popover(emojiKeyboard);
        emojiPopover.setArrowLocation(Popover.ArrowLocation.TOP_CENTER);
        emojiPopover.setHeaderAlwaysVisible(false);
        emojiPopover.setDetachable(true);

        Image profilePicture = new Image(String.valueOf(getClass().getResource("/Images/profile2.jpg")), 280, 280, true, false);
        ImagePattern pattern = new ImagePattern(
                profilePicture
        );
        profile_image.setFill(pattern);

        newpost_btn.getStyleClass().addAll(Styles.ACCENT, Styles.LARGE);
        newpost_btn.setGraphic(new FontIcon(Feather.PLUS));

        newpost_btn.setOnMouseClicked(e -> {
            if (!status_fld.getText().isEmpty()) {

                // Create new post
                User tempNewUser = new User(0, "yousif", "yousif", "salah", "hey", profilePicture);
                Post post = new Post(Model.getInstance().getAllPosts().size(), currentImage, status_fld.getText(), tempNewUser, LocalDate.now());
                newPost(post);
                status_fld.setText("");
                currentImage = null;
            }
        });

        photo_btn.setOnMouseClicked(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose Image File");

            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
            );

            File selectedFile = fileChooser.showOpenDialog(Model.getInstance().getViewFactory().getPrimaryStage());
            if (selectedFile != null) {
                currentImage = new Image(selectedFile.toURI().toString());
            }
        });

        emoji_btn.setOnMouseClicked(e -> {
            if (emojiPopover.showingProperty().get()){
                emojiPopover.hide();
            } else {
                emojiPopover.show(emoji_btn);
            }
        });
    }

    private void setupToolBar() {
        var textField = new CustomTextField();
        textField.setPromptText("Search Here");
        textField.setRight(new FontIcon(Feather.SEARCH));
        HBox.setHgrow(textField, Priority.ALWAYS);
        textField.setMaxWidth(1000);

        var dropdown = new MenuButton("", new FontIcon(Feather.MENU));
        dropdown.getItems().setAll(
                new MenuItem("Theme 1"),
                new MenuItem("Theme 2"),
                new MenuItem("Theme 3")
        );

        var msg_btn = new Button("", new FontIcon(Feather.MESSAGE_SQUARE));
        var user_btn = new Button("", new FontIcon(Feather.USER));
        var settings_btn = new Button("", new FontIcon(Feather.SETTINGS));

        toolbar.getChildren().addAll(
                new ImageView(),
                new Spacer(10),
                textField,
                msg_btn,
                user_btn,
                settings_btn,
                dropdown
        );
        toolbar.setPadding(new Insets(0, 20, 0, 50));
    }

    private void newPost(Post post){
        // init new Post Cell
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/PostCell.fxml"));
        PostCellController postCellController = new PostCellController(post);
        loader.setController(postCellController);
        try {
            root.getChildren().addAll((Node) loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // add to model's list
        Model.getInstance().getAllPosts().add(post);
    }

    static class EmojiKeyboard extends VBox {
        private final TextArea textArea;

        public EmojiKeyboard(TextArea textArea) {
            this.textArea = textArea;
            setSpacing(5);
            setPadding(new Insets(5));

            // Create rows of emojis
            createRow("\uD83D\uDE00", "\uD83D\uDE01", "\uD83D\uDE02", "\uD83D\uDE03");
            createRow("\uD83D\uDE04", "\uD83D\uDE05", "\uD83D\uDE06", "\uD83D\uDE07");
            createRow("\uD83D\uDE08", "\uD83D\uDE09", "\uD83D\uDE0A", "\uD83D\uDE0B");
            createRow("\uD83D\uDE0C", "\uD83D\uDE0D", "\uD83D\uDE0E", "\uD83D\uDE0F");
        }

        private void createRow(String... emojis) {
            HBox row = new HBox(5);
            for (String emoji : emojis) {
                Button emojiButton = new Button(emoji);
                emojiButton.setFont(javafx.scene.text.Font.font("Segoe UI Emoji", 20));
                emojiButton.setOnAction(e -> addEmojiToTextArea(emojiButton.getText()));
                row.getChildren().add(emojiButton);
            }
            getChildren().add(row);
        }

        private void addEmojiToTextArea(String emoji) {
            textArea.appendText(emoji);
        }
    }
}
