package com.rhyswalker.ricepos;

/**
 * Pos.java is the class responsible for the actuall till screen of the application.
 * 
 * @author Rhys Walker
 * @version 0.3
 * @since 2023-12-23
 */

import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.layout.*;


public class Pos{

    private App app;
    private BorderPane borderPane;

    public Pos(App app){
        this.app = app;

        // set temporary text to be displayed in the center of the screen
        Text centerText = new Text("This is the center");

        // create the border pane object
        borderPane = new BorderPane();

        // set all of the positions in the borderPane
        borderPane.setLeft(createLeftSideOptions()); // set the left side to the VBox returned by createLeftSideOptions()
        borderPane.setTop(createTitle()); // set the top to the HBox returned by createTitle()
        borderPane.setRight(createRightOptions()); // set the right side to the VBox returned by createRightOptions()
        borderPane.setCenter(centerText);
        borderPane.setBottom(createBottomText()); // set the bottom to the HBox returned by createBottomText()        
    }

    /**
     * Returns what the root object for this scene should be
     * @return The root object forthis scene
     */
    public javafx.scene.Parent getRoot() {
        return borderPane;
    }

    /**
     * A function that creates a button to send us to the login screen
     * Will add functionality to logout the current user we are tracking
     * @return The button that sends back to the login screen
     */
    private Button logoutButton(){
        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> app.showLoginScreen());
        return logoutButton;
    }

    /**
     * Function that currently does nothing with the button but will be updated in future
     * @param button The button to update
     */
    private void updateGUI(Button button) {
        // Change the text of the button
        System.out.println("Button pressed");
    }

    /**
     * Create the options that will appear on the left of the screen
     * @return VBox containing the options
     */
    private VBox createLeftSideOptions() {

        // create the home button
        Button homeButton = new Button("Home");

        // create an event handler for the button
        homeButton.setOnAction(e -> {
            // update the button
            updateGUI(homeButton);
        });

        Button discountsButton = new Button("Discount");

        // create an event handler for the button
        discountsButton.setOnAction(e -> {
            // update the button
            updateGUI(discountsButton);
        });

        // format the buttons to fill the whole VBox and get colour settings
        homeButton.setMaxWidth(Double.MAX_VALUE);
        homeButton.getStyleClass().add("left-buttons");
        discountsButton.setMaxWidth(Double.MAX_VALUE);
        discountsButton.getStyleClass().add("left-buttons");

        // use VBox to set the bottom with a button and text
        VBox leftOptions = new VBox(homeButton, discountsButton);

        // apply styling to the box
        leftOptions.getStyleClass().addAll("leftBox");
        
        // return the VBox containing the options
        return leftOptions;
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

    /**
     * Create and format the text that will appear on the bottom of the screen
     * @return HBox containing the text
     */
    private HBox createBottomText(){
        // create the text to be displayed
        Text bottomText = new Text("Rice POS developed by Rhys Walker. For info and help checkout the GitHub page");

        // create the HBox to contain the text and style it
        HBox bottomBox = new HBox(bottomText);
        bottomBox.getStyleClass().add("bottom-box");

        return bottomBox;
    }

    /**
     * For now this will hold the logout button
     * @return A VBox containing the logout button
     */
    private VBox createRightOptions(){
        // add a logout button to the right side
        // this will eventually change the session as well as send to login screen
        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> app.showLoginScreen());

        // create the VBox and add the button and css
        VBox rightBox = new VBox(logoutButton);
        rightBox.getStyleClass().add("left-buttons");

        return rightBox;
    }
}