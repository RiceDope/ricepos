package com.rhyswalker.ricepos;

/**
 * This class is specifically for employees it shows a list of all stock but none of the management options that come along with it.
 * This is to be used by employees to see what stock is available and what is not.
 * 
 * @author Rhys Walker
 * @version 1.0
 * @since 2023-12-29
 */

import javafx.scene.layout.*;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.control.ScrollPane;
import org.json.JSONObject;

public class StockList {

    private App app;
    private BorderPane borderPane;
    
    public StockList(App app){
        this.app = app;

        // create the main pane
        borderPane = new BorderPane();
    
        // perform user checks
        if (app.getUser() == null){
            System.err.println("No user logged in");
            app.showLoginScreen();
        }

        // create the main pane
        createPage();

    }

    private void createPage(){
        borderPane.setTop(createTitle());
        borderPane.setLeft(leftOptions());
        borderPane.setCenter(createStockList());
    }

    private ScrollPane createStockList(){
        ScrollPane scrollPane = new ScrollPane();
        VBox vBox = new VBox();

        FileManagement fileManagement = new FileManagement();
        JSONObject stockList = fileManagement.getAllStock();

        for (String stockID : stockList.keySet()){
            // get this stock item
            JSONObject stockItem = stockList.getJSONObject(stockID);

            // create a HBOX to store each stock item
            HBox thisStock = new HBox();

            // create the text for the stock item
            Text stockName = new Text("Name: " + stockItem.getString("name"));
            Text stockPrice = new Text(" Price: £" + String.valueOf(stockItem.getDouble("cost")));
            Text thisStockID = new Text(" ID: " + String.valueOf(stockItem.getInt("id")));

            // hbox to hold this item of stock
            thisStock.getChildren().addAll(stockName, stockPrice, thisStockID);

            // the vbox that will hold all the stock items
            vBox.getChildren().add(thisStock);
        }

        scrollPane.setContent(vBox);
        return scrollPane;
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
     * Creates the left options for the screen
     * @return VBox containing the left options
     */
    private VBox leftOptions(){
        Button tempButton = new Button("Home");

        tempButton.setOnAction(e -> {
            app.showPosScreen();
        });

        VBox tempBox = new VBox(tempButton);
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
