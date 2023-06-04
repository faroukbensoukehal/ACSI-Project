package com.example.demo1;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
   private Button button_login= null;
    @FXML
    private Button button_sign_up= null;
    @FXML
    private TextField tf_username= null;
    @FXML
    private TextField tf_password= null;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        button_login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DbUtils.loginUser(event,tf_username.getText(),tf_password.getText());
            }
        });

        button_sign_up.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DbUtils.changeScene(event,"sign-up.fxml","Sign Up!",null,null);
            }
        });
    }
}