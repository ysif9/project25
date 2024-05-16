package com.project25.Models;

import com.project25.Components.*;
import com.project25.Views.ViewFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class Model {
    private static Model model;
    private final DatabaseDriver databaseDriver;
    private final ViewFactory viewFactory;
    private final ObservableList<Post> allPosts = FXCollections.observableArrayList();
    private User currentUser;
    private final ObservableList<Comment> allComments = FXCollections.observableArrayList();
    private  ObservableList<Like> likes= FXCollections.observableArrayList();
    //private final ObservableList<Like> currentLikes= FXCollections.observableArrayList();
    private  ObservableList<Dislike> dislikes= FXCollections.observableArrayList();
    //private final ObservableList<Dislike> currentdislikes= FXCollections.observableArrayList();
    private ObservableList<Comment> comments = FXCollections.observableArrayList();

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

    public ObservableList<Comment> getAllPostsComments(Post post){
        ObservableList<Comment> currentPostsComments= FXCollections.observableArrayList();
        for (Comment allComment : allComments) {
            if (allComment.getParentPost().equals(post)) {
            currentPostsComments.add(allComment);
            }
        }
        return currentPostsComments;
    }

    public ObservableList<Comment> getAllComments() {

        return allComments;
    }

    public void addComment(Comment comment, int postid){
        allComments.add(comment);
        databaseDriver.storeComment(comment, postid);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
/*
    public ObservableList<Like> getLikes() {
        return likes;
    }
    public void addLike(Like like){
        likes.add(like);
    }

    public ObservableList<Like> getCurrentLikes(Post post) {
        for (Like like:likes){
            if(like.getParentPost().equals(post)){
                currentLikes.add(like);
            }

        }
        return currentLikes;
    }
    public ObservableList<Dislike> getDislikes() {
        return dislikes;
    }
    public void addDislike(Dislike dislike){
        dislikes.add(dislike);
    }

    public ObservableList<Dislike> getCurrentdislikes(Post post) {
        for (Like like:likes){
            if(like.getParentPost().equals(post)){
                currentLikes.add(like);
            }

        }
        return currentdislikes;
    }
    public void removedislike(Dislike dislike){
        dislikes.remove(dislike);
    }
    */
public void storeLikes(Like like , int postID){
    databaseDriver.storeLike(like,postID);

}
public void deleteLikes(Like like){
    databaseDriver.deleteLike(like.getId());
}
    public void storeDislikes(Dislike dislike , int postID){
        databaseDriver.storeDislike(dislike,postID);
    }
    public void deleteDislikes(Dislike dislike){
        databaseDriver.deleteDislike(dislike.getId());
    }
    public int getPostsLikes(Post post){
   likes = (ObservableList<Like>) databaseDriver.loadLikesForPost(post.getID());
    return likes.size();
    }
    public int getPostsDislike(Post post){
    dislikes = (ObservableList<Dislike>) databaseDriver.loadDislikesForPost(post.getID());
    return dislikes.size();
    }

    public ObservableList<Comment> getComments(Post post) {
    comments = (ObservableList<Comment>) databaseDriver.loadCommentsForPost(post.getID());
        return comments;
    }
}
