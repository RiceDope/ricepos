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

        // json object to contain all of the data for the default user
        JSONObject defaultManager = new JSONObject();
        defaultManager.put("username", "defaultmanager");
        defaultManager.put("password", "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8"); // default password is password
        defaultManager.put("fullName", "Default Manager");
        defaultManager.put("manager", true);

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
            // read the file as a string and then return the height to the application
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
}
