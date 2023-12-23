# Welcome to RicePos

*Currently the project can be compiled which is good news. Make sure you use the commands liseted below in the given order to execute. Check dependencies tab for things that you need installed in order to run the test version of the software.*

The aim of the project is to create a rough point of sale system. This is my first attempt with using OpenJfx and Maven. Any important messages will be displayed at the top of this README in *italics*.

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
java -cp target/ricepos-1.0-SNAPSHOT.jar com.rhyswalker.ricepos.App
```

## Your dependencies
Unfortunately while this is in development if you want to test the program you will need some software on your computer to run this.

In order to run the project you will need maven installed on your PC. I have linked a five minute setup guide at the bottom of the page.

*You shouldnt need to have jfx installed Maven should handle all of this for you. Should compilation fail then install jfx and change some of the directory names to match your given pc.*

## Project Breakdown

So now that the groundwork has been laid I can begin work on the actuall software and development. Below I will list all of the different screens that need to be developed as well as features for some of those screens and scripts.

*Some of these files have not been created yet. If you can't see them treat this as a future plans list.*

### App.java
This is the "main" script of the whole project. It will first of all open a json file and from there load all custom settings and launch the Login.java file.

### Pos.java
This is the till screen this is responsible for processing payments adding items to the basket and so on.

### Login.java
This is the login screen for the project. This is the point at which the program decides wether you are a Manager or an Employee.

### StockManagement.java
This is a screen specifically for users that are registered as a manager. If you are you can edit the products on sale adjust stock counts change prices etc..

### Management.java
This is again only accessible to management users. Here you can manage different employees, reset passwords and check reports on sales that have gone through the till.

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


# Notes

- This is technically my second attempt at this program. The first attempt didn't use Maven and I wanted to start again doing things in the correct way. Code from the previous attempt will be reused.

- Please if you have any advice on improvement to the code and general layout then let me know.

- To get the project to the state it is in now has taken many hours of things not compiling and constant crashes. Now the project is in a state where I am happy with its progress.

- Big thanks to the java decompiler project (https://java-decompiler.github.io/) which once again helped me with checking the .jar files and finding out why things were not working.