package com.rhyswalker.ricepos;

/**
 * The stock management page for the whole application.
 * Users can choose to either view, edit, add or remove stock
 * 
 * @author Rhys Walker
 * @version 0.2
 * @since 2023-12-24
 */

import javafx.scene.layout.*;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.TextFlow;
import org.json.JSONObject;

public class StockManagement {

    private App app;
    private BorderPane borderPane;

    public StockManagement(App app){
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
        borderPane.setLeft(leftOptions());
        borderPane.setCenter(createCentralOptions());
    }

    

    /**
     * Set all of the options for the central part of the screen
     * @return
     */
    private TabPane createCentralOptions(){

        // create all the tabs and set them to not be closeable
        Tab allStock = new Tab("All Stock", createAllStockTabContents());
        allStock.setClosable(false);
        Tab addStock = new Tab("Add Stock", createAddStockTabContents());
        addStock.setClosable(false);
        Tab editStock = new Tab("Edit Stock", createEditStockTabContents());
        editStock.setClosable(false);
        Tab removeStock = new Tab("Remove Stock", createRemoveStockTabContents());
        removeStock.setClosable(false);

        // create the tab pane
        TabPane tabPane = new TabPane(allStock, addStock, editStock, removeStock);

        // return the pane
        return tabPane;
    }

    /**
     * Force a refresh of the screen to update contents
     */
    private void forceRefresh(){
        borderPane.setCenter(createCentralOptions());
    }

    /**
     * Create the contents of the all stock tab
     * @return A Scroll Pane Containing all of the stock
     */
    private ScrollPane createAllStockTabContents(){

        // get all of the stock from the fileManager
        JSONObject allStock = app.fileManagement.getAllStock();

        // create the VBox to store all of the stock
        VBox allStockBox = new VBox();

        // iterate over the stock and create a HBox for each item that contains the details of each item
        for (String key : allStock.keySet()){
            JSONObject item = allStock.getJSONObject(key);

            // create the HBox
            HBox itemBox = new HBox();

            // create the text objects
            Text name = new Text("Product name: " + item.getString("name"));
            Text price = new Text(String.valueOf(" Cost £" + item.getDouble("cost")));
            Text quantity = new Text(String.valueOf(" Quantity in stock: " + item.getInt("count")));
            Text stockID = new Text(" Stock ID: " + key);

            // add the text objects to the HBox
            itemBox.getChildren().addAll(name, price, quantity, stockID);

            // add the HBox to the VBox
            allStockBox.getChildren().add(itemBox);
        }

        // create and return the scrollPane
        ScrollPane scrollPane = new ScrollPane(allStockBox);
        return scrollPane;
    }

    /**
     * Create the contents of the add stock tab
     * @return A VBox containing the options for adding stock
     */
    private VBox createAddStockTabContents(){

        // create all of the text fields needed for adding a product
        Label costText = new Label("Cost:");
        TextField cost = new TextField();
        Label countText = new Label("Count:");
        TextField count = new TextField();
        Label nameText = new Label("Name");
        TextField name = new TextField();

        // create a hbox to hold these fields
        HBox userInputBar = new HBox(costText, cost, countText, count, nameText, name);

        // create a hbox to hold the add product button
        Button addButton = new Button("Add product");
        addButton.setOnAction(e -> { // set an event listener
            // add the product to the file
            app.fileManagement.addStock(Double.parseDouble(cost.getText()), Integer.parseInt(count.getText()), name.getText());
            forceRefresh();
        });
        HBox buttonBar = new HBox(addButton);

        // create the vbox to hold the hboxs
        VBox addStockBox = new VBox(userInputBar, buttonBar);
        return addStockBox;
    }

    private VBox createEditStockTabContents(){

        // text to explain how to edit the stock counts and values
        Text instructions = new Text("To edit the stock count or price of an item enter the stock ID or name of the item (NOT BOTH) and then enter the new values Stock count or Price to edit. Both can be changed at the same time");
        TextFlow instructionsFlow = new TextFlow(instructions); // allows text to wrap
        HBox instructionsBox = new HBox(instructionsFlow);
        instructionsFlow.prefWidthProperty().bind(instructionsBox.widthProperty()); // sets the wrap to size of hbox

        // the find stock options
        Label stockIDText = new Label("Stock ID:");
        TextField stockID = new TextField();
        Label stockNameText = new Label("Stock name");
        TextField stockName = new TextField();
        HBox findStockOptions = new HBox(stockIDText, stockID, stockNameText, stockName);

        // the new values options
        Label newStockCountText = new Label("New stock count");
        TextField newStockCount = new TextField();
        Label newPriceText = new Label("New Price");
        TextField newPrice = new TextField();
        HBox userValueOptions = new HBox(newStockCountText, newStockCount, newPriceText, newPrice);

        // create the button that will confirm the changes
        Button submitChanges = new Button("Commit changes");
        /*
         * This function changes the values based on the users input. They must choose either stockID or stockName not both
         * Then it works out the values that we are changing based on user input. One or both of the values can be changed
         * Then we submit a page refresh should all of this be done correctly.
         */
        submitChanges.setOnAction(e -> {

            // check which has been set stockID or stockName
            if (stockID.getText().equals("") && !stockName.getText().equals("")){

                // stockName has been set

                // check which of the new values have been set
                if (!newStockCount.getText().equals("") && !newPrice.getText().equals("")){
                    // both have been set
                    app.fileManagement.updateCountByName(stockName.getText(), Integer.parseInt(newStockCount.getText()));
                    app.fileManagement.updatePriceOfItemByName(stockName.getText(), Double.parseDouble(newPrice.getText()));
                }
                else if (!newStockCount.getText().equals("") && newPrice.getText().equals("")){
                    // only newStockCount has been set
                    app.fileManagement.updateCountByName(stockName.getText(), Integer.parseInt(newStockCount.getText()));
                }
                else if (newStockCount.getText().equals("") && !newPrice.getText().equals("")){
                    // only newPrice has been set
                    app.fileManagement.updatePriceOfItemByName(stockName.getText(), Double.parseDouble(newPrice.getText()));
                }
                else{
                    // neither have been set
                    System.err.println("Neither newStockCount or newPrice have been set");
                }

            }
            else if (!stockID.getText().equals("") && stockName.getText().equals("")){

                // stockID has been set

                // check which of the new values have been set
                if (!newStockCount.getText().equals("") && !newPrice.getText().equals("")){
                    // both have been set
                    app.fileManagement.updateCountByID(Integer.parseInt(stockID.getText()), Integer.parseInt(newStockCount.getText()));
                    app.fileManagement.updatePriceOfItemByID(Integer.parseInt(stockID.getText()), Double.parseDouble(newPrice.getText()));
                }
                else if (!newStockCount.getText().equals("") && newPrice.getText().equals("")){
                    // only newStockCount has been set
                    app.fileManagement.updateCountByID(Integer.parseInt(stockID.getText()), Integer.parseInt(newStockCount.getText()));
                }
                else if (newStockCount.getText().equals("") && !newPrice.getText().equals("")){
                    // only newPrice has been set
                    app.fileManagement.updatePriceOfItemByID(Integer.parseInt(stockID.getText()), Double.parseDouble(newPrice.getText()));
                }
                else{
                    // neither have been set
                    System.err.println("Neither newStockCount or newPrice have been set");
                }
            }
            else{
                // both have been set so we do nothing and print an error
                System.err.println("Both stockID and stockName have been set please select only one");
            }

            // now refresh the screen
            forceRefresh();
        });
        HBox buttonBar = new HBox(submitChanges);

        // create the final vbox that will contain the two rows of objects
        VBox editStockBox = new VBox(instructionsBox, findStockOptions, userValueOptions, buttonBar);
        return editStockBox;
    }

    private VBox createRemoveStockTabContents(){

        // instructions for removing an item
        Text instructions = new Text("To remove a stock item enter the stock ID or name of the item (NOT BOTH)");
        TextFlow instructionsFlow = new TextFlow(instructions); // allows text to wrap
        HBox instructionsBox = new HBox(instructionsFlow);
        instructionsFlow.prefWidthProperty().bind(instructionsBox.widthProperty()); // sets the wrap to size of hbox

        // the find stock options
        Label stockIDText = new Label("Stock ID:");
        TextField stockID = new TextField();
        Label stockNameText = new Label("Stock name");
        TextField stockName = new TextField();
        HBox findStockOptions = new HBox(stockIDText, stockID, stockNameText, stockName);

        // create the button that will confirm the changes
        Button submitChanges = new Button("Remove item");
        /*
         * This function removes the item from the file based on the users input. They must choose either stockID or stockName not both
         * Then we submit a page refresh should all of this be done correctly.
         */
        submitChanges.setOnAction(e -> {

            // check which has been set stockID or stockName
            if (stockID.getText().equals("") && !stockName.getText().equals("")){

                // stockName has been set
                app.fileManagement.removeStockItemWithName(stockName.getText());

            }
            else if (!stockID.getText().equals("") && stockName.getText().equals("")){

                // stockID has been set
                app.fileManagement.removeStockItemWithID(Integer.parseInt(stockID.getText()));
            }
            else{
                // both have been set so we do nothing and print an error
                System.err.println("Both stockID and stockName have been set please select only one");
            }

            // now refresh the screen
            forceRefresh();
        });
        HBox buttonBar = new HBox(submitChanges);

        // create the vbox to return
        VBox removeStockBox = new VBox(instructionsBox, findStockOptions, buttonBar);
        return removeStockBox;
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
