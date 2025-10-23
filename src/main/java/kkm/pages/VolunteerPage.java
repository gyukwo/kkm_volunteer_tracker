package kkm.pages;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class VolunteerPage {
    // Function to create the volunteer page
    public static Scene createVolunteerPage(Stage primaryStage, String userName, boolean isSignedIn, String signInTime) {
        // Create the main layout for the page
        VBox vbox = new VBox(20); // Space between components
        vbox.setAlignment(javafx.geometry.Pos.CENTER);

        // Welcome message and dynamic user information
        Text welcomeText = new Text("Welcome, " + userName + "!");
        welcomeText.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        
        String signInStatus = isSignedIn ? "You are currently signed in since " + signInTime : "You are currently NOT signed in";
        Text statusText = new Text(signInStatus);
        statusText.setStyle("-fx-font-size: 14px;");
        
        // Buttons for navigation
        Button dailyEventsButton = new Button("Daily Events");
        Button totalHoursButton = new Button("Total Hours");
        Button signOutButton = new Button("Sign Out");

        // Action for the buttons (example)
        dailyEventsButton.setOnAction(e -> {
            // Logic to navigate to daily events page (can use a method like loadDailyEvents())
            System.out.println("Navigating to Daily Events...");
        });
        
        totalHoursButton.setOnAction(e -> {
            // Logic to navigate to total hours page (can use a method like loadTotalHours())
            System.out.println("Navigating to Total Hours...");
        });

        signOutButton.setOnAction(e -> {
            // Logic for signing out (can use a method like signOutUser())
            System.out.println("Signing out...");
        });

        // Back button to navigate to the main menu
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            // Logic for going back to the previous page or main menu
            System.out.println("Navigating back to the main menu...");
        });

        // Add all components to the VBox layout
        vbox.getChildren().addAll(backButton, welcomeText, statusText, dailyEventsButton, totalHoursButton, signOutButton);

        // Set up the scene and the stage
        Scene scene = new Scene(vbox, 400, 300);
        return scene;  // Return the scene to be set on the stage
    }

    // Sample method to simulate the sign-out action
    public static void signOutUser() {
        // Logic to update the database and handle sign-out action
        System.out.println("User signed out.");
    }

    // Sample method to simulate loading daily events
    public static void loadDailyEvents() {
        // Logic to display daily events from the database
        System.out.println("Loading daily events...");
    }

    // Sample method to simulate loading total volunteer hours
    public static void loadTotalHours() {
        // Logic to display total volunteer hours from the database
        System.out.println("Loading total hours...");
    }
    
}
