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

        if (app.getUser() == null){
            System.err.println("No user logged in");
            app.showLoginScreen();
        }
        else{ // no need for a manager check here just load the correct options at making each side
            System.out.println("User is a Manager: " + app.getUser().isManager());
            createDefaultScreen(app.getUser().isManager()); // pass in isManager to create the correct screen
        }

                
    }

    /**
     * Creates the default options for a non manager user
     */
    private void createDefaultScreen(Boolean isManager){
        // set temporary text to be displayed in the center of the screen
        Text centerText = new Text("This is the center");

        // create the border pane object
        borderPane = new BorderPane();

        // only certain ones need to be changed if the user is a manager (leftSideOptionsSpecifically)
        if (isManager == true){ // is a manager
            // style of left side is 2 vbox's within a vbox
            VBox wholeLeftSide = new VBox(createLeftSideOptions(), createManagerLeftSideOptions(), logoutButton());
            borderPane.setLeft(wholeLeftSide);
        }
        else{ // is an employee
            // style of left side is a vbox in a vbox
            VBox wholeLeftSide = new VBox(createLeftSideOptions(), logoutButton());
            borderPane.setLeft(wholeLeftSide);
        }


        // set all of the positions in the borderPane
        
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
        // return the root UI component for the screen
        return borderPane;
    }

    /**
     * A function that creates a button to send us to the login screen
     * Will add functionality to logout the current user we are tracking
     * @return The button that sends back to the login screen
     */
    private Button logoutButton(){
        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> {
            // set the current user to null
            app.setUserNull();

            // show the logout screen
            app.showLoginScreen();
        });

        logoutButton.setMaxWidth(Double.MAX_VALUE);
        logoutButton.getStyleClass().add("left-buttons");

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

    private VBox createManagerLeftSideOptions(){

        // create the stock button
        Button stock = new Button("Stock Management");

        // create an event handler for the button
        stock.setOnAction(e -> {
            // update the button
            updateGUI(stock);
        });

        // create the user management button
        Button userManagement = new Button("User Management");

        // create an event handler for the button
        userManagement.setOnAction(e -> {
            // update the button
            updateGUI(userManagement);
        });

        // format the buttons to fill the whole VBox and get colour settings
        stock.getStyleClass().add("left-buttons");
        stock.setMaxWidth(Double.MAX_VALUE);
        userManagement.getStyleClass().add("left-buttons");
        userManagement.setMaxWidth(Double.MAX_VALUE);

        // use a vbox to set organise the buttons
        VBox managerOptions = new VBox(stock, userManagement);

        // apply styling to the box
        managerOptions.getStyleClass().addAll("leftBox");

        return managerOptions;
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
     * For now text will sit here
     * @return A VBox containing the logout button
     */
    private VBox createRightOptions(){
        // create the VBox and add the text
        VBox rightBox = new VBox(new Text("Right Side Options"));

        return rightBox;
    }
}