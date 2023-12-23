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
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import java.security.NoSuchAlgorithmException;

public class Login{

    // create default variables that will be needed later
    private App app;
    private BorderPane borderPane;

    public Login(App app) throws NoSuchAlgorithmException{ // exception needed for hashing
        this.app = app;

        // create the border pane object and add our items to it
        borderPane = new BorderPane();
        borderPane.setCenter(createLogin());
        borderPane.setTop(createTitle());

    }

    /**
     * A function that creates a button to send us to the POS screen
     * @return The button
     */
    private VBox createLogin() throws NoSuchAlgorithmException{
        Label usernameText = new Label("Username:");
        TextField username = new TextField();
        Label passwordText = new Label("Password:");
        PasswordField password = new PasswordField();

        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> {
            // try and except is needed here due to hashing
            try {
                app.loginClicked(username.getText(), password.getText());
            } catch (NoSuchAlgorithmException e1) {
                System.err.println("Error in hashing");
                e1.printStackTrace();
            }
        });

        VBox vbox = new VBox(usernameText, username, passwordText, password, loginButton);
        return vbox;
    }

    private HBox createTitle(){
        // create the text to be disaplyed on the title
        Text titleText = new Text("RicePOS");
        titleText.getStyleClass().add("title-text");
        HBox hbox = new HBox(titleText);
        hbox.getStyleClass().add("title");
        return hbox;
    }

    /**
     * Returns what the root object for this scene should be
     * @return The root object forthis scene
     */
    public javafx.scene.Parent getRoot() {
        // return the root UI component for the screen
        return borderPane;
    }
}
