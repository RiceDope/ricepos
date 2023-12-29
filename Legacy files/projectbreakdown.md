# Rice Pos breakdown

In this document I hope to explain the complete workings of the software and the order that things are done in. This could be slightly inaccuracte but I hope for a general guide as to how things work this should work as a good reference point.

## Runthrough of the program

First when running ```mvn exec:java``` what happens well first of all it launches the App.java module. This is responsible for the general running of the whole application.

The code first starts by accessing the fileManager (FileManagement.java) this runs through checking that all of the files are in the right place and exist before, if they don't then creating them, before the project continues.

Next it access the fileManager again and sets the height and width of the screen from the customisation.json file (This contains any custom parameters, more will be added shortly).

We then instantiate the login screen (All other screens are created at a different time as a user needs to be set before any of these work). Then we show the login screen.

When the login screen is instantiated it adds in all of its objects to the screen. From there the user then enters their username and password (Nothing telling them they could be wrong outputs yet). When the login button is clicked it sends a request back to the App.java class to run the loginClicked function.

Firstly this accesses the fileManager one more time to get a list of all users and their hashed passwords (If I had access to a db I would do it with SQL). It then iterates over all of the users in this list. If we come to a user that matches our user then we set the user variable to be this JSONObject.

Next if we found a user then we hash the password that was entered. If it matches the password hash on file then we have successfully validated the user. We create a User object called currentUser, this tracks basic details like whether they are a manager or employee, usernames and fullNames.

Then the Pos screen is created and launched. Here we check to see if a currentUser is set inside of App.java if it is then we continue to check whether they are a manager or employee. We create the screen and add options accordingly.

The only options listed currently don't do anything and are just there as placeholders. Logout does work however it sets the currentUser variable to null and load the login screen.

This is the project as a whole so far and this is as much for someone elses reference as my own. I am aware that there are certainly security issues here but this is a proof of concept as I am learning ui design with java. Any improvements would be greatly accepted however. Hope this helps with explaining the structure to anyone that was curious or if someone is learning how to use javafx, maven and file access using java.