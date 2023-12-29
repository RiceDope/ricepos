package com.rhyswalker.ricepos;

/**
 * The reports page for the whole system.
 * Will allow managers to veiw reports based on sales refinds and profit made.
 * 
 * @author Rhys Walker
 * @version 1.0
 * @since 2023-12-29
 */

import javafx.scene.layout.*;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ScrollPane;
import org.json.JSONObject;

public class Reports {

    private App app;
    private BorderPane borderPane;
    private FileManagement fileManagement;

    public Reports(App app){
        this.app = app;

        // create the file management object
        fileManagement = new FileManagement();
    
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
        borderPane.setLeft(temporaryButton());
        borderPane.setCenter(createCentreTabs());
    }

    /**
     * Create the central tabs for the reports screen
     * @return A TabPane containing the tabs
     */
    private TabPane createCentreTabs(){
        TabPane tabPane = new TabPane();

        Tab salesTab = new Tab("All sales", createAllSales());
        salesTab.setClosable(false);
        Tab refundsTab = new Tab("All refunds", createAllRefunds());
        refundsTab.setClosable(false);
        Tab profitTab = new Tab("Total Throughput", totalThroughput());
        profitTab.setClosable(false);

        tabPane.getTabs().addAll(salesTab, refundsTab, profitTab);

        return tabPane;
    }

    private ScrollPane createAllRefunds(){
        // create a Vbox to hold each of the sales
        VBox allRefunds = new VBox();

        // get a jsonObject containing all the sales
        JSONObject refunds = fileManagement.getAllRefunds();

        // loop over each sale add them 
        for (String key : refunds.keySet()){
            JSONObject refund = refunds.getJSONObject(key); // get the sale object

            Text refundID = new Text("Refund ID: " + refund.getInt("ReceiptID"));
            Text refundDate = new Text(" Date: " + refund.getString("date"));
            Text refundPaymentMethod = new Text(" Payment Method: " + refund.getString("paymentMethod"));
            Text refundTotalValue = new Text(" Total Value: £" + refund.getDouble("totalValue"));
            
            // Now get each of the products in the sale
            VBox products = new VBox();
            // loop over each item and add it to a VBox for the sale
            JSONObject refundProducts = refund.getJSONObject("products");
            for (String productKey : refundProducts.keySet()){
                JSONObject product = refundProducts.getJSONObject(productKey);

                Text productName = new Text(" Product Name: " + product.getString("name"));
                Text productQuantity = new Text(" Quantity: " + product.getInt("quantity"));
                Text productPrice = new Text(" Price: £" + product.getDouble("price"));

                HBox productBox = new HBox(productName, productQuantity, productPrice);

                // add the product to the products VBox
                products.getChildren().addAll(productBox);
            }

            // create a new HBox to hold the sale
            HBox refundBox = new HBox(refundID, refundDate, refundPaymentMethod, refundTotalValue, products);
            allRefunds.getChildren().addAll(refundBox);
        }

        // create a scroll pane to hold the sales
        ScrollPane scrollPane = new ScrollPane(allRefunds);

        // return the scroll pane
        return scrollPane;
    }

    /**
     * Create a VBox containing the total throughput
     * @return VBox containing the total throughput
     */
    private VBox totalThroughput(){
        VBox totalThroughput = new VBox();

        // get a JSONObject containing all the sales
        JSONObject sales = fileManagement.getAllSales();

        // all payment methods
        double cash = 0;
        double credit = 0;
        double debit = 0;
        double amex = 0;
        double giftCard = 0;
        double other = 0;

        // loop over each sale and add the total value to the correct card type
        for (String key : sales.keySet()){
            JSONObject sale = sales.getJSONObject(key); // get the sale object

            // get the payment method
            String paymentMethod = sale.getString("paymentMethod");

            // get the total value
            double totalValue = sale.getDouble("totalValue");

            // add the total value to the correct card type
            switch (paymentMethod){
                case "Cash":
                    cash += totalValue;
                    break;
                case "Credit":
                    credit += totalValue;
                    break;
                case "Debit":
                    debit += totalValue;
                    break;
                case "Amex":
                    amex += totalValue;
                    break;
                case "Gift Card":
                    giftCard += totalValue;
                    break;
                default:
                    other += totalValue;
                    break;
            }
        }

        // calculate the total of all tender types
        double total = cash + credit + debit + amex + giftCard + other;

        // format text
        Text totalText = new Text("Total Throughput: £" + total);
        Text cashText = new Text("Cash: £" + cash);
        Text creditText = new Text("Credit: £" + credit);
        Text debitText = new Text("Debit: £" + debit);
        Text amexText = new Text("Amex: £" + amex);
        Text giftCardText = new Text("Gift Card: £" + giftCard);
        Text otherText = new Text("Other: £" + other);

        // add to vbox
        totalThroughput.getChildren().addAll(totalText, cashText, creditText, debitText, amexText, giftCardText, otherText);

        // return the VBox
        return totalThroughput;
    }

    /**
     * Create a scroll pane containing all the sales
     * @return ScrollPane containing all the sales
     */
    private ScrollPane createAllSales(){
        // create a Vbox to hold each of the sales
        VBox allSales = new VBox();

        // get a jsonObject containing all the sales
        JSONObject sales = fileManagement.getAllSales();

        // loop over each sale add them 
        for (String key : sales.keySet()){
            JSONObject sale = sales.getJSONObject(key); // get the sale object

            Text saleID = new Text("Sale ID: " + sale.getInt("ReceiptID"));
            Text saleDate = new Text(" Date: " + sale.getString("date"));
            Text salePaymentMethod = new Text(" Payment Method: " + sale.getString("paymentMethod"));
            Text saleTotalValue = new Text(" Total Value: £" + sale.getDouble("totalValue"));
            
            // Now get each of the products in the sale
            VBox products = new VBox();
            // loop over each item and add it to a VBox for the sale
            JSONObject saleProducts = sale.getJSONObject("products");
            for (String productKey : saleProducts.keySet()){
                JSONObject product = saleProducts.getJSONObject(productKey);

                Text productName = new Text(" Product Name: " + product.getString("name"));
                Text productQuantity = new Text(" Quantity: " + product.getInt("quantity"));
                Text productPrice = new Text(" Price: £" + product.getDouble("price"));

                HBox productBox = new HBox(productName, productQuantity, productPrice);

                // add the product to the products VBox
                products.getChildren().addAll(productBox);
            }

            // create a new HBox to hold the sale
            HBox saleBox = new HBox(saleID, saleDate, salePaymentMethod, saleTotalValue, products);
            allSales.getChildren().addAll(saleBox);
        }

        // create a scroll pane to hold the sales
        ScrollPane scrollPane = new ScrollPane(allSales);

        // return the scroll pane
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
