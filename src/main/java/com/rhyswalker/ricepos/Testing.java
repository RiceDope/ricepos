package com.rhyswalker.ricepos;

/**
 * This class is ever chaning and will be changed depending on the thing that I need to test. At the moment it is the fileManagement class
 * 
 * @author Rhys Walker
 * @version 1.0
 * @since 2023-12-23
 */

import org.json.JSONObject;

public class Testing {

    public static void main(String[] args) {

        FileManagement fileManagement = new FileManagement();

        // fileManagement.addStock(10, 50, "Brush");

        System.out.println(fileManagement.getStockByName("Brush"));

        fileManagement.removeStockItemWithID(2);

        System.out.println(fileManagement.getStockByName("Brush"));

    }
}
