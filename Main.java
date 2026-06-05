package com.mycompany.groupproject;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    
    public User authenticator = new User();
    
    @Override
    public void start(Stage stage) {
        
        // Login Screen UI
        Label title = new Label("Login / Register");
        
        TextField user = new TextField();
        user.setPromptText("Username");
        
        PasswordField pass = new PasswordField();
        pass.setPromptText("Password");
        
        TextField target = new TextField();
        target.setPromptText("Target Calories");
        
        Button login = new Button("Login");
        Button register = new Button("Register");
        
        // Error Message
        Label message = new Label();
        
        // Window Layout
        VBox loginBox = new VBox(10, title, user, pass, target, login, register, message);
        Scene loginScene = new Scene(loginBox, 300, 250);
        
        // When Login Button Pressed
        login.setOnAction(e -> {
            
            // Check if user and password match inside text file
            if (authenticator.login(user.getText(), pass.getText())) {
                // Get the target calorie
                int targetCal = authenticator.getTarget(user.getText());
                
                // Create new dashboard window for user
                stage.setScene(new Dashboard(user.getText(), targetCal).getScene());
            }
            else {
                message.setText("Invalid login!");
            }
        });
        
        // When Register Button Pressed
        register.setOnAction(e -> {
            // Check if user is available in text file
            try {
                int calTarget = Integer.parseInt(target.getText());

                if (authenticator.register(user.getText(), pass.getText(), calTarget)) {
                    message.setText("Account created!");
                }
                else {
                    message.setText("User exists!");
                }

            }
            // Input Validation
            catch (Exception ex) {
                message.setText("Enter valid calorie target!");
            }
        });
        
        stage.setScene(loginScene);
        stage.setTitle("Calorie Tracker");
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}