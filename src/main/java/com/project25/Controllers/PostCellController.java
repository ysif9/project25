package com.project25.Controllers;

import atlantafx.base.util.Animations;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.fontawesome.demo.FontAwesomeIconsDemoApp;
import javafx.animation.Animation;

import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

import java.net.URL;
import java.time.Duration;
import java.util.ResourceBundle;

public class PostCellController implements Initializable {
    //controls
    public ImageView profile_picture;
    public Label user_name_lbl;
    public FontAwesomeIconView like_btn;
    public FontAwesomeIconView dislike_btn;
    public Button comment_btn;
    public ImageView post_image;
    public Label post_text_lbl;

    //variables
    BooleanProperty likeClicked =new SimpleBooleanProperty(false);
    BooleanProperty dislikeClicked =new SimpleBooleanProperty(false);
    BooleanProperty commentClicked =new SimpleBooleanProperty(false);



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FontAwesomeIconView comment_ico = new FontAwesomeIconView();
        comment_ico.setGlyphName("COMMENT");
        comment_ico.setSize("30");
        comment_btn.setGraphic(comment_ico);
        like_btn.setOnMouseClicked(this::likeBtnClicked);
        dislike_btn.setOnMouseClicked(this::dislikeBtnClicked);
        comment_btn.setOnMouseClicked(this::commentBtnClicked);


    }



    private void commentBtnClicked(MouseEvent mouseEvent) {
        //open comment window
    }

    private void likeBtnClicked(MouseEvent event) {
        var likePulse =  Animations.pulse(like_btn, 2);
        likePulse.setRate(2.5);
        likeClicked.set(!likeClicked.getValue());

        if(likeClicked.getValue()){
            this.like_btn.setGlyphName("HEART");
            this.like_btn.setFill(new Color(0.85, 0.06, 0.27, 1));
            likePulse.playFromStart();
            //addLike method in model

        }
        else {
            this.like_btn.setGlyphName("HEART_ALT");
            this.like_btn.setFill(new Color(0, 0, 0, 1));
            //removeLike method in model
        }

    }

    private void dislikeBtnClicked(MouseEvent event) {

        dislikeClicked.set(!dislikeClicked.getValue());

        if(dislikeClicked.getValue()){
            dislike_btn.setFill(new Color(0.12, 0.46, 0.9, 1));

            //addDislike method in model
        }
        else {
            this.dislike_btn.setFill(new Color(0, 0, 0, 1));
            //removeDislike method in model
        }

    }
}

