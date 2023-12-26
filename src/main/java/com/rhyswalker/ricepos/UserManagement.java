package com.rhyswalker.ricepos;

/**
 * This class handles the userManagement screen.
 * This screen allows the manager to add, edit and remove users from the system.
 * 
 * @author Rhys Walker
 * @version 0.5
 * @since 2023-12-26
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import java.security.NoSuchAlgorithmException;



public class UserManagement {

    private App app;
    private BorderPane borderPane;

    public UserManagement(App app) throws NoSuchAlgorithmException{
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
     * Force a refresh of the screen to update contents
     */
    private void forceRefresh() throws NoSuchAlgorithmException{
        borderPane.setCenter(createCentralOptions());
    }

    /**
     * Creates the default screen when accessing the reports screen
     */
    private void createDefaultScreen() throws NoSuchAlgorithmException{

        // initialise the borderpane object
        borderPane = new BorderPane();

        // fill the border pane with choices
        borderPane.setTop(createTitle());
        borderPane.setLeft(temporaryButton());
        borderPane.setCenter(createCentralOptions());
    }

    /**
     * Creates the central options for the user management screen
     * @return TabPane containing the options
     */
    private TabPane createCentralOptions() throws NoSuchAlgorithmException{
        // create all the tabs and set them to not be closeable
        Tab allUsers = new Tab("All Users", createAllUsersTabContents());
        allUsers.setClosable(false);
        Tab addUser = new Tab("Add User", createAddUserTabContents());
        addUser.setClosable(false);
        Tab editUser = new Tab("Edit User", createEditUserTabContents());
        editUser.setClosable(false);
        Tab removeUser = new Tab("Remove User", createRemoveUserTabContents());
        removeUser.setClosable(false);

        // create the tab pane
        TabPane tabPane = new TabPane(allUsers, addUser, editUser, removeUser);

        return tabPane;
    }

    private VBox createRemoveUserTabContents(){

        // instructions for removing an item
        Text instructions = new Text("To remove a user enter the username");
        TextFlow instructionsFlow = new TextFlow(instructions); // allows text to wrap
        HBox instructionsBox = new HBox(instructionsFlow);
        instructionsFlow.prefWidthProperty().bind(instructionsBox.widthProperty()); // sets the wrap to size of hbox

        // the find stock options
        Label usernameText = new Label("Username: ");
        TextField username = new TextField();
        Label confirmText = new Label("Confirm");
        CheckBox confirm = new CheckBox();
        HBox findStockOptions = new HBox(usernameText, username, confirmText, confirm);

        // create the button that will confirm the changes
        Button submitChanges = new Button("Remove item");
        /*
         * This function removes the item from the file based on the users input. They must choose either stockID or stockName not both
         * Then we submit a page refresh should all of this be done correctly.
         */
        submitChanges.setOnAction(e -> {

            // check if the user has confirmed the removal
            if (confirm.isSelected() == true && !username.getText().equals("admin") && !username.getText().equals(app.getUser().getUsername())){ // make the user confirm before making changes

                // check if a username was entered
                if (username.getText().equals("")){ // no username entered
                    System.err.println("No username entered");
                    return;
                }
                // a username was entered
                else{
                    app.fileManagement.removeUser(username.getText());
                }
            }
            else{
                System.err.println("Please confirm you wish to remove the user");
                System.err.println("Do not remove admin or yourself");
                return;
            }

            // now refresh the screen
            try{
                forceRefresh();
            }
            catch (Exception ex){
                System.err.println("Error refreshing screen, NoSuchAlgorithmException");
                ex.printStackTrace();
            }

        });
        HBox buttonBar = new HBox(submitChanges);

        // create the vbox to return
        VBox removeStockBox = new VBox(instructionsBox, findStockOptions, buttonBar);
        return removeStockBox;
    }

    /**
     * Creates the contents of the edit user tab
     * @return VBox containing the contents of the tab
     */
    private VBox createEditUserTabContents() throws NoSuchAlgorithmException{

        // text to explain how to edit the stock counts and values
        Text instructions = new Text("Select the user to edit and then change the values you wish to change. The manager box will always be updated so make sure you want to change it.");
        TextFlow instructionsFlow = new TextFlow(instructions); // allows text to wrap
        HBox instructionsBox = new HBox(instructionsFlow);
        instructionsFlow.prefWidthProperty().bind(instructionsBox.widthProperty()); // sets the wrap to size of hbox

        // the find user option
        Label usernameText = new Label("Username: ");
        TextField username = new TextField();
        HBox findUser = new HBox(usernameText, username);

        // the new values options
        Label newFullNameText = new Label("New full name: ");
        TextField newFullName = new TextField();
        Label isManagerText = new Label("Manager: ");
        CheckBox isManager = new CheckBox();
        HBox userValueOptions = new HBox(newFullNameText, newFullName, isManagerText, isManager);

        // the danger text
        Text dangerText = new Text("WARNING: you are about to edit a users login details. Please make sure you know what you are changing these details too as they cannot be reverted easily.");
        TextFlow dangerFlow = new TextFlow(dangerText); // allows text to wrap
        HBox dangerTextBox = new HBox(dangerFlow);

        // the danger zone
        Label newUsernameText = new Label("New username: ");
        TextField newUsername = new TextField();
        Label newPasswordText = new Label("New password: ");
        PasswordField newPassword = new PasswordField();
        HBox dangerZone = new HBox(newUsernameText, newUsername, newPasswordText, newPassword);

        // create the button that will confirm the changes
        Button submitChanges = new Button("Commit changes");
        Label confirmLabel = new Label("I confirm I have check the changes and wish to make them: ");
        CheckBox confirm = new CheckBox();

        /*
         * There are two sections to this function first there is the safe details to edit (Full name - completely safe) (Mangager - less safe but ok as not editing self)
         * Then the danger zone details (Username - less safe as could forget username and lock self out or change the the username of a user that already exists)
         * (Password - less safe as could forget password and lock self out)
         * User cannot change own details or admin details - admin is a special class that will be added for safety
         */
        submitChanges.setOnAction(e -> {

            // NoSuchAlgorithmException is thrown by the fileManagement class so we need to catch it
            try {

                if (confirm.isSelected() == true){ // make the user confirm before making changes

                    // Make sure user is not editing their own details or admins details
                    if (!username.getText().equals(app.getUser().getUsername()) && !username.getText().equals("admin")){

                        // SAFE DETAILS TO EDIT

                        // check if a username was entered
                        if (username.getText().equals("")){ // no username entered
                            System.err.println("No username entered");
                            return;
                        }
                        // a username was entered
                        else{
                            if (newFullName.getText().equals("")){ // no new full name entered
                                System.out.println("No new full name entered just updating manager status");
                                app.fileManagement.updateUser(username.getText(), app.fileManagement.getUsersFullName(username.getText()), isManager.isSelected());
                            }
                            else{
                                app.fileManagement.updateUser(username.getText(), newFullName.getText(), isManager.isSelected());
                            }       
                        }

                        // DANGER ZONE DETAILS

                        // check to make sure new username is not the same as old username
                        if (newUsername.getText().equals(username.getText())){
                            System.err.println("New username is the same as old username");
                            return;
                        }
                        else{
                            // check if a new username was entered
                            // update the username
                            if (!newUsername.getText().equals("") && newPassword.getText().equals("")){ // one if full one empty
                                // update username keeping password the same as before
                                app.fileManagement.updateUserLoginDetails(username.getText(), newUsername.getText(), app.fileManagement.getAllUsers().getJSONObject(username.getText()).getString("password"));
                            }
                            else{
                                System.out.println("No new username entered");
                            }

                            // check if a new password was entered
                            // update the password
                            if (!newPassword.getText().equals("") && newUsername.getText().equals("")){ // one is full one is empty
                                // update password keeping username the same as before
                                app.fileManagement.updateUserLoginDetails(username.getText(), app.fileManagement.getAllUsers().getJSONObject(username.getText()).getString("username"), newPassword.getText());
                            }
                            else{
                                System.out.println("No new password entered");
                            }

                            if (!newUsername.getText().equals("") && !newPassword.getText().equals("")){ // both are full
                                // update both username and password
                                app.fileManagement.updateUserLoginDetails(username.getText(), newUsername.getText(), newPassword.getText());
                            }
                            else{
                                System.out.println("Username and password not updated together");
                            }
                        }

                        

                    }
                    else{
                        System.err.println("Cannot edit your own details or the admin details");
                        return;
                    }
                }
                else{
                    System.err.println("Please confirm you wish to make the changes");
                    return;
                }

                // now refresh the screen
                forceRefresh();
            }
            catch (Exception ex){
                System.err.println("Error updating user details, NoSuchAlgorithmException");
                ex.printStackTrace();
            }
        
        });
        HBox buttonBar = new HBox(submitChanges, confirmLabel, confirm);

        // create the final vbox that will contain the two rows of objects
        VBox editStockBox = new VBox(instructionsBox, findUser, userValueOptions, dangerTextBox, dangerZone, buttonBar);
        return editStockBox;
    }

    /**
     * Creates the contents of the add user tab
     * @return VBox containing the contents of the tab
     */
    private VBox createAddUserTabContents() throws NoSuchAlgorithmException{

        // create all of the text fields needed for adding a product
        Label usernameText = new Label("Username:");
        TextField username = new TextField();
        Label fullNameText = new Label("Full name:");
        TextField fullName = new TextField();
        Label passwordText = new Label("Password");
        PasswordField password = new PasswordField();
        Label managerText = new Label("Manager:");
        CheckBox manager = new CheckBox();

        // create a hbox to hold these fields
        HBox userInputBar = new HBox(usernameText, username, fullNameText, fullName, passwordText, password, managerText, manager);

        // create a hbox to hold the add user button
        Button addButton = new Button("Add user");
        addButton.setOnAction(e -> { // set an event listener
            // add the product to the file
            app.fileManagement.addUser(username.getText(), password.getText(), fullName.getText(), manager.isSelected());

            try{
                // refresh the screen
                forceRefresh();
            }
            catch (Exception ex){
                System.err.println("Error refreshing screen, NoSuchAlgorithmException");
                ex.printStackTrace();
            }
        });
        HBox buttonBar = new HBox(addButton);

        // create the vbox to hold the hboxs
        VBox addUserBox = new VBox(userInputBar, buttonBar);
        return addUserBox;
    }

    /**
     * Create the contents of the all users tab
     * @return A Scroll Pane Containing all of the users
     */
    private ScrollPane createAllUsersTabContents(){

        // get all of the users from the fileManager
        JSONObject allUsers = app.fileManagement.getAllUsers();

        // create the VBox to store all of the users
        VBox allUsersBox = new VBox();

        // iterate over the users and create a HBox for each item that contains the details of each user
        for (String key : allUsers.keySet()){
            JSONObject item = allUsers.getJSONObject(key);

            // create the HBox
            HBox itemBox = new HBox();

            // create the text objects
            Text fullName = new Text("User full name: " + item.getString("fullName"));
            Text username = new Text(" Username: " + item.getString("username"));
            Text isManager = new Text(String.valueOf(" Manager: " + item.getBoolean("manager")));

            // add the text objects to the HBox
            itemBox.getChildren().addAll(fullName, username, isManager);

            // add the HBox to the VBox
            allUsersBox.getChildren().add(itemBox);
        }

        // create and return the scrollPane
        ScrollPane scrollPane = new ScrollPane(allUsersBox);
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
     * Creates a termporary home button to allow navigation back to the POS screenw
     * @return HBox containing the button
     */
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
