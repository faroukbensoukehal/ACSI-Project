package com.example.demo1;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import javax.xml.transform.Result;
import java.sql.*;
import java.io.IOException;

public class DbUtils {

    public static void changeScene(ActionEvent event, String fxmlFile, String title, String username, String type) {
        Parent root = null;


        if (username != null && type != null) {

            try {
                FXMLLoader loader = new FXMLLoader(DbUtils.class.getResource(fxmlFile));
                root = loader.load();
                SignInController SignInController = loader.getController();
                SignInController.setUserInformation(username, type);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        else {
                try{
                    root =FXMLLoader.load(DbUtils.class.getResource(fxmlFile));  // change betwenn sign in and sign up
                }
                catch(IOException e) {
                    e.printStackTrace();
                }

                    }

        Stage stage =(Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root,600,400));
        stage.show();
    }

    public static void signUpUser(ActionEvent event,String username,String password,String type){
        Connection connection =null;
        PreparedStatement psInsert =null;
        PreparedStatement psCheckUserExists=null;
        ResultSet resultSet = null;
        try {
            connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/java-fx","root","");
            psCheckUserExists = connection.prepareStatement("SELECT * FROM users where username=?");
            psCheckUserExists.setString(1,username);
            resultSet= psCheckUserExists.executeQuery();

                if(resultSet.isBeforeFirst()){

                    System.out.print("User already exist!");
                    Alert alert=new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("you can not use this username");
                }
                else{
                    psInsert= connection.prepareStatement("INSERT INTO users(username,password,type) VALUES (?, ?, ?)");
                    psInsert.setString(1,username);
                    psInsert.setString(2,password);
                    psInsert.setString(3,type);
                    psInsert.executeUpdate();

                    changeScene(event,"sign-in.fxml","Welcome",username,type);
                }


        }catch (SQLException e){
            e.printStackTrace();
        } finally {

            if (resultSet!=null){
                try{
                    resultSet.close();
                }catch(SQLException e) {
                    e.printStackTrace();
                }

            }
            if (psCheckUserExists!=null){
                try{
                    psCheckUserExists.close();
                }catch(SQLException e) {
                    e.printStackTrace();
                }

            }
            if (psInsert!=null){
                try{
                    psInsert.close();
                }catch(SQLException e) {
                    e.printStackTrace();
                }

            }
            if (connection!=null){
                try{
                    connection.close();
                }catch(SQLException e) {
                    e.printStackTrace();
                }

            }

        }

    }

    public static void loginUser(ActionEvent event,String username,String passsword){

        Connection connection =null;
        PreparedStatement preparedStatement =null;
        ResultSet resultSet = null;

            try{
                connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/java-fx","root","");
                preparedStatement = connection.prepareStatement("SELECT password ,type FROM users WHERE username =?");
                preparedStatement.setString(1,username);
                resultSet = preparedStatement.executeQuery();

                if(!resultSet.isBeforeFirst()) {
                    System.out.println("User not found in the database!");
                    Alert alert =new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Provided credentials are incorrect");
                    alert.show();
                } else {
                    while(resultSet.next()) {
                        String retrievedPassword = resultSet.getString("password");
                        String retrievedType = resultSet.getString("type");
                        if (retrievedPassword.equals(passsword)) {
                            changeScene(event,"sign-in.fxml","Welcome",username,retrievedType);


                        }else {
                            System.out.println("password did not match!");
                            Alert alert =new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("The Provided credentials are incorrect!");
                            alert.show();
                        }
                    }

                }

            } catch(SQLException e) {
                e.printStackTrace();
            }finally{




                if (resultSet!=null){
                    try{
                        resultSet.close();
                    }catch(SQLException e) {
                        e.printStackTrace();
                    }

                }

                if (preparedStatement!=null){
                    try{
                        preparedStatement.close();
                    }catch(SQLException e) {
                        e.printStackTrace();
                    }

                }



                if (connection!=null){
                    try{
                        connection.close();
                    }catch(SQLException e) {
                        e.printStackTrace();
                    }

                }

            }

        }


    public static void insertFeedback(String feedback, String username) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/java-fx", "root", "");
            preparedStatement = connection.prepareStatement("UPDATE users SET feedback = ? WHERE username = ?");
            preparedStatement.setString(1, feedback);
            preparedStatement.setString(2, username);
            preparedStatement.executeUpdate();

            System.out.println("Feedback inserted successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static String getFeedback(String username) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String feedback = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/java-fx", "root", "");
            preparedStatement = connection.prepareStatement("SELECT feedback FROM users WHERE username = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                feedback = resultSet.getString("feedback");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return feedback;
    }






}



