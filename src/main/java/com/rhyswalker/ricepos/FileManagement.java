package com.rhyswalker.ricepos;

/**
 * The default file manager for the application. Will contain all of the commands to read and write to files for the application.
 * By default the application will create a directory in the users home directory.
 * 
 * @author Rhys Walker
 * @version 0.2
 * @since 2023-12-23
 */

import java.nio.file.*;
import org.json.JSONObject;
import java.util.ArrayList;

public class FileManagement {

    // strings that contain paths to important directories
    private String userHome;
    private String programDirectory;

    // paths to files or directories in the home directory
    private Path directoryPath;
    private Path usersFilePath;
    private Path customisationsFilePath;
    private Path sysfilesFilePath;
    private Path stockFilePath;

    public FileManagement(){

        // set the userHome variable to the users home directory
        userHome = System.getProperty("user.home");

        // set the path to the riceposfiles directory
        programDirectory = "riceposfiles";
        directoryPath = Paths.get(userHome, programDirectory);

        // set the path to the users.json file
        usersFilePath = Paths.get(userHome, programDirectory, "users.json");

        // set the path to the customisations.json file
        customisationsFilePath = Paths.get(userHome, programDirectory, "customisations.json");

        // set the path to the sysfiles.json file
        sysfilesFilePath = Paths.get(userHome, programDirectory, "sysfiles.json");

        // set the path to the stock.json file
        stockFilePath = Paths.get(userHome, programDirectory, "stock.json");

        // now check whether the directory exists if not create
        if (checkDirectoryExists(directoryPath) == false){
            System.out.println("Creating directory riceposfiles");
            createDirectory();
        }
        else{
            // should check if files exist inside of here
            System.out.println("Directory riceposfiles exists - checking if all subfiles exist");
            checkFilesExist();
        }
    }

    /*
     * ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
     * BELOW ARE ALL OF THE FILES REQUIRED FOR SETUP OF THE APPLICATION
     * ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
     * 
     * checkFilesExist()
     * checkDirectoryExists()
     * createDirectory()
     * defaultCustomisations()
     * defaultUsers()
     */

    /**
     * A function that checks if all of the files inside of riceposfiles exist.
     * Should they not exist then it will create them
     */
    private void checkFilesExist(){

        // check if users.json exists
        if (Files.exists(usersFilePath)) {
            System.out.println("users.json exists");
        } 
        // if not then create the file
        else {
            System.out.println("users.json does not exist creating file");

            // set the default contents of the file
            JSONObject usersJson = defaultUsers();

            // write to the file
            try {
                Files.write(usersFilePath, usersJson.toString(4).getBytes());
            }
            // catch any errors
            catch (Exception e) {
                System.err.println("Error creating users.json: " + e.getMessage());
            }
        }

        // check if customisations.json exists
        if (Files.exists(customisationsFilePath)) {
            System.out.println("customisations.json exists");
        } 
        // if not then create the file
        else {
            System.out.println("customisations.json does not exist creating file");

            // set the default contents of the file
            JSONObject customisationsJson = defaultCustomisations();

            // write to the file
            try {
                Files.write(customisationsFilePath, customisationsJson.toString(4).getBytes());
            }
            // catch any errors
            catch (Exception e) {
                System.err.println("Error creating customisations.json: " + e.getMessage());
            }
        }

        // check if sysfiles.json exists
        if (Files.exists(sysfilesFilePath)) {
            System.out.println("sysfiles.json exists");
        } 
        // if not then create the file
        else {
            System.out.println("sysfiles.json does not exist creating file");

            // set the default contents of the file
            JSONObject sysfilesJson = defaultsysfiles();

            // write to the file
            try {
                // need to check if stock.json exists as it depends on the stockID
                if (Files.exists(stockFilePath)){
                    // if it exists already there is an issue as sysfiles will be out of date and not format correctly
                    System.err.println("Error stock.json exists but sysfiles.json does not. Error: A2 check errors.md");
                    Files.write(sysfilesFilePath, sysfilesJson.toString(4).getBytes());

                    // increment the stockID in case of a specific edge case
                    incrementStockID();
                }
                else{
                    // if it doesn't exist there is no issue and continue as normal
                    Files.write(sysfilesFilePath, sysfilesJson.toString(4).getBytes());
                }

                
            }
            // catch any errors
            catch (Exception e) {
                System.err.println("Error creating sysfiles.json: " + e.getMessage());
            }
        }

        // check if sysfiles.json exists
        if (Files.exists(stockFilePath)) {
            System.out.println("stock.json exists");
        } 
        // if not then create the file
        else {
            System.out.println("stock.json does not exist creating file");

            // set the default contents of the file
            JSONObject stockJson = defaultStock();

            // write to the file
            try {
                // don't need to check on sysfiles existance as it was check on function above
                Files.write(stockFilePath, stockJson.toString(4).getBytes());
            }
            // catch any errors
            catch (Exception e) {
                System.err.println("Error creating stock.json: " + e.getMessage());
            }
        }


    }

    /**
     * Check whether riceposfiles directory exists
     * @return A boolean 1 if exists 0 if not
     */
    private boolean checkDirectoryExists(Path directoryPath){
        // check if the directory exists in that case we should already have the files inside but will perform check
        if (Files.exists(directoryPath) && Files.isDirectory(directoryPath)) {
            return true;
        } 
        else {
            return false;
        }
    }

    /**
     * Create the riceposfiles directory. Will create all files that are needed inside of the directory
     */
    private void createDirectory(){

        // users default contents
        JSONObject usersJson = defaultUsers();

        // customisations default contents
        JSONObject customisationsJson = defaultCustomisations();

        // create the base file riceposfiles
        try {
            // create a new directory
            Files.createDirectory(directoryPath);
            
            // create the users.json file
            Files.write(usersFilePath, usersJson.toString(4).getBytes());

            // create the customisations.json file
            Files.write(customisationsFilePath, customisationsJson.toString(4).getBytes());

            // create the sysfiles.json file
            Files.write(sysfilesFilePath, defaultsysfiles().toString(4).getBytes());

            // create the stock.json file
            Files.write(stockFilePath, defaultStock().toString(4).getBytes());

        } catch (Exception e) {
            // catch any errors
            System.err.println("Error creating directory: " + e.getMessage());
        }
    }

    /**
     * Returns the default contents of the customisations.json file
     * @return A JSONObject containing the default contents of the customisations.json file
     */
    private JSONObject defaultCustomisations(){
        // set the default contents of the file
        JSONObject customisationsJson = new JSONObject();
        customisationsJson.put("width", "1000");
        customisationsJson.put("height", "600");

        return customisationsJson;
    }

    /**
     * Returns the default contents of the users.json file
     * @return A JSONObject containing the default contents of the users.json file
     */
    private JSONObject defaultUsers(){

        // json object to contain all of the data for the default manager
        JSONObject defaultManager = new JSONObject();
        defaultManager.put("username", "defaultmanager");
        defaultManager.put("password", "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8"); // default password is password
        defaultManager.put("fullName", "Default Manager");
        defaultManager.put("manager", true);

        // json object to contain all of the data for the default employee
        JSONObject defaultEmployee = new JSONObject();
        defaultEmployee.put("username", "defaultemployee");
        defaultEmployee.put("password", "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8"); // default password is password
        defaultEmployee.put("fullName", "Default Employee");
        defaultEmployee.put("manager", false);

        // add to the default key-value pair
        JSONObject usersJson = new JSONObject();
        usersJson.put("defaultmanager", defaultManager);
        usersJson.put("defaultemployee", defaultEmployee);

        return usersJson;
    }

    /**
     * Returns the default contents of stock.json file
     * Should only ever run if stockID is 0. If it isnt then report an error to the user and close the screen
     * @return A JSONObject containing the default contents of the stock.json file
     */
    private JSONObject defaultStock(){

        // if stockID is not 0 then there is an error as the file has got lost. Stock could exist but the sysfiles.json file has been lost.
        if (getCurrentStockID() != 0){
            // print an error to the console
            System.err.println("Error stockID is not 0 but stock.json does not exist. Error: A1 check errors.md");

            // create and add the default stock
            JSONObject defaultItem = new JSONObject();
            defaultItem.put("id", 0); // get the starting stock figure which is 0
            defaultItem.put("count", 0);
            defaultItem.put("cost", 0);
            defaultItem.put("name", "defaultStock");

            // THE ABOVE ARE DEFAULT VALUES DO NOT REMOVE THIS ITEM

            // create the larger object
            JSONObject stockJson = new JSONObject();
            stockJson.put("0", defaultItem);

            incrementStockID();

            // return the stockJson
            return stockJson;

        }
        else{
            // create and add the default stock
            JSONObject defaultItem = new JSONObject();
            defaultItem.put("id", 0); // get the starting stock figure which is 0
            defaultItem.put("count", 0);
            defaultItem.put("cost", 0);
            defaultItem.put("name", "defaultStock");

            // THE ABOVE ARE DEFAULT VALUES DO NOT REMOVE THIS ITEM

            // create the larger object
            JSONObject stockJson = new JSONObject();
            stockJson.put("0", defaultItem);

            // increment the stockID
            incrementStockID();

            // return the JSON
            return stockJson;
        }
    }

    /**
     * Returns the default contents of sysfiles.json file
     * @return A JSONObject containing the default contents of the sysfiles.json file
     */
    private JSONObject defaultsysfiles(){

        // check to see if stock.json exists already if it does print an error and ask user to fix

        JSONObject sysfilesjson = new JSONObject();
        sysfilesjson.put("receiptID", 0);
        sysfilesjson.put("stockID", 0);

        return sysfilesjson;
    }

    /*
     * ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
     * END OF THE SETUP FILES
     * ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
     */

     /*
      * ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
      * START OF THE FILE READING SCRIPTS
      * ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
      *
      * getHeight()
      * getWidth()
      */

      /**
       * Will read the customisations.json file and return the custom height of the application
       * @return An integer containing the height of the application
       */
    public int getHeight(){
        try{
            // read the file as a string and then return the height to the application
            String fileContent = Files.readString(customisationsFilePath);
            JSONObject customisationsJson = new JSONObject(fileContent);
            return Integer.parseInt(customisationsJson.getString("height"));
        }
        catch(Exception e){
            // if there is an error then return the default height
            System.err.println("Error reading customisations.json: " + e.getMessage());
            return 600;
        }
    }

    /**
    * Will read the customisations.json file and return the custom width of the application
    * @return An integer containing the width of the application
    */
    public int getWidth(){
        try{
            // read the file as a string and then return the height to the application
            String fileContent = Files.readString(customisationsFilePath);
            JSONObject customisationsJson = new JSONObject(fileContent);
            return Integer.parseInt(customisationsJson.getString("width"));
        }
        catch(Exception e){
            // if there is an error then return the default height
            System.err.println("Error reading customisations.json: " + e.getMessage());
            return 600;
        }
    }

    /**
     * This function will return a list of all of the users in the users.json file
     * @return An arrayList containing JSONObjects of all of the registered users.
     */
    public ArrayList<JSONObject> getListOfUsers(){

        ArrayList<JSONObject> users = new ArrayList<JSONObject>();

        try{
            // read the file as a string and then return the height to the application
            String fileContent = Files.readString(usersFilePath);
            JSONObject usersFile = new JSONObject(fileContent);

            for (String key: usersFile.keySet()){
                JSONObject user = usersFile.getJSONObject(key);
                users.add(user);
            }

            return users;
        }
        catch(Exception e){
            // if there is an error then return the default height
            System.err.println("Error reading users.json: " + e.getMessage());
        }

        return null;
    }

    /**
     * This function will return the currentStockID
     * @return An integer containing the currentStockID
     */
    public int getCurrentStockID(){
        try{
            // read the file as a string and then return the height to the application
            String fileContent = Files.readString(sysfilesFilePath);
            JSONObject sysfilesJson = new JSONObject(fileContent);
            return sysfilesJson.getInt("stockID");
        }
        catch(Exception e){
            // if there is an error then return the default height
            System.err.println("Error reading sysfiles.json: " + e.getMessage());
            return 0;
        }
    }

    /**
     * Get a specific stock item by its StockID
     * @param id The id of the stock we wish to retrieve
     * @return A JSONObject containing the stock item
     */
    public JSONObject getStockByID(int id){
        try{
            // read the file as a string and then return the height to the application
            String fileContent = Files.readString(stockFilePath);
            JSONObject stockJson = new JSONObject(fileContent);
            return stockJson.getJSONObject(String.valueOf(id));
        }
        catch(Exception e){
            // if there is an error then return the default height
            System.err.println("Error reading stock.json: " + e.getMessage());
            return null;
        }
    }



      /*
       * ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       * END OF THE FILE READING SCRIPTS
       * ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       */

       /*
        * ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        * START OF THE FILE WRITING SCRIPTS
        * ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        */

    /**
     * Will add a new user to the file users.json
     * @param username The username of the user
     * @param password The hashed password of the user
     * @param fullName The full name of the user
     * @param manager Boolean saying whether the user is a manager or not
     */
    public void addUser(String username, String password, String fullName, boolean manager){
        try{
            // read the file as a string
            String fileContent = Files.readString(usersFilePath);
            JSONObject usersJson = new JSONObject(fileContent);

            // now create a new json object containing the new user
            JSONObject newUser = new JSONObject();
            newUser.put("username", username);
            newUser.put("password", password);
            newUser.put("fullName", fullName);
            newUser.put("manager", manager);

            // add to the existing json object
            usersJson.put(username, newUser);

            // write to the file directly
            Files.write(usersFilePath, usersJson.toString(4).getBytes());
            
        }
        catch(Exception e){
            // if there is an error then return the default height
            System.err.println("Error writing to users.json: " + e.getMessage());
        }
    }

    /**
     * Read and increment the StockID count in the sysfiles.json file
     */
    public void incrementStockID(){
        try{
            // read the file as a string
            String fileContent = Files.readString(sysfilesFilePath);
            JSONObject sysfilesJson = new JSONObject(fileContent);

            // increment StockID
            int StockID = sysfilesJson.getInt("stockID");
            StockID++;

            // update the JSON field
            sysfilesJson.put("stockID", StockID);

            // write to the file directly
            Files.write(sysfilesFilePath, sysfilesJson.toString(4).getBytes());
            
        }
        catch(Exception e){
            // if there is an error then return the default height
            System.err.println("Error incremening StockID in sysfiles.json: " + e.getMessage());
        }
    }

    /**
     * Read and increment the ReceiptID count in the sysfiles.json file
     */
    public void incrementReceiptID(){
        try{
            // read the file as a string
            String fileContent = Files.readString(sysfilesFilePath);
            JSONObject sysfilesJson = new JSONObject(fileContent);

            // increment ReceiptID
            int StockID = sysfilesJson.getInt("receiptID");
            StockID++;

            // update the JSON field
            sysfilesJson.put("receiptID", StockID);

            // write to the file directly
            Files.write(sysfilesFilePath, sysfilesJson.toString(4).getBytes());
            
        }
        catch(Exception e){
            // if there is an error then return the default height
            System.err.println("Error incremening ReceiptID in sysfiles.json: " + e.getMessage());
        }
    }

    /**
     * Add a new item to the stock.json file
     * id will be managed by the sysfiles
     * @param cost The cost of the givenitem
     * @param count Allow the user to initialise with a count
     * @param name Set the name of the given item
     */
    public void addStock(int cost, int count, String name){

        // get the current stockID
        int stockID = getCurrentStockID();

        // create the new stock item
        JSONObject newStock = new JSONObject();
        newStock.put("cost", cost); // cost
        newStock.put("count", count); // count
        newStock.put("name", name); // name 
        newStock.put("id", stockID);

        // Need to add a check here to find out if stock item being added is duplicate
        
        try {
            // read the file as a string
            String fileContent = Files.readString(stockFilePath);
            JSONObject stock = new JSONObject(fileContent);

            // add the new stock to the file
            stock.put(String.valueOf(stockID), newStock);

            // write to the file directly
            Files.write(stockFilePath, stock.toString(4).getBytes());

            // stock successfully added increment the stockID
            incrementStockID();
        }
        catch(Exception e){
            // if there is an error then return the default height
            System.err.println("Error adding to the stock.json file: " + e.getMessage());
        }
        
    }
}