package com.rhyswalker.ricepos;

/**
 * App.java is the class responsible for the loading and unloading of all of the other classes.
 * It is responsible for the successfull boot of the application with all customised settings and such.
 * 
 * @author Rhys Walker
 * @version 0.5
 * @since 2023-12-24
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.security.NoSuchAlgorithmException;
import org.json.JSONObject;
import java.util.ArrayList;

public class App extends Application{

    // primary stage
    private Stage primaryStage;

    // different screens for access in any function
    private Pos posScreen;
    private Login loginScreen;
    private Reports reportsScreen;
    private UserManagement userManagementScreen;
    private StockManagement stockManagementScreen;

    // so we can access screen dimensions from anywhere
    private int height;
    private int width;

    // so we can access the scenes from anywhere
    private Scene loginScene;
    private Scene posScene;
    private Scene reportsScene;
    private Scene userManagementScene;
    private Scene stockManagementScene;
    

    // so we can access the file manager from anywhere
    public FileManagement fileManagement;

    // the current user object
    private User currentUser;

    public static void main( String[] args )
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws NoSuchAlgorithmException{ // need exception for hashing
        this.primaryStage = primaryStage;

        // create the file management object
        fileManagement = new FileManagement();

        // set the height and width properties will eventually be access from the pos_settings.json file
        height = fileManagement.getHeight();
        width = fileManagement.getWidth();

        // Create instances of the login screen all other will be created when needed
        loginScreen = new Login(this);

        // create the scenes
        createLoginScene();

        // Show the login screen initially
        showLoginScreen();
    }

    private User setUser(){

        // WILL SET THE USER ONCE LOGGED IN

        return null;
    }

    /**
     * A function that takes in the data from the login form and checks it against the user.json file
     * From there it will correctly call and format the pos screen for the type of user
     * 
     * @param username The username the user has entered
     * @param password The password the user has entered
     * @throws NoSuchAlgorithmException
     */
    public void loginClicked(String username, String password) throws NoSuchAlgorithmException{ // need exception for hashing

        // get the list of users from the file users.json
        ArrayList<JSONObject> users = fileManagement.getListOfUsers();

        JSONObject user = null;

        // iterate over the list of users to see if our user exists
        for (JSONObject givenUser : users){
            // if our username matches a username on file we have found our user
            if(givenUser.getString("username").equals(username)){
                user = givenUser;
            }
        }

        // if we found a user
        if (user != null){
            // hash the users password
            String hashedPassword = com.rhyswalker.ricepos.Hash.toHexString(com.rhyswalker.ricepos.Hash.getSHA(password));

            // check the passwords against eachother
            if (hashedPassword.equals(user.getString("password"))){
                System.out.println("Password correct for user " + username);

                // create the user object
                currentUser = new User(user.getString("username"), user.getString("fullName"), user.getBoolean("manager"));

                // create and show the scene
                posScreen = new Pos(this);
                createPosScene();
                showPosScreen();
            }
            else{
                // handle the incorrect password
                System.out.println("Password incorrect");
            }
        }
        else{
            // handle the user not found
            System.out.println("User not found");
        }
    }

    /*
     * Function that is called when the reports button is clicked
     */
    public void reportsButtonClicked(){
        reportsScreen = new Reports(this);
        createReportsScene();
        showReportsScreen();
    }

    /*
     * Function that is called when the user management button is clicked
     */
    public void userManagementButtonClicked(){
        try{
            userManagementScreen = new UserManagement(this);
            createUserManagementScene();
            showUserManagementScreen();
        }
        catch(NoSuchAlgorithmException e){
            System.out.println("Error hashing password. In creation of user management screen");
        }
        
    }

    /* 
     * Function that is called when the stock management button is clicked
     */
    public void stockManagementButtonClicked(){
        stockManagementScreen = new StockManagement(this);
        createStockManagementScene();
        showStockManagementScreen();
    }

    /*
     * Function that creates the login screen
     */
    private void createLoginScene() {
        loginScene = new Scene(loginScreen.getRoot(), width, height);
        loginScene.getStylesheets().add(getClass().getResource("/styles/login.css").toExternalForm());
    }

    /**
     * Function that creates the pos screen
     */
    private void createPosScene() {
        posScene = new Scene(posScreen.getRoot(), width, height);
        posScene.getStylesheets().add(getClass().getResource("/styles/pos.css").toExternalForm());
    }

    /*
     * Function that creates the reports screen
     */
    private void createReportsScene() {
        reportsScene = new Scene(reportsScreen.getRoot(), width, height);
        reportsScene.getStylesheets().add(getClass().getResource("/styles/reports.css").toExternalForm());
    }

    /*
     * Function that creates the user management screen
     */
    private void createUserManagementScene() {
        userManagementScene = new Scene(userManagementScreen.getRoot(), width, height);
        userManagementScene.getStylesheets().add(getClass().getResource("/styles/usermanagement.css").toExternalForm());
    }

    /*
     * Function that creates the stock management screen
     */
    private void createStockManagementScene() {
        stockManagementScene = new Scene(stockManagementScreen.getRoot(), width, height);
        stockManagementScene.getStylesheets().add(getClass().getResource("/styles/stockmanagement.css").toExternalForm());
    }

    
    /*
     * Functions that show the pos screen
     */
    public void showPosScreen() {
        primaryStage.setScene(posScene);
        primaryStage.setTitle("POS Screen");
        primaryStage.show();
    }

    /**
     * Function that shows the login screen
     */
    public void showLoginScreen() {
        primaryStage.setScene(loginScene);
        primaryStage.setTitle("Login Screen");
        primaryStage.show();
    }

    /**
     * Function that shows the reports screen
     */
    public void showReportsScreen() {
        primaryStage.setScene(reportsScene);
        primaryStage.setTitle("Reports Screen");
        primaryStage.show();
    }

    /*
     * Functions that show the user management screen
     */
    public void showUserManagementScreen() {
        primaryStage.setScene(userManagementScene);
        primaryStage.setTitle("User Management Screen");
        primaryStage.show();
    }

    /*
     * Functions that show the stock management screen
     */
    public void showStockManagementScreen() {
        primaryStage.setScene(stockManagementScene);
        primaryStage.setTitle("Stock Management Screen");
        primaryStage.show();
    }

    /**
     * Getter for the current user
     * @return A User object containing our current user
     */
    public User getUser(){
        return currentUser;
    }

    /**
     * Function to set the current user to null
     */
    public void setUserNull(){
        currentUser = null;
    }
}