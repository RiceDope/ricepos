# Welcome to RicePos

*Login as manager for functionality*

The aim of the project is to create a rough point of sale system. This is my first attempt with using OpenJfx and Maven. Any important messages will be displayed at the top of this README in *italics*.

## Notices
- The project creates a file in your *user.home* directory called *riceposfiles* you may delete this file as and when you wish. It creates this any time you run the project.
- The default password for defaultemployee and default manager is *password*.

## Running the project
*This may be simple to some but for me this took a while to get my head around so here goes*

The following are all cmd commands to be run in the root directory of the project *ricepos*

### Old way of compiling
```
mvn package

java --module-path "C:/Program Files/Java/javafx-sdk-21.0.1/lib" --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.base -cp target/ricepos-1.0-SNAPSHOT.jar com.rhyswalker.ricepos.App
```
### New way
The new way uses a plugin called *Maven-Shade-Plugin* in order to package the dependencies inside of the jar. Here are the steps to run.
```
// creating the executable
mvn clean
mvn compile
mvn package

// execution
mvn exec:java
```

## Your dependencies
Unfortunately while this is in development if you want to test the program you will need some software on your computer to run this.

In order to run the project you will need maven installed on your PC. I have linked a five minute setup guide at the bottom of the page.

*You shouldnt need to have jfx installed Maven should handle all of this for you. Should compilation fail then install jfx and change some of the directory names to match your given pc.*

## Project Breakdown

So now that the groundwork has been laid I can begin work on the actuall software and development. Below I will list all of the different screens that need to be developed as well as features for some of those screens and scripts.

For future reference the goal for users is that when the project is downloaded and started there will be a setup user. This is a standardised username and password to allow the manager to setup the software. From there multiple users can be created and then an actual manager can be added and setup user removed for security.

For reference and an explanation of how the project works and runs then check projectbreakdown.md.

*Some of these files have not been created yet. If you can't see them treat this as a future plans list.*

### App.java
This is the "main" script of the whole project. It will first of all open a json file and from there load all custom settings and launch the Login.java file.

### Pos.java
This is the till screen this is responsible for processing payments adding items to the basket and so on.

### Login.java
This is the login screen for the project. This is the point at which the program decides wether you are a Manager or an Employee.

### StockManagement.java
This is a screen specifically for users that are registered as a manager. If you are you can edit the products on sale adjust stock counts change prices etc..

### UserManagement.java
This is again only accessible to management users. Here you can manage different employees, reset passwords and check reports on sales that have gone through the till.

### Reports.java
This will contain all the information on sales as well as custom reports that can be generated.

### User.java
This is going to be a custom datatype that will store data about the user we currently have logged into the system.

### FileManagement.java
This is a class that will deal with primarily file access and json reading and writing. For now it will focus on opening and closing the users file which will contain basic information "username", "fullName", "manager", "password" - password will be a hashedString

### Hash.java
This contains the functions required in order to implement a SHA-256 hash. The code in here was developed by *(https://www.geeksforgeeks.org/sha-256-hash-in-java/)* all credit to them. Would not have been able to do this without that.

### users.json
This is the file containing all of the users data and information. Currently stores in the home directory.

### customisation.json
This is the file containing all custom settings that will be applied globaly throughout the application. Window size is the first one that I will be adding.

### sysfiles.json
This will track data like current receiptID current stockID. Just variables that the system will need to track

### stock.json
This is the file that contains all of the details on stock that can be processed. (Count, id, Cost, Name)

### purchases.json
This is the file that will count all of the purchases that have been made. (ReceiptID, itemsInTransaction{id, amountSold}, transactionValue, tenderType, beenRefunded)

### refunds.json
This is the file that will count all of the refunds that have been processed through the till (purchases.ReceiptId, reason(Of specified list))

## Recent Updates
- Added groundwork for StockManagement.java, UserManagement.java and Reports.java
- FileManagement.java has been updated to include stock.json and sysfiles.json
- Can now add a stock item to stock.json
- Can increment values inside of sysfiles.json
- Can search for a stock item by its ID
- Added errors.md

## Known issues
- Need to add check to make sure that dupilcate names cannot be inputed. Despite having unique IDs they need to have Unique names to make searching by name possible.
- Description needs to be added to stock.json to allow differentiation between names of similar type.

## Links
These are links to sites that helped me while developing the project or software that you need to run the program.

### Requirements
- https://openjfx.io/
- https://www.oracle.com/java/technologies/downloads/
- https://maven.apache.org/

### Links that have helped me
- https://docs.oracle.com/javafx/2/layout/builtin_layouts.htm
- https://maven.apache.org/what-is-maven.html
- https://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html
- https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html
- https://mvnrepository.com/
- https://java-decompiler.github.io/
- https://www.geeksforgeeks.org/sha-256-hash-in-java/
- https://java-decompiler.github.io/


# Notes

- This is technically my second attempt at this program. The first attempt didn't use Maven and I wanted to start again doing things in the correct way. Code from the previous attempt will be reused.

- Please if you have any advice on improvement to the code and general layout then let me know.

- To get the project to the state it is in now has taken many hours of things not compiling and constant crashes. Now the project is in a state where I am happy with its progress.

- Big thanks to the java decompiler project which once again helped me with checking the .jar files and finding out why things were not working.

- Thanks to geeksforgeeks.com for help with the code for Hash.java. I would not have been able to create that without the source code from their website check the links section for the page.