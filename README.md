# Welcome to the "ricepos" project

This is the current V1.0 release of ricepos. Ricepos is a basic point of sale system that would allow a user to manage a small number of users and manually input stock that can be sold through the till. There are some basic functions that "managers" can run as well as an "employee" login which can be used. Please do checkout the application. It is still being worked on but is now at a stage where I would consider the *core* functions to have been implemented.

*This is a project for fun and to help me learn in the area of graphical application development please do not take it too seriously*

## Running the project
*This is basic but would've helped me when I was working on this project.*

### PLEASE HAVE MAVEN INSTALLED ON YOUR SYSTEM

*Run in cmd where you would like to install the project*
```
// pulling the repository
git clone https://github.com/RiceDope/ricepos.git
cd ricepos

// creating the executable
mvn clean compile package

// execution
mvn exec:java
```

## General notes
- The application will make a directory inside of "C:/Users/yourusername/riceposfiles" *Or whereever you have set your home directory*

- For setup of additional users and employees please login using "admin", "password". The admin account cannot be changed for now.

- Any questions please feel free to ask.

## Recent updates
- This is release 1.0 and nothing has been changed as of now.

## Future additions
- CSS to improve styling and look over the whole application
- Description needs to be added to stock.json to allow differentiation between names of similar type. (Toaster, nice toaster) needs a descriptions to differentiate correctly.
- Track which user has made transactions or refunded, removed stock.
- A removed stock tab which will show us which stock has been removed

## Known issues
- Input needs to be validity tested when adding stock and updating stock. Currently relying on user to do things correctly. *Error if inputs are text*
- Usernames need to be unique (Will add)
- If username is changed to the username of a person that already exists then it will overwrite the existing user causing problems
- NoSuchAlgorithmException needs to be organised to work correctly 100%
- General Code Cleanliness needs to be updated to be maintainable

## Links
*These are links that have helped me while developing the project or things that are required to run the project*

- https://openjfx.io/
- https://www.oracle.com/java/technologies/downloads/
- https://maven.apache.org/
- https://docs.oracle.com/javafx/2/layout/builtin_layouts.htm
- https://maven.apache.org/what-is-maven.html
- https://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html
- https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html
- https://mvnrepository.com/
- https://java-decompiler.github.io/
- https://www.geeksforgeeks.org/sha-256-hash-in-java/
- https://java-decompiler.github.io/
- https://jenkov.com/tutorials/javafx/tabpane.html