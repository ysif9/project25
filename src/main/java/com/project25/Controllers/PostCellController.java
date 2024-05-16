package com.project25.Controllers;

import atlantafx.base.util.Animations;
import com.project25.Components.Comment;
import com.project25.Components.Dislike;
import com.project25.Components.Like;
import com.project25.Components.Post;
import com.project25.Models.Model;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class PostCellController implements Initializable {
    //controls
    public Circle profile_picture;
    public Label user_name_lbl;
    public FontAwesomeIconView like_btn;
    public FontAwesomeIconView dislike_btn;
    public Button comment_btn;
    public ImageView post_image;
    public Label post_text_lbl;
    public BorderPane post_cell_container;
    public Label like_count_lbl;
    public Label dislike_count_lbl;
    public Label comment_count_lbl;
    public VBox comment_section_container;
    public TextArea comment_input_fld;
    public Button comment_submit_btn;
    public ScrollPane scrollpane;
    public VBox comment_section;
    public TitledPane comment_pane;

    //variables
    BooleanProperty likeClicked =new SimpleBooleanProperty(false);
    BooleanProperty dislikeClicked =new SimpleBooleanProperty(false);
    BooleanProperty commentClicked =new SimpleBooleanProperty(false);
    //Objects
    Post currentPost;
    Dislike dislike;
    Like like;
    public PostCellController(Post post) {
        currentPost = post;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //post initialization
        try {
            commentsLoading();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        post_text_lbl.setWrapText(true);
        post_cell_container.setPrefHeight(710);
        ImagePattern pattern = new ImagePattern(
                currentPost.getAuthor().getProfilePicture()
        );
        profile_picture.setFill(pattern);
        user_name_lbl.setText(currentPost.getAuthor().getUsername());
        post_image.setImage(currentPost.getPostImage());
        post_text_lbl.setText(currentPost.getContent());
        like_count_lbl.setText(String.valueOf(currentPost.getLikes().size()));
        dislike_count_lbl.setText(String.valueOf(currentPost.getDislikes().size()));
        comment_count_lbl.setText(String.valueOf(currentPost.getComments().size()));

        FontAwesomeIconView comment_ico = new FontAwesomeIconView();
        comment_ico.setGlyphName("COMMENT");
        comment_ico.setSize("30");
        comment_btn.setGraphic(comment_ico);

        //buttons reactions
        like_btn.setOnMouseClicked(this::likeBtnClicked);
        dislike_btn.setOnMouseClicked(this::dislikeBtnClicked);
        comment_btn.setOnAction(e-> commentBtnClicked());
        comment_submit_btn.setOnAction(e-> {
            try {
                createNewComment();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void commentsLoading() throws IOException {
        for(Comment comment:Model.getInstance().getComments(currentPost)){
            loadComment(comment);

        }
        comment_count_lbl.setText(String.valueOf(Model.getInstance().getComments(currentPost).size()));
    }

    private void loadComment(Comment comment) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/CommentCell.fxml"));
        CommentCellController cellController = new CommentCellController();
        loader.setController(cellController);
        VBox commentCell = loader.load();
        cellController.newComment(comment);

        comment_section_container.getChildren().add(commentCell);
    }

    private void createNewComment() throws IOException {
        //move to model
        if(currentPost.getAuthor() != null) {
            if(!comment_input_fld.getText().isEmpty()){
            Comment comment = new Comment(Model.getInstance().getAllComments().size(), LocalDate.now(), currentPost.getAuthor(), comment_input_fld.getText());
                comment.setParentPost(currentPost);
                Model.getInstance().addComment(comment, currentPost.getID());
                loadComment(comment);
                comment_input_fld.clear();}
            else{
                var animation = Animations.shakeX(comment_submit_btn, 0.5);
                animation.playFromStart();
            }
        }
        comment_count_lbl.setText(String.valueOf(Model.getInstance().getAllComments().size()));
    }


    // buttons reactions methods
    private void commentBtnClicked() {
        commentClicked.set(!commentClicked.getValue());

        //open comment window
        if (commentClicked.getValue()){
            post_cell_container.setPrefHeight(1000);
            comment_pane.setExpanded(true);
        }
        else {
            post_cell_container.setPrefHeight(710);
            comment_pane.setExpanded(false);
        }

    }

    private void likeBtnClicked(MouseEvent event) {
        var likePulse =  Animations.pulse(like_btn, 2);
        likePulse.setRate(2.5);
        likeClicked.set(!likeClicked.getValue());
         like =new Like(currentPost.getLikes().size(), LocalDate.now(), Model.getInstance().getCurrentUser());
        if(likeClicked.getValue()){
            this.like_btn.setGlyphName("HEART");
            this.like_btn.setFill(new Color(0.85, 0.06, 0.27, 1));
            likePulse.playFromStart();
            currentPost.removeLike(like);
        currentPost.addLike(like);
        Model.getInstance().storeLikes(like, currentPost.getID());
        }
        else {
            this.like_btn.setGlyphName("HEART_ALT");
            this.like_btn.setFill(new Color(0, 0, 0, 1));
            //removeLike method in model
            currentPost.removeLike(like);
        Model.getInstance().deleteLikes(like);
        }
        like_count_lbl.setText(String.valueOf(Model.getInstance().getPostsLikes(currentPost)));
    }

    private void dislikeBtnClicked(MouseEvent event) {

        dislikeClicked.set(!dislikeClicked.getValue());

        if(dislikeClicked.getValue()){
            dislike_btn.setFill(new Color(0.12, 0.46, 0.9, 1));
            dislike = new Dislike(currentPost.getDislikes().size(),LocalDate.now(),Model.getInstance().getCurrentUser());
            //addDislike method in model
            currentPost.addDisLike(dislike);
            Model.getInstance().storeDislikes(dislike,currentPost.getID());
        }
        else {
            this.dislike_btn.setFill(new Color(0, 0, 0, 1));
            //removeDislike method in model
            currentPost.removeDisLike(dislike);
            Model.getInstance().deleteDislikes(dislike);
        }
dislike_count_lbl.setText(String.valueOf(currentPost.getDislikes().size()));
    }
}

