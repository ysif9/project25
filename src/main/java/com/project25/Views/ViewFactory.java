package com.project25.Views;

import com.project25.Models.Model;
import com.project25.WindowSelection;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.io.IOException;


// needs an overhaul when done
public class ViewFactory {
    private Window currentStage;
    private final ObjectProperty<WindowSelection> windowSelection = new SimpleObjectProperty<>();

    public ObjectProperty<WindowSelection> windowSelectionProperty() {
        return windowSelection;
    }

    public void showNewPost() {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/PostsPage.fxml"));
                createStage(loader);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

    }

    private void createStage(FXMLLoader fxmlLoader) {
        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load(), 2000, 200);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

        stage.setOnCloseRequest(e -> {
            e.consume();
            Model.getInstance().getDatabaseDriver().closeConnection();
            System.exit(0);
        });
        currentStage = stage.getScene().getWindow();
    }

    public void closeStage(Stage stage) {
        stage.close();
    }

    public Window getPrimaryStage() {
        return currentStage;
    }

    public void showLoginWindow() {
        Scene scene = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/login.fxml"));
            scene = new Scene(fxmlLoader.load(), 800, 540);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Gogi Connect");
        Image icon = new Image(String.valueOf(getClass().getResource("/Images/gogi-favicon-white.png")));
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.show();
    }

    public void showRegisterWindow() {
        Scene scene = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/register.fxml"));
            scene = new Scene(fxmlLoader.load(), 800, 540);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Gogi Connect");
        Image icon = new Image(String.valueOf(getClass().getResource("/Images/gogi-favicon-white.png")));
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.show();
    }
}
