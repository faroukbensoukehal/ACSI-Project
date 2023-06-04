package com.example.demo1;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SignInController implements Initializable {
    @FXML
    private Button button_submit;
    @FXML
    private Button button_logout;
    @FXML
    private Label label_welcome;
    @FXML
    private Label label_type;
    @FXML
    private TextField tf_feedback;
    @FXML
    private Label label_feedback;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        button_logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DbUtils.changeScene(event,"hello-view.fxml","Log In",null,null);

            }
        });

        button_submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String feedback = tf_feedback.getText();
                String username = label_welcome.getText().replace("Welcome ", "").replace("!", "");

                if (!feedback.trim().isEmpty()) {
                    DbUtils.insertFeedback(feedback, username);
                    label_feedback.setText("Your last feedback: " + feedback);
                } else {
                    System.out.println("Please enter a feedback");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please enter a feedback");
                    alert.show();
                }
            }
        });

    }
    public void setUserInformation(String username,String type) {
            label_welcome.setText("Welcome " + username + "!");
            label_type.setText("You're a " + type + "!");
        String feedback = DbUtils.getFeedback(username);
        if (feedback != null) {
            label_feedback.setText("Your last feedback: " + feedback);
        } else {
            label_feedback.setText("No feedback available");
        }


    }




}
