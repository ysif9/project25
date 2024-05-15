package com.project25.Models;

import com.project25.Components.Post;
import com.project25.Views.ViewFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Model {
    private static Model model;
    private final DatabaseDriver databaseDriver;
    private final ViewFactory viewFactory;
    private final ObservableList<Post> allPosts = FXCollections.observableArrayList();

    public Model() {
        viewFactory = new ViewFactory();
        this.databaseDriver = new DatabaseDriver();
    }

    public static synchronized Model getInstance() {
        if (model == null) {
            model = new Model();
        }

        return model;
    }

    public ViewFactory getViewFactory() {
        return viewFactory;
    }

    public void newPost(Post post) {
        databaseDriver.savePost(post);
        allPosts.add(post);
    }


    public ObservableList<Post> getAllPosts(){
        return allPosts;
    }

    public void fetchPostsFromDatabase() {
        allPosts.addAll(Model.getInstance().databaseDriver.loadAllPosts());
    }

    public DatabaseDriver getDatabaseDriver() {
        return this.databaseDriver;
    }
}
