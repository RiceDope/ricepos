package com.rhyswalker.ricepos;

/**
 * App.java is the class responsible for the loading and unloading of all of the other classes.
 * It is responsible for the successfull boot of the application with all customised settings and such.
 * 
 * @author Rhys Walker
 * @version 0.2
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
    private Scene loginScene;
    private Scene posScene;

    public static void main( String[] args )
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        // create the file management object
        FileManagement fileManagement = new FileManagement();

        // set the height and width properties will eventually be access from the pos_settings.json file
        height = fileManagement.getHeight();
        width = fileManagement.getWidth();

        // Create instances of the screens
        posScreen = new Pos(this);
        loginScreen = new Login(this);

        // create the scenes
        createLoginScene();
        createPosScene();

        // Show the login screen initially
        showLoginScreen();
    }

    private void createLoginScene() {
        loginScene = new Scene(loginScreen.getRoot(), width, height);
        // loginScene.getStylesheets().add(getClass().getResource("/styles/pos.css").toExternalForm());
    }

    private void createPosScene() {
        posScene = new Scene(posScreen.getRoot(), width, height);
        posScene.getStylesheets().add(getClass().getResource("/styles/pos.css").toExternalForm());
    }

    
    public void showPosScreen() {
        primaryStage.setScene(posScene);
        primaryStage.setTitle("POS Screen");
        primaryStage.show();
    }

    public void showLoginScreen() {
        primaryStage.setScene(loginScene);
        primaryStage.setTitle("Login Screen");
        primaryStage.show();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}