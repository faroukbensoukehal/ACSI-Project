package com.example.demo1;

import com.example.demo1.DbUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

    @FXML
    private Button button_signup = null;
    @FXML
    private Button button_log_in = null;
    @FXML
    private TextField tf_username= null;
    @FXML
    private TextField tf_password= null;
    @FXML
    private RadioButton rb_patient = null;
    @FXML
    private RadioButton rb_caregiver = null;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ToggleGroup toggleGroup =new ToggleGroup();
        rb_patient.setToggleGroup(toggleGroup);
        rb_caregiver.setToggleGroup(toggleGroup);

        rb_patient.setSelected(true);

        button_signup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String toggleName = ((RadioButton) toggleGroup.getSelectedToggle()).getText() ;
                if(!tf_username.getText().trim().isEmpty() && !tf_password.getText().trim().isEmpty()){

                    DbUtils.signUpUser(event,tf_username.getText(),tf_password.getText(),toggleName);
                }else {
                    System.out.println("please fill in all information");
                    Alert alert =new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("please fill in all information to sign up!");
                    alert.show();
                }
            }
        });
        button_log_in.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DbUtils.changeScene(event, "hello-view.fxml", "Log In!", null, null);
            }


        });
}
}