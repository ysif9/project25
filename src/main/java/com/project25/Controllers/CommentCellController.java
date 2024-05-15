package com.project25.Controllers;

import com.project25.Components.Comment;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class CommentCellController implements Initializable {
    public Label username_lbl;
    public Label comment_text_lbl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    username_lbl.setText("USER");
    comment_text_lbl.setText("");
    comment_text_lbl.setWrapText(true);
    }
    public void newComment(Comment comment, String content){
        if(comment.getOwner() != null){
        username_lbl.setText(comment.getOwner().getUsername());}
        comment_text_lbl.setText(content);

    }

}
