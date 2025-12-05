package kkm.pages;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import kkm.MainFrame;
import kkm.Session;

public class VolunteerPage {

    public static final Font PAGE_HEADING_FONT   = new Font("Arial Rounded MT Bold", 34);
    public static final Font TABLE_HEADING_FONT = new Font("Arial", 40);
    public static final Font TABLE_BODY_FONT = new Font("Arial", 25);

    private static final String BACKGROUND_COLOR = "#E5F3FD";

    public static void showVolunteerPage(Stage stage) {
        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);

        String userName = Session.getUserName();
        LocalDateTime signInTime = Session.getSignInTime();

        Text welcomeText = new Text("Welcome, " + (userName == null ? "" : userName) + "!");
        welcomeText.setFont(PAGE_HEADING_FONT);
        welcomeText.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy hh:mm a");
        String formatted = (signInTime == null) ? "-" : signInTime.format(formatter);

        String signInStatus = Session.isSignedIn()
                ? "You are currently signed in since " + formatted
                : "You are currently NOT signed in for an event";
        Text statusText = new Text(signInStatus);
        statusText.setFont(TABLE_BODY_FONT);
        statusText.setStyle("-fx-font-size: 14px;");

        Button dailyEventsButton = new Button("Daily Events");
        dailyEventsButton.setOnAction(e -> EventsPage.showDailyEvents(stage));

        Button totalHoursButton = new Button("Total Hours");
        totalHoursButton.setOnAction(e -> HoursPage.showUserHours(stage));

        Button signOutButton = new Button("Sign Out");
        signOutButton.setOnAction(e -> {
            Session.signOut();
            MainFrame.loadMenu(stage);
        });

        vbox.getChildren().addAll(welcomeText, statusText,
                dailyEventsButton, totalHoursButton, signOutButton);
        vbox.setStyle("-fx-background-color: " + BACKGROUND_COLOR + ";");


        stage.setScene(new Scene(vbox, 800, 600));
        stage.setTitle("Volunteer Page");
        stage.show();
    }
}