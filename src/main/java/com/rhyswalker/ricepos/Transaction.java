package com.rhyswalker.ricepos;

/**
 * A custom data type to process a transaction
 * 
 * @author Rhys Walker
 * @version 1.0
 * @since 2023-12-26
 */

import org.json.JSONObject;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;

public class Transaction {

    private ArrayList<Integer> stockIDs;
    private ArrayList<Integer> quantities;
    private String paymentMethod; // Can be Debit, Credit, Amex, Cash, Gift Card, Other
    private String date;
    private FileManagement fileManagement;
    
    public Transaction(ArrayList<Integer> stockIDs, ArrayList<Integer> quantities, String paymentMethod){
        this.stockIDs = stockIDs;
        this.quantities = quantities;
        this.date = createDate();
        this.paymentMethod = paymentMethod;
        fileManagement = new FileManagement();
    }

    /**
     * A function that will format the transaction into a JSON object
     * @return The transaction as a JSON object
     */
    public JSONObject formatTransaction(){

        // create the large receipt object
        JSONObject newReceipt = new JSONObject();
        JSONObject products = new JSONObject();

        double totalValue = 0;

        // increment at each loop so we can get the correct quantity
        int i = 0;

        for (Integer stockID : stockIDs){

            // get the stock object
            JSONObject stock = fileManagement.getStockByID(stockID);

            // create the product object
            JSONObject product = new JSONObject();
            product.put("name", stock.getString("name"));
            product.put("price", stock.getDouble("cost"));
            product.put("quantity", quantities.get(i));
            product.put("stockID", stockID);

            // add the product to the receipt
            double thisProductValue = stock.getDouble("cost") * quantities.get(i);
            totalValue += thisProductValue;

            // add this product to the products tab
            products.put(stockID.toString(), product);

            // increment to next quantity
            i++;
        }

        newReceipt.put("totalValue", totalValue);
        newReceipt.put("date", date);
        newReceipt.put("ReceiptID", fileManagement.getCurrentReceiptID());
        newReceipt.put("paymentMethod", paymentMethod);
        newReceipt.put("products", products);

        return newReceipt;
    }

    /**
     * A function that will get the data as a string
     * @return The current date and time as a string
     */
    private String createDate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now();  
        return dtf.format(now);
    }
}
