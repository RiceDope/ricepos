package com.rhyswalker.ricepos;

/**
 * App.java is the class responsible for the loading and unloading of all of the other classes.
 * It is responsible for the successfull boot of the application with all customised settings and such.
 * 
 * @author Rhys Walker
 * @version 0.1
 * @since 2023-12-23
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application{

    private Stage primaryStage;
    private Pos posScreen;
    private Login loginScreen;
    private int height;
    private int width;

    public static void main( String[] args )
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        // set the height and width properties will eventually be access from the pos_settings.json file
        height = 600;
        width = 1000;

        // Create instances of the screens
        posScreen = new Pos(this);
        loginScreen = new Login(this);

        // Show the login screen initially
        showLoginScreen();
    }

    
    public void showPosScreen() {
        Scene scene = new Scene(posScreen.getRoot(), width, height);
        scene.getStylesheets().add(getClass().getResource("/styles/pos.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("POS Screen");
        primaryStage.show();
    }

    public void showLoginScreen() {
        Scene scene = new Scene(loginScreen.getRoot(), width, height);
        // scene.getStylesheets().add(getClass().getResource("/styles/pos.css").toExternalForm()); CREATE NEW CSS AND APPLY
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login Screen");
        primaryStage.show();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}