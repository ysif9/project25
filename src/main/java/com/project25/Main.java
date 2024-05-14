package com.project25;

import atlantafx.base.theme.CupertinoLight;
import com.project25.Models.Model;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
       launch(args);

    }

    @Override
    public void start(Stage stage) {
        Application.setUserAgentStylesheet(new CupertinoLight().getUserAgentStylesheet());
        Model.getInstance().getViewFactory().showNewPost();
    }
}

