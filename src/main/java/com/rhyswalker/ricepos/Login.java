package com.rhyswalker.ricepos;

/**
 * The login page for the whole system.
 * This is in a very much testing state at the moment. Will be updated as the project progresses.
 * 
 * @author Rhys Walker
 * @version 0.1
 * @since 2023-12-23
 */

import javafx.scene.control.Button;
import javafx.scene.layout.*;

public class Login{

    // create default variables that will be needed later
    private App app;
    private BorderPane borderPane;

    public Login(App app){
        this.app = app;

        // create the border pane object and add our items to it
        borderPane = new BorderPane();
        borderPane.setCenter(createLoginButton());

    }

    /**
     * A function that creates a button to send us to the POS screen
     * @return The button
     */
    private Button createLoginButton(){
        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> app.showPosScreen());
        return loginButton;
    }

    /**
     * Returns what the root object for this scene should be
     * @return The root object forthis scene
     */
    public javafx.scene.Parent getRoot() {
        // Return the root UI component
        // You might load an FXML file here or create the UI programmatically
        // Example: return new StackPane(new Label("POS Screen"));
        return borderPane;
    }
}
