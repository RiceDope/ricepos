package com.rhyswalker.ricepos;

/**
 * Lays the groundwork for the login system. Allows the App.java to track which user is currently logged in.
 * Will have custom permision loaded from a json file at login.
 * 
 * @author Rhys Walker
 * @version 0.1
 * @since 2023-12-23
 */

public class User {
    
    // set up the fields that must be set when logging in
    private String username;
    private String fullName;
    private boolean manager;

    public User(String username, String fullName, boolean manager){
        // when the user has logged in App.java will have access to all of these variables and can set up the current user
        this.username = username;
        this.fullName = fullName;
        this.manager = manager;
    }

    /**
     * Returns the username of the current user
     * @return String containing the username
     */
    public String getUsername(){
        return username;
    }

    /**
     * Returns the full name of the current user
     * @return String containing the full name
     */
    public String getFullName(){
        return fullName;
    }

    /**
     * Returns whether the current user is a manager or not
     * @return true if is a manager, false if not
     */
    public boolean isManager(){
        return manager;
    }

}
