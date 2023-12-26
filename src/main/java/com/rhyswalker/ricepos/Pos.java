package com.rhyswalker.ricepos;

/**
 * Pos.java is the class responsible for the actuall till screen of the application.
 * 
 * @author Rhys Walker
 * @version 0.3
 * @since 2023-12-23
 */

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;
import javafx.scene.layout.*;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.json.JSONObject;
import java.util.ArrayList;

public class Pos{

    private App app;
    private BorderPane borderPane;
    private Double tillTotal;
    private FileManagement fileManagement;
    private ArrayList<Integer> stockIDs;
    private ArrayList<Integer> stockQuantities;

    public Pos(App app){
        this.app = app;

        // initialise the stockIDs and stockQuantities
        stockIDs = new ArrayList<Integer>();
        stockQuantities = new ArrayList<Integer>();

        // create the file management object
        fileManagement = new FileManagement();

        // set the till total to 0 to start
        tillTotal = 0.00;

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
        borderPane.setCenter(createCentreOptions());
        borderPane.setBottom(createBottomText()); // set the bottom to the HBox returned by createBottomText()
    }

    /**
     * Creates the central options for the POS screen
     * @return A TabPane containing the options
     */
    private TabPane createCentreOptions(){

        // create the tabs
        Tab till = new Tab("Till", createTillTab());
        till.setClosable(false);
        Tab refunds = new Tab("Refunds", new Label("This is the refunds"));
        refunds.setClosable(false);

        // create the tabs pane
        TabPane tabs = new TabPane(till, refunds);

        // return the tabs
        return tabs;
    }

    private BorderPane createTillTab(){

        // create the border pane for the whole till
        BorderPane till = new BorderPane();

        // create the total for the till at the top of the page
        Text total = new Text("Total: " + tillTotal);
        VBox totalForTill = new VBox(total);
        till.setTop(totalForTill);

        // create the items in the till section
        ScrollPane itemsInTill = new ScrollPane();
        VBox itemsContainer = new VBox();
        itemsInTill.setContent(itemsContainer);
        till.setCenter(itemsInTill);

        // create the search for an item section
        Text searchForItem = new Text("Add an item by ID");
        TextField searchForItemByID = new TextField();
        Button searchForItemButton = new Button("Add");
        searchForItemButton.setOnAction(e -> {

            // check to see if item exists
            if (fileManagement.getAllStock().keySet().contains((searchForItemByID.getText()))){
                JSONObject item = fileManagement.getStockByID(Integer.parseInt(searchForItemByID.getText()));

                // if ID is already in there then get the quantity and add 1
                if (stockIDs.contains(item.getInt("id"))){

                    // because the item was in the till previously then we need to update the HBox
                    int index = stockIDs.indexOf(item.getInt("id"));
                    stockQuantities.set(index, stockQuantities.get(index) + 1); // the new quanitity
                    int newQuantity = stockQuantities.get(index);

                    // get the HBox and update with the new quantity
                    HBox existingItemBox = findItemBoxById(itemsContainer, item.getInt("id"));
                    ((Text) existingItemBox.getChildren().get(2)).setText(" Quantity: " + newQuantity);
                }

                else{ // if ID is not in there then add it and set the quantity to 1
                    stockIDs.add(item.getInt("id"));
                    stockQuantities.add(1);
                    
                    // because the item was not previously in the till we need to create a new HBox
                    HBox itemToAdd = new HBox();
                    itemToAdd.getChildren().add(new Text("Name: " + item.getString("name")));
                    itemToAdd.getChildren().add(new Text(" £ " + item.getDouble("cost")));
                    itemToAdd.getChildren().add(new Text(" Quantity: " + stockQuantities.get(stockIDs.indexOf(item.getInt("id")))));
                    itemToAdd.getChildren().add(new Text(" ID: " + String.valueOf(item.getInt("id"))));

                    // add the item to the till
                    itemsContainer.getChildren().add(itemToAdd);
                }

                // update the till total
                javafx.scene.Node totalVBox = till.getTop();
                tillTotal += item.getDouble("cost");
                if(totalVBox instanceof VBox){ // NO IDEA WHY THIS IS NEEDED AS I KNOW A VBOX IS THE ONLY THING IN THE TOP OF THE BORDERPANE BUT YK FUN
                    ((Text) ((VBox) totalVBox).getChildren().get(0)).setText("Total: " + String.valueOf(tillTotal));
                }
                else{
                    System.err.println("Till total is not a VBox");
                }    
            }
            else{
                System.err.println("Item does not exist");
            }
        });
        VBox searchForItemSection = new VBox(searchForItem, searchForItemByID, searchForItemButton);

        // now add the options to send the till to a receipt this will also sit on the right side of the borderPane
        ComboBox paymentMethods = new ComboBox();
        paymentMethods.getItems().add("Debit");
        paymentMethods.getItems().add("Credit");
        paymentMethods.getItems().add("Amex");
        paymentMethods.getItems().add("Cash");
        paymentMethods.getItems().add("Gift Card");
        paymentMethods.getItems().add("Other");

        Button sendToReceipt = new Button("Process Payment");
        sendToReceipt.setOnAction(e -> {
            // tell user payment was successfull
            System.out.println("Payment Made");

            // add payment to the receipts.json file
            Transaction transaction = new Transaction(stockIDs, stockQuantities, paymentMethods.getValue().toString());
            fileManagement.addReceipt(transaction);

            // update the stock count
            for (int itemID : stockIDs){
                JSONObject item = fileManagement.getStockByID(itemID);
                int newQuantity = item.getInt("count") - stockQuantities.get(stockIDs.indexOf(itemID));
                fileManagement.updateCountByID(itemID, newQuantity);
            }

            // set both of the till lists and tilltotal to nothing
            stockIDs = new ArrayList<Integer>();
            stockQuantities = new ArrayList<Integer>();
            tillTotal = 0.00;

            // update the till total
            javafx.scene.Node totalVBox = till.getTop();
            if(totalVBox instanceof VBox){ // NO IDEA WHY THIS IS NEEDED AS I KNOW A VBOX IS THE ONLY THING IN THE TOP OF THE BORDERPANE BUT YK FUN
                ((Text) ((VBox) totalVBox).getChildren().get(0)).setText("Total: " + String.valueOf(tillTotal));
            }
            else{
                System.err.println("Till total is not a VBox");
            }

            // clear the items in the till
            itemsContainer.getChildren().clear();

        });

        VBox receiptOptions = new VBox(paymentMethods, sendToReceipt);

        // contains item adding and receipt options
        VBox entireTillRightSide = new VBox(searchForItemSection, receiptOptions);
        till.setRight(entireTillRightSide);

        return till;
    }

    /**
     * Find the HBox containing the item with the given ID
     * @param container // the container of the HBox
     * @param itemId // the ID of the item to find
     * @return The HBox containing the item
     */
    private HBox findItemBoxById(VBox container, int itemId) {
        for (javafx.scene.Node node : container.getChildren()) { // for each HBox
            if (node instanceof HBox) { // make sure is a HBox
                int existingItemId = extractItemId((HBox) node); // get the HBox's ID
                if (existingItemId == itemId) { // if it matches the ID we are looking for return
                    return (HBox) node;
                }
            }
        }
        return null;
    }

    /**
     * Get the ID of the till item from the HBox
     * @param itemBox The HBox containing the item
     * @return The ID of the item in the HBOX
     */
    private int extractItemId(HBox itemBox) {
    for (javafx.scene.Node child : itemBox.getChildren()) { // for each child
        if (child instanceof Text) { // if the child is a text node
            String text = ((Text) child).getText();
            if (text.startsWith(" ID: ")) { // if it starts with our prefix for ID
                String idString = text.replace(" ID: ", ""); // remove the " ID: " prefix
                return Integer.parseInt(idString); // return as an int
            }
        }
    }
    return -1; // Or throw an exception or handle the case where item ID is not found
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
            app.stockManagementButtonClicked();
        });

        // create the user management button
        Button userManagement = new Button("User Management");

        // create an event handler for the button
        userManagement.setOnAction(e -> {
            // update the button
            app.userManagementButtonClicked();
        });

        // create the user management button
        Button reportsButton = new Button("Reports");

        // create an event handler for the button
        reportsButton.setOnAction(e -> {
            // update the button
            app.reportsButtonClicked();
        });

        // format the buttons to fill the whole VBox and get colour settings
        stock.getStyleClass().add("left-buttons");
        stock.setMaxWidth(Double.MAX_VALUE);
        userManagement.getStyleClass().add("left-buttons");
        userManagement.setMaxWidth(Double.MAX_VALUE);
        reportsButton.getStyleClass().add("left-buttons");
        reportsButton.setMaxWidth(Double.MAX_VALUE);

        // use a vbox to set organise the buttons
        VBox managerOptions = new VBox(stock, userManagement, reportsButton);

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