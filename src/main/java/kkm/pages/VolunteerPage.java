package kkm.pages;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import kkm.MainFrame;

public class VolunteerPage {


    // Static method to show the Volunteer Page
    public static void showVolunteerPage(int userId, Stage stage, String userName, final boolean isSignedIn, LocalDateTime signInTime) {
                    // Create the main layout for the page
                    VBox vbox = new VBox(20); // Space between components
                    vbox.setAlignment(javafx.geometry.Pos.CENTER);
            
                    // Welcome message and dynamic user information
                    Text welcomeText = new Text("Welcome, " + userName + "!");
                    welcomeText.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
            
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy hh:mm a");
                    String formatted = signInTime.format(formatter);
                    String signInStatus = isSignedIn ? "You are currently signed in since " + formatted: "You are currently NOT signed in";
                    Text statusText = new Text(signInStatus);
                    statusText.setStyle("-fx-font-size: 14px;");
                    
                    // Buttons for navigation
                    Button dailyEventsButton = new Button("Daily Events");
                    Button totalHoursButton = new Button("Total Hours");
                    Button signOutButton = new Button("Sign Out");
            
                    // Action for the buttons (example)
                    dailyEventsButton.setOnAction(e -> {
                        // Logic to navigate to daily events page
                        EventsPage.showDailyEvents(userId, stage, userName, isSignedIn, signInTime);
                        System.out.println("Loading daily events...");
                    });
                    
                    totalHoursButton.setOnAction(e -> {
                        HoursPage.showUserHours(userId, stage, userName, isSignedIn, signInTime);
                        System.out.println("Loading total hours...");
                    });
            
                    signOutButton.setOnAction(e -> {
                        MainFrame.loadMenu(stage);  // go back to main menu
                        isSignedIn = false;   // mark user as signed out
                });

        // Back button to navigate to the main menu
        Button backButton = new Button("Back");
        backButton.setOnAction(e ->  {
            // Logic for going back to the previous page or main menu
            MainFrame.loadMenu(stage);
            System.out.println("Navigating back to the main menu...");
        });

        // Add all components to the VBox layout
        vbox.getChildren().addAll(backButton, welcomeText, statusText, dailyEventsButton, totalHoursButton, signOutButton);

        // Set up the scene and the stage
        Scene scene = new Scene(vbox, 400, 300);
        stage.setScene(scene);
        stage.setTitle("Volunteer Page");
        stage.show(); // Show the page
    }

    // Sample method to simulate the sign-out action
    public static void signOutUser() {
        // Logic to update the database and handle sign-out action
        
        System.out.println("User signed out.");
    }
}