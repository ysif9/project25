package com.project25.Views;

import com.project25.Models.Model;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;


// needs an overhaul when done
public class ViewFactory {
    private Window currentStage;


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

    public Window getPrimaryStage() {
        return currentStage;
    }
}
