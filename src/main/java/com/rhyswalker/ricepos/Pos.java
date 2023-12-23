package com.rhyswalker.ricepos;

/**
 * GUI.java is the base class for all of the GUI elements in Rice POS.
 * 
 * @author Rhys Walker
 * @version 0.2
 * @since 2023-12-22
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.*;


public class Pos extends Application {

    //private Button button;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        // set width and height for the window
        int width = 1000;
        int height = 600;

        // set texts for each position in a borderPane
        Text topText = new Text("This is the top");
        Text leftText = new Text("This is the left");
        Text rightText = new Text("This is the right");
        Text centerText = new Text("This is the center");
        Text bottomText = new Text("This is the bottom");

        // create the border pane object
        BorderPane borderPane = new BorderPane();

        // set all of the positions in the borderPane
        borderPane.setLeft(createLeftSideOptions()); // set the left side to the VBox returned by createLeftSideOptions()
        borderPane.setTop(createTitle()); // set the top to the HBox returned by createTitle()
        borderPane.setRight(createRightOptions()); // set the right side to the VBox returned by createRightOptions()
        borderPane.setCenter(centerText);
        borderPane.setBottom(createBottomText()); // set the bottom to the HBox returned by createBottomText()

        // make a new scene with the border pane
        Scene scene = new Scene(borderPane, width, height);
        scene.getStylesheets().add("stylesheet.css");

        // set the current scene, add a title and show it
        primaryStage.setScene(scene);
        primaryStage.setTitle("Rice POS");
        primaryStage.show();
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
     * For now will just return text inside a VBox and will be updated later to feature more information
     * @return A VBox containing the options
     */
    private VBox createRightOptions(){
        Text rightText = new Text("Will be updated \nlater to feature \nmore informaiton \nand options related \nto taking payments");
        VBox rightBox = new VBox(rightText);
        rightBox.getStyleClass().add("right-pane");

        return rightBox;
    }
}