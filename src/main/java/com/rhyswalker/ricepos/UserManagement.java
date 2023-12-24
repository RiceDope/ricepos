package com.rhyswalker.ricepos;

import javafx.scene.layout.*;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class UserManagement {

    private App app;
    private BorderPane borderPane;

    public UserManagement(App app){
        this.app = app;
    
        // perform user checks
        if (app.getUser() == null){
            System.err.println("No user logged in");
            app.showLoginScreen();
        }
        else{ // we have a user so check permissions
            if (app.getUser().isManager() == true){ // user is a manager so we can continue
                createDefaultScreen();
            }
            else { // user is not a manager
                System.err.println("User is not a manager");
                app.showPosScreen();
            }
        }

    }

    /**
     * Creates the default screen when accessing the reports screen
     */
    private void createDefaultScreen(){

        // initialise the borderpane object
        borderPane = new BorderPane();

        // fill the border pane with choices
        borderPane.setTop(createTitle());
        borderPane.setCenter(temporaryButton());
    }

    /**
     * Create a box that will centre the title on the page
     * @return HBox containing the title
     */
    private HBox createTitle() {

        // create the text and add to a HBox
        Text title = new Text("Rice POS");
        HBox titleBox = new HBox(title);

        // set the css
        titleBox.getStyleClass().add("title");
        title.getStyleClass().add("title-text");

        // return the HBox containing the title
        return titleBox;
    }

    private HBox temporaryButton(){
        Button tempButton = new Button("Home");

        tempButton.setOnAction(e -> {
            app.showPosScreen();
        });

        HBox tempBox = new HBox(tempButton);
        return tempBox;
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
